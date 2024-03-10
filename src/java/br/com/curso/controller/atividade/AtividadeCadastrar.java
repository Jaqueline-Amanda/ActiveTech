package br.com.curso.controller.atividade;

import br.com.curso.dao.AtividadeDAO;
import br.com.curso.dao.GenericDAO;
import br.com.curso.dao.DisciplinaDAO;
import br.com.curso.model.Atividade;
import br.com.curso.model.Disciplina;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AtividadeCadastrar", urlPatterns = {"/AtividadeCadastrar"})
public class AtividadeCadastrar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         int idAtividade = Integer.parseInt(request.getParameter("idatividade"));
        int idDisciplina = Integer.parseInt(request.getParameter("iddisciplina"));
        String descricao = request.getParameter("descricao");
        String situacao = request.getParameter("situacao");
        String status = request.getParameter("status");
        String documento =request.getParameter("documento");
        int pontuacaomax = Integer.parseInt(request.getParameter("pontuacaomax"));
        String mensagem = null;
       try{
           Atividade oAtividade = new Atividade();
           DisciplinaDAO oDisciplina = new DisciplinaDAO();
           oAtividade.setDisciplina((Disciplina) oDisciplina.carregar(idDisciplina));
           oAtividade.setIdAtividade(idAtividade);
           oAtividade.setDescricao(descricao);
           oAtividade.setSituacao(situacao);
           oAtividade.setStatus(status);
           oAtividade.setDataPrazo(Date.valueOf(request.getParameter("dataprazo")));
           oAtividade.setDocumento(documento);
           oAtividade.setPontuacaomax(pontuacaomax);
           GenericDAO dao = new AtividadeDAO();
           
           if(dao.cadastrar(oAtividade)){
              response.getWriter().write("1");
            }else{
               response.getWriter().write("0");
           }
          
        }catch(Exception ex){
            System.out.println("Problemas no servlet ao inserir Atividade! Erro: "+ex.getMessage());
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
