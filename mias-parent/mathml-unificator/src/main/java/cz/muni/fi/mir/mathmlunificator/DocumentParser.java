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
import static cz.muni.fi.mir.mathmlunificator.config.Constants.*;
import cz.muni.fi.mir.mathmlunificator.utils.DOMBuilder;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Tools for analysing XML documents and extracting wanted data.
 *
 * @author Michal Růžička
 */
public class DocumentParser {

    /**
     * <p>
     * Find MathML nodes in the W3C DOM represented input XML document and
     * return collection of links to thiese nodes to the DOM.
     * </p>
     * <p>
     * The method is XML namespace aware and expectes the input DOM to be build
     * as XML aware (see
     * {@link javax.xml.parsers.DocumentBuilderFactory#setNamespaceAware(boolean)}).
     * </p>
     * <p>
     * In case the input DOM was created as namespace unaware or the input XML
     * document does not correctly use namespaces the method tries to fall back
     * to element-plain-name-only maths nodes detection. The behaviour is as
     * follows:
     * </p>
     * <ol>
     * <li>Find all &lt;math&gt; elements (see
     * {@link Constants#MATHML_ROOT_ELEM}) in MathML namespace, i.e. XML
     * namespace represented by URI
     * <code>http://www.w3.org/1998/Math/MathML</code> (see
     * {@link Constants#MATHML_NS}). If at least one element is found, stop
     * futher processing and return collection of links to these nodes.</li>
     * <li>Find any &lt;math&gt; named elements regadless of XML namespaces. If
     * at least one element is found, stop futher processing and return
     * collection of links to these nodes.</li>
     * <li>Return empty list.</li>
     * </ol>
     *
     * @param doc W3C DOM representation of the XML document to find maths nodes
     * in.
     * @return Collection of maths W3C DOM Nodes that are still linkend to the
     * original input DOM.
     * @see DOMBuilder
     */
    public static List<Node> findMathMLNodes(Document doc) {

        NodeList mathNodeList = doc.getElementsByTagNameNS(MATHML_NS, MATHML_ROOT_ELEM);
        if (mathNodeList.getLength() == 0) {
            // If no math elements are found maybe the input DOM was created as
            // namespace unaware. In this case try to use any math-named element
            // regardless of its namespace.
            mathNodeList = doc.getElementsByTagName(MATHML_ROOT_ELEM);
        }

        List mathNodes = new ArrayList(mathNodeList.getLength());
        for (int i = 0; i < mathNodeList.getLength(); i++) {
            mathNodes.add(mathNodeList.item(i));
        }

        return mathNodes;

    }

}
