/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.webmias.suggest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.util.Version;

/**
 *
 * @author mato
 */
public class MathNamesSuggester implements Suggester {
    
    private AnalyzingSuggester suggester;

    public MathNamesSuggester() {
        try {
            suggester = new AnalyzingSuggester(new StandardAnalyzer(Version.LUCENE_4_10_2));
            suggester.build(new PlainTextDictionary(getMathNamesReader()));
        } catch (IOException ex) {
            Logger.getLogger(MathNamesSuggester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<String> getMathNames() {
        List<String> result = new ArrayList<String>();
        try {
            InputStream resourceAsStream = MathNamesSuggester.class.getResourceAsStream("math_dictionary.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                if (split[0].equals("en")) {
                    String mathName = line.substring(line.indexOf(" ")+1);
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
            List<String> result = new ArrayList<String>();
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
