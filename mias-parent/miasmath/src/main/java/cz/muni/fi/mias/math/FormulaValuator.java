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
 * Interface for computing formula's complexity.
 *
 * @author Martin Liska
 */
public interface FormulaValuator {

    /**
     * @param node MathML node denoting a formula
     * @param mmlType Type of the MathML which needs to be considered in the
     * valuation
     * @return formula's complexity
     */
    float count(Node node, MathTokenizer.MathMLType mmlType);

}
