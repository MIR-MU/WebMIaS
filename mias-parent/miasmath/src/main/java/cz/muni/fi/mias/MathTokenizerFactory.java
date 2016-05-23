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
package cz.muni.fi.mias;

import cz.muni.fi.mias.math.MathTokenizer;
import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

/**
 * Factory used for calling MathTokenizer from SOLR environment. The following
 * attributes must be specified in the schema.xml file for tokenizer
 * MathTokenizer:
 * <ul>
 * <li>
 * subformulae – true for analyzer type index, false for analyzer type query
 * </li>
 * </ul>
 *
 * Complete example:
 * <pre>{@code
 * <fieldType name="math" class="solr.TextField">
 *   <analyzer type="index">
 *     <tokenizer class="cz.muni.fi.mias.MathTokenizerFactory" subformulae="true"  />
 *   </analyzer>
 *   <analyzer type="query">
 *     <tokenizer class="cz.muni.fi.mias.MathTokenizerFactory" subformulae="false" />
 *   </analyzer>
 * </fieldType>
 * }</pre>
 *
 * @author Martin Liska
 */
public class MathTokenizerFactory extends TokenizerFactory {

    private final boolean subformulae;

    public MathTokenizerFactory(Map<String, String> args) {
        super(args);
        String subforms = args.get("subformulae");
        subformulae = Boolean.parseBoolean(subforms);
    }

    @Override
    public Tokenizer create(AttributeFactory af, Reader reader) {
        return new MathTokenizer(reader, subformulae, MathTokenizer.MathMLType.BOTH);
    }

}
