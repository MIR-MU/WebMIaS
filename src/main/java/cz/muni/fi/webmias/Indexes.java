package cz.muni.fi.webmias;

import cz.muni.fi.mias.Settings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

/**
 * Class responsible for loading indexes from indexes.properties file
 * 
 * @author Martin Liska
 */
public class Indexes {

    private static final List<IndexDef> indexes = new ArrayList<>();
    private static final char dirSep = System.getProperty("file.separator").charAt(0);
    
    
    static {
        try {
            Properties prop = new Properties();
            prop.load(Indexes.class.getResourceAsStream("indexes.properties"));
            String[] indexesNames = prop.getProperty("INDEX_NAMES").split(",");
            String[] indexesPaths = prop.getProperty("PATHS").split(",");
            String[] storageArray = prop.getProperty("STORAGES").split(",");
            for (int i = 0; i < indexesNames.length; i++) {
                String name = indexesNames[i];
                IndexSearcher is = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File(indexesPaths[i]))));
                String storage = storageArray[i];
                int sl = storage.length();
                if (storage.charAt(sl - 1) != dirSep) {
                    storage = storage + dirSep;
                }
                
                IndexDef indexDef = new IndexDef(name, storage, is);
                indexes.add(indexDef);
            }
            Settings.init();
            Settings.setMaxResults(prop.getProperty("MAXRESULTS"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Indexes() {
    }
    
    public static IndexDef getIndexDef(int i) {
        return indexes.get(i);
    }
    
    public static String[] getIndexNames() {
        String[] result = new String[indexes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = indexes.get(i).getName();
        }
        
        return result;
    }
    
}
