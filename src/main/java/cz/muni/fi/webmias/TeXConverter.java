package cz.muni.fi.webmias;

import cz.muni.fi.mias.MIaSUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import uk.ac.ed.ph.snuggletex.SerializationMethod;
import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleSession;
import uk.ac.ed.ph.snuggletex.XMLStringOutputOptions;

/**
 * Class providing TeX to MathML conversion.
 * 
 * @author Martin Liska
 */
public class TeXConverter {
    
    public static String PARSE_ERROR = "parse_error";

    /**
     * Converts TeX formula to MathML using LaTeXML through a web service.
     * 
     * @param query String containing one or more TeX formulae enclosed in $ or $$
     * @return String containing formulae converted to MathML that replaced original TeX forms. 
     * Non math tokens are connected at the end.
     */
    public static String convertTexLatexML(String query) {
        query = query.replaceAll("\\$\\$", "\\$");
        String[] split = query.split("\\$");
        String textTerms = "";
        String toConvert = "";
        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                if (!split[i].trim().isEmpty()) {
                    textTerms += " " + split[i];
                }
            } else {
                toConvert += " $" + split[i] + "$";
            }
        }
        query = "\\input amstex\\documentclass[a4paper]{article}\\usepackage{amsmath}\\usepackage{amsbsy}\\usepackage{amstext}\\usepackage{amsxtra}\\usepackage{amsopn}\\begin{document}" + toConvert + "\\end{document}";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://mir.fi.muni.cz/cgi-bin/latex-converter.cgi");
            Credentials credentials = new UsernamePasswordCredentials("liska", "eir0Aid0");
            BasicScheme authScheme = new BasicScheme();
            Header authHeader = authScheme.authenticate(credentials, httppost, null);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("code", new StringBody(query));
            httppost.setEntity(reqEntity);
            httppost.addHeader(authHeader);
            HttpResponse response = httpclient.execute(httppost);
            int status = response.getStatusLine().getStatusCode();
            EntityUtils.consume(response.getEntity());
            if (status == 200) {
                HttpGet httpGet = new HttpGet("https://mir.fi.muni.cz/latex-converter-output/latexmlpost-output.xhtml");
                httpGet.addHeader(authHeader);
                response = httpclient.execute(httpGet);
                HttpEntity resEntity = response.getEntity();
                DocumentBuilder dBuilder = MIaSUtils.prepareDocumentBuilder();
                org.w3c.dom.Document doc = dBuilder.parse(resEntity.getContent());
                NodeList ps = doc.getElementsByTagName("p");
                String convertedMath = "";
                for (int k = 0; k < ps.getLength(); k++) {
                    Node p = ps.item(k);
                    NodeList pContents = p.getChildNodes();
                    for (int j = 0; j < pContents.getLength(); j++) {
                        Node pContent = pContents.item(j);
                        if (pContent instanceof Text) {
                            convertedMath += pContent.getNodeValue() + "\n";
                        } else {
                            TransformerFactory transFactory = TransformerFactory.newInstance();
                            Transformer transformer = transFactory.newTransformer();
                            StringWriter buffer = new StringWriter();
                            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                            transformer.transform(new DOMSource(pContent), new StreamResult(buffer));
                            convertedMath += buffer.toString() + "\n";
                        }
                    }
                }
                return convertedMath + textTerms;
            }
        } catch (TransformerException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AuthenticationException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return textTerms;
    }

    /**
     * Converts TeX formula to MathML using SnuggleTeX library.
     * 
     * @param query String containing one or more TeX formulae enclosed in $ or $$
     * @return String with TeX formulae replaced by their MathML representations. Positions of other tokens are retained.
     * @throws IOException 
     */
    public static String convertTexSnuggle(String query) {
        try {
            SnuggleEngine engine = new SnuggleEngine();
            SnuggleSession session = engine.createSession();
            session.getConfiguration().setFailingFast(true);
            SnuggleInput input = new SnuggleInput(query);
            boolean parseInput = session.parseInput(input);
            if (!parseInput) {
                return PARSE_ERROR;
            }
            XMLStringOutputOptions options = new XMLStringOutputOptions();
            options.setSerializationMethod(SerializationMethod.XHTML);
            options.setIndenting(true);
            options.setEncoding("UTF-8");
            options.setAddingMathSourceAnnotations(false);
            options.setUsingNamedEntities(true);
            String convertedMath = session.buildXMLString(options);
            return convertedMath;
        } catch (IOException ex) {
            Logger.getLogger(TeXConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
