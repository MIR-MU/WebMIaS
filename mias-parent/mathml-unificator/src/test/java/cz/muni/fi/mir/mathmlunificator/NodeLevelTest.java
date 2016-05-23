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

import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author Michal Růžička
 */
public class NodeLevelTest {

    @Test
    public void testToString() {
        NodeLevel<Integer, Integer> nl = new NodeLevel<>(2, 5);
        assertEquals("2.5", nl.toString());
    }

    @Test
    public void testHashCode() {
        NodeLevel<Integer, Integer> nl = new NodeLevel<>(2, 5);
        assertEquals(66062, nl.hashCode());
    }

    @Test
    public void testEquals() {
        NodeLevel<Integer, Integer> nlA = new NodeLevel<>(2, 5);
        NodeLevel<Integer, Integer> nlB = new NodeLevel<>(Integer.valueOf("2"), Integer.valueOf("5"));
        NodeLevel<Integer, Integer> nlX = new NodeLevel<>(5, 2);
        assertEquals(true, nlA.equals(nlA));
        assertEquals(true, nlA.equals(nlB));
        assertEquals(false, nlA.equals(nlX));

    }

}
