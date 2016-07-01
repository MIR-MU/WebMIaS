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

import static cz.muni.fi.mir.mathmlunificator.config.Constants.CMATHML_ELEMENTS;
import org.w3c.dom.Node;

/**
 * Tools for handling MathML code.
 *
 * @author Michal Růžička
 */
public class MathMLTools {

    /**
     * For given XML {@link org.w3c.dom.Node} decide the node is Content MathML
     * node or not.
     *
     * The decision is done base on element name compared with a
     * <a href="https://www.w3.org/TR/MathML2/appendixb.html">list of known
     * Content MathML elements</a>.
     *
     * <em>Warning, the method is <strong>not</strong> XML namespace aware, the
     * decision is base on element local name only!</em>
     *
     * @param xmlNode XML {@link org.w3c.dom.Node} to check.
     * @return {@code true} if given {@link org.w3c.dom.Node} represents Content
     * MathML node, {@code false} otherwise.
     */
    public static boolean isContentMathMLNode(Node xmlNode) {

        return CMATHML_ELEMENTS.contains(xmlNode.getNodeName());

    }

}
