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
import cz.muni.fi.mir.mathmlunificator.utils.XMLOut;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * MathML Unificator is a tool which performs simple MathML (Mathematical Markup
 * Language) unification
 * <a href="http://research.nii.ac.jp/ntcir/workshop/OnlineProceedings11/pdf/NTCIR/Math-2/07-NTCIR11-MATH-RuzickaM.pdf#page=7&zoom=page-fit">as
 * proposed</a>, i.e. single MathML formula transforms to series of formulae
 * with leaf elements substituted gradually for a special unification
 * representing symbol <code>&#x25CD;</code> (see {@link Constants#UNIFICATOR}).
 *
 * @author Michal Růžička
 */
public class MathMLUnificator {

    /**
     * Number of second level categories for ordering of elements in
     * {@link #nodesByDepth}.
     */
    private static final int NUMOFMINORLEVELS = 2;

    /**
     * Data structure represents ordered series of gradually unified MathML
     * formulae. The order is represented in two levels by
     * {@link NodeLevel<Integer, Integer>} object: The major level represents
     * depth of the {@link org.w3c.dom.Node} in the XML document DOM. The minor
     * level represents type of the {@link org.w3c.dom.Node} in MathML:
     * <code>1</code> represents elements of MathML operators, <code>2</code>
     * represents other types of MathML elements.
     */
    private HashMap<NodeLevel<Integer, Integer>, List> nodesByDepth;

    /**
     * <p>
     * In the given W3C DOM represented XML document find all maths nodes (see
     * {@link DocumentParser#findMathMLNodes(org.w3c.dom.Document)}) and
     * substitute them for series of formulae with leaf elements substituted
     * gradually for a special unification representing symbol
     * <code>&#x25CD;</code> (see {@link Constants#UNIFICATOR}).
     * </p>
     * <p>
     * Resulting series of the original and unified MathML nodes is itself
     * encapsulated in a new element &lt;unified-math&gt; (see
     * {@link Constants#UNIFIED_MATHML_ROOT_ELEM}) in XML namespace
     * <code>http://mir.fi.muni.cz/mathml-unification/</code> (see
     * {@link Constants#UNIFIED_MATHML_NS}).
     * </p>
     *
     * @param doc W3C DOM representation of the XML document to work on.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     * @see DocumentParser#findMathMLNodes(org.w3c.dom.Document)
     */
    public static void unifyMathML(Document doc, boolean operatorUnification) {

        List<Node> mathNodes = DocumentParser.findMathMLNodes(doc);
        for (Node mathNode : mathNodes) {
            MathMLUnificator.unifyMathMLNode(mathNode, operatorUnification);
        }

    }

    /**
     * <p>
     * In the given W3C DOM represented XML document find all maths nodes (see
     * {@link DocumentParser#findMathMLNodes(org.w3c.dom.Document)}) and
     * substitute them for series of formulae with leaf elements substituted
     * gradually for a special unification representing symbol
     * <code>&#x25CD;</code> (see {@link Constants#UNIFICATOR}).
     * </p>
     * <p>
     * Resulting series of the original and unified MathML nodes is itself
     * encapsulated in a new element &lt;unified-math&gt; (see
     * {@link Constants#UNIFIED_MATHML_ROOT_ELEM}) in XML namespace
     * <code>http://mir.fi.muni.cz/mathml-unification/</code> (see
     * {@link Constants#UNIFIED_MATHML_NS}).
     * </p>
     * <p>
     * Untouched input will be returned and error logged in case of any error
     * during the processing.
     * </p>
     *
     * @param is Input stream with UTF-8 encoded string representation of the
     * XML document to work on.
     * @param os Output stream to write UTF-8 encoded string representation of
     * the processed XML document.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     * @throws java.io.IOException If any I/O error occurs while reading the
     * input stream.
     * @see DocumentParser#findMathMLNodes(org.w3c.dom.Document)
     */
    public static void unifyMathML(InputStream is, OutputStream os, boolean operatorUnification) throws IOException {

        String originalDoc = IOUtils.toString(is);

        try {
            Document doc = DOMBuilder.buildDoc(new ByteArrayInputStream(originalDoc.getBytes()));
            unifyMathML(doc, operatorUnification);
            XMLOut.xmlSerializer(doc, os);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(MathMLUnificator.class.getName()).log(Level.SEVERE, "MathML Unification from string failed, input string not modified.", ex);
            IOUtils.write(originalDoc, os);
        }

    }

