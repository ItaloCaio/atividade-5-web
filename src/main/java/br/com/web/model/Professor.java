package br.com.web.model;

import javax.persistence.Entity;

@Entity
public class Professor extends  AbstractEntity{

    private String nome;
    private String matricula;
    private String atuacao;
    private String formacao;

    public Professor(String nome, String matricula, String atuacao, String formacao) {
        this.nome = nome;
        this.matricula = matricula;
        this.atuacao = atuacao;
        this.formacao = formacao;
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
