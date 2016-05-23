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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Tools for building DOM from various string representations.
 *
 * @author Michal Růžička
 */
public class DOMBuilder {

    /**
     * Build W3C DOM representation of XML file specified by filesystem path.
     *
     * @param filepath Path of the file in filesystem to build DOM from.
     * @return W3C DOM representation of the XML document.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be
     * created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     */
    public static Document buildDocFromFilepath(String filepath) throws ParserConfigurationException, SAXException, IOException {
        return getDocumentBuilder().parse(filepath);
    }

    /**
     * Build W3C DOM representation of XML file specified by filesystem path.
     *
     * @param doc String with XML document to build DOM from.
     * @return W3C DOM representation of the XML document.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be
     * created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     */
    public static Document buildDoc(String doc) throws ParserConfigurationException, SAXException, IOException {
        return getDocumentBuilder().parse(IOUtils.toInputStream(doc));
    }

    /**
     * Build W3C DOM representation of XML from {@link java.io.File}.
     *
     * @param file XML {@link java.io.File} to build DOM from.
     * @return W3C DOM representation of the XML document.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be
     * created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     */
    public static Document buildDoc(File file) throws ParserConfigurationException, SAXException, IOException {
        return getDocumentBuilder().parse(file);
    }

    /**
     * Build W3C DOM representation of XML from
     * <code>{@link org.xml.sax.InputSource}</code>.
     *
     * @param is Input source to build DOM from.
     * @return W3C DOM representation of the XML document.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be
     * created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     */
    public static Document buildDoc(InputSource is) throws ParserConfigurationException, SAXException, IOException {
        return getDocumentBuilder().parse(is);
    }

    /**
     * Build W3C DOM representation of XML from
     * <code>{@link java.io.InputStream}</code>.
     *
     * @param is Input stream to build DOM from.
     * @return W3C DOM representation of the XML document.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be
     * created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     */
    public static Document buildDoc(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        return getDocumentBuilder().parse(is);
    }

    /**
     * Create new empty document.
     *
     * @return New empty W3C DOM document if successful; <code>null</code>
     * otherwise.
     */
    public static Document createEmptyDoc() {
        try {
            return getDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            return null;
        }
    }

    /**
     * Create new W3C DOM document with only child node created as the clone of
     * the given node (detached from the DOM of the source node).
     *
     * @param nodeToClone Node to clone as the only child node of the new
     * document.
     * @param deep If true, recursively clone the subtree under the given node;
     * if false, clone only the node itself (and its attributes, if it is an
     * Element).
     * @return New W3C DOM document with the only child node created as the
     * clone of the given node (detached from the DOM of the source node).
     */
    public static Document createNewDocWithNodeClone(Node nodeToClone, boolean deep) {
        Document newDoc = DOMBuilder.createEmptyDoc();
        newDoc.appendChild(newDoc.importNode(nodeToClone, deep));
        return newDoc;
    }

    /**
     * Create new W3C DOM document with only child node created as the clone of
     * the given node (detached from the DOM of the source node) and return the
     * cloned node attached to the new document.
     *
     * @param nodeToClone Node to clone as the only child node of the new
     * document.
     * @param deep If true, recursively clone the subtree under the given node;
     * if false, clone only the node itself (and its attributes, if it is an
     * Element).
     * @return Clone of the given node attached as the only child node to a new
     * W3C DOM document.
     */
    public static Node cloneNodeToNewDoc(Node nodeToClone, boolean deep) {
        return DOMBuilder.createNewDocWithNodeClone(nodeToClone, deep).getDocumentElement();
    }

    /**
     * Get W3C DOM document builder with common configuration set: most
     * importantly build is set to be namesapace aware.
     *
     * @return W3C DOM document builder with common configuration.
     * @throws ParserConfigurationException
     */
    public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(true);
        return docFactory.newDocumentBuilder();
    }

}
