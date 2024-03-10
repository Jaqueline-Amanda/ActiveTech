package br.com.curso.model;

public class Semestre {
    private int idSemestre;
    private String numSemestre; 
    private String situacao;

    public Semestre() {
        this.idSemestre = 0;
        this.numSemestre = "";
         this.situacao = "A";
    }
    
    public Semestre(int idSemestre, String numSemestre, String situacao){
       this.idSemestre = idSemestre;
       this.numSemestre = numSemestre;
       this.situacao = situacao;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }

    public String getNumSemestre() {
        return numSemestre;
    }

    public void setNumSemestre(String numSemestre) {
        this.numSemestre = numSemestre;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
}
