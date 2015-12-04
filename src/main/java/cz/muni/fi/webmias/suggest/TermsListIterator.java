/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.webmias.suggest;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.search.spell.TermFreqIterator;
import org.apache.lucene.util.BytesRef;

/**
 *
 * @author mato
 */
public class TermsListIterator implements TermFreqIterator {
    
    private Iterator<String> iterator;
    
    public TermsListIterator(List<String> titles) {
        this.iterator = titles.iterator();
    }

    @Override
    public long weight() {
        return 1;
    }

    @Override
    public BytesRef next() throws IOException {
        if (iterator.hasNext()) {
            return new BytesRef(iterator.next());
        }
        return null;
    }

    @Override
    public Comparator<BytesRef> getComparator() {
        return null;
    }
    
}
