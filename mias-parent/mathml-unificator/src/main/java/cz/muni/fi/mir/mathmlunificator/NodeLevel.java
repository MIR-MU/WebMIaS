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

/**
 * Representation of multi-level order of elements in
 * {@link MathMLUnificator#nodesByDepth} data structure.
 *
 * @author Michal Růžička
 * @param <MajorLevel> The top level order in the hierarchy. Expected to be
 * {@link Integer}.
 * @param <MinorLevel> The second (lower) level order in the hierarchy. Expected
 * to be {@link Integer}.
 */
public class NodeLevel<MajorLevel, MinorLevel> {

    /**
     * <p>
     * The top level order the object in the hierarchy. Usually will be
     * {@link Integer}.
     * </p>
     * <p>
     * In {@link MathMLUnificator} the major level represents depth of the
     * {@link org.w3c.dom.Node} in the document DOM.
     * </p>
     */
    public MajorLevel major;

    /**
     * <p>
     * The second (lower) level order the object in the hierarchy. Usually will
     * be {@link Integer}.
     * </p>
     * <p>
     * In {@link MathMLUnificator} the minor level represents type of the
     * {@link org.w3c.dom.Node} in MathML: <code>1</code> represents elements of
     * MathML operators, <code>2</code> represents other types of MathML
     * elements.
     * </p>
     */
    public MinorLevel minor;

    /**
     * Construct an instance of representation of multi-level order of elements
     * in {@link MathMLUnificator#nodesByDepth} data structure.
     *
     * @param major The top level order the object in the hierarchy. In
     * {@link MathMLUnificator} expected to be {@link Integer} representing
     * depth of the {@link org.w3c.dom.Node} in the document DOM.
     * @param minor The second (lower) level order the object in the hierarchy.
     * In {@link MathMLUnificator} expected to be {@link Integer} representing
     * the type of the {@link org.w3c.dom.Node} in MathML: <code>1</code>
     * represents elements of MathML operators, <code>2</code> represents other
     * types of MathML elements.
     */
    public NodeLevel(MajorLevel major, MinorLevel minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public String toString() {
        return major + "." + minor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.major);
        hash = 97 * hash + Objects.hashCode(this.minor);
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
        final NodeLevel<?, ?> other = (NodeLevel<?, ?>) obj;
        if (!Objects.equals(this.major, other.major)) {
            return false;
        }
        if (!Objects.equals(this.minor, other.minor)) {
            return false;
        }
        return true;
    }

}
