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

import static cz.muni.fi.mias.math.MathMLConf.unifiedNodeWeightCoefModifier;
import cz.muni.fi.mir.mathmlunificator.MathMLUnificator;
import cz.muni.fi.mir.mathmlunificator.UnificationLevel;
import org.w3c.dom.Node;

/**
 * Formula valuator that returns weight coefficient of given {@link Node}. The
 * node is expected to be unified node (see
 * {@link MathMLUnificator#isUnifiedMathNode(org.w3c.dom.Node)}). If the given
 * node is not unified node the ‘neutral’ coefficient 1.00 is returned.
 *
 * @author Michal Růžička
 * @see MathMLUnificator
 */
public class UnifiedFormulaValuator implements FormulaValuator {

    /**
     * Returns the unified node weight coefficient. This coefficient is expected
     * to be combined with the weight of the source (parent of the unified node)
     * node.
     *
     * @param node MathML node to valuate.
     * @param mmlType Type of the MathML which needs to be considered in the
     * valuation. (<i>This parameter is ignored in this implementation.</i>)
     * @return 1.00 if the node is not unified node (see
     * {@link MathMLUnificator#isUnifiedMathNode(org.w3c.dom.Node)}); otherweise
     * the weight coefficient of given unified {@link Node}.
     */
    @Override
    public float count(Node node, MathTokenizer.MathMLType mmlType) {

        UnificationLevel ul = MathMLUnificator.getNodeUnificationLevel(node);
        Integer level = ul.getNodeLevel();
        Integer maxLevel = ul.getMaxLevel() + 1; // We add 1 for the original formula that is not included in the number of levels.

        if (level != null && maxLevel != null && level > 0 && maxLevel > 0) {
            return (unifiedNodeWeightCoefModifier * ((float) (maxLevel - level) / maxLevel)) / ((float) Math.pow(level, 3));
        } else {
            return 1.0f;
        }

    }

}
