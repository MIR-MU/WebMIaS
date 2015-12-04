/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.service.mterms;

import cz.muni.fi.mias.math.Formula;
import cz.muni.fi.mias.math.MathMLConf;
import cz.muni.fi.mias.math.MathTokenizer;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mato
 */
@WebServlet(name = "MtermServlet", urlPatterns = {"/mterms"})
public class MtermServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String query = request.getParameter("mathml");
        
        List<MIaSTerm> result = new ArrayList<MIaSTerm>();
        String mathQuery = "<html>" + query + "</html>";
        MathTokenizer mt = new MathTokenizer(new StringReader(mathQuery), true, MathTokenizer.MathMLType.BOTH);
        Map<Integer, List<Formula>> forms = mt.getFormulae();
        for (int i = 0; i < forms.size(); i++) {
            List<Formula> flist = forms.get(i);
            for (int j = 0; j < flist.size(); j++) {
                Formula f = flist.get(j);
                result.add(new MIaSTerm(Formula.nodeToString(f.getNode(), false, MathMLConf.getElementDictionary(), MathMLConf.getAttrDictionary(), MathMLConf.getIgnoreNode()), f.getWeight()));
            }
        }
        MIaSTermContainer fc = new MIaSTermContainer();
        fc.setForms(result);
        
        StringWriter sw = new StringWriter();
        JsonMarshallHelper.marshall(fc, sw);
        
        response.getWriter().write(sw.toString());
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
