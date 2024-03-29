package br.com.curso.model;

import java.text.ParseException;

public class Professor extends Pessoa {
    
    private int idProfessor;
    private long rm;
    private String formacaoProfessor;
    private String emailProfessor;
    private String situacao;
    private String permiteLogin;
    private Disciplina disciplina;

    public Professor(int idProfessor, long rm, String formacaoProfessor, String emailProfessor, String situacao, String permiteLogin, Disciplina disciplina, int idPessoa, String nome, String login, String senha) {
        super(idPessoa, nome, login, senha);
        this.idProfessor = idProfessor;
        this.rm = rm;
        this.formacaoProfessor = formacaoProfessor;
        this.emailProfessor = emailProfessor;
        this.situacao = situacao;
        this.permiteLogin = permiteLogin;
        this.disciplina = disciplina;
    }
    
    public static Professor professorVazio() throws ParseException {
        Disciplina disciplinaVazia = new Disciplina();
        
        Professor oProfessor = new Professor(0,0,"","","A","S",disciplinaVazia,0,"","","");
        return oProfessor;
    }
    
    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public long getRm() {
        return rm;
    }

    public void setRm(long rm) {
        this.rm = rm;
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    
}

