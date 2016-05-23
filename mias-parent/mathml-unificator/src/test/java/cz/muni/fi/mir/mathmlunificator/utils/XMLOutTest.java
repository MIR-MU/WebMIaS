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
import cz.muni.fi.mir.mathmlunificator.config.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
public class XMLOutTest extends AbstractXMLTransformationTest {

    @Test
    public void testXmlSerializer_Document_OutputStream() {
        final String testFile = "multiple-formulae";
        try {
            Document inputDoc = DOMBuilder.buildDoc(getInputXMLTestResource(testFile));
            OutputStream os = new ByteArrayOutputStream();
            XMLOut.xmlSerializer(inputDoc, os);
            String output = os.toString();
            System.out.println("testXmlSerializer – output:\n" + output);
            testXML(testFile, output);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testXmlSerializer_Node_OutputStream() {
        final String testFile = "single-formula";
        try {
            Document inputDoc = DOMBuilder.buildDoc(getInputXMLTestResource(testFile));
            NodeList nodeList = inputDoc.getElementsByTagNameNS(Constants.MATHML_NS, "mfrac");
            assertEquals(1, nodeList.getLength());
            Node node = nodeList.item(0);
            OutputStream os = new ByteArrayOutputStream();
            XMLOut.xmlSerializer(node, os);
            String output = os.toString();
            System.out.println("testXmlSerializer – output:\n" + output);
            testXML(testFile, output);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testXmlStdoutSerializer_Document() {
        final String testFile = "multiple-formulae";
        try {
            Document inputDoc = DOMBuilder.buildDoc(getInputXMLTestResource(testFile));
            ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
            PrintStream stdout = System.out;
            System.setOut(new PrintStream(stdoutContent));
            XMLOut.xmlStdoutSerializer(inputDoc);
            System.setOut(stdout);
            String output = stdoutContent.toString();
            System.out.println("testXmlStdoutSerializer – output:\n" + output);
            testXML(testFile, output);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testXmlStdoutSerializer_Node() {
        final String testFile = "single-formula";
        try {
            Document inputDoc = DOMBuilder.buildDoc(getInputXMLTestResource(testFile));
            NodeList nodeList = inputDoc.getElementsByTagNameNS(Constants.MATHML_NS, "mfrac");
            assertEquals(1, nodeList.getLength());
            Node node = nodeList.item(0);
            ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
            PrintStream stdout = System.out;
            System.setOut(new PrintStream(stdoutContent));
            XMLOut.xmlStdoutSerializer(node);
            System.setOut(stdout);
            String output = stdoutContent.toString();
            System.out.println("testXmlStdoutSerializer – output:\n" + output);
            testXML(testFile, output);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testXmlStringSerializer_Document() {
        final String testFile = "multiple-formulae";
        try {
            Document inputDoc = DOMBuilder.buildDoc(getInputXMLTestResource(testFile));
            String outputDoc = XMLOut.xmlStringSerializer(inputDoc);
            System.out.println("testXmlStringSerializer – output:\n" + outputDoc);
            testXML(testFile, outputDoc);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testXmlStringSerializer_Node() {
        final String testFile = "single-formula";
        try {
            Document inputDoc = DOMBuilder.buildDoc(getInputXMLTestResource(testFile));
            NodeList nodeList = inputDoc.getElementsByTagNameNS(Constants.MATHML_NS, "mfrac");
            assertEquals(1, nodeList.getLength());
            Node node = nodeList.item(0);
            String outputDoc = XMLOut.xmlStringSerializer(node);
            System.out.println("testXmlStringSerializer – output:\n" + outputDoc);
            testXML(testFile, outputDoc);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

}
