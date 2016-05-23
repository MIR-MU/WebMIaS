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
package cz.muni.fi.mir.mathmlunificator;

import cz.muni.fi.mir.mathmlunificator.utils.XMLOut;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.*;
import org.junit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Michal Růžička
 */
public class DocumentParserTest extends AbstractXMLTransformationTest {

    private static DocumentBuilder docBuilderNSAware = null;
    private static DocumentBuilder docBuilderNSUnaware = null;

    @Override
    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        super.setUp();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(true);
        docBuilderNSAware = docFactory.newDocumentBuilder();
        docFactory.setNamespaceAware(false);
        docBuilderNSUnaware = docFactory.newDocumentBuilder();
    }

    @After
    public void tearDown() {
        docBuilderNSAware = null;
    }

    public void findMathMLNodes_NSAware(String testFile, int expectedNumberOfNodes) {
        findMathMLNodes(docBuilderNSAware, testFile, expectedNumberOfNodes);
    }

    public void findMathMLNodes_NSUnaware(String testFile, int expectedNumberOfNodes) {
        findMathMLNodes(docBuilderNSUnaware, testFile, expectedNumberOfNodes);
    }

    public void findMathMLNodes(DocumentBuilder docBuilder, String testFile, int expectedNumberOfNodes) {
        try {
            Document doc = docBuilder.parse(getTestResource(testFile));
            System.out.println("findMathMLNodes – doc:\n" + XMLOut.xmlStringSerializer(doc));
            List<Node> nl = DocumentParser.findMathMLNodes(doc);
            assertEquals(expectedNumberOfNodes, nl.size());
        } catch (SAXException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testFindMathMLNodes_NSAware_multipleMixedNamespaces() {
        findMathMLNodes_NSAware("multiple-formulae.mixed-namespace.xml", 2);
    }

    @Test
    public void testFindMathMLNodes_NSAware_multipleWithNamespaces() {
        findMathMLNodes_NSAware("multiple-formulae.with-namespace.xml", 3);
    }

    @Test
    public void testFindMathMLNodes_NSAware_multipleWithoutNamespaces() {
        findMathMLNodes_NSAware("multiple-formulae.without-namespace.xml", 3);
    }

    @Test
    public void testFindMathMLNodes_NSAware_singleWithNamespaces() {
        findMathMLNodes_NSAware("single-formula.with-namespace.xml", 1);
    }

    @Test
    public void testFindMathMLNodes_NSAware_singleWithoutNamespaces() {
        findMathMLNodes_NSAware("single-formula.without-namespace.xml", 1);
    }

    @Test
    public void testFindMathMLNodes_NSUnaware_multipleMixedNamespaces() {
        findMathMLNodes_NSUnaware("multiple-formulae.mixed-namespace.xml", 3);
    }

    @Test
    public void testFindMathMLNodes_NSUnaware_multipleWithNamespaces() {
        findMathMLNodes_NSUnaware("multiple-formulae.with-namespace.xml", 3);
    }

    @Test
    public void testFindMathMLNodes_NSUnaware_multipleWithoutNamespaces() {
        findMathMLNodes_NSUnaware("multiple-formulae.without-namespace.xml", 3);
    }

    @Test
    public void testFindMathMLNodes_NSUnaware_singleWithNamespaces() {
        findMathMLNodes_NSUnaware("single-formula.with-namespace.xml", 1);
    }

    @Test
    public void testFindMathMLNodes_NSUnaware_singleWithoutNamespaces() {
        findMathMLNodes_NSUnaware("single-formula.without-namespace.xml", 1);
    }

}
