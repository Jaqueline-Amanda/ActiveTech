package br.com.curso.model;
import br.com.curso.utils.Hash;
import java.security.NoSuchAlgorithmException;
public class Pessoa {
    
    private int idPessoa;
    private String nome;
    private String login;
    private String senha;

    public Pessoa(int idPessoa, String nome, String login, String senha) {
        this.idPessoa = idPessoa;
        this.nome = nome;
    
        this.login = login;
        this.senha = senha;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
        /*try {
            this.senha = Hash.toHexString(Hash.getSHA(senha));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }
}
