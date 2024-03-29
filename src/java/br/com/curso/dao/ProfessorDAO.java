package br.com.curso.dao;

import br.com.curso.model.Professor;
import static br.com.curso.model.Professor.professorVazio;
import br.com.curso.utils.SingleConnection;
import br.com.curso.utils.Hash;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ProfessorDAO implements GenericDAO {
    
     private final Connection conexao;
    
    public ProfessorDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }

    @Override
     public Boolean cadastrar(Object objeto) {
        Boolean retorno = false;
         Professor oProfessor = (Professor) objeto;
        try {
           
             if(oProfessor.getIdProfessor() == 0){
                retorno = this.inserir(oProfessor);
            }else{
               retorno = this.alterar(oProfessor);
            }
            return retorno;
        } catch (Exception ex){
            System.out.println("Problemas ao incluir professor! Erro "+ex.getMessage());            
        }
        return retorno;
    }

    @Override
   public Boolean inserir(Object objeto) {
        Professor oProfessor = (Professor) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into professor(idpessoa, emailprofessor, formacaoprofessor, situacao, permitelogin) values(?,?,?,?,?);";
        try{
         PessoaDAO oPessoaDAO = new PessoaDAO();
        int idPessoa = oPessoaDAO.cadastrar(oProfessor);

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            stmt.setString(2, oProfessor.getEmailProfessor());
            stmt.setString(3, oProfessor.getFormacaoProfessor());
            stmt.setString(4, oProfessor.getSituacao());
            stmt.setString(5, oProfessor.getPermiteLogin());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception ex){
            try{
                System.out.println("Problemas ao cadastrar o Professor! Erro: "+ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            }catch(SQLException e){
                System.out.println("Erro:"+e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Professor oProfessor = (Professor) objeto;
        PreparedStatement stmt = null;
        String sql = "update professor set emailprofessor=?, formacaoprofessor=?, permitelogin=? where idprofessor=?";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.alterar(oProfessor);
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oProfessor.getEmailProfessor());
            stmt.setString(2, oProfessor.getFormacaoProfessor());
            stmt.setString(3, oProfessor.getPermiteLogin());
            stmt.setInt(4, oProfessor.getIdProfessor());
            stmt.execute();
            conexao.commit();
            return true;
        } catch(Exception ex) {
            try {
                System.out.println("Problemas ao alterar o Professor! Erro: "+ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch(SQLException e){
                System.out.println("Erro:"+e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        int idProfessor = numero;
        PreparedStatement stmt = null;
        String sql = "update professor set situacao=? where idprofessor=?";
        try{
             Professor oProfessor = (Professor) this.carregar(idProfessor);
            stmt = conexao.prepareStatement(sql);
            if(oProfessor.getSituacao().equals("A"))
                stmt.setString(1, "I"); 
            else stmt.setString(1, "A");
            stmt.setInt(2, idProfessor);
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao excluir o professor! Erro: "+ex.getMessage());
            try{
                conexao.rollback();
            }catch(SQLException e){
                System.out.println("Erro rollback "+e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
     public Object carregar(int numero){
        int idProfessor = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Professor oProfessor = null;
        String sql="select * from professor pr, pessoa p where p.idpessoa = pr.idpessoa and pr.idprofessor=?;";

        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idProfessor);
            rs=stmt.executeQuery();
            while (rs.next()) {
                oProfessor = professorVazio();
                oProfessor.setIdProfessor(rs.getInt("idprofessor"));
                oProfessor.setFormacaoProfessor(rs.getString("formacaoprofessor"));
                oProfessor.setEmailProfessor(rs.getString("emailprofessor"));
                oProfessor.setSituacao(rs.getString("situacao"));
                oProfessor.setPermiteLogin(rs.getString("permitelogin"));
                oProfessor.setIdPessoa(rs.getInt("idpessoa"));
                oProfessor.setNome(rs.getString("nome"));
                oProfessor.setLogin(rs.getString("login"));
                oProfessor.setSenha(rs.getString("senha"));
            }
        }catch(Exception ex){
            System.out.println("Problemas ao carregar Professor! Erro:"+ex.getMessage());
            return false;
        }
        return oProfessor;
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select p.*, pr.idprofessor, pr.situacao, pr.permitelogin, pr.emailprofessor, pr.formacaoprofessor from professor pr, pessoa p "
                + "where pr.idpessoa = p.idpessoa order by idpessoa";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while(rs.next()){
                Professor oProfessor = professorVazio();
                oProfessor.setIdProfessor(rs.getInt("idProfessor"));
                oProfessor.setFormacaoProfessor(rs.getString("formacaoprofessor"));
                oProfessor.setEmailProfessor(rs.getString("emailprofessor"));
                oProfessor.setSituacao(rs.getString("situacao"));
                oProfessor.setPermiteLogin(rs.getString("permitelogin"));
                oProfessor.setIdPessoa(rs.getInt("idpessoa"));
                oProfessor.setNome(rs.getString("nome"));
                oProfessor.setLogin(rs.getString("login"));
                oProfessor.setSenha(rs.getString("senha"));
                resultado.add(oProfessor);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar Professor! Erro: "+ex.getMessage());
        } catch (ParseException ex) {
             Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public String listarJSON(){
        String strJson="";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object> resultado = new ArrayList<>();
        Professor oProfessor = null;
        String sql = "select *from professor";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            strJson = "[";
            int i = 0;
            while(rs.next()){
                if(i>0) strJson += ",";
                strJson += "{\"idProfessor\":"+rs.getInt("idprofessor")+","
                        + "\"nomeProfessor\":\""+rs.getString("nomeprofessor")+"\","
                        + "\"emailProfessor\":\""+rs.getString("emailprofessor")+"\","
                        + "\"loginProfessor\":\""+rs.getString("loginprofessor")+"\","
                        + "\"senhaProfessor\":\""+rs.getString("senhaprofessor")+"\","
                        +"\"situacao\":\""+rs.getString("situacao")+"\","
                        +"\"formacaoProfessor\":"+rs.getString("formacaoprofessor")+"\"}";
                i++;
            }
            strJson += "]";
        }catch(Exception e){
            System.out.println("Problemas ao listar Professor! Erro: "+e.getMessage());
            e.printStackTrace();
        }
        System.out.println(strJson);
        return strJson;
    }
    
    /*public int verificarCpf(String cpf){
        PreparedStatement stmt = null;
        ResultSet rs= null;
        int idProfessor = 0;
        String sql = "Select pr.* from professor pr, pessoa p "
                + "where pr.idpessoa = p.idpessoa and p.cpf=?;";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while(rs.next()){
                idProfessor = rs.getInt("idprofessor");
            }
            return idProfessor;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar pessoa! Erro: "+ex.getMessage());
            return idProfessor;
        }
    } */
        
}
