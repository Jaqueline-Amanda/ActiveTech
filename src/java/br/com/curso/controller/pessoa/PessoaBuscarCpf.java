package br.com.curso.controller.pessoa;

import br.com.curso.dao.AdministradorDAO;
import br.com.curso.dao.AlunoDAO;
import br.com.curso.dao.PessoaDAO;
import br.com.curso.dao.ProfessorDAO;
import br.com.curso.model.Administrador;
import br.com.curso.model.Aluno;
import br.com.curso.model.Pessoa;
import br.com.curso.model.Professor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PessoaBuscarCpf", urlPatterns = {"/PessoaBuscarCpf"})
public class PessoaBuscarCpf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        try{
           
            int ra =  Integer.parseInt(request.getParameter("ra"));
            String cpf = request.getParameter("cpfcnpjpessoa");
            String tipoPessoa = request.getParameter("tipopessoa");
            String jsonRetorno="";
            if (tipoPessoa.equals("administrador")){
                AdministradorDAO oAdmDAO = new AdministradorDAO();
                
                Administrador oAdm = (Administrador) oAdmDAO.carregar(oAdmDAO.verificarCpf(cpf));
                
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                jsonRetorno = gson.toJson(oAdm);
                
            } else if (tipoPessoa.equals("professor")){
                ProfessorDAO oProfessorDAO = new ProfessorDAO();

                Professor oProfessor = (Professor) oProfessorDAO.carregar(oProfessorDAO.verificarCpf(cpf));

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                jsonRetorno = gson.toJson(oProfessor);   
            }else if (tipoPessoa.equals("aluno")){
                AlunoDAO oAlunoDAO = new AlunoDAO();

                Aluno oAluno = (Aluno) oAlunoDAO.carregar(oAlunoDAO.verificarRA(ra));

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                jsonRetorno = gson.toJson(oAluno);   
            }
            
            else {
                PessoaDAO oPessoaDAO = new PessoaDAO();
                Pessoa oPessoa = oPessoaDAO.carregarCpf(cpf);
                
                
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                jsonRetorno = gson.toJson(oPessoa);
            }
            
            response.setCharacterEncoding("iso-8859-1");
            response.getWriter().write(jsonRetorno);
            
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar pessoa por CPF"
            + "Erro: " + ex.getMessage());
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