    /**
     * <p>
     * Substitute the given MathML {@link Node} for series of MathML formulae
     * with leaf elements substituted gradually for a special unification
     * representing symbol <code>&#x25CD;</code> (see
     * {@link Constants#UNIFICATOR}).
     * </p>
     * <p>
     * Resulting series of the original and unified MathML nodes is itself
     * encapsulated in a new element &lt;unified-math&gt; (see
     * {@link Constants#UNIFIED_MATHML_ROOT_ELEM}) in XML namespace
     * <code>http://mir.fi.muni.cz/mathml-unification/</code> (see
     * {@link Constants#UNIFIED_MATHML_NS}).
     * </p>
     *
     * @param mathNode W3C DOM XML document representation attached MathML node
     * to work on.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     */
    public static void unifyMathMLNode(Node mathNode, boolean operatorUnification) {

        new MathMLUnificator().unifyMathMLNodeImpl(mathNode, operatorUnification, true);

    }

    /**
     * <p>
     * Transforms the given MathML {@link Node} for series of MathML formulae
     * with leaf elements substituted gradually for a special unification
     * representing symbol <code>&#x25CD;</code> (see
     * {@link Constants#UNIFICATOR}).
     * </p>
     *
     * @param mathNode W3C DOM XML document representation attached MathML node
     * to work on.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     * @return Collection of unified versions of the <code>mathNode</code> with
     * key of the {@link HashMap} describing order (level of unification) of
     * elements in the collection.
     */
    public static HashMap<Integer, Node> getUnifiedMathMLNodes(Node mathNode, boolean operatorUnification) {

        return new MathMLUnificator().unifyMathMLNodeImpl(mathNode, operatorUnification, false);

    }

    /**
     * <p>
     * Implementation of MathML unification. In the given W3C DOM represented
     * XML document find all maths nodes (see
     * {@link DocumentParser#findMathMLNodes(org.w3c.dom.Document)}) and
     * remember links to operator elements and other elements in
     * {@link #nodesByDepth} data structure. Then substitute them gradualy for
     * series of formulae with leaf elements substituted for a special
     * unification representing symbol <code>&#x25CD;</code> (see
     * {@link Constants#UNIFICATOR}).
     * </p>
     * <p>
     * Resulting series of the original and unified MathML nodes is itself
     * encapsulated in a new element &lt;unified-math&gt; (see
     * {@link Constants#UNIFIED_MATHML_ROOT_ELEM}) in XML namespace
     * <code>http://mir.fi.muni.cz/mathml-unification/</code> (see
     * {@link Constants#UNIFIED_MATHML_NS}) and put to the place of the original
     * math element {@link Node} in the XML DOM representation the node is
     * attached to.
     * </p>
     *
     * @param mathNode W3C DOM XML document representation attached MathML node
     * to work on.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     */
    private void unifyMathMLNodeImpl(Node mathNode, boolean operatorUnification) {

        unifyMathMLNodeImpl(mathNode, operatorUnification, true);

    }

