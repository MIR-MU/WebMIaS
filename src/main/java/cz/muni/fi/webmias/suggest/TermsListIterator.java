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

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefIterator;

/**
 * @author Martin Liska
 */
// TODO the interface TermFreqIterator disappeared without
// any reference between Lucene 4.10.4 (the latest Lucene 4.x)
// and Lucene 5.0.0 (the first Lucene 5.x)
// TODO TermsListIterator not used anywhere since the very first commit?
public class TermsListIterator implements BytesRefIterator {

    private final Iterator<String> iterator;

    public TermsListIterator(List<String> titles) {
        this.iterator = titles.iterator();
    }

    // @Override
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

    // @Override
    public Comparator<BytesRef> getComparator() {
        return null;
    }

}
