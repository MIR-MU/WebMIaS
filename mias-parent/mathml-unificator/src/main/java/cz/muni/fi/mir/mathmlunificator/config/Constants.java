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
package cz.muni.fi.mir.mathmlunificator.config;

/**
 * Various constants definitions used by MathML Unificator.
 *
 * @author Michal Růžička
 */
public class Constants {

    /**
     * MathML XML namespace.
     */
    public static final String MATHML_NS = "http://www.w3.org/1998/Math/MathML";

    /**
     * MathML XML root element name.
     */
    public static final String MATHML_ROOT_ELEM = "math";

    /**
     * Presentation MathML operator element name.
     */
    public static final String PMATHML_OPERATOR = "mo";

    /**
     * Presentation MathML identifier element name.
     */
    public static final String PMATHML_IDENTIFIER = "mi";

    /**
     * MathML Unificator XML markup namespace.
     */
    public static final String UNIFIED_MATHML_NS = "http://mir.fi.muni.cz/mathml-unification/";

    /**
     * MathML Unificator XML markup namespace prefix.
     */
    public static final String UNIFIED_MATHML_NS_PREFIX = "uni";

    /**
     * MathML Unificator XML root element name for batch processing output.
     * documents
     */
    public static final String UNIFIED_MATHML_BATCH_OUTPUT_ROOT_ELEM = "unified-math-batch";

    /**
     * MathML Unificator XML item element name for batch processing output.
     * documents
     */
    public static final String UNIFIED_MATHML_BATCH_OUTPUT_ITEM_ELEM = "unified-math-batch-item";

    /**
     * MathML Unificator XML name of input file attribute for batch processing
     * output item.
     */
    public static final String UNIFIED_MATHML_BATCH_OUTPUT_ITEM_FILEPATH_ATTR = "input-file";

    /**
     * MathML Unificator XML root element name.
     */
    public static final String UNIFIED_MATHML_ROOT_ELEM = "unified-math";

    /**
     * MathML Unification current unification level XML attribute.
     */
    public static final String UNIFIED_MATHML_LEVEL_ATTR = "unification-level";

    /**
     * MathML Unification number of unification levels XML attribute.
     */
    public static final String UNIFIED_MATHML_MAX_LEVEL_ATTR = "unification-max-level";

    /**
     * Symbol used in MathML Unificator XML markup for XML tree substitutions.
     */
    public static final String UNIFICATOR = "\u25CD";

}
