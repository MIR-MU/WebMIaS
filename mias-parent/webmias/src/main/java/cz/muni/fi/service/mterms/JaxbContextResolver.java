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

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import cz.muni.fi.mias.search.Result;
import cz.muni.fi.service.search.Results;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;

/**
 * @author Martin Liska
 */
public class JaxbContextResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;
    private final Class[] types = {MIaSTerm.class, MIaSTermContainer.class, MathMLTerm.class, Result.class, Results.class};

    public JaxbContextResolver() throws Exception {
        this.context = new JSONJAXBContext(JSONConfiguration.mapped().arrays("forms").nonStrings("weight").build(), types);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return context;
    }

}
