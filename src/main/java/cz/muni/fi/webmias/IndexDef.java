/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.webmias;

import org.apache.lucene.search.IndexSearcher;

/**
 *
 * @author mato
 */
public class IndexDef {
    
    private String name;
    private String storage;
    private IndexSearcher indexSearcher;

    public IndexDef(String name, String storage, IndexSearcher indexSearcher) {
        this.name = name;
        this.storage = storage;
        this.indexSearcher = indexSearcher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    public void setIndexSearcher(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }
    
}
