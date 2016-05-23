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
package cz.muni.fi.mias.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Container class for the information about a single MathML formula
 *
 * @author Martin Liska
 */
public class Formula {

    private float weight;
    private Node node;
    private String miasString;

    public Formula() {
    }

    public Formula(Node node, float weight) {
        this.weight = weight;
        this.node = node;
    }

    public Formula(Formula f) {
        this.node = f.getNode().cloneNode(true);
        this.weight = f.getWeight();
    }

    public String getMiasString() {
        return miasString;
    }

    public void setMiasString(String miasString) {
        this.miasString = miasString;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Formula{weight=").append(weight).append("node=");

        nodeToString(builder, node, false, new HashMap<>(), new HashMap<>(), new ArrayList<>());

        builder.append('}');

        return builder.toString();
    }

    /**
     * Creates M-term styled string representation of a MathML formula.
     *
     * @param node MathML with the formula
     * @param withoutTextContent if true, resulting string will not contain text
     * content of MathML nodes
     * @param eldict dictionary for substituting standard MathML element names
     * for custom ones
     * @param attrdict dictionary for substituting standard MathML attribute
     * names and their values for custom ones
     * @param ignorableNodes a list of MathML nodes which the output should not
     * contain
     * @return M-terms styled string representing the input MathML formula
     */
    public static String nodeToString(Node node, boolean withoutTextContent,
            Map<String, String> eldict, Map<String, String> attrdict, List<String> ignorableNodes) {
        StringBuilder builder = new StringBuilder();
        nodeToString(builder, node, withoutTextContent, eldict, attrdict, ignorableNodes);
        return builder.toString();
    }

    /**
     * Creates M-term styled string representation of a MathML formula.
     *
     * @param builder StringBuilder to save M-terms styled string representing
     * the input MathML formula to
     * @param node MathML with the formula
     * @param withoutTextContent if true, resulting string will not contain text
     * content of MathML nodes
     * @param eldict dictionary for substituting standard MathML element names
     * for custom ones
     * @param attrdict dictionary for substituting standard MathML attribute
     * names and their values for custom ones
     * @param ignorableNodes a list of MathML nodes which the output should not
     * contain
     */
    public static void nodeToString(StringBuilder builder, Node node, boolean withoutTextContent,
            Map<String, String> eldict, Map<String, String> attrdict, List<String> ignorableNodes) {

        if (shouldIgnoreNode(node, ignorableNodes)) {
            // do nothing
            return;
        }

        String name = node.getLocalName();
        NodeList children = node.getChildNodes();
        int childrenSize = children.getLength();

        if (isMrowOrMathOrMfenced(name) && childrenSize <= 1) {
            if (childrenSize == 1) {
                nodeToString(builder, node.getFirstChild(), withoutTextContent, eldict, attrdict, ignorableNodes);
            }
        } else {
            String normalizedName = eldict.get(name);
            if (normalizedName == null || withoutTextContent) {
                builder.append(name);
            } else {
                builder.append(normalizedName);
            }

            if (!withoutTextContent) {
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    String attrName = attrs.item(i).getNodeName();
                    if (attrdict.containsKey(attrName)) {
                        String dictAttrName = attrdict.get(attrName);
                        String attrValue = attrs.item(i).getNodeValue();
                        String dictValue = attrdict.get(attrValue);
                        if (dictValue == null) {
                            dictValue = attrValue;
                        }

                        builder.append("[").append(dictAttrName).append("=").append(dictValue).append("]");
                    }
                }
            }

            if (childrenSize > 1) {
                builder.append("(");
                for (int j = 0; j < childrenSize; j++) {
                    nodeToString(builder, children.item(j), withoutTextContent, eldict, attrdict, ignorableNodes);
                }
                builder.append(")");
            } else if (!withoutTextContent) {
                builder.append("(").append(node.getTextContent()).append(")");
            }
        }
    }

    private static boolean shouldIgnoreNode(Node node, List<String> ignorableNodes) {
        return !(node instanceof Element) || ignorableNodes.contains(node.getLocalName());
    }

    private static boolean isMrowOrMathOrMfenced(String name) {
        return name != null && (name.equals("mrow") || name.equals("math") || name.equals("mfenced"));
    }

}
