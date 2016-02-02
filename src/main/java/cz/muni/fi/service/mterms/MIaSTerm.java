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
package cz.muni.fi.service.mterms;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * REST Web Service
 *
 * @author Martin Liska
 */
@XmlRootElement(name = "formula")
public class MIaSTerm {

    public String term;
    public float weight;

    /**
     * Creates a new instance of MIaSTerm
     */
    public MIaSTerm(String term, float weight) {
        this.term = term;
        this.weight = weight;
    }

    public MIaSTerm() {
    }

}
