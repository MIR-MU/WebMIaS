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
    public void testMain() throws Exception {

        String testFile = "multiple-formulae";

        String[] argvNonOperators = {getTestResourceAsFilepath(testFile + ".input.xml")};
        String[] argvOperators = {"-p", getTestResourceAsFilepath(testFile + ".input.xml")};

        ByteArrayOutputStream stdoutContent = new ByteArrayOutputStream();
        PrintStream stdout = System.out;

        // non-operators
        System.setOut(new PrintStream(stdoutContent));
        MathMLUnificatorCommandLineTool.main(argvNonOperators);
        System.setOut(stdout);

        String output = stdoutContent.toString(StandardCharsets.UTF_8.toString());

        System.out.println("testMain – non-operators output:\n" + output);
        assertEquals(IOUtils.toString(getExpectedXMLTestResource(testFile + ".non-operator"), StandardCharsets.UTF_8), output);

        // operators
        stdoutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdoutContent));
        MathMLUnificatorCommandLineTool.main(argvOperators);
        System.setOut(stdout);

        output = stdoutContent.toString(StandardCharsets.UTF_8.toString());

        System.out.println("testMain – operators output:\n" + output);
        assertEquals(IOUtils.toString(getExpectedXMLTestResource(testFile + ".operator"), StandardCharsets.UTF_8), output);

    }

}
