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

import org.w3c.dom.Node;

/**
 * Formula valuator which values formula's complexity on the number of its nodes
 *
 * @author Michal Růžička
 */
public class ConstantFormulaValuator implements FormulaValuator {

    /**
     * Value to be returned as the result of valuation of any given node
     */
    private final float valuatorValue;

    /**
     * Create {@link ConstantFormulaValuator} instance returning constant value
     * <em>1.0</em> for any given {@link Node}.
     */
    public ConstantFormulaValuator() {
        this(1.0f);
    }

    /**
     * Create {@link ConstantFormulaValuator} instance returning constant value
     * {@code value} for any given {@link Node}.
     *
     * @param value Value to be returned as the result of valuation of any given
     * node.
     */
    public ConstantFormulaValuator(float value) {
        this.valuatorValue = value;
    }

    /**
     * Returns constant value for any given node.
     *
     * The value depends on constant given on construction of the instance and
     * cannot be changed later. The set value for particular instance can be
     * checked via {@link #getValuatorValue()} method.
     *
     * @param node MathML node to valuate.
     * @param mmlType Type of the MathML which needs to be considered in the
     * valuation. (<i>This parameter is ignored in this implementation.</i>)
     * @return Constant value {@link #getValuatorValue()} for any given node.
     */
    @Override
    public float value(Node node, MathTokenizer.MathMLType mmlType) {
        return getValuatorValue();
    }

    /**
     * Returns constant value set to be returned for any node by this instance.
     *
     * The value depends on the constant given on construction of the instance
     * and cannot be changed.
     *
     * @return Constant value set to be returned for any node.
     */
    public float getValuatorValue() {
        return this.valuatorValue;
    }

}
