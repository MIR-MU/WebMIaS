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
package cz.muni.fi.mir.mathmlunificator.utils;

import cz.muni.fi.mir.mathmlunificator.AbstractXMLTransformationTest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import org.junit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Michal Růžička
 */
public class DOMBuilderTest extends AbstractXMLTransformationTest {

    private static final String testFile = "multiple-formulae";

    private Document doc = null;

    @Override
    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        super.setUp();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.parse(getXMLTestResource(testFile));
    }

    @After
    public void tearDown() {
        doc = null;
    }

    @Test
    public void testBuildDocFromFilepath() throws Exception {
        Document testedDoc = DOMBuilder.buildDocFromFilepath(getXMLTestResourceAsFilepath(testFile));
        System.out.println("testBuildDocFromFilepath – doc:\n" + XMLOut.xmlStringSerializer(testedDoc));
        if (!isMathMLElementsDOMEqual(doc, testedDoc)) {
            fail("Produced W3C DOM is not equivalent to the expected W3C DOM.");
        }
    }

    @Test
    public void testBuildDoc_String() throws Exception {
        Document testedDoc = DOMBuilder.buildDoc(IOUtils.toString(getXMLTestResource(testFile), StandardCharsets.UTF_8));
        System.out.println("testBuildDoc_String – doc:\n" + XMLOut.xmlStringSerializer(testedDoc));
        if (!isMathMLElementsDOMEqual(doc, testedDoc)) {
            fail("Produced W3C DOM is not equivalent to the expected W3C DOM.");
        }
    }

    @Test
    public void testBuildDoc_File() throws Exception {
        Document testedDoc = DOMBuilder.buildDoc(new File(getXMLTestResourceAsFilepath(testFile)));
        System.out.println("testBuildDoc_File – doc:\n" + XMLOut.xmlStringSerializer(testedDoc));
        if (!isMathMLElementsDOMEqual(doc, testedDoc)) {
            fail("Produced W3C DOM is not equivalent to the expected W3C DOM.");
        }
    }

    @Test
    public void testBuildDoc_InputSource() throws Exception {
        Document testedDoc = DOMBuilder.buildDoc(new InputSource(getXMLTestResource(testFile)));
        System.out.println("testBuildDoc_InputSource – doc:\n" + XMLOut.xmlStringSerializer(testedDoc));
        if (!isMathMLElementsDOMEqual(doc, testedDoc)) {
            fail("Produced W3C DOM is not equivalent to the expected W3C DOM.");
        }
    }

    @Test
    public void testBuildDoc_InputStream() throws Exception {
        Document testedDoc = DOMBuilder.buildDoc(getXMLTestResource(testFile));
        System.out.println("testBuildDoc_InputStream – doc:\n" + XMLOut.xmlStringSerializer(testedDoc));
        if (!isMathMLElementsDOMEqual(doc, testedDoc)) {
            fail("Produced W3C DOM is not equivalent to the expected W3C DOM.");
        }
    }

    @Test
    public void testGetDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilder docBuilder = DOMBuilder.getDocumentBuilder();
        assertNotNull(docBuilder);
        assertTrue(docBuilder.isNamespaceAware());
    }

    @Test
    public void testCreateEmptyDoc() {
        Document doc = DOMBuilder.createEmptyDoc();
        assertNotNull(doc);
        assertTrue(doc instanceof Document);
    }

    @Test
    public void testCreateNewDocWithNodeClone() {

        try {

            String xmlString
                    = "<math xmlns:uni=\"http://mir.fi.muni.cz/mathml-unification/\"\n"
                    + "    uni:unification-level=\"2\" uni:unification-max-level=\"4\" xmlns=\"http://www.w3.org/1998/Math/MathML\">\n"
                    + "    <msup>\n"
                    + "        <mi>a</mi>\n"
                    + "        <mi>b</mi>\n"
                    + "    </msup>\n"
                    + "    <mo>+</mo>\n"
                    + "    <mfrac>\n"
                    + "        <mi>c</mi>\n"
                    + "        <mi>d</mi>\n"
                    + "    </mfrac>\n"
                    + "</math>";
            Document originalDoc = DOMBuilder.buildDoc(xmlString);
            Document doc = DOMBuilder.buildDoc(xmlString);

            // deep == true
            NodeList deepNodeList = doc.getElementsByTagNameNS("http://www.w3.org/1998/Math/MathML", "mo");
            assertEquals(deepNodeList.getLength(), 1);
            Node deepNode = deepNodeList.item(0);
            Document newDeepDoc = DOMBuilder.createNewDocWithNodeClone(deepNode, true);
            System.out.println("testCreateNewDocWithNodeClone – deep output:\n" + XMLOut.xmlStringSerializer(newDeepDoc));
            testXML("Deep clonning failed", DOMBuilder.buildDoc("<mo xmlns=\"http://www.w3.org/1998/Math/MathML\">+</mo>"), newDeepDoc);
            newDeepDoc.getDocumentElement().setTextContent("deep different content");
            System.out.println("testCreateNewDocWithNodeClone – input document DOM after deep processing:\n" + XMLOut.xmlStringSerializer(doc));
            testXML("Original document DOM changed after processing", originalDoc, doc);

            // deep == false
            NodeList noDeepNodeList = doc.getElementsByTagNameNS("http://www.w3.org/1998/Math/MathML", "mfrac");
            assertEquals(noDeepNodeList.getLength(), 1);
            Node noDeepNode = noDeepNodeList.item(0);
            Document newNoDeepDoc = DOMBuilder.createNewDocWithNodeClone(noDeepNode, false);
            System.out.println("testCreateNewDocWithNodeClone – non-deep output:\n" + XMLOut.xmlStringSerializer(newNoDeepDoc));
            testXML("Non-deep clonning failed", DOMBuilder.buildDoc("<mfrac xmlns=\"http://www.w3.org/1998/Math/MathML\"/>"), newNoDeepDoc);
            newNoDeepDoc.getDocumentElement().setTextContent("non-deep different content");
            System.out.println("testCreateNewDocWithNodeClone – input document DOM after non-deep processing:\n" + XMLOut.xmlStringSerializer(doc));
            testXML("Original document DOM changed after processing", originalDoc, doc);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testCloneNodeToNewDoc() {

        try {

            String xmlString
                    = "<math xmlns:uni=\"http://mir.fi.muni.cz/mathml-unification/\"\n"
                    + "    uni:unification-level=\"2\" uni:unification-max-level=\"4\" xmlns=\"http://www.w3.org/1998/Math/MathML\">\n"
                    + "    <msup>\n"
                    + "        <mi>a</mi>\n"
                    + "        <mi>b</mi>\n"
                    + "    </msup>\n"
                    + "    <mo>+</mo>\n"
                    + "    <mfrac>\n"
                    + "        <mi>c</mi>\n"
                    + "        <mi>d</mi>\n"
                    + "    </mfrac>\n"
                    + "</math>";
            Document originalDoc = DOMBuilder.buildDoc(xmlString);
            Document doc = DOMBuilder.buildDoc(xmlString);

            // deep == true
            NodeList deepNodeList = doc.getElementsByTagNameNS("http://www.w3.org/1998/Math/MathML", "mo");
            assertEquals(deepNodeList.getLength(), 1);
            Node deepNode = deepNodeList.item(0);
            Node newDeepNode = DOMBuilder.cloneNodeToNewDoc(deepNode, true);
            System.out.println("testCloneNodeToNewDoc – deep output:\n" + XMLOut.xmlStringSerializer(newDeepNode.getOwnerDocument()));
            testXML("Deep clonning failed", DOMBuilder.buildDoc("<mo xmlns=\"http://www.w3.org/1998/Math/MathML\">+</mo>"), newDeepNode.getOwnerDocument());
            newDeepNode.setTextContent("deep different content");
            System.out.println("testCloneNodeToNewDoc – input document DOM after non-deep processing:\n" + XMLOut.xmlStringSerializer(doc));
            testXML("Original document DOM changed after processing", originalDoc, doc);

            // deep == false
            NodeList noDeepNodeList = doc.getElementsByTagNameNS("http://www.w3.org/1998/Math/MathML", "mfrac");
            assertEquals(noDeepNodeList.getLength(), 1);
            Node noDeepNode = noDeepNodeList.item(0);
            Node newNoDeepNode = DOMBuilder.cloneNodeToNewDoc(noDeepNode, false);
            System.out.println("testCloneNodeToNewDoc – non-deep output:\n" + XMLOut.xmlStringSerializer(newNoDeepNode.getOwnerDocument()));
            testXML("Non-deep clonning failed", DOMBuilder.buildDoc("<mfrac xmlns=\"http://www.w3.org/1998/Math/MathML\"/>"), newNoDeepNode.getOwnerDocument());
            newNoDeepNode.setTextContent("non-deep different content");
            System.out.println("testCloneNodeToNewDoc – input document DOM after non-deep processing:\n" + XMLOut.xmlStringSerializer(doc));
            testXML("Original document DOM changed after processing", originalDoc, doc);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }

    }

}
