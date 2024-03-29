package br.com.curso.controller.professor;

import br.com.curso.dao.GenericDAO;
import br.com.curso.dao.ProfessorDAO;
import br.com.curso.model.Professor;
import static br.com.curso.model.Professor.professorVazio;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfessorCadastrar", urlPatterns = {"/ProfessorCadastrar"})
public class ProfessorCadastrar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=iso-8859-1");
        int idPessoa = Integer.parseInt(request.getParameter("idpessoa"));
        int idProfessor = Integer.parseInt(request.getParameter("idprofessor"));
        String formacao = request.getParameter("formacaoprofessor");
        String email = request.getParameter("emailprofessor");
        String situacao = request.getParameter("situacao");
        String permiteLogin = request.getParameter("permitelogin");
        String nome = request.getParameter("nome");
        
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        Professor oProfessor = professorVazio();
        oProfessor.setIdProfessor(idProfessor);
        oProfessor.setFormacaoProfessor(formacao);
        oProfessor.setEmailProfessor(email);
        oProfessor.setSituacao(situacao);
        oProfessor.setPermiteLogin(permiteLogin);
        oProfessor.setIdPessoa(idPessoa);
        oProfessor.setNome(nome);
        
        oProfessor.setLogin(login);
        oProfessor.setSenha(senha);
        try {
            GenericDAO dao = new ProfessorDAO();
            if(dao.cadastrar(oProfessor)) {
               response.getWriter().write("1");
            } else{
               response.getWriter().write("0");
            }
        }catch(Exception ex){
            System.out.println("Problemas no servlet ao inserir Professor! Erro: "+ex.getMessage());
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ProfessorCadastrar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ProfessorCadastrar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
