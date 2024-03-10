package br.com.curso.dao;

import br.com.curso.model.Pessoa;
import br.com.curso.utils.Hash;
import br.com.curso.utils.SingleConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class PessoaDAO {
    
    private Connection conexao;
    
    public PessoaDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
    
    public int cadastrar(Object objeto) throws ParseException {
        Pessoa oPessoa = (Pessoa) objeto;
        int retorno = 0;
        if (oPessoa.getIdPessoa()==0)
        {
            Pessoa objPessoa = this.carregarCpf(oPessoa.getCpf());
            if (objPessoa.getIdPessoa()==0)
                retorno = this.inserir(oPessoa);
            else
                retorno = objPessoa.getIdPessoa();
        }
        else {
            retorno = this.alterar(oPessoa);
        }
        return retorno;
    }
    
    public int inserir(Object objeto) {
        Pessoa oPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        Integer idPessoa=null;
        String sql = "insert into pessoa (nome, cpf, login, senha)"
                + " values (?, ?, ?, ?) returning idpessoa";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getNome());
            stmt.setString(2, oPessoa.getCpf());
            stmt.setString(3, oPessoa.getLogin());
            stmt.setString(4, oPessoa.getSenha());

            /*try  
            {  
            String senha = Hash.toHexString(Hash.getSHA(oPessoa.getSenha()));  
            stmt.setString(4,senha);
            }  
            catch (NoSuchAlgorithmException e){  
            System.out.println("Exception thrown for incorrect algorithm: " + e);  
            } */  
            rs=stmt.executeQuery();
            conexao.commit();
            
            while (rs.next()){
                idPessoa = rs.getInt("idpessoa");
            }
        } catch (SQLException e){
            try {
                System.out.println("Problemas ao cadastrar Pessoa! Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback();  
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }
    
    public int alterar(Object objeto) {
        Pessoa oPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        Integer idPessoa=oPessoa.getIdPessoa();
        String sql = "update pessoa set nome=?, cpf=?,"
                + " login=?, senha=?"
                + " where idpessoa=?";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getNome());
            stmt.setString(2, oPessoa.getCpf());
            stmt.setString(3, oPessoa.getLogin());
            /* try  
            {  
            String senha = Hash.toHexString(Hash.getSHA(oPessoa.getSenha()));  
            stmt.setString(4,senha);
            }  
            catch (NoSuchAlgorithmException e){  
            System.out.println("Exception thrown for incorrect algorithm: " + e);  
            }   */
            stmt.setString(4, oPessoa.getSenha());
            stmt.setInt(5, oPessoa.getIdPessoa());
            stmt.execute();
            conexao.commit();
        } catch (SQLException e){
            try {
                System.out.println("Problemas ao alterar Pessoa!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback();  
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }
    
    public Pessoa carregar(int id){
        int idPessoa = id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa oPessoa = null;
        String sql = "Select * from pessoa where idpessoa=?";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            rs=stmt.executeQuery();            

            while(rs.next()){
                
                oPessoa = new Pessoa(rs.getInt("idpessoa"),
                                     rs.getString("nome"),
                                     rs.getString("cpf"),
                                     rs.getString("login"),
                                     rs.getString("senha"));
            }
            
            return oPessoa;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar pessoa! Erro "+ex.getMessage());
            return null;
        }   
    }
    
    public Pessoa carregarCpf(String cpf) throws ParseException {
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Pessoa oPessoa = null;
       String sql = "Select * from pessoa where cpf=?";

       try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while (rs.next()){
               oPessoa = this.carregar(rs.getInt("idpessoa"));
            }
            if (oPessoa == null)
            {
                oPessoa = new Pessoa(0,"","","","");
            }
       }
       catch(SQLException ex){
           System.out.println("Problemas ao carregar pessoa! Erro:"+ex.getMessage());
       }
       return oPessoa;
    }
}
