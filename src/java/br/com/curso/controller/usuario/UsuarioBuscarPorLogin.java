package br.com.curso.controller.usuario;
 
import br.com.curso.dao.UsuarioDAO;
import br.com.curso.model.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 
@WebServlet(name = "UsuarioBuscarPorLogin", urlPatterns = {"/UsuarioBuscarPorLogin"})
public class UsuarioBuscarPorLogin extends HttpServlet {
 
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        String loginUsuario = request.getParameter("loginusuario");
        try{
            UsuarioDAO oUsuarioDAO = new UsuarioDAO();
            List<Usuario> lstUsuario = oUsuarioDAO.listar(loginUsuario);
           
            Gson gson = new Gson();
            String jsonUsuario = gson.toJson(lstUsuario);
           
            response.setCharacterEncoding("iso-8859-1");
            response.getWriter().write(jsonUsuario);
           
        } catch (Exception ex) {
            System.out.println("Problemas ao listar Usuario! Erro: "
                +ex.getMessage());
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