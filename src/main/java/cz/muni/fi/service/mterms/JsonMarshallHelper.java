package cz.muni.fi.service.mterms;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author mato
 */
public class JsonMarshallHelper {
    
    public static void marshall(Object o, Writer w) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.writeValue(w, o);
        } catch (JsonGenerationException ex) {
            Logger.getLogger(JsonMarshallHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonMappingException ex) {
            Logger.getLogger(JsonMarshallHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonMarshallHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Object unmarshall(String s, Class c) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Object readValue = mapper.readValue(s, c);
            return readValue;
        } catch (JsonParseException ex) {
            Logger.getLogger(JsonMarshallHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonMappingException ex) {
            Logger.getLogger(JsonMarshallHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonMarshallHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
