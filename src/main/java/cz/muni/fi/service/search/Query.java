package cz.muni.fi.service.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Query class needed for OpenSearch
 * 
 * @author Martin Liska
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Query {
    
    @XmlAttribute
    private String role = "request";
    @XmlAttribute
    private String searchTerms;

    public Query(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public Query() {
    }
    
    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
