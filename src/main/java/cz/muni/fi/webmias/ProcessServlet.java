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

import cz.muni.fi.mias.Settings;
import cz.muni.fi.mias.search.Searching;
import cz.muni.fi.mias.math.MathSeparator;
import cz.muni.fi.mias.math.MathTokenizer;
import cz.muni.fi.mias.math.MathTokenizer.MathMLType;
import cz.muni.fi.mias.search.Result;
import cz.muni.fi.mias.search.SearchResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.lucene.search.IndexSearcher;

/**
 * @author Martin Liska
 */
public class ProcessServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ProcessServlet.class.getName());

    private final int resPerPage = 20;
    private Searching s;
    private File queryLog;
    private static final String QUERY_PARAMETER = "query";
    private static final boolean forbidden = false;

    @Override
    public void init(ServletConfig config) throws ServletException {
        queryLog = new File(config.getInitParameter("querylog"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(true);

        int page = Integer.parseInt(request.getParameter("n"));
        String query = request.getParameter(QUERY_PARAMETER);
        boolean debug = request.getParameter("debug") != null;
        boolean extractSubformulae = request.getParameter("extractSubformulae") != null;
        boolean reduceWeighting = request.getParameter("reduceWeighting") != null;
        String indexNo = request.getParameter("index");
        int indexNumber = 0;
        if (indexNo != null) {
            indexNumber = Integer.parseInt(indexNo);
        }
        String qc = request.getParameter("qc");
        String variant = request.getParameter("variant");
        MathMLType mmlType = getType(variant);

        IndexDef currentIndexDef = Indexes.getIndexDef(indexNumber);
        IndexSearcher searcher = currentIndexDef.getIndexSearcher();
        request.setAttribute("index", indexNumber);
        request.setAttribute(QUERY_PARAMETER, query);
        request.setAttribute("debug", debug);
        request.setAttribute("extractSubformulae", extractSubformulae);
        request.setAttribute("reduceWeighting", reduceWeighting);
        request.setAttribute("qc", qc);
        request.setAttribute("variant", variant);
        request.setAttribute("forbidden", forbidden);

        request.setAttribute("indexes", Indexes.getIndexNames());

        if (query != null && !query.isEmpty()) {
            logQuery(request, query);
            String[] sep = MathSeparator.separate(query, "");
            query = sep[1];
            if (sep[0] != null && !sep[0].isEmpty()) {
                query += " " + TeXConverter.convertTexLatexML(sep[0]);
            }

            s = new Searching(searcher, currentIndexDef.getStorage());
            page = page == 0 ? page : page - 1;
            SearchResult searchResult = s.search(query, false, page * resPerPage, resPerPage, debug, mmlType, extractSubformulae, reduceWeighting);
            long totalResults = searchResult.getTotalResults();
            request.setAttribute("processedQuery", searchResult.getProcessedQuery());
            request.setAttribute("luceneQuery", searchResult.getLuceneQuery());
            request.setAttribute("total", searchResult.getTotalResults());
            request.setAttribute("coreTime", searchResult.getCoreSearchTime());
            request.setAttribute("totalTime", searchResult.getTotalSearchTime());
            request.setAttribute("resPerPage", resPerPage);
            totalResults = Math.min(totalResults, Settings.getMaxResults());
            if (totalResults > 0 && page >= 0) {
                List<Integer> pages = new ArrayList<>();
                for (int i = 0; i < (totalResults / resPerPage) + 1; i++) {
                    pages.add(i + 1);
                }
                request.setAttribute("pages", pages);
                request.setAttribute("n", page);
                for (Result r : searchResult.getResults()) {
                    r.setInfo(r.getInfo().replaceAll("\n", "<br/>"));
                }
                request.setAttribute("results", searchResult.getResults());
            } else if (page == -1) {
                session.invalidate();
            } else {
                request.setAttribute("nores", "No results.");
            }
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void logQuery(HttpServletRequest request, String query) {
        String logMessage = "Searching query from IP:" + request.getRemoteAddr() + ":\n" + query;
        LOG.log(Level.INFO, logMessage);
        try {
            try (BufferedWriter out = new BufferedWriter(new FileWriterWithEncoding(queryLog, StandardCharsets.UTF_8, true))) {
                String log = new Date().toString() + "\n" + logMessage + "\n\n";
                out.write(log);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Cannot write to query logging file in {0}", queryLog.getAbsolutePath());
        }
    }

    private MathTokenizer.MathMLType getType(String variant) {
        if (variant == null) {
            return MathTokenizer.MathMLType.BOTH;
        }
        if (variant.equals("pmath")) {
            return MathTokenizer.MathMLType.PRESENTATION;
        }
        if (variant.equals("cmath")) {
            return MathTokenizer.MathMLType.CONTENT;
        }
        return MathTokenizer.MathMLType.BOTH;
    }

}
