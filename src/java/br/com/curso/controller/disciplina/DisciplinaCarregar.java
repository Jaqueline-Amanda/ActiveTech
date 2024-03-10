
package br.com.curso.controller.disciplina;

import br.com.curso.dao.DisciplinaDAO;
import br.com.curso.dao.SemestreDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DisciplinaCarregar", urlPatterns = {"/DisciplinaCarregar"})
public class DisciplinaCarregar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        try {
            int idDisciplina = Integer.parseInt(request.getParameter("idDisciplina"));
            DisciplinaDAO oDisciplinaDAO = new DisciplinaDAO();
            request.setAttribute("disciplina", oDisciplinaDAO.carregar(idDisciplina));
            SemestreDAO oSemestreDAO = new SemestreDAO();
            request.setAttribute("semestres", oSemestreDAO.listar());
            request.getRequestDispatcher("/cadastros/disciplina/disciplinaCadastrar.jsp").forward(request, response);
        } catch (Exception ex) {
            System.out.println("Erro carregar disciplina "+ex.getMessage());
            ex.printStackTrace();
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

}
