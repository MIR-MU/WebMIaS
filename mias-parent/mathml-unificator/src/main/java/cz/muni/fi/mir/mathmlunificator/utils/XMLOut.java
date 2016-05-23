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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * Tools for outputing XML.
 *
 * @author Michal Růžička
 */
public class XMLOut {

    /**
     * Pretty print W3C DOM represented XML document to the application standard
     * output.
     *
     * @param doc W3C DOM represented XML document to pretty print.
     */
    public static void xmlStdoutSerializer(Document doc) {
        xmlSerializer(doc, System.out);
    }

    /**
     * Pretty print W3C DOM represented XML node to the application standard
     * output.
     *
     * @param node W3C DOM represented XML node to pretty print.
     */
    public static void xmlStdoutSerializer(Node node) {
        xmlSerializer(node, System.out);
    }

    /**
     * Pretty print W3C DOM represented XML document to a given output stream.
     *
     * @param doc W3C DOM represented XML document to pretty print.
     * @param os Output stream to pretty print input XML to.
     */
    public static void xmlSerializer(Document doc, OutputStream os) {
        xmlSerializer((Node) doc, os);
    }

    /**
     * Pretty print W3C DOM represented XML document to a given output stream.
     *
     * @param node W3C DOM node to pretty print.
     * @param os Output stream to pretty print input XML to.
     */
    public static void xmlSerializer(Node node, OutputStream os) {
        DOMImplementation domImpl = node.getNodeType() == Node.DOCUMENT_NODE
                ? ((Document) node).getImplementation()
                : node.getOwnerDocument().getImplementation();
        DOMImplementationLS ls = (DOMImplementationLS) domImpl;
        LSOutput lso = ls.createLSOutput();
        LSSerializer lss = ls.createLSSerializer();
        lss.getDomConfig().setParameter("xml-declaration", true);
        lss.getDomConfig().setParameter("format-pretty-print", true);
        lso.setByteStream(os);
        lss.write(node, lso);
    }

    /**
     * Transform W3C DOM represented XML document to pretty-printed string
     * representation.
     *
     * @param doc W3C DOM represented XML document to pretty print.
     * @return Pretty-printed strign representation of the input DOM.
     */
    public static String xmlStringSerializer(Document doc) {
        OutputStream os = new ByteArrayOutputStream();
        xmlSerializer(doc, os);
        return os.toString();
    }

    /**
     * Transform W3C DOM represented XML node to pretty-printed string
     * representation.
     *
     * @param node W3C DOM represented XML node to pretty print.
     * @return Pretty-printed strign representation of the input DOM.
     */
    public static String xmlStringSerializer(Node node) {
        OutputStream os = new ByteArrayOutputStream();
        xmlSerializer(node, os);
        return os.toString();
    }

}
