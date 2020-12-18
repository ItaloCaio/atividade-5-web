package br.com.web.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Aluno extends AbstractEntity {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String curso;
    @NotEmpty
    private String usuario;
    @NotEmpty
    private String senha;
    @OneToMany(mappedBy="aluno")
    private List<Integrante> integrantes;


    public Aluno(String nome, String curso, String usuario, String senha) {
        this.nome = nome;
        this.curso = curso;
        this.usuario = usuario;
        this.senha = senha;
    }


    public Aluno() {
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
