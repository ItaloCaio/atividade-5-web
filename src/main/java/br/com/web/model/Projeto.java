package br.com.web.model;

import javax.persistence.Entity;

@Entity
public class Projeto extends AbstractEntity {

    private String nome;
    private String descricao;

    public Projeto() {
    }

    public Projeto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
