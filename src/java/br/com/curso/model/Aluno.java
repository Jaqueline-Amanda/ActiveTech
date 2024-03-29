package br.com.curso.model;

import java.text.ParseException;

public class Aluno extends Pessoa{
    
    private int idAluno;
    private long ra;
    private Double saldoAds;
    private String situacao;
    private String permiteLogin;

    public Aluno(int idAluno, long ra, Double saldoAds, String situacao, String permiteLogin, int idPessoa, String nome, String login, String senha) {
        super(idPessoa, nome, login, senha);
        this.idAluno = idAluno;
        this.ra = ra;
        this.saldoAds = saldoAds;
        this.situacao = situacao;
        this.permiteLogin = permiteLogin;
    }
    public static Aluno alunoVazio() throws ParseException {
        Aluno oAluno = new Aluno(0,0,0.0,"A","S",0,"","","");
        return oAluno;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public long getRa() {
        return ra;
    }

    public void setRa(long ra) {
        this.ra = ra;
    }

   

    public Double getSaldoAds() {
        return saldoAds;
    }

    public void setSaldoAds(Double saldoAds) {
        this.saldoAds = saldoAds;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getPermiteLogin() {
        return permiteLogin;
    }

    public void setPermiteLogin(String permiteLogin) {
        this.permiteLogin = permiteLogin;
    }
    
                
    
}
