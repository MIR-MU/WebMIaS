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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for instant TeX to MathML conversion requests
 *
 * @author Martin Liska
 */
public class InputConversionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String query = request.getParameter("query");
        query = query.replaceAll("\\$\\$", "\\$");
        String[] split = query.split("\\$");
        String convertedQuery = "";
        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                if (!split[i].trim().isEmpty()) {
                    convertedQuery += "<span class=\"text\">" + split[i] + "</span>";
                }
            } else {
                String toConvert = ("$" + split[i] + "$");
                String convertedMath = TeXConverter.convertTexSnuggle(toConvert);
                if (convertedMath.equals(TeXConverter.PARSE_ERROR)) {
                    response.getWriter().write(convertedMath);
                } else {
                    convertedQuery += "<span class=\"formula\">" + convertedMath.replaceAll("display=\"block\"", "display=\"inline\"") + "</span>";
                }
            }
        }
        response.getWriter().write(convertedQuery);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
