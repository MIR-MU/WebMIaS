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
 * Helper class for converting different number types for payloads
 *
 * @author Martin Liska
 */
public class PayloadHelper extends org.apache.lucene.analysis.payloads.PayloadHelper {

    /**
     * Converts short number to byte array
     */
    public static byte[] encodeShort(short payload) {
        byte[] data = new byte[2];
        data[0] = (byte) (payload >> 8);
        data[1] = (byte) payload;
        return data;
    }

    /**
     * Converts byte array to short number
     */
    public static short decodeShort(byte[] bytes) {
        return (short) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
    }

    /**
     * Converts float number to byte array
     */
    public static byte[] encodeFloatToShort(float f) {
        return encodeShort((short) (Math.round(Math.log10(f) * 3276) + 32768));
    }

    /**
     * Converts byte array to float number
     */
    public static float decodeFloatFromShortBytes(byte[] bytes) {
        float result = (float) decodeShort(bytes);
        return result / 10000;
    }

}
