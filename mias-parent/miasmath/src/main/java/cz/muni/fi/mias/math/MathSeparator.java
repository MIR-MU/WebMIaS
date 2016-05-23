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

/**
 * Class providing functionality to separate text content from the formulae in
 * the given input.
 *
 * @author Martin Liska
 * @since 14.5.2010
 */
public class MathSeparator {

    private MathSeparator() {
    }

    /**
     * Separates normal text and MathML formulae with given prefix.
     *
     * @param content String input to be separated.
     * @param prefix Prefix of MathML notation in given content.
     * @return String field of size 2. [0] holds seoparated text and [1] holds
     * separated formulae.
     */
    public static String[] separate(String content, String prefix) {
        if (prefix != null && prefix.trim().length() > 0) {
            prefix = prefix + ":";
        } else {
            prefix = "";
        }
        String[] result = new String[2];
        String math = "";
        if (content.contains("<" + prefix + "math")) {
            StringBuilder sb = new StringBuilder(content);
            String start = "<" + prefix + "math";
            String end = "/" + prefix + "math>";
            int i1 = 0, i2 = 0;
            while ((i1 = sb.indexOf(start, i1)) != -1) {
                i2 = sb.indexOf(end, i1 + 1) + end.length();
                math += sb.substring(i1, i2);
                sb.delete(i1, i2);
            }
            content = sb.toString();
            content = content.trim();
        }
        result[0] = content;
        result[1] = math;
        return result;
    }

}
