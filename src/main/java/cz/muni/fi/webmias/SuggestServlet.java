package cz.muni.fi.webmias;

import com.google.gson.Gson;
import cz.muni.fi.webmias.suggest.MathNamesSuggester;
import cz.muni.fi.webmias.suggest.Suggester;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for instant TeX to MathML conversion requests
 * 
 * @author Martin Liska
 */
public class SuggestServlet extends HttpServlet {
    
    private Suggester suggester;
    
    public void init() {
        suggester = new MathNamesSuggester();
//        suggester = new TitlesSuggester(Indexes.getSearcher(0).getIndexReader());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String query = request.getParameter("query");
        query = query.replaceAll("\\$\\$", "\\$");
        String[] split = query.split("\\$");
        StringBuilder textQuery = new StringBuilder();
        for (int i = 0; i < split.length; i++ ) {
            if (i%2==0) {
                if (!split[i].trim().isEmpty()) {
                    textQuery.append(split[i]).append(" ");
                }
            }
        }
        query = textQuery.substring(0, textQuery.length()-1);
        List<String> suggest = suggester.suggest(query);
//        System.out.println(suggest);
        response.getWriter().write(new Gson().toJson(suggest));
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
