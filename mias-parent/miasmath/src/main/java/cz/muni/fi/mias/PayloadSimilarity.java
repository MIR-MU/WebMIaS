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

import cz.muni.fi.mias.math.PayloadHelper;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.util.BytesRef;

/**
 * PayloadSimilarity class extending default similarity. Alters the way how
 * lucene scores hit documents in order to consider payloads located at formuale
 * in index.
 *
 * @author Martin Liska
 */
public class PayloadSimilarity extends DefaultSimilarity {

    @Override
    public float scorePayload(int docId, int start, int end, BytesRef byteRef) {
        float score = 1.0F;
        if (byteRef.bytes != BytesRef.EMPTY_BYTES) {
            score = PayloadHelper.decodeFloatFromShortBytes(byteRef.bytes);
        }
        return score;
    }
}
