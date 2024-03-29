package br.com.curso.model;

import java.text.ParseException;

public class Administrador extends Pessoa {
    
    private int idAdministrador;
    private String cpf;
    private String permiteLogin;
    private String situacao;

    public Administrador(int idAdministrador, String cpf, String permiteLogin, String situacao, int idPessoa, String nome, String login, String senha) {
        super(idPessoa, nome, login, senha);
        this.idAdministrador = idAdministrador;
        this.cpf = cpf;
        this.permiteLogin = permiteLogin;
        this.situacao = situacao;
    }

    public static Administrador administradorVazio() throws ParseException {
        Administrador oAdministrador = new Administrador(0, "", "S", "A", 0, "", "", "");
        return oAdministrador;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    

    public String getPermiteLogin() {
        return permiteLogin;
    }

    public void setPermiteLogin(String permiteLogin) {
        this.permiteLogin = permiteLogin;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
