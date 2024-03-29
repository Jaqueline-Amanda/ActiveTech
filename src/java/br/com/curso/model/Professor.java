package br.com.curso.model;

import java.text.ParseException;

public class Professor extends Pessoa {
    
    private int idProfessor;
    private String formacaoProfessor;
    private String emailProfessor;
    private String situacao;
    private String permiteLogin;

    public Professor(int idProfessor, String formacaoProfessor, String emailProfessor, String situacao, String permiteLogin, int idPessoa, String nome, String login, String senha) {
        super(idPessoa, nome, login, senha);
        this.idProfessor = idProfessor;
        this.formacaoProfessor = formacaoProfessor;
        this.emailProfessor = emailProfessor;
        this.situacao = situacao;
        this.permiteLogin = permiteLogin;
    }
    
    public static Professor professorVazio() throws ParseException {
        Professor oProfessor = new Professor(0,"","","A","S",0,"","","");
        return oProfessor;
    }
    
    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getFormacaoProfessor() {
        return formacaoProfessor;
    }

    public void setFormacaoProfessor(String formacaoProfessor) {
        this.formacaoProfessor = formacaoProfessor;
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

    public String getEmailProfessor() {
        return emailProfessor;
    }

    public void setEmailProfessor(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }
}

