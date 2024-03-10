package br.com.curso.dao;

import br.com.curso.model.Semestre;
import br.com.curso.utils.SingleConnection;
import br.com.curso.dao.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SemestreDAO implements GenericDAO {
    private Connection conexao;
    
    public SemestreDAO() throws Exception{
    conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Semestre oSemestre = (Semestre) objeto;
        Boolean retorno=false;
        if (oSemestre.getIdSemestre() == 0) {
            retorno = this.inserir(oSemestre);
        }else{
            retorno = this.alterar(oSemestre);            
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Semestre oSemestre = (Semestre) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into semestre (numsemestre, situacao) values (?, ?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oSemestre.getNumSemestre());
            stmt.setString(2, oSemestre.getSituacao());
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao cadastrar o Semestre! Erro: "+ex.getMessage());
            ex.printStackTrace();
            conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro:"+e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Semestre oSemestre = (Semestre) objeto;
        PreparedStatement stmt = null;
        String sql = "update semestre set numsemestre=? where idsemestre=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1,oSemestre.getNumSemestre());
            stmt.setInt(2,oSemestre.getIdSemestre());
            stmt.execute();
           conexao.commit();
           return true;
    } catch (Exception ex) {
        try {
            System.out.println("Problemas ao alterar o Semestre! Erro: "+ex.getMessage());
            ex.printStackTrace();
            conexao.rollback();
        } catch (SQLException e) {
            System.out.println("Erro:"+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

    @Override
   public Boolean excluir(int numero) {
       int idSemestre = numero;
        PreparedStatement stmt = null;
        String sql = "update semestre set situacao=? where idsemestre=?";
        try{
            Semestre oSemestre = (Semestre) this.carregar(idSemestre);
            stmt = conexao.prepareStatement(sql);
            if(oSemestre.getSituacao().equals("A"))
                stmt.setString(1, "I"); 
            else stmt.setString(1, "A");
            stmt.setInt(2, idSemestre);
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao excluir  o semestre! Erro: "+ex.getMessage());
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
        int idSemestre = numero;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        Semestre oSemestre = null;
        String sql ="select * from semestre where idSemestre=?";
        
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idSemestre);
            rs=stmt.executeQuery();
            while(rs.next()){
                oSemestre = new Semestre();
                oSemestre.setIdSemestre(rs.getInt("idSemestre"));
                oSemestre.setNumSemestre(rs.getString("numsemestre"));
                oSemestre.setSituacao(rs.getString("situacao"));
            }
            return oSemestre;
        } catch (Exception ex) {
            System.out.println("Problema ao carregar Semestre. Erro:"+ex.getMessage());
            return false;
    }
}

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from semestre order by numsemestre";
        try {
            stmt = conexao.prepareStatement(sql);   
            rs = stmt.executeQuery();
            while(rs.next()){
                Semestre oSemestre = new Semestre();
                oSemestre.setIdSemestre(rs.getInt("idsemestre"));
                oSemestre.setNumSemestre(rs.getString("numsemestre"));
                oSemestre.setSituacao(rs.getString("situacao"));
                resultado.add(oSemestre);
            }
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Semestre! Erro: "+ex.getMessage());
        }
        return resultado;
    }
    
    public String listarJSON(){
        String strJson="";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object> resultado = new ArrayList<>();
        Semestre oSemestre = null;
        String sql = "select *from semestre";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            strJson = "[";
            int i = 0;
            while(rs.next()){
                if(i>0) strJson += ",";
                strJson += "{\"idSemestre\":"+rs.getInt("idsemestre")+","
                        + "\"numSemestre\":\""+rs.getString("numsemestre")+"\","
                       +"\"situacao\":\""+rs.getString("situacao")+"\"}";
                i++;
            }
            strJson += "]";
        }catch(Exception e){
            System.out.println("Problemas ao listar Semestre! Erro: "+e.getMessage());
            e.printStackTrace();
        }
        System.out.println(strJson);
        return strJson;
    }
}
    
    