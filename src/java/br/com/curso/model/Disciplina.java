
package br.com.curso.model;

public class Disciplina {
    
    private int idDisciplina;
    private String nomeDisciplina;
    private Semestre semestre;
    private String situacao;
    
    public Disciplina(int idDisciplina, String nomeDisciplina, Semestre semestre, String situacao) {
        this.idDisciplina = idDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.semestre = semestre;
        this.situacao = situacao;
}

    public Disciplina() {
        this.idDisciplina = 0;
        this.nomeDisciplina = "";
        this.situacao="A";
        this.semestre = new Semestre();
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }  
}


