/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.service.mterms;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mato
 */
@XmlRootElement
public class MIaSTermContainer {

    public List<MIaSTerm> forms;

    public void setForms(List<MIaSTerm> forms) {
        this.forms = forms;
    }

    public MIaSTermContainer() {}

}
