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

import java.util.Objects;
import org.w3c.dom.Node;

/**
 * Representation of unification level of a {@link Node} by
 * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)}.
 *
 * @see MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)
 *
 * @author Michal Růžička
 */
public class UnificationLevel {

    /**
     * Level of unification of a {@link Node} in the XML W3C DOM produced by
     * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)}. If
     * the node is not unified node the result is {@code null}.
     *
     * @see MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)
     */
    private Integer nodeLevel = null;

    /**
     * Maximal level of unification in the XML W3C DOM produced by
     * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)} the
     * {@link Node} belongs to. If the node is not part of the unification
     * series the result is {@code null}.
     *
     * @see MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)
     */
    private Integer maxLevel = null;

    /**
     * Construct an instance of representation of unification level of a
     * {@link Node} by
     * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)}.
     *
     * @param nodeLevel Level of unification of a {@link Node} in the XML W3C
     * DOM produced by
     * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)}. If
     * the node is not unified node the result is {@code null}.
     * @param maxLevel Maximal level of unification in the XML W3C DOM produced
     * by {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)}
     * the {@link Node} belongs to. If the node is not part of the unification
     * series the result is {@code null}.
     */
    public UnificationLevel(Integer nodeLevel, Integer maxLevel) {
        this.nodeLevel = nodeLevel;
        this.maxLevel = maxLevel;
    }

    /**
     * Construct an instance of representation of unification level of a
     * {@link Node} that is not part of unification series, i.e. both
     * {@code nodeLevel} and {@code maxLevel} will be {@code null}.
     */
    public UnificationLevel() {
        this.nodeLevel = null;
        this.maxLevel = null;
    }

    /**
     * Returns level of unification of a {@link Node} in the XML W3C DOM
     * produced by
     * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)} that
     * is represented by this instance.
     *
     * @return Unification level of the represented node or {@code null} if the
     * represented node is not unified node the result is.
     */
    public Integer getNodeLevel() {
        return this.nodeLevel;
    }

    /**
     * Returns maximal level of unification in the XML W3C DOM produced by
     * {@link MathMLUnificator#unifyMathMLNode(org.w3c.dom.Node, boolean)} of
     * the {@link Node} this instance represents.
     *
     * @return Maximal unification level of unification series the represented
     * node belongs to or {@code null} if the represented node is not part of
     * the unification series.
     */
    public Integer getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public String toString() {
        return nodeLevel + ":" + maxLevel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nodeLevel);
        hash = 97 * hash + Objects.hashCode(this.maxLevel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnificationLevel other = (UnificationLevel) obj;
        if (!Objects.equals(this.nodeLevel, other.nodeLevel)) {
            return false;
        }
        if (!Objects.equals(this.maxLevel, other.maxLevel)) {
            return false;
        }
        return true;
    }

}
