/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.service.mterms;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * REST Web Service
 *
 * @author mato
 */

@XmlRootElement(name = "formula")
public class MIaSTerm {

    public String term;
    public float weight;

    /** Creates a new instance of MIaSTerm */
    public MIaSTerm(String term, float weight) {
        this.term = term;
        this.weight = weight;
    }

    public MIaSTerm() {}

//    /**
//     * Retrieves representation of an instance of pojo.MIaSTerm
//     * @return an instance of java.lang.String
//     */
//    @GET
//    @Produces("text/plain")
//    public String getJson() {
//        return "{\"term\": \""+term+"\",\n \"weight\": \""+weight+"\"}";
//    }
}
