
package br.com.curso.dao;

import br.com.curso.model.Disciplina;
import br.com.curso.model.Semestre;
import br.com.curso.utils.SingleConnection;
import br.com.curso.dao.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO implements GenericDAO {
    
    private Connection conexao;
    
    public DisciplinaDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Disciplina oDisciplina = (Disciplina) objeto;
        Boolean retorno=false;
        if (oDisciplina.getIdDisciplina() == 0) {
            retorno = this.inserir(oDisciplina);
        }else {
            retorno = this.alterar(oDisciplina);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Disciplina oDisciplina = (Disciplina) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into disciplina (nomedisciplina,idsemestre,situacao) values (?,?,?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oDisciplina.getNomeDisciplina());
            stmt.setInt(2, oDisciplina.getSemestre().getIdSemestre());
            stmt.setString(3, oDisciplina.getSituacao());
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao cadastrar Disciplina! Erro: "+ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro: "+e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Disciplina oDisciplina = (Disciplina) objeto;
        PreparedStatement stmt = null;
        String sql = "update disciplina set nomedisciplina=?, idsemestre=?, situacao=? where iddisciplina=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oDisciplina.getNomeDisciplina());
            stmt.setInt(2, oDisciplina.getSemestre().getIdSemestre());
            stmt.setString(3, oDisciplina.getSituacao());
            stmt.setInt(4, oDisciplina.getIdDisciplina());
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex) {
            try {
                System.out.println("Problemas ao alaterar a Disciplina! Erro: " +ex.getMessage());
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
        int idDisciplina = numero;
        PreparedStatement stmt = null;
        String sql = "update disciplina set situacao=? where iddisciplina=?";
        try {
            Disciplina oDisciplina = (Disciplina) this.carregar(idDisciplina);
            stmt = conexao.prepareStatement(sql);
            if (oDisciplina.getSituacao().equals("A"))
                stmt.setString(1,"I");
            else stmt.setString(1,"A");
            stmt.setInt(2, idDisciplina);
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Disciplina! Erro: "+ex.getMessage());
            try {
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro rollback "+ex.getMessage());
                e.printStackTrace();
            }
            return false;
        } 
    }

    @Override
    public Object carregar(int numero) {
        int idDisciplina = numero;
        PreparedStatement stmt = null; 
        ResultSet rs = null;
        Disciplina oDisciplina = null;
        String sql="select * from disciplina where iddisciplina=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idDisciplina);
            rs=stmt.executeQuery();
            while (rs.next()) {
                oDisciplina = new Disciplina();
                oDisciplina.setIdDisciplina(rs.getInt("iddisciplina"));
                oDisciplina.setNomeDisciplina(rs.getString("nomedisciplina"));
                oDisciplina.setSituacao(rs.getString("situacao"));
                
                SemestreDAO oSemestreDAO = new SemestreDAO();
                oDisciplina.setSemestre((Semestre) oSemestreDAO.carregar(rs.getInt("idsemestre")));
            }
            return oDisciplina;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Disciplina! Erro:"+ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null; 
        ResultSet rs = null;
        String sql="select * from disciplina order by nomedisciplina";
        try {
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()) {
                Disciplina oDisciplina = new Disciplina();
                oDisciplina.setIdDisciplina(rs.getInt("idDisciplina"));
                oDisciplina.setNomeDisciplina(rs.getString("nomedisciplina"));
                oDisciplina.setSituacao(rs.getString("situacao"));
                
                SemestreDAO oSemestreDAO = null;
                try {
                     oSemestreDAO = new SemestreDAO();
                } catch (Exception ex) {
                    System.out.println("Erro ao buscar semestre "+ex.getMessage());
                    ex.printStackTrace();
                }
                oDisciplina.setSemestre((Semestre) oSemestreDAO.carregar(rs.getInt("idsemestre")));
                resultado.add(oDisciplina);
            }
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Disciplina! Erro: "+ex.getMessage());
        }
        return resultado;
        
    }
    public String listarJSON(){
        String strJson="";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object> resultado = new ArrayList<>();
        Disciplina oDisciplina = null;
        String sql = "select *from disciplina";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            strJson = "[";
            int i = 0;
            while(rs.next()){
                if(i>0) strJson += ",";
                strJson += "{\"idDisciplina\":"+rs.getInt("iddisciplina")+","
                        + "\"nomeDisciplina\":\""+rs.getString("nomedisciplina")+"\","
                       +"\"situacao\":\""+rs.getString("situacao")+"\","
                       +"\"idSemestre\":"+rs.getInt("idsemestre")+"\"}";
                i++;
            }
          
            strJson += "]";
        }catch(Exception e){
            System.out.println("Problemas ao listar Disciplina! Erro: "+e.getMessage());
            e.printStackTrace();
        }
        System.out.println(strJson);
        return strJson;
    }
    
}
