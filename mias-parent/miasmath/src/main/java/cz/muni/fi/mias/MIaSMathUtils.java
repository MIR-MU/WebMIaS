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
package cz.muni.fi.mias;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utilities class.
 *
 * @author Martin Liska
 */
public class MIaSMathUtils {

    private static final Logger log = Logger.getLogger(MIaSMathUtils.class.getName());

    private static final String MATHML_DTD = "/cz/muni/fi/mias/math/xhtml-math11-f.dtd";

    public static DocumentBuilder prepareDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId)
                    throws SAXException, java.io.IOException {
                log.log(Level.FINEST, "{0} {1}", new Object[]{publicId, systemId});
                if (systemId.endsWith("dtd")) {
                    return new InputSource(MIaSMathUtils.class.getResourceAsStream(MATHML_DTD));
                } else {
                    return null;
                }
            }
        });
        return builder;
    }
}
