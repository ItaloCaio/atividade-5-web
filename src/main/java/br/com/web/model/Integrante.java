package br.com.web.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Integrante extends AbstractEntity{
    private String funcao;
    @ManyToOne
    @JoinColumn(name="aluno_id", referencedColumnName="id")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name="integrante_id", referencedColumnName="id")
    private Projeto projeto;

    public Integrante(String funcao, Aluno aluno, Projeto projeto) {
        this.funcao = funcao;
        this.aluno = aluno;
        this.projeto = projeto;
    }

    public Integrante() {
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
