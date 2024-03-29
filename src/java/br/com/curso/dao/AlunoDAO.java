package br.com.curso.dao;

import br.com.curso.model.Aluno;
import static br.com.curso.model.Aluno.alunoVazio;
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

public class AlunoDAO implements GenericDAO{
    
     private final Connection conexao;
    
    public AlunoDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }


    @Override
    public Boolean cadastrar(Object objeto) {
         Boolean retorno = false;
         Aluno oAluno = (Aluno) objeto;
        try {
           
             if(oAluno.getIdAluno() == 0){
                retorno = this.inserir(oAluno);
            }else{
               retorno = this.alterar(oAluno);
            }
            return retorno;
        } catch (Exception ex){
            System.out.println("Problemas ao incluir aluno! Erro "+ex.getMessage());            
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Aluno oAluno = (Aluno) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into aluno(idpessoa, ra, saldoads, situacao, permitelogin) values(?,?,?,?,?)";
        try{
         PessoaDAO oPessoaDAO = new PessoaDAO();
        int idPessoa = oPessoaDAO.cadastrar(oAluno);

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            stmt.setLong(2, oAluno.getRa());
            stmt.setDouble(3, oAluno.getSaldoAds());
            stmt.setString(4, oAluno.getSituacao());
            stmt.setString(5, oAluno.getPermiteLogin());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception ex){
            try{
                System.out.println("Problemas ao cadastrar o Aluno! Erro: "+ex.getMessage());
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
        Aluno oAluno = (Aluno) objeto;
        PreparedStatement stmt = null;
        String sql = "update aluno set ra=?, saldoads=?, permitelogin=? where idaluno=?";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.alterar(oAluno);
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, oAluno.getRa());
            stmt.setDouble(2, oAluno.getSaldoAds());
            stmt.setString(3, oAluno.getPermiteLogin());
            stmt.setInt(4, oAluno.getIdAluno());
            stmt.execute();
            conexao.commit();
            return true;
        } catch(Exception ex) {
            try {
                System.out.println("Problemas ao alterar o Aluno! Erro: "+ex.getMessage());
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
        int idAluno = numero;
        PreparedStatement stmt = null;
        String sql = "update aluno set situacao=? where idaluno=?";
        try{
             Aluno oAluno = (Aluno) this.carregar(idAluno);
            stmt = conexao.prepareStatement(sql);
            if(oAluno.getSituacao().equals("A"))
                stmt.setString(1, "I"); 
            else stmt.setString(1, "A");
            stmt.setInt(2, idAluno);
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao excluir o aluno! Erro: "+ex.getMessage());
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
        int idAluno = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Aluno oAluno = null;
        String sql="select * from aluno al, pessoa p where al.idpessoa = p.idpessoa and al.idaluno=?;";

        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            rs=stmt.executeQuery();
            while (rs.next()) {
                oAluno = alunoVazio();
                oAluno.setIdAluno(rs.getInt("idaluno"));
                oAluno.setRa(rs.getLong("ra"));
                oAluno.setSaldoAds(rs.getDouble("saldoads"));
                oAluno.setSituacao(rs.getString("situacao"));
                oAluno.setPermiteLogin(rs.getString("permitelogin"));
                oAluno.setIdPessoa(rs.getInt("idpessoa"));
                oAluno.setNome(rs.getString("nome"));
                oAluno.setLogin(rs.getString("login"));
                oAluno.setSenha(rs.getString("senha"));
            }
        }catch(Exception ex){
            System.out.println("Problemas ao carregar Aluno! Erro:"+ex.getMessage());
            return false;
        }
        return oAluno;
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select p.*, al.idaluno, al.situacao, al.permitelogin, al.ra, al.saldoads from aluno al, pessoa p "
                + "where al.idpessoa = p.idpessoa order by idpessoa";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while(rs.next()){
                Aluno oAluno = alunoVazio();
                oAluno.setIdAluno(rs.getInt("idaluno"));
                oAluno.setRa(rs.getLong("ra"));
                oAluno.setSaldoAds(rs.getDouble("saldoads"));
                oAluno.setSituacao(rs.getString("situacao"));
                oAluno.setPermiteLogin(rs.getString("permitelogin"));
                oAluno.setIdPessoa(rs.getInt("idpessoa"));
                oAluno.setNome(rs.getString("nome"));
                oAluno.setLogin(rs.getString("login"));
                oAluno.setSenha(rs.getString("senha"));
                resultado.add(oAluno);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar aluno! Erro: "+ex.getMessage());
        } catch (ParseException ex) {
             Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
     /*public int verificarCpf(String cpf){
        PreparedStatement stmt = null;
        ResultSet rs= null;
        int idAluno = 0;
        String sql = "Select al.* from aluno al, pessoa p "
                + "where al.idpessoa = p.idpessoa and p.cpf=?;";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while(rs.next()){
                idAluno = rs.getInt("idaluno");
            }
            return idAluno;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar pessoa! Erro: "+ex.getMessage());
            return idAluno;
        }
    } */
        
    
}
