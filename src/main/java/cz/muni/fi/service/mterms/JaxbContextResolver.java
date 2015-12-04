/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.service.mterms;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import cz.muni.fi.mias.search.Result;
import cz.muni.fi.service.search.Results;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;

/**
 *
 * @author mato
 */
//@Provider
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
