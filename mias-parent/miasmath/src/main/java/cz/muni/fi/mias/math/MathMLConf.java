/*
 * Copyright 2016 MIR@MU Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mias.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.muni.fi.mias.MIaSError;

/**
 * Helper class containing static MathML configuration
 *
 * @author Martin Liska
 */
public class MathMLConf {

    private static final Logger LOG = LogManager.getLogger(MathMLConf.class);

    private static final List<String> ignoreNode = Arrays.asList("semantics", "annotation-xml");
    private static final List<String> ignoreAll = Arrays.asList("annotation");

    private static final List<String> presentationElements = Arrays.asList("mi", "mn", "mo", "mtext", "mspace", "ms", "mglyph", "mrow", "mfrac", "msqrt", "mroot", "mstyle", "merror",
            "mpadded", "mphantom", "mfenced", "menclose", "msub", "msup", "msubsup", "munder", "mover", "munderover", "mmultiscripts", "mtable", "mlabeledtr", "mtr", "mtd");

    private static final List<String> contentElements = Arrays.asList("ci", "cn", "csymbol", "apply", "cs", "bind", "bvar", "share", "cerror", "cbytes", "set", "domainofapplication",
            "interval", "condition", "lowlimit", "uplimit", "degree", "momentabout", "logbase", "union", "piecewise", "piece", "otherwise", "reln", "fn", "declare", "ident",
            "domain", "codomain", "image", "ln", "log", "moment", "lambda", "compose", "quotient", "divide", "minues", "power", "rem", "root", "factorial", "abs", "conjugate",
            "arg", "real", "imaginary", "floor", "ceiling", "exp", "max", "min", "plus", "times", "gcd", "lcm", "and", "or", "xor", "not", "implies", "equivalent", "forall",
            "exists", "eq", "gt", "lt", "geq", "leq", "neq", "approx", "factorof", "tendsto", "int", "diff", "partialdiff", "divergence", "grad", "curl", "laplacian", "set",
            "\\list", "union", "intersect", "cartesianproduct", "in", "notin", "notsubset", "notprsubset", "setdiff", "subset", "prsubset", "card", "sum", "product",
            "limit", "sin", "cos", "tan", "sec", "csc", "cot", "sinh", "cosh", "tanh", "sech", "csch", "coth", "arcsin", "arccos", "arctan", "arccosh", "arccot", "arccoth",
            "arccsc", "arccsch", "arcsec", "arccsch", "arcsec", "arcsinh", "arctanh", "mean", "sdev", "variance", "median", "mode", "vector", "matrix", "matrixrow",
            "determinant", "transpose", "selector", "vectorproduct", "scalarproduct", "outerproduct", "integers", "reals", "rationals", "naturalnumbers", "complexes",
            "primes", "emptyset", "exponentiale", "imaginaryi", "notanumber", "true", "false", "pi", "eulergamma", "infinity");

    private static final List<String> operatorElements = (Arrays.asList("mo", "times", "plus", "minus", "power", "log", "max", "min", "divide", "ln", "log", "lambda", "and", "or", "xor", "implies", "equivalen", "forall", "exists", "int"));

    public static final List<String> additiveOperators = (Arrays.asList("-", "\u2212", "\u2213", "\u2214", "\u2238", "\u2295", "\u2296", "\u229d", "\u229e", "\u229f", "plus", "minus"));

    public static final String timesOperators = "\u2062 \u00d7 \u00b7";

    public static final String MATHML_NAMESPACE_URI = "http://www.w3.org/1998/Math/MathML";

    /**
     * General modificator of weight coefficients of unified nodes.
     *
     * @see UnifiedFormulaValuator
     */
    public static final float unifiedNodeWeightCoefModifier = 0.1f;

    /**
     * Threshold of input node complexity (i.e. the number of subnodes of the
     * node tree) to process the node with MathML Unificator. Nodes with
     * complexity above this limit will not be processed.
     */
    public static final int inputNodeComplexityUnificationLimit = 2000;

    public static List<String> getPresentationElements() {
        return presentationElements;
    }

    public static List<String> getContentElements() {
        return contentElements;
    }

    /**
     * @return List of MathML nodes which should be ignored during the
     * processing
     */
    public static List<String> getIgnoreNode() {
        return ignoreNode;
    }

    /**
     * @return List of MathML nodes which should be ignored together with their
     * children during the processing
     */
    public static List<String> getIgnoreAll() {
        return ignoreAll;
    }

    /**
     * @return default dictionary for substituting standard MathML element names
     * for custom ones
     */
    public static Map<String, String> getElementDictionary() {
        Map<String, String> result = new HashMap<>();
        BufferedReader br = null;
        String resource = "element-dictionary";
        try {
            br = new BufferedReader(new InputStreamReader(MathMLConf.class.getResourceAsStream(resource)));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] entry = line.split("=");
                result.put(entry[0], entry[1]);
            }
        } catch (Exception e) {
            handleError("Cannot load element dictionary file", e);
        } finally {
            closeReader(br, resource);
        }
        return result;
    }

    /**
     * @return default dictionary for substituting standard MathML attribute
     * names and their values for custom ones
     */
    public static Map<String, String> getAttrDictionary() {
        Map<String, String> result = new HashMap<>();
        BufferedReader br = null;
        String resource = "attr-dictionary";
        try {
            br = new BufferedReader(new InputStreamReader(MathMLConf.class.getResourceAsStream(resource)));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] entry = line.split("=");
                result.put(entry[0], entry[1]);
            }
        } catch (Exception e) {
            handleError("Cannot load attribute dictionary file", e);
        } finally {
            closeReader(br, resource);
        }
        return result;
    }

    /**
     * @return Map of commutative operators and their priorities. The key is a
     * commutative operator and the value is a list of operators that have a
     * higher priority than the key
     */
    public static Map<String, List<String>> getOperators() {
        Map<String, List<String>> result = new HashMap<>();
        BufferedReader br = null;
        String resource = "operators";
        try {
            br = new BufferedReader(new InputStreamReader(MathMLConf.class.getResourceAsStream(resource)));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] op = line.substring(0, line.indexOf(";")).split(",");
                String[] ops = (line.substring(line.indexOf(";") + 1)).split(",");
                List<String> opsList = Arrays.asList(ops);
                for (String op1 : op) {
                    result.put(op1, opsList);
                }
            }
        } catch (Exception e) {
            handleError("Cannot load operators file", e);
        } finally {
            closeReader(br, resource);
        }
        return result;
    }

    public static boolean isIndexableContentElement(String s) {
        return getContentElements().contains(s) && !isOperatorElement(s);
    }

    public static boolean isIndexablePresentationElement(String s) {
        return getPresentationElements().contains(s) && !isOperatorElement(s);
    }

    public static boolean isIndexableElement(String s) {
        return isIndexableContentElement(s) || isIndexablePresentationElement(s);
    }

    public static boolean isOperatorElement(String s) {
        return operatorElements.contains(s);
    }

    public static boolean ignoreNode(String s) {
        return getIgnoreNode().contains(s);
    }

    public static boolean ignoreNodeAndChildren(String s) {
        return getIgnoreAll().contains(s);
    }

    private static void closeReader(Reader reader, String fileName) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            handleError("Cannot close " + fileName + " file", e);
        }
    }

    private static void handleError(String msg, Exception e) throws MIaSError {
        LOG.error(msg, e);
        throw new MIaSError(msg, e);
    }

}