    /**
     * <p>
     * Implementation of MathML unification. In the given W3C DOM represented
     * XML document find all maths nodes (see
     * {@link DocumentParser#findMathMLNodes(org.w3c.dom.Document)}) and
     * remember links to operator elements and other elements in
     * {@link #nodesByDepth} data structure. Then substitute them gradualy for
     * series of formulae with leaf elements substituted for a special
     * unification representing symbol <code>&#x25CD;</code> (see
     * {@link Constants#UNIFICATOR}).
     * </p>
     * <p>
     * Resulting series of the original and unified MathML nodes is itself
     * encapsulated in a new element &lt;unified-math&gt; (see
     * {@link Constants#UNIFIED_MATHML_ROOT_ELEM}) in XML namespace
     * <code>http://mir.fi.muni.cz/mathml-unification/</code> (see
     * {@link Constants#UNIFIED_MATHML_NS}) and put to the place of the original
     * math element {@link Node} in the XML DOM representation the node is
     * attached to.
     * </p>
     *
     * @param mathNode W3C DOM XML document representation attached MathML node
     * to work on.
     * @param workInPlace If <code>true</code>, given <code>mathNode</code> will
     * be modified in place; if <code>false</code>, <code>mathNode</code> will
     * not be modified and series of modified nodes will be returned.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     * @return <code>null</code> if <code>workInPlace</code> is
     * <code>false</code>; otherwise collection of unified versions of the
     * <code>mathNode</code> with key of the {@link HashMap} describing order
     * (level of unification) of elements in the collection.
     */
    private HashMap<Integer, Node> unifyMathMLNodeImpl(Node mathNode, boolean operatorUnification, boolean workInPlace) {

        if (mathNode.getOwnerDocument() == null) {
            String msg = "The given node is not attached to any document.";
            if (mathNode.getNodeType() == Node.DOCUMENT_NODE) {
                msg = "The given node is document itself. Call with mathNode.getDocumentElement() instead.";
            }
            throw new IllegalArgumentException(msg);
        }

        nodesByDepth = new HashMap<>();

        Node unifiedMathNode = null;
        HashMap<Integer, Node> unifiedNodesList = null;
        Document unifiedMathDoc = null;

        if (workInPlace) {
            // New element encapsulating the series of unified formulae.
            unifiedMathNode = mathNode.getOwnerDocument().createElementNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_ROOT_ELEM);
            mathNode.getParentNode().replaceChild(unifiedMathNode, mathNode);
            unifiedMathNode.appendChild(mathNode.cloneNode(true));
        } else {
            unifiedNodesList = new HashMap<>();
            // Create a new separate DOM to work over with imporeted clone of the node given by user
            unifiedMathDoc = DOMBuilder.createNewDocWithNodeClone(mathNode, true);
            mathNode = unifiedMathDoc.getDocumentElement();
        }

        // Parse XML subtree starting at mathNode and remember elements by their depth.
        rememberLevelsOfNodes(mathNode, operatorUnification);

