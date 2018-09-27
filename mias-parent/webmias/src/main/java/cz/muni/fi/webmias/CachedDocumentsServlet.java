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

import cz.muni.fi.mias.search.Searching;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Martin Liska
 */
public class CachedDocumentsServlet extends HttpServlet {

    private static final int BUFSIZE = 4096;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String localPath = request.getParameter("path");
        String indexNumber = request.getParameter("index");
        IndexDef indexDef = Indexes.getIndexDef(Integer.valueOf(indexNumber));
        String storage = indexDef.getStorage();

        String fullPath = storage + localPath;
        InputStream inputStreamFromDataPath = getInputStreamFromDataPath(fullPath);
        String fileName = getFileName(localPath);

        try (ServletOutputStream outStream = response.getOutputStream()) {
            String mimetype = "application/xhtml+xml";

            response.setContentType(mimetype);

            byte[] byteBuffer = new byte[BUFSIZE];
            DataInputStream in = new DataInputStream(inputStreamFromDataPath);

            int length;
            while ((length = in.read(byteBuffer)) != -1) {
                outStream.write(byteBuffer, 0, length);
            }

            in.close();
        }
    }

    private InputStream getInputStreamFromDataPath(String dataPath) {

        InputStream is = null;

        try {
            File f = new File(dataPath);

            if (f.exists() && !dataPath.endsWith("zip")) {
                is = new FileInputStream(f);
            }
            if (dataPath.contains(".zip")) {
                String zipPath = getZipPath(dataPath);
                f = new File(getZipPath(dataPath));
                if (f.exists()) {
                    String archivePath = getZipFilePath(dataPath);
                    ZipFile zipFile = new ZipFile(zipPath);
                    Enumeration e = zipFile.entries();
                    while (e.hasMoreElements() && is == null) {
                        ZipEntry entry = (ZipEntry) e.nextElement();
                        if (entry.getName().equals(archivePath)) {
                            is = zipFile.getInputStream(entry);
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Searching.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return is;
        }

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

    private String getZipPath(String dataPath) {
        return dataPath.substring(0, dataPath.indexOf(".zip") + 4);
    }

    private String getZipFilePath(String dataPath) {
        return dataPath.substring(dataPath.indexOf(".zip") + 5);
    }

    private String getFileName(String localPath) {
        return localPath.substring(localPath.lastIndexOf(File.separator) + 1);

    }

}
