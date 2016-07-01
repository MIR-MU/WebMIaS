/*
 * Copyright 2016 Michal Růžička.
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
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Michal Růžička
 */
public class MathMLToolsTest extends AbstractXMLTransformationTest {

    private static final String testFile = "latexml-pmath-cmath-output";

    private Document doc = null;

    @Before
    @Override
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        super.setUp();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(false);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.parse(getXMLTestResource(testFile));
    }

    @After
    public void tearDown() {
        doc = null;
    }

    @Test
    public void testIsContentMathMLNode() throws XPathExpressionException {

        System.out.println("isContentMathMLNode – doc:\n"
                + XMLOut.xmlStringSerializer(doc));

        XPath xpath = XPathFactory.newInstance().newXPath();

        Node xmlNode = (Node) xpath.evaluate("//*[@id='S5.p1.1.m1.1.7.2']",
                doc, XPathConstants.NODE);
        assertNotNull(xmlNode);
        System.out.println("isContentMathMLNode – Presentation MathML node:\n"
                + XMLOut.xmlStringSerializer(xmlNode));
        assertFalse(MathMLTools.isContentMathMLNode(xmlNode));

        xmlNode = (Node) xpath.evaluate("//*[@id='S5.p1.1.m1.1.7.2.cmml']",
                doc, XPathConstants.NODE);
        assertNotNull(xmlNode);
        System.out.println("isContentMathMLNode – Presentation MathML node:\n"
                + XMLOut.xmlStringSerializer(xmlNode));
        assertTrue(MathMLTools.isContentMathMLNode(xmlNode));

    }

}
