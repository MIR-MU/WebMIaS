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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Formula valuator which values formula's complexity on the number of its nodes
 *
 * @author Martin Liska
 */
public class CountNodesFormulaValuator implements FormulaValuator {

    @Override
    public float count(Node n, MathTokenizer.MathMLType mmlType) {
        if (mmlType == MathTokenizer.MathMLType.BOTH) {
            mmlType = MathTokenizer.MathMLType.PRESENTATION;
        }
        float result = 0;
        if (n instanceof Element) {
            String name = n.getLocalName();
            if (!MathMLConf.ignoreNodeAndChildren(name)) {
                boolean count = false;
                if (((mmlType == MathTokenizer.MathMLType.BOTH && MathMLConf.isIndexableElement(name))
                        || (mmlType == MathTokenizer.MathMLType.PRESENTATION && MathMLConf.isIndexablePresentationElement(name))
                        || (mmlType == MathTokenizer.MathMLType.CONTENT && MathMLConf.isIndexableContentElement(name)))) {
                    count = true;
                }
                NodeList nl = n.getChildNodes();
                for (int i = 0; i < nl.getLength(); i++) {
                    result += count(nl.item(i), mmlType);
                }
                if (count) {
                    result += 1.0;
                }
            }
        }
        return result;
    }

}
