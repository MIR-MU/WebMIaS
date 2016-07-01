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
package cz.muni.fi.mir.mathmlunificator;

import static cz.muni.fi.mir.mathmlunificator.config.Constants.*;
import cz.muni.fi.mir.mathmlunificator.utils.DOMBuilder;
import static cz.muni.fi.mir.mathmlunificator.utils.XMLOut.xmlStdoutSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Command line tool that enables the user to select multiple input files and
 * print results of MathML unification by {@link MathMLUnificator} to the
 * application standard output.
 *
 * @author Michal Růžička
 */
public class MathMLUnificatorCommandLineTool {

    /**
     * Name of the JARFILE for prints in help.
     */
    private static final String JARFILE = "mathml-unificator.jar";

    /**
     * If <code>true</code> unify also operator nodes, otherwise keep operator
     * nodes intact.
     */
    private static boolean operatorUnification = false;

    /**
     * Main (starting) method of the command line application.
     *
     * @param argv Array of command line arguments that are expected to be
     * filesystem paths to input XML documents with MathML to be unified.
     * @throws ParserConfigurationException If a XML DOM builder cannot be
     * created with the configuration requested.
     */
    public static void main(String argv[]) throws ParserConfigurationException {

        final Options options = new Options();
        options.addOption("p", "operator-unification", false, "unify operator in addition to other types of nodes");
        options.addOption("h", "help", false, "print help");

        final CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, argv);
        } catch (ParseException ex) {
            printHelp(options);
            System.exit(1);
        }

        if (line != null) {
            if (line.hasOption('h')) {
                printHelp(options);
                System.exit(0);
            }
            operatorUnification = line.hasOption('p');

            final List<String> arguments = Arrays.asList(line.getArgs());
            if (arguments.size() > 0) {

                Document outerDocument = DOMBuilder.getDocumentBuilder().newDocument();
                Node rootNode = outerDocument.createElementNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_NS_PREFIX + ":" + UNIFIED_MATHML_BATCH_OUTPUT_ROOT_ELEM);
                outerDocument.appendChild(rootNode);

                for (String filepath : arguments) {
                    try {

                        Document doc = DOMBuilder.buildDocFromFilepath(filepath);
                        MathMLUnificator.unifyMathML(doc, operatorUnification);
                        if (arguments.size() == 1) {
                            xmlStdoutSerializer(doc);
                        } else {
                            Node itemNode = rootNode.getOwnerDocument()
                                    .createElementNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_NS_PREFIX + ":" + UNIFIED_MATHML_BATCH_OUTPUT_ITEM_ELEM);
                            Attr filenameAttr = itemNode.getOwnerDocument()
                                    .createAttributeNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_NS_PREFIX + ":" + UNIFIED_MATHML_BATCH_OUTPUT_ITEM_FILEPATH_ATTR);
                            filenameAttr.setTextContent(String.valueOf(filepath));
                            ((Element) itemNode).setAttributeNodeNS(filenameAttr);
                            itemNode.appendChild(rootNode.getOwnerDocument().importNode(doc.getDocumentElement(), true));
                            rootNode.appendChild(itemNode);

                        }

                    } catch (SAXException | IOException ex) {
                        Logger.getLogger(MathMLUnificatorCommandLineTool.class
                                .getName()).log(Level.SEVERE, "Failed processing of file: " + filepath, ex);
                    }
                }

                if (rootNode.getChildNodes().getLength() > 0) {
                    xmlStdoutSerializer(rootNode.getOwnerDocument());
                }

            } else {
                printHelp(options);
                System.exit(0);
            }
        }

    }

    /**
     * Print summary of command line arguments and usage examples.
     *
     * @param options Options recognized by the application.
     */
    private static void printHelp(Options options) {

        System.err.println("Usage:");
        System.err.println("\tjava -jar " + JARFILE + " [ -p ] input.xml [ input.xml ... ]");
        System.err.println("\tjava -jar " + JARFILE + " -h");
        System.err.println("Options:");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printOptions(new PrintWriter(System.err, true), 80, options, 8, 8);

    }

}
