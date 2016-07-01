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
package cz.muni.fi.mir.mathmlunificator.config;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Various constants definitions used by MathML Unificator.
 *
 * @author Michal Růžička
 */
public class Constants {

    /**
     * MathML XML namespace.
     */
    public static final String MATHML_NS = "http://www.w3.org/1998/Math/MathML";

    /**
     * MathML XML root element name.
     */
    public static final String MATHML_ROOT_ELEM = "math";

    /**
     * Presentation MathML operator element name.
     */
    public static final String PMATHML_OPERATOR = "mo";

    /**
     * Presentation MathML identifier element name.
     */
    public static final String PMATHML_IDENTIFIER = "mi";

    /**
     * Content MathML symbol element name.
     */
    public static final String CMATHML_SYMBOL = "csymbol";

    /**
     * Content MathML identifier element name.
     */
    public static final String CMATHML_IDENTIFIER = "ci";

    /**
     * MathML Unificator XML markup namespace.
     */
    public static final String UNIFIED_MATHML_NS = "http://mir.fi.muni.cz/mathml-unification/";

    /**
     * MathML Unificator XML markup namespace prefix.
     */
    public static final String UNIFIED_MATHML_NS_PREFIX = "uni";

    /**
     * MathML Unificator XML root element name for batch processing output.
     * documents
     */
    public static final String UNIFIED_MATHML_BATCH_OUTPUT_ROOT_ELEM = "unified-math-batch";

    /**
     * MathML Unificator XML item element name for batch processing output.
     * documents
     */
    public static final String UNIFIED_MATHML_BATCH_OUTPUT_ITEM_ELEM = "unified-math-batch-item";

    /**
     * MathML Unificator XML name of input file attribute for batch processing
     * output item.
     */
    public static final String UNIFIED_MATHML_BATCH_OUTPUT_ITEM_FILEPATH_ATTR = "input-file";

    /**
     * MathML Unificator XML root element name.
     */
    public static final String UNIFIED_MATHML_ROOT_ELEM = "unified-math";

    /**
     * MathML Unification current unification level XML attribute.
     */
    public static final String UNIFIED_MATHML_LEVEL_ATTR = "unification-level";

    /**
     * MathML Unification number of unification levels XML attribute.
     */
    public static final String UNIFIED_MATHML_MAX_LEVEL_ATTR = "unification-max-level";

    /**
     * Symbol used in MathML Unificator XML markup for XML tree substitutions of
     * Presentation MathML nodes.
     */
    public static final String PMATHML_UNIFICATOR = "\u25CD";

    /**
     * Symbol used in MathML Unificator XML markup for XML tree substitutions of
     * Content MathML nodes.
     */
    public static final String CMATHML_UNIFICATOR = "\u25D0";

    /**
     * Names of Content MathML identifier and number elements.
     *
     * See the list of
     * <a href="https://www.w3.org/TR/MathML2/appendixc.html">known Content
     * MathML elements</a>.
     */
    public static final Set<String> CMATHML_IDENTIFIER_OR_NUMBER
            = Stream.of(
                    "ci",
                    "cn"
            ).collect(Collectors.toCollection(HashSet::new));

    /**
     * Names of Content MathML annotation-like elements.
     *
     * See the list of
     * <a href="https://www.w3.org/TR/MathML2/appendixc.html">known Content
     * MathML elements</a>.
     */
    public static final Set<String> CMATHML_ANNOTATIONS
            = Stream.of(
                    "semantics",
                    "annotation",
                    "annotation-xml"
            ).collect(Collectors.toCollection(HashSet::new));

    /**
     * Set of <a href="https://www.w3.org/TR/MathML2/appendixb.html">names of
     * known Content MathML elements</a>.
     */
    public static final Set<String> CMATHML_ELEMENTS
            = Stream.of(
                    "abs",
                    "and",
                    "apply",
                    "approx",
                    "arccos",
                    "arccosh",
                    "arccot",
                    "arccoth",
                    "arccsc",
                    "arccsch",
                    "arcsec",
                    "arcsech",
                    "arcsin",
                    "arcsinh",
                    "arctan",
                    "arctanh",
                    "arg",
                    "bvar",
                    "card",
                    "cartesianproduct",
                    "ceiling",
                    "ci",
                    "cn",
                    "codomain",
                    "complexes",
                    "compose",
                    "condition",
                    "conjugate",
                    "cos",
                    "cosh",
                    "cot",
                    "coth",
                    "csc",
                    "csch",
                    "csymbol",
                    "curl",
                    "declare",
                    "degree",
                    "determinant",
                    "diff",
                    "divergence",
                    "divide",
                    "domain",
                    "domainofapplication",
                    "emptyset",
                    "eq",
                    "equivalent",
                    "eulergamma",
                    "exists",
                    "exp",
                    "exponentiale",
                    "factorial",
                    "factorof",
                    "false",
                    "floor",
                    "fn",
                    "forall",
                    "gcd",
                    "geq",
                    "grad",
                    "gt",
                    "ident",
                    "image",
                    "imaginary",
                    "imaginaryi",
                    "implies",
                    "in",
                    "infinity",
                    "int",
                    "integers",
                    "intersect",
                    "interval",
                    "inverse",
                    "lambda",
                    "laplacian",
                    "lcm",
                    "leq",
                    "limit",
                    "list",
                    "ln",
                    "log",
                    "lowlimit",
                    "lt",
                    "matrix",
                    "matrixrow",
                    "max",
                    "mean",
                    "median",
                    "min",
                    "minus",
                    "mode",
                    "moment",
                    "momentabout",
                    "naturalnumbers",
                    "neq",
                    "not",
                    "notanumber",
                    "notin",
                    "notprsubset",
                    "notsubset",
                    "or",
                    "otherwise",
                    "outerproduct",
                    "partialdiff",
                    "pi",
                    "piece",
                    "piecewise",
                    "plus",
                    "power",
                    "primes",
                    "product",
                    "prsubset",
                    "quotient",
                    "rationals",
                    "real",
                    "reals",
                    "reln",
                    "rem",
                    "root",
                    "scalarproduct",
                    "sdev",
                    "sec",
                    "sech",
                    "selector",
                    "sep",
                    "set",
                    "setdiff",
                    "sin",
                    "sinh",
                    "subset",
                    "sum",
                    "tan",
                    "tanh",
                    "tendsto",
                    "times",
                    "transpose",
                    "true",
                    "union",
                    "uplimit",
                    "variance",
                    "vector",
                    "vectorproduct",
                    "xor"
            ).collect(Collectors.toCollection(HashSet::new));

}
