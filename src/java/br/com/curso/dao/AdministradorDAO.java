package br.com.curso.dao;

import br.com.curso.model.Administrador;
import static br.com.curso.model.Administrador.administradorVazio;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorDAO implements GenericDAO {
    
    private Connection conexao;
    
    public AdministradorDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
    @Override
    public Boolean cadastrar(Object objeto) {
       Boolean retorno = false;
         Administrador oAdministrador = (Administrador) objeto;
        try {
           
             if(oAdministrador.getIdAdministrador() == 0){
                retorno = this.inserir(oAdministrador);
            }else{
               retorno = this.alterar(oAdministrador);
            }
            return retorno;
        } catch (Exception ex){
            System.out.println("Problemas ao incluir administrador! Erro "+ex.getMessage());            
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Administrador oAdministrador = (Administrador) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into administrador (idpessoa, cpf, situacao, permitelogin)"
                + " values (?, ?, ?,?)";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            int idPessoa = oPessoaDAO.cadastrar(oAdministrador);
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            stmt.setString(2, oAdministrador.getCpf());
            stmt.setString(3, "A");
            stmt.setString(4, oAdministrador.getPermiteLogin());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao cadastrar Administrador!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Administrador oAdministrador = (Administrador) objeto;
        PreparedStatement stmt = null;
        String sql = "update administrador set cpf=?, permitelogin=? where idadministrador=?";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.alterar(oAdministrador);
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oAdministrador.getCpf());
            stmt.setString(2, oAdministrador.getPermiteLogin());
            stmt.setInt(3, oAdministrador.getIdAdministrador());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao alterar Administrador!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        int idAdministrador = numero;
        PreparedStatement stmt = null;
        String sql = "update administrador set situacao=? where idadministrador=?";
        try{
             Administrador oAdministrador = (Administrador) this.carregar(idAdministrador);
            stmt = conexao.prepareStatement(sql);
            if(oAdministrador.getSituacao().equals("A"))
                stmt.setString(1, "I"); 
            else stmt.setString(1, "A");
            stmt.setInt(2, idAdministrador);
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao excluir o administrador! Erro: "+ex.getMessage());
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
    public Object carregar(int numero) {
        int idAdministrador = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Administrador oAdministrador = null;
        String sql = "select * from administrador a, pessoa p where a.idpessoa = p.idpessoa and a.idadministrador=?;";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idAdministrador);
            rs=stmt.executeQuery();
            while (rs.next()) {
                oAdministrador = administradorVazio();
                oAdministrador.setIdAdministrador(rs.getInt("idadministrador"));
                oAdministrador.setCpf(rs.getString("cpf"));
                oAdministrador.setSituacao(rs.getString("situacao"));
                oAdministrador.setPermiteLogin(rs.getString("permitelogin"));
                oAdministrador.setIdPessoa(rs.getInt("idpessoa"));
                oAdministrador.setNome(rs.getString("nome"));
                oAdministrador.setLogin(rs.getString("login"));
                oAdministrador.setSenha(rs.getString("senha"));
            }
        }catch(SQLException e){
            System.out.println("Problemas ao carregar Administrador! Erro: " + e.getMessage());
            return false;
        } catch (ParseException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oAdministrador;
    }

    @Override
    
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql= "Select p.*, a.idadministrador, a.cpf, a.situacao, a.permitelogin "
                + "from administrador a, pessoa p "
                + "where a.idpessoa = p.idpessoa order by idpessoa";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while(rs.next()){
                Administrador oAdministrador = administradorVazio();
                oAdministrador.setIdAdministrador(rs.getInt("idadministrador"));
                oAdministrador.setCpf(rs.getString("cpf"));
                oAdministrador.setSituacao(rs.getString("situacao"));
                oAdministrador.setPermiteLogin(rs.getString("permitelogin"));
                oAdministrador.setIdPessoa(rs.getInt("idpessoa"));
                oAdministrador.setNome(rs.getString("nome"));
                oAdministrador.setLogin(rs.getString("login"));
                oAdministrador.setSenha(rs.getString("senha"));
                resultado.add(oAdministrador);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar administrador! Erro "+ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    /*public int verificarCpf(String cpf){
        PreparedStatement stmt = null;
        ResultSet rs= null;
        int idAdministrador = 0;
        String sql = "Select a.* from administrador a, pessoa p "
                + "where a.idpessoa = p.idpessoa and p.cpf=?";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while(rs.next()){
                idAdministrador = rs.getInt("idadministrador");
            }
            return idAdministrador;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar pessoa! Erro: "+ex.getMessage());
            return idAdministrador;
        }
    }    */  
}
