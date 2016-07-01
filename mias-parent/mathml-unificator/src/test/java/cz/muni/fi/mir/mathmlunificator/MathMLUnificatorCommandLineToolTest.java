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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author Michal Růžička
 */
public class MathMLUnificatorCommandLineToolTest extends AbstractXMLTransformationTest {

    @Test
    public void testMain_multiple_formulae_non_operators() throws Exception {

        String testFile = "multiple-formulae";

        String[] argv = {getTestResourceAsFilepath(testFile + ".input.xml")};

        ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
        PrintStream stdout = System.out;

        System.setOut(new PrintStream(stdoutContent));
        MathMLUnificatorCommandLineTool.main(argv);
        System.setOut(stdout);

        String output = stdoutContent.toString(StandardCharsets.UTF_8.toString());

        System.out.println("testMain_multiple_formulae_non_operators – non-operators output:\n" + output);
        assertEquals(IOUtils.toString(getExpectedXMLTestResource(testFile + ".non-operator"), StandardCharsets.UTF_8), output);

    }

    @Test
    public void testMain_multiple_formulae_operators() throws Exception {

        String testFile = "multiple-formulae";

        String[] argv = {"-p", getTestResourceAsFilepath(testFile + ".input.xml")};

        ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
        PrintStream stdout = System.out;

        System.setOut(new PrintStream(stdoutContent));
        MathMLUnificatorCommandLineTool.main(argv);
        System.setOut(stdout);

        String output = stdoutContent.toString(StandardCharsets.UTF_8.toString());

        System.out.println("testMain_multiple_formulae_operators – operators output:\n" + output);
        assertEquals(IOUtils.toString(getExpectedXMLTestResource(testFile + ".operator"), StandardCharsets.UTF_8), output);

    }

    @Test
    public void testMain_latexml_non_operators() throws Exception {

        String testFile = "latexml";

        String[] argv = {getTestResourceAsFilepath(testFile + ".input.xml")};

        ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
        PrintStream stdout = System.out;

        System.setOut(new PrintStream(stdoutContent));
        MathMLUnificatorCommandLineTool.main(argv);
        System.setOut(stdout);

        String output = stdoutContent.toString(StandardCharsets.UTF_8.toString());

        System.out.println("testMain_latexml_non_operators – non-operators output:\n" + output);
        assertEquals(IOUtils.toString(getExpectedXMLTestResource(testFile + ".non-operator"), StandardCharsets.UTF_8), output);

    }

    @Test
    public void testMain_latexml_operators() throws Exception {

        String testFile = "latexml";

        String[] argv = {"-p", getTestResourceAsFilepath(testFile + ".input.xml")};

        ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
        PrintStream stdout = System.out;

        System.setOut(new PrintStream(stdoutContent));
        MathMLUnificatorCommandLineTool.main(argv);
        System.setOut(stdout);

        String output = stdoutContent.toString(StandardCharsets.UTF_8.toString());

        System.out.println("testMain_latexml_operators – operators output:\n" + output);
        assertEquals(IOUtils.toString(getExpectedXMLTestResource(testFile + ".operator"), StandardCharsets.UTF_8), output);

    }

}
