/*
 * Copyright 2016 MIR@MU Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.webmias;

import cz.muni.fi.mias.MIaSUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
    public static final String LATEX_TO_XHTML_CONVERSION_WS_URL = "https://mir.fi.muni.cz/cgi-bin/latex-to-mathml-via-latexml.cgi";

    /**
     * Converts TeX formula to MathML using LaTeXML through a web service.
     *
     * @param query String containing one or more keywords and TeX formulae
     * (formulae enclosed in $ or $$).
     * @return String containing formulae converted to MathML that replaced
     * original TeX forms. Non math tokens are connected at the end.
     */
    public static String convertTexLatexML(String query) {
        query = query.replaceAll("\\$\\$", "\\$");
        if (query.matches(".*\\$.+\\$.*")) {
            try {
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(LATEX_TO_XHTML_CONVERSION_WS_URL);

                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<>(1);
                params.add(new BasicNameValuePair("code", query));
                httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

                // Execute and get the response.
                HttpResponse response = httpclient.execute(httppost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        try (InputStream responseContents = resEntity.getContent()) {
                            DocumentBuilder dBuilder = MIaSUtils.prepareDocumentBuilder();
                            org.w3c.dom.Document doc = dBuilder.parse(responseContents);
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
                            return convertedMath;
                        }
                    }
                }

            } catch (TransformerException | SAXException | ParserConfigurationException | IOException ex) {
                Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return query;
    }

    /**
     * Converts TeX formula to MathML using SnuggleTeX library.
     *
     * @param query String containing one or more TeX formulae enclosed in $ or
     * $$
     * @return String with TeX formulae replaced by their MathML
     * representations. Positions of other tokens are retained.
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
