package br.com.web.model;

public class Integrante {
    private String funcao;
    private Aluno aluno;

    public Integrante(String funcao, Aluno aluno) {
        this.funcao = funcao;
        this.aluno = aluno;
    }

    public Integrante() {
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
