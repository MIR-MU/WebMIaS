/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.service.mterms;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mato
 */
@XmlRootElement
public class MathMLTerm {

    public String mathml;

    public MathMLTerm(String mathml) {
        this.mathml = mathml;
    }

    public MathMLTerm() {}

}
