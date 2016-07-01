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

import cz.muni.fi.mir.mathmlunificator.config.Constants;
import cz.muni.fi.mir.mathmlunificator.utils.DOMBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.input.ReaderInputStream;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Abstract class to allow descendants to simply compare desired and produced
 * XML documents by calling {@link #testXML(java.lang.String, java.lang.String)}
 * method.
 *
 * @author Michal Růžička
 */
@Ignore // no tests
public abstract class AbstractXMLTransformationTest {

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        XMLUnit.setIgnoreWhitespace(true);
    }

    protected InputStream getInputXMLTestResource(String testFile) {
        return getXMLTestResource(testFile + ".input");
    }

    protected InputStream getExpectedXMLTestResource(String testFile) {
        return getXMLTestResource(testFile + ".expected-output");
    }

    protected InputStream getXMLTestResource(String testFile) {
        return getTestResource(testFile + ".xml");
    }

    protected String getXMLTestResourceAsFilepath(String testFile) {
        return getTestResourceAsFilepath(testFile + ".xml");
    }

    protected InputStream getTestResource(String testFile) {
        String resourceFile = this.getClass().getSimpleName() + "/" + testFile;
        InputStream rs = this.getClass().getResourceAsStream(resourceFile);
        return new ReaderInputStream(new InputStreamReader(rs, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    protected String getTestResourceAsFilepath(String testFile) {
        String resourceFile = this.getClass().getSimpleName() + "/" + testFile;
        return this.getClass().getResource(resourceFile).getFile();
    }

    protected void testXML(String expectedXMLDoc, String testedXMLDoc) throws ParserConfigurationException, SAXException, IOException {
        testXML(null, expectedXMLDoc, testedXMLDoc);
    }

    protected void testXML(String msg, String expectedXMLDoc, String testedXMLDoc) throws ParserConfigurationException, SAXException, IOException {
        Document testedDoc = DOMBuilder.buildDoc(testedXMLDoc);
        Document expectedDoc = DOMBuilder.buildDoc(getExpectedXMLTestResource(expectedXMLDoc));
        testXML(null, expectedDoc, testedDoc);
    }

    protected void testXML(Document expectedDoc, String testedXMLDoc) throws ParserConfigurationException, SAXException, IOException {
        testXML(null, expectedDoc, testedXMLDoc);
    }

    protected void testXML(String msg, Document expectedDoc, String testedXMLDoc) throws ParserConfigurationException, SAXException, IOException {
        Document testedDoc = DOMBuilder.buildDoc(testedXMLDoc);
        testXML(null, expectedDoc, testedDoc);
    }

    protected void testXML(Document expectedDoc, Document testedDoc) {
        testXML(null, expectedDoc, testedDoc);
    }

    protected void testXML(String msg, Document expectedDoc, Document testedDoc) {
        if (msg == null) {
            XMLAssert.assertXMLEqual(expectedDoc, testedDoc);
        } else {
            XMLAssert.assertXMLEqual(msg, expectedDoc, testedDoc);
        }
    }

    protected boolean isDOMEqual(Document templateDoc, Document testedDoc) {
        return templateDoc.isEqualNode(testedDoc);
    }

    protected boolean isMathMLElementsDOMEqual(Document templateDoc, Document testedDoc) {
        return isDOMEqual(templateDoc, testedDoc)
                && (templateDoc.getElementsByTagNameNS(Constants.MATHML_NS, Constants.MATHML_ROOT_ELEM).getLength()
                == testedDoc.getElementsByTagNameNS(Constants.MATHML_NS, Constants.MATHML_ROOT_ELEM).getLength());
    }

}
