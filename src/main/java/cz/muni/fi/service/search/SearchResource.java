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
package cz.muni.fi.service.search;

import cz.muni.fi.mias.math.MathSeparator;
import cz.muni.fi.mias.search.Searching;
import cz.muni.fi.mias.search.SearchResult;
import cz.muni.fi.webmias.IndexDef;
import cz.muni.fi.webmias.Indexes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.lucene.search.IndexSearcher;

/**
 * Web service providing MIaS searching functionality
 *
 * @author Martin Liska
 */
@Path("search")
public class SearchResource {

    private static final int LIMIT = 1000;

    public SearchResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Results search(@QueryParam(value = "query") String query, @QueryParam(value = "offset") int offset, @QueryParam(value = "limit") int limit, @QueryParam(value = "index") int indexNo) {
        if (limit > LIMIT || limit <= 0) {
            limit = LIMIT;
        }
        System.out.println(query);
        String[] sep = MathSeparator.separate(query, "");
        String convertedQuery = sep[1];
        if (sep[0] != null && !sep[0].isEmpty()) {
            //convertedQuery += " " + TeXConverter.convertTexLatexML(sep[0]);
            convertedQuery += " " + sep[0];
        }
        Results r = new Results();

        IndexDef indexDef = Indexes.getIndexDef(indexNo);
        IndexSearcher is = indexDef.getIndexSearcher();
        Searching s = new Searching(is, indexDef.getStorage());
        SearchResult result = s.search(convertedQuery, false, offset, limit, false);
        r.setTime(result.getTotalSearchTime());
        r.setTotalResults(result.getTotalResults());
        r.setItemsPerPage(limit);
        r.setStartIndex(offset);
        r.setQuery(new Query(query));
        r.setResult(result.getResults());
        return r;
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public Results searchPost(@FormParam(value = "query") String query, @FormParam(value = "offset") int offset, @FormParam(value = "limit") int limit, @FormParam(value = "index") int indexNo) {
        return search(query, offset, limit, indexNo);
    }
}
