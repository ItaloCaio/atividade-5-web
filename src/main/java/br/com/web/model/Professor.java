package br.com.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Professor extends  AbstractEntity{
    @NotEmpty
    private String nome;
    @NotEmpty
    private String matricula;
    @NotEmpty
    private String atuacao;
    @NotEmpty
    private String formacao;
    @NotEmpty
    private String usuario;
    @NotEmpty
    private String senha;
    @OneToMany(mappedBy="coordenador")
    private List<Projeto> projetos;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public Professor(String nome, String matricula, String atuacao, String formacao, String usuario, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.atuacao = atuacao;
        this.formacao = formacao;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Professor() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }
}
