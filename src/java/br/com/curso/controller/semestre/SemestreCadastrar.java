
package br.com.curso.controller.semestre;

import br.com.curso.dao.SemestreDAO;
import br.com.curso.dao.GenericDAO;
import br.com.curso.model.Semestre;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SemestreCadastrar", urlPatterns = {"/SemestreCadastrar"})
public class SemestreCadastrar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        int idSemestre = Integer.parseInt(request.getParameter("idsemestre"));
        String numSemestre = request.getParameter("numsemestre");
         String situacao = request.getParameter("situacao");
        String mensagem = null;
        Semestre oSemestre = new Semestre();
        oSemestre.setIdSemestre(idSemestre);
        oSemestre.setNumSemestre(numSemestre);
        oSemestre.setSituacao(situacao);
        try{
            GenericDAO dao = new SemestreDAO();
            if (dao.cadastrar(oSemestre)){
                response.getWriter().write("1");
            }else{
               response.getWriter().write("0");
           }
           
        } catch (Exception ex){
            System.out.println("Problemas no Servlet ao cadastrar Semestre. Erro: "+ex.getMessage());           
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