        // Build series of formulae of level by level unified MathML.
        NodeLevel<Integer, Integer> level = new NodeLevel<>(getMaxMajorNodesLevel(), NUMOFMINORLEVELS);
        int levelAttrCounter = 0;
        Collection<Attr> maxLevelAttrs = new LinkedList<>();
        while (level.major > 0) {
            if (nodesByDepth.containsKey(level)) {
                if (unifyAtLevel(level)) {
                    levelAttrCounter++;

                    Node thisLevelMathNode = mathNode.cloneNode(true);
                    Attr thisLevelAttr = thisLevelMathNode.getOwnerDocument()
                            .createAttributeNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_NS_PREFIX + ":" + UNIFIED_MATHML_LEVEL_ATTR);
                    Attr maxLevelAttr = thisLevelMathNode.getOwnerDocument()
                            .createAttributeNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_NS_PREFIX + ":" + UNIFIED_MATHML_MAX_LEVEL_ATTR);
                    maxLevelAttrs.add(maxLevelAttr);

                    thisLevelAttr.setTextContent(String.valueOf(levelAttrCounter));

                    ((Element) thisLevelMathNode).setAttributeNodeNS(thisLevelAttr);
                    ((Element) thisLevelMathNode).setAttributeNodeNS(maxLevelAttr);

                    if (workInPlace) {
                        unifiedMathNode.appendChild(thisLevelMathNode);
                    } else {
                        // Create a new document for every node in the collection.
                        unifiedNodesList.put(levelAttrCounter, DOMBuilder.cloneNodeToNewDoc(thisLevelMathNode, true));
                    }
                }
            }
            level.minor--;
            if (level.minor <= 0) {
                level.major--;
                level.minor = NUMOFMINORLEVELS;
            }
        }
        for (Attr attr : maxLevelAttrs) {
            attr.setTextContent(String.valueOf((levelAttrCounter)));
        }

        if (workInPlace) {
            return null;
        } else {
            for (Node node : unifiedNodesList.values()) {
                Attr maxLevelAttr = (Attr) node.getAttributes().getNamedItemNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_MAX_LEVEL_ATTR);
                if (maxLevelAttr != null) {
                    maxLevelAttr.setTextContent(String.valueOf((levelAttrCounter)));
                }
            }
            return unifiedNodesList;
        }

    }

    /**
     * Parse XML subtree starting at the given node and remember the type and
     * depth of all found (sub)elements in the XML DOM representation using
     * {@link #nodesByDepth}.
     *
     * @param rootNode XML DOM node to use as root element for processing.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     */
    private void rememberLevelsOfNodes(Node rootNode, boolean operatorUnification) {

        NodeList nodeList = rootNode.getChildNodes();

        rememberNodesStartingAtLevel(1, nodeList, operatorUnification);

    }

    /**
     * Parse XML subtrees starting by the given nodes with the given starting
     * level and remember the type and depth of all found (sub)elements in the
     * XML DOM representation using {@link #nodesByDepth}.
     *
     * @param level The level the starting nodes will be rembered at. The
     * subelements of these nodes will be remeber on higher levels relative to
     * this starting level.
     * @param nodeList Collection of nodes to use as root elements for
     * processing.
     * @param operatorUnification If <code>true</code> unify also operator
     * nodes, otherwise keep operator nodes intact.
     */
    private void rememberNodesStartingAtLevel(int level, NodeList nodeList, boolean operatorUnification) {

        if (nodeList != null && nodeList.getLength() > 0) {

            NodeLevel<Integer, Integer> levelOperators = new NodeLevel<>(level, NUMOFMINORLEVELS - 1);
            NodeLevel<Integer, Integer> levelNonOperators = new NodeLevel<>(level, NUMOFMINORLEVELS);
            List<Node> nodesNonOperator = new ArrayList(nodeList.getLength());
            List<Node> nodesOperator = new ArrayList(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeName().equals(PMATHML_OPERATOR)) {
                    if (operatorUnification) {
                        nodesOperator.add(node);
                    }
                } else {
                    nodesNonOperator.add(node);
                }
            }

            nodesByDepth.merge(levelNonOperators, nodesNonOperator, (List t, List u) -> {
                t.addAll(u);
                return t;
            });
            nodesByDepth.merge(levelOperators, nodesOperator, (List t, List u) -> {
                t.addAll(u);
                return t;
            });

            for (Node node : nodesNonOperator) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    rememberNodesStartingAtLevel(level + 1, node.getChildNodes(), operatorUnification);
                }
            }

        }

    }

    /**
     * Get nodes rembemerd in {@link #nodesByDepth} of this instance at level
     * given by an instance of {@link NodeLevel} and replace these nodes (if
     * applicable, i.e. nodes are {@link Node#ELEMENT_NODE} elements) with
     * unification elements containing unification representing symbol
     * <code>&#x25CD;</code> (see {@link Constants#UNIFICATOR}).
     *
     * @param level Level in {@link #nodesByDepth} to work on.
     * @return <code>true</code> if any unifiable nodes were found at given
     * level and an unification was done, <code>false</code> otherwise.
     * @see #replaceNodeWithUnificator(org.w3c.dom.Node)
     */
    private boolean unifyAtLevel(NodeLevel<Integer, Integer> level) {

        boolean modified = false;

        if (level.major <= 0) {
            throw new IllegalArgumentException("Major level must be greather than 0.");
        } else if (level.minor <= 0) {
            throw new IllegalArgumentException("Minor level must be greather than 0.");
        } else if (nodesByDepth.containsKey(level)) {

            List<Node> nodes = nodesByDepth.get(level);
            for (Node node : nodes) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    replaceNodeWithUnificator(node);
                    modified = true;
                }
            }

        }

        return modified;

    }

    /**
     * Replace the given node with unification element containing unification
     * representing symbol <code>&#x25CD;</code> (see
     * {@link Constants#UNIFICATOR}).
     *
     * @param oldNode The node to be replaced with the unification representing
     * element.
     * @throws IllegalArgumentException If the given node does not have parent.
     */
    public static void replaceNodeWithUnificator(Node oldNode) {

        Node parentNode = oldNode.getParentNode();

        if (parentNode == null) {
            throw new IllegalArgumentException("Cannot replace node [" + oldNode + "] that has no parent.");
        } else {
            String unificatorElementType = oldNode.getNodeName().equals(PMATHML_OPERATOR) ? PMATHML_OPERATOR : PMATHML_IDENTIFIER;
            Node newNode = oldNode.getOwnerDocument().createElementNS(oldNode.getNamespaceURI(), unificatorElementType);
            newNode.setTextContent(UNIFICATOR);
            parentNode.replaceChild(newNode, oldNode);
        }

    }

    /**
     * Get the highest major level from {@link #nodesByDepth} of this instance.
     *
     * @return The highest major level from {@link #nodesByDepth} of this
     * instance.
     * @see NodeLevel#major
     */
    private int getMaxMajorNodesLevel() {

        int maxInt = 0;

        for (NodeLevel<Integer, Integer> level : nodesByDepth.keySet()) {
            if (maxInt < level.major) {
                maxInt = level.major;
            }
        }

        return maxInt;

    }

    /**
     * <p>
     * Test whether the given node is unified math node. The node is considered
     * to be unified math node if
     * </p>
     * <ol>
     * <li>the node is not <code>null</code>,</li>
     * <li>the node is math node,</li>
     * <li>the node has current unification level attribute (see
     * {@link Constants#UNIFIED_MATHML_LEVEL_ATTR}) in MathML Unification XML
     * namespace (see {@link Constants#UNIFIED_MATHML_NS}) with valid value,
     * i.e. integer greather than 0.</li>
     * </ol>
     * <p>
     * The method is XML namespace aware and expects the node DOM to be build as
     * XML aware (see
     * {@link javax.xml.parsers.DocumentBuilderFactory#setNamespaceAware(boolean)}).
     * </p>
     * <p>
     * In case the input DOM was created as namespace unaware or the input XML
     * document does not correctly use namespaces the method tries to fall back
     * to element-plain-name-only math node detection. The element is considered
     * to be math element if
     * <ul>
     * <li>the element is in MathML namespace XML namespace
     * {@code http://www.w3.org/1998/Math/MathML} (see
     * {@link Constants#MATHML_NS}),</li>
     * <li>or the local name of the element is {@code <math>} (see
     * {@link Constants#UNIFIED_MATHML_ROOT_ELEM}) without any namespace
     * definition.
     * </ul>
     * </p>
     *
     * @param node The node to test.
     * @return <code>true</code> if the above description is fulfilled,
     * <code>false</code> otherwise.
     */
    public static boolean isUnifiedMathNode(Node node) {

        // Test the nodes is element
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            // Test the node is math node in MathML namesapce or at least with corrent name if no namesapce is defined at all
            if ((node.getNamespaceURI() == null && node.getNodeName().equals(MATHML_ROOT_ELEM))
                    || (node.getNamespaceURI() != null && node.getNamespaceURI().equals(MATHML_NS))) {
                // Test presence of unification level attribute
                Node uniLevel = node.getAttributes().getNamedItemNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_LEVEL_ATTR);
                if (uniLevel != null && uniLevel.getNodeType() == Node.ATTRIBUTE_NODE) {
                    Integer value;
                    try {
                        value = Integer.parseInt(((Attr) uniLevel).getTextContent());
                    } catch (NumberFormatException ex) {
                        return false;
                    }
                    if (value >= 0) {
                        return true;
                    }
                }

            }
        }

        return false;

    }

    /**
     * <p>
     * Determine the unification level of the {@link Node} and max level of the
     * unification series. If the node does not possess the appropriate
     * unification level XML attributes the {@link UnificationLevel} object will
     * indicate that by having both {@link UnificationLevel#nodeLevel} and
     * {@link UnificationLevel#maxLevel} set to {@code null}.
     * </p>
     * <p>
     * <strong>Please note that only existance of valid unification level XML
     * attributes is tested. If you want to be sure the node is a valid unified
     * math node use {@link #isUnifiedMathNode(org.w3c.dom.Node)}!</strong>
     * </p>
     *
     * @param node The node to determine the unification level for.
     * @return The node's unification level represented by an instance of
     * {@link UnificationLevel}.
     * @see #unifyMathMLNode(org.w3c.dom.Node, boolean)
     */
    public static UnificationLevel getNodeUnificationLevel(Node node) {

        Integer uniLevelValue = 0;
        Integer maxLevelValue = 0;

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            // Get unification level attributes if exists
            Node uniLevel = node.getAttributes().getNamedItemNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_LEVEL_ATTR);
            Node maxLevel = node.getAttributes().getNamedItemNS(UNIFIED_MATHML_NS, UNIFIED_MATHML_MAX_LEVEL_ATTR);
            if (uniLevel != null && uniLevel.getNodeType() == Node.ATTRIBUTE_NODE
                    && maxLevel != null && maxLevel.getNodeType() == Node.ATTRIBUTE_NODE) {
                try {
                    uniLevelValue = Integer.parseInt(((Attr) uniLevel).getTextContent());
                    maxLevelValue = Integer.parseInt(((Attr) maxLevel).getTextContent());
                    if (uniLevelValue > 0 && maxLevelValue > 0) {
                        return new UnificationLevel(uniLevelValue, maxLevelValue);
                    }
                } catch (NumberFormatException ex) {
                    return new UnificationLevel();
                }
            }
        }

        return new UnificationLevel();

    }

}
