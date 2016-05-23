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
public class UnificationLevelTest {

    private UnificationLevel nullUL;
    private UnificationLevel ul;

    @Before
    public void setUp() throws Exception {
        nullUL = new UnificationLevel();
        ul = new UnificationLevel(4, 6);
    }

    @After
    public void tearDown() throws Exception {
        nullUL = null;
        ul = null;
    }

    @Test
    public void testGetNodeLevel() {
        assertEquals(null, nullUL.getNodeLevel());
        assertEquals(Integer.valueOf("4"), ul.getNodeLevel());
    }

    @Test
    public void testGetMaxLevel() {
        assertEquals(null, nullUL.getMaxLevel());
        assertEquals(Integer.valueOf("6"), ul.getMaxLevel());
    }

    @Test
    public void testToString() {
        assertEquals("null:null", nullUL.toString());
        assertEquals("4:6", ul.toString());
    }

    @Test
    public void testHashCode() {
        assertEquals(65863, nullUL.hashCode());
        assertEquals(66257, ul.hashCode());
    }

    @Test
    public void testEquals() {

        UnificationLevel anotherNullUL = new UnificationLevel();
        UnificationLevel anotherSameUL = new UnificationLevel(4, 6);
        UnificationLevel anotherDifferentUL = new UnificationLevel(6, 4);

        assertEquals(true, nullUL.equals(nullUL));
        assertEquals(true, ul.equals(ul));
        assertEquals(true, nullUL.equals(anotherNullUL));
        assertEquals(true, ul.equals(anotherSameUL));
        assertEquals(false, ul.equals(nullUL));
        assertEquals(false, nullUL.equals(ul));
        assertEquals(false, nullUL.equals(anotherDifferentUL));
        assertEquals(false, ul.equals(anotherDifferentUL));

    }

}
