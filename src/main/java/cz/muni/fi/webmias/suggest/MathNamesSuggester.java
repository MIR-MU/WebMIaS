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
package cz.muni.fi.webmias.suggest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Martin Liska
 */
public class MathNamesSuggester implements Suggester {

    private AnalyzingSuggester suggester;

    public MathNamesSuggester() {
        try {
            // TODO tempDir set to Tomcat's temp directory
            Directory tempDir = FSDirectory.open(Paths.get("./temp"));
            String tempFileNamePrefix = "webmias-lucene-suggester";
            suggester = new AnalyzingSuggester(tempDir, tempFileNamePrefix, new StandardAnalyzer());
            suggester.build(new PlainTextDictionary(getMathNamesReader()));
        } catch (IOException ex) {
            Logger.getLogger(MathNamesSuggester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> getMathNames() {
        List<String> result = new ArrayList<>();
        try {
            InputStream resourceAsStream = MathNamesSuggester.class.getResourceAsStream("math_dictionary.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                if (split[0].equals("en")) {
                    String mathName = line.substring(line.indexOf(" ") + 1);
                    result.add(mathName);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MathNamesSuggester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private Reader getMathNamesReader() {
        List<String> mathNames = getMathNames();
        StringBuilder result = new StringBuilder();
        for (String s : mathNames) {
            result.append(s).append("\n");
        }
        return new StringReader(result.toString());
    }

    @Override
    public List<String> suggest(String key) {
        try {
            List<LookupResult> lookup = suggester.lookup(key, false, 10);
            List<String> result = new ArrayList<>();
            for (LookupResult lookupResult : lookup) {
                result.add(lookupResult.key.toString());
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(MathNamesSuggester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }

}
