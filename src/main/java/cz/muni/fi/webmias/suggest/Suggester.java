/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.webmias.suggest;

import java.util.List;

/**
 *
 * @author mato
 */
public interface Suggester {
    
    public List<String> suggest(String term);
    
}
