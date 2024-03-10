package br.com.curso.dao;

import br.com.curso.model.Atividade;
import br.com.curso.model.Disciplina;
import static br.com.curso.utils.Conversao.data2String;
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

public class AtividadeDAO implements GenericDAO{
    private Connection conexao;
    
    public AtividadeDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
    
    @Override
    public Boolean cadastrar(Object objeto){
      Atividade oAtividade = (Atividade) objeto;
      Boolean retorno = false;
      if(oAtividade.getIdAtividade() == 0){
          retorno = this.inserir(oAtividade);
      }else{
         retorno = this.alterar(oAtividade);
      }
      return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Atividade oAtividade = (Atividade) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into atividade(descricao, situacao, status, dataatividade,dataprazo,documento, iddisciplina, pontuacaomax) values (?,?,?,?,?,?,?,?);";
        try{
           
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oAtividade.getDescricao());
            stmt.setString(2, oAtividade.getSituacao());
            stmt.setString(3, oAtividade.getStatus());
            stmt.setDate(4, new java.sql.Date(oAtividade.getDataAtividade().getTime()));
            stmt.setDate(5, new java.sql.Date(oAtividade.getDataPrazo().getTime()));
            stmt.setString(6, oAtividade.getDocumento());
            stmt.setInt(7, oAtividade.getDisciplina().getIdDisciplina());
            stmt.setInt(8, oAtividade.getPontuacaomax());

            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            try{
                System.out.println("Problemas ao inserir Atividade! Erro: "+ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();

            }catch (SQLException e){
                System.out.println("Erro:"+e.getMessage());
                 e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Atividade oAtividade = (Atividade) objeto;
        PreparedStatement stmt = null;
        String sql = "update atividade set descricao=?, status=?, dataatividade=?, dataprazo=?, documento=?, iddisciplina=?, pontuacaomax=? where idatividade=?";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oAtividade.getDescricao());
            stmt.setString(2, oAtividade.getStatus());
            stmt.setDate(3, new java.sql.Date(oAtividade.getDataAtividade().getTime()));
            stmt.setDate(4, new java.sql.Date(oAtividade.getDataPrazo().getTime()));
            stmt.setString(5, oAtividade.getDocumento());
            stmt.setInt(6, oAtividade.getDisciplina().getIdDisciplina());
            stmt.setInt(7, oAtividade.getPontuacaomax());
            stmt.setInt(8, oAtividade.getIdAtividade());
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            try{
                System.out.println("Problemas ao alterar a Atividade! Erro: "+ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            }catch (SQLException e){
                System.out.println("Erro:"+e.getMessage());
                 e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        int idAtividade = numero;
        PreparedStatement stmt = null;
        String sql = "update atividade set situacao=? where idatividade=?";
        try{
            Atividade oAtividade = (Atividade) this.carregar(idAtividade);
            stmt = conexao.prepareStatement(sql);
            if(oAtividade.getSituacao().equals("A"))
                stmt.setString(1, "I"); 
            else stmt.setString(1, "A");
            stmt.setInt(2, idAtividade);
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao excluir a Atividade! Erro: "+ex.getMessage());
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
        int idAtividade = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Atividade oAtividade = null;
        String sql = "select * from atividade where idatividade=?";
        try{
            stmt = conexao.prepareStatement (sql);
            stmt.setInt(1, idAtividade);
            rs=stmt.executeQuery();
            while(rs.next()){
                oAtividade = new Atividade();
                oAtividade.setIdAtividade(rs.getInt("idatividade"));
                oAtividade.setDescricao(rs.getString("descricao"));
                oAtividade.setSituacao(rs.getString("situacao"));
                oAtividade.setStatus(rs.getString("status"));
                oAtividade.setDataAtividade(rs.getDate("dataatividade"));
                oAtividade.setDataPrazo(rs.getDate("dataprazo"));
                oAtividade.setDocumento(rs.getString("documento"));
                oAtividade.setPontuacaomax(rs.getInt("pontuacaomax"));
                DisciplinaDAO oDisciplinaDAO = new DisciplinaDAO();
                oAtividade.setDisciplina((Disciplina) oDisciplinaDAO.carregar(rs.getInt("iddisciplina")));
            }
            return oAtividade;
        }catch (Exception ex){
            System.out.println("Problemas ao carregar atividade! Erro:"+ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Object> listar(){   
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs= null;
        String sql = "Select * from atividade order by descricao";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()){
                Atividade oAtividade = new Atividade();
                oAtividade.setIdAtividade(rs.getInt("idatividade"));
                oAtividade.setDescricao(rs.getString("descricao"));
                oAtividade.setSituacao(rs.getString("situacao"));
                oAtividade.setStatus(rs.getString("status"));
                oAtividade.setDataAtividade(rs.getDate("dataatividade"));
                oAtividade.setDataPrazo(rs.getDate("dataprazo"));
                oAtividade.setDocumento(rs.getString("documento"));
                oAtividade.setPontuacaomax(rs.getInt("pontuacaomax"));
                DisciplinaDAO oDisciplinaDAO = null;
                try{
                    oDisciplinaDAO = new DisciplinaDAO();
                }catch(Exception ex){
                    System.out.println("Erro buscar disciplina"+ex.getMessage());
                    ex.printStackTrace();
                }
                oAtividade.setDisciplina((Disciplina) oDisciplinaDAO.carregar(rs.getInt("iddisciplina")));
                resultado.add(oAtividade);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar atividade!Erro :"+ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public String listarJSON(){
        String strJson="";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object> resultado = new ArrayList<>();
        Atividade oAtividade = null;
        String sql = "select *from atividade";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            strJson = "[";
            int i = 0;
            while(rs.next()){
                if(i>0) strJson += ",";
                strJson += "{\"idAtividade\":"+rs.getInt("idatividade")+","
                        + "\"descricao\":\""+rs.getString("descricao")+"\","
                        +"\"situacao\":\""+rs.getString("situacao")+"\","
                        +"\"status\":\""+rs.getString("status")+"\","
                        +"\"dataAtividade\":\""+data2String(rs.getDate("dataatividade"))+"\","
                        +"\"dataPrazo\":\""+data2String(rs.getDate("dataprazo"))+"\","
                        +"\"documento\":\""+data2String(rs.getDate("documento"))+"\","
                        +"\"idDisciplina\":"+rs.getInt("iddisciplina")+"\","
                        +"\"pontuacaomax\":"+rs.getInt("pontuacaomax")+"\"}";
                i++;
            }
            strJson += "]";
        }catch(Exception e){
            System.out.println("Problemas ao listar Atividade! Erro: "+e.getMessage());
            e.printStackTrace();
        }
        System.out.println(strJson);
        return strJson;
    }
}
