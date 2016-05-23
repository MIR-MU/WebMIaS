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

import static cz.muni.fi.mir.mathmlunificator.config.Constants.MATHML_NS;
import cz.muni.fi.mir.mathmlunificator.utils.DOMBuilder;
import cz.muni.fi.mir.mathmlunificator.utils.XMLOut;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.*;
import org.junit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Michal Růžička
 */
public class MathMLUnificatorTest extends AbstractXMLTransformationTest {

    private final boolean[] trueFalseCollection = {true, false};

    @Test
    public void testUnifyMathML_Document() {
        try {

            DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();

            for (boolean operators : trueFalseCollection) {
                Document expectedDoc = docBuilder.parse(getExpectedXMLTestResource("multiple-formulae.document-unification.operators-" + operators));
                Document doc = docBuilder.parse(getInputXMLTestResource("multiple-formulae.document-unification"));

                MathMLUnificator.unifyMathML(doc, operators);

                System.out.println("testUnifyMathML_Document (operators " + operators + ") – output:\n" + XMLOut.xmlStringSerializer(doc));
                testXML(expectedDoc, doc);
            }

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testUnifyMathML_InputStream_OutputStream() throws Exception {

        try {

            DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();
            for (boolean operators : trueFalseCollection) {
                Document expectedDoc = docBuilder.parse(getExpectedXMLTestResource("multiple-formulae.document-unification.operators-" + operators));

                ByteArrayOutputStream osDoc = new ByteArrayOutputStream();
                MathMLUnificator.unifyMathML(getInputXMLTestResource("multiple-formulae.document-unification"), osDoc, operators);
                String doc = osDoc.toString();

                System.out.println("testUnifyMathML_InputStream_OutputStream (operators " + operators + ") – output:\n" + doc);
                testXML(expectedDoc, doc);
            }

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testUnifyMathMLNode() {
        try {

            DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();

            for (boolean operators : trueFalseCollection) {
                Document expectedDoc = docBuilder.parse(getExpectedXMLTestResource("single-formula.node-unification.operators-" + operators));
                Document doc = docBuilder.parse(getInputXMLTestResource("single-formula.node-unification"));

                NodeList nodeList = doc.getElementsByTagNameNS(MATHML_NS, "mfrac");
                assertEquals(1, nodeList.getLength());

                Node node = nodeList.item(0);
                MathMLUnificator.unifyMathMLNode(node, operators);

                System.out.println("testUnifyMathMLNode (operators " + operators + ") – output:\n" + XMLOut.xmlStringSerializer(doc));
                testXML(expectedDoc, doc);
            }

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testGetUnifiedMathMLNodes() {

        final String inputFile = "single-formula.node-unification";

        try {

            DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();

            for (boolean operators : trueFalseCollection) {

                // Expected collection
                HashMap<Integer, Node> expectedNodeList = new HashMap<>(4);
                Document node1 = DOMBuilder.buildDoc(getXMLTestResource("collection-of-unified-mathml-nodes.item-1.operators-" + operators));
                expectedNodeList.put(1, node1.getDocumentElement());
                Document node2 = DOMBuilder.buildDoc(getXMLTestResource("collection-of-unified-mathml-nodes.item-2.operators-" + operators));
                expectedNodeList.put(2, node2.getDocumentElement());
                Document node3 = DOMBuilder.buildDoc(getXMLTestResource("collection-of-unified-mathml-nodes.item-3.operators-" + operators));
                expectedNodeList.put(3, node3.getDocumentElement());
                if (operators) {
                    Document node4 = DOMBuilder.buildDoc(getXMLTestResource("collection-of-unified-mathml-nodes.item-4.operators-" + operators));
                    expectedNodeList.put(4, node4.getDocumentElement());
                }

                // Produced collection
                Document doc = docBuilder.parse(getInputXMLTestResource(inputFile));
                HashMap<Integer, Node> docNodeList = MathMLUnificator.getUnifiedMathMLNodes(doc.getDocumentElement(), operators);

                System.out.println("testGetUnifyMathMLNodes (operators " + operators + ") – output:");
                for (Node n : docNodeList.values()) {
                    XMLOut.xmlStdoutSerializer(n.getOwnerDocument());
                }

                assertEquals(expectedNodeList.keySet(), docNodeList.keySet());
                for (Integer i : expectedNodeList.keySet()) {
                    testXML("Different unification at level " + Integer.toString(i) + " (operators " + operators + ")",
                            expectedNodeList.get(i).getOwnerDocument(), docNodeList.get(i).getOwnerDocument());
                }

                System.out.println("testGetUnifyMathMLNodes (operators " + operators + ") – input document DOM after processing:\n" + XMLOut.xmlStringSerializer(doc));
                Document originalDoc = docBuilder.parse(getInputXMLTestResource(inputFile));
                testXML("Original document DOM changed after processing (operators " + operators + ")", originalDoc, doc);
            }

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testReplaceNodeWithUnificator_nonOperator() {

        try {

            DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();
            Document doc = docBuilder.parse(getInputXMLTestResource("single-formula.non-operator"));
            Document expectedDoc = docBuilder.parse(getExpectedXMLTestResource("single-formula.non-operator"));

            NodeList nodeList = doc.getElementsByTagNameNS(MATHML_NS, "msqrt");
            assertEquals(1, nodeList.getLength());

            Node node = nodeList.item(0);
            MathMLUnificator.replaceNodeWithUnificator(node);

            System.out.println("testReplaceNodeWithUnificator_nonOperator – output:\n" + XMLOut.xmlStringSerializer(doc));
            assertTrue(isDOMEqual(expectedDoc, doc));

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testReplaceNodeWithUnificator_operator() {

        try {

            DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();
            Document doc = docBuilder.parse(getInputXMLTestResource("single-formula.operator"));
            Document expectedDoc = docBuilder.parse(getExpectedXMLTestResource("single-formula.operator"));

            NodeList nodeList = doc.getElementsByTagNameNS(MATHML_NS, "mo");
            assertEquals(1, nodeList.getLength());

            Node node = nodeList.item(0);
            MathMLUnificator.replaceNodeWithUnificator(node);

            System.out.println("testReplaceNodeWithUnificator_operator – output:\n" + XMLOut.xmlStringSerializer(doc));
            assertTrue(isDOMEqual(expectedDoc, doc));

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testIsUnifiedMathNode() {

        try {

            Node node = null;

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-1")).getDocumentElement();
            assertNotNull(node);
            assertFalse("Namespaced math node without unification level attribute is not unified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-2")).getDocumentElement();
            assertNotNull(node);
            assertFalse("Not-namespaced math node without unification level attribute is not unified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-3")).getDocumentElement();
            assertNotNull(node);
            assertFalse("Not-math node is not unfified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-4")).getDocumentElement();
            assertNotNull(node);
            assertTrue("Namespaced math node with unification level attribute is unified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-5")).getDocumentElement();
            assertNotNull(node);
            assertTrue("Namespaced math node with unification level attribute is unified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-6")).getDocumentElement();
            assertNotNull(node);
            assertFalse("Namespaced math node with not-a-number unification level attribute is not unified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-7")).getDocumentElement();
            assertNotNull(node);
            assertTrue("Namespaced msqrt node with unification level attribute is unified",
                    MathMLUnificator.isUnifiedMathNode(node));

            node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("is-unified-math-nodes.case-8")).getDocumentElement();
            assertNotNull(node);
            assertFalse("Not-namespaced msqrt node with unification level attribute is not unified",
                    MathMLUnificator.isUnifiedMathNode(node));

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testGetNodeUnificationLevel() {

        try {

            Node nonMathMLNSNullNullNode = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("get-node-unification-level.without-mathml-ns.null-null")).getDocumentElement();
            Node mathMLNSNullNullNode = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("get-node-unification-level.with-mathml-ns.null-null")).getDocumentElement();
            Node nonMathMLNS12Node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("get-node-unification-level.without-mathml-ns.1-2")).getDocumentElement();
            Node mathMLNS12Node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("get-node-unification-level.with-mathml-ns.1-2")).getDocumentElement();
            Node nonMathMLNS23Node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("get-node-unification-level.without-mathml-ns.2-3")).getDocumentElement();
            Node mathMLNS23Node = DOMBuilder.getDocumentBuilder()
                    .parse(getXMLTestResource("get-node-unification-level.with-mathml-ns.2-3")).getDocumentElement();

            assertNotNull(nonMathMLNSNullNullNode);
            assertEquals(new UnificationLevel(), MathMLUnificator.getNodeUnificationLevel(nonMathMLNSNullNullNode));

            assertNotNull(mathMLNSNullNullNode);
            assertEquals(new UnificationLevel(), MathMLUnificator.getNodeUnificationLevel(mathMLNSNullNullNode));

            assertNotNull(nonMathMLNS12Node);
            assertEquals(new UnificationLevel(1, 2), MathMLUnificator.getNodeUnificationLevel(nonMathMLNS12Node));

            assertNotNull(mathMLNS12Node);
            assertEquals(new UnificationLevel(1, 2), MathMLUnificator.getNodeUnificationLevel(mathMLNS12Node));

            assertNotNull(nonMathMLNS23Node);
            assertEquals(new UnificationLevel(2, 3), MathMLUnificator.getNodeUnificationLevel(nonMathMLNS23Node));

            assertNotNull(mathMLNS23Node);
            assertEquals(new UnificationLevel(2, 3), MathMLUnificator.getNodeUnificationLevel(mathMLNS23Node));

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            fail(ex.getMessage());
        }

    }

}
