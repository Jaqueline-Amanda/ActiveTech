package br.com.curso.model;

import br.com.curso.utils.Conversao;
import java.text.ParseException;
import java.util.Date;

public class Atividade {
    private int idAtividade;
    private String descricao;
    private String situacao;
    private String status;
    private Date dataAtividade;
    private Date dataPrazo;
    private String documento;
    private Disciplina disciplina;
    private int pontuacaomax;

    public Atividade(int idAtividade, String descricao, String situacao, String status, Date dataAtividade, Date dataPrazo, String documento, Disciplina disciplina, int pontuacaoMax) {
        this.idAtividade = idAtividade;
        this.descricao = descricao;
        this.situacao = situacao;
        this.status = status;
        this.dataAtividade = dataAtividade;
        this.dataPrazo = dataPrazo;
        this.documento = documento;
        this.disciplina = disciplina;
        this.pontuacaomax = pontuacaoMax;
    }
    
    public Atividade() throws ParseException{
        this.idAtividade = 0;
        this.descricao = "";
        this.situacao = "A";
        this.status = "N";
        this.dataAtividade = Conversao.dataAtual();
        this.dataPrazo = Conversao.dataAtual();
        this.disciplina = new Disciplina();
        this.pontuacaomax = 0;
        
    }

    public int getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(int idAtividade) {
        this.idAtividade = idAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataAtividade() {
        return dataAtividade;
    }

    public void setDataAtividade(Date dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    public Date getDataPrazo() {
        return dataPrazo;
    }

    public void setDataPrazo(Date dataPrazo) {
        this.dataPrazo = dataPrazo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public int getPontuacaomax() {
        return pontuacaomax;
    }

    public void setPontuacaomax(int pontuacaomax) {
        this.pontuacaomax = pontuacaomax;
    }
    

}
