package br.com.web.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Projeto extends AbstractEntity {

    @NotEmpty
    private String nome;
    @NotEmpty
    private String descricao;
    @ManyToOne
    @JoinColumn(name="coordenador_id", referencedColumnName="id")
    private Professor coordenador;
    @OneToMany(mappedBy="projeto")
    private List<Integrante> integrantes;

    public Projeto() {
    }

    public Projeto(String nome, String descricao, Professor coordenador) {
        this.nome = nome;
        this.descricao = descricao;
        this.coordenador = coordenador;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(Integrante integrante) {
        this.integrantes.add(integrante);
    }

    public Professor getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Professor coordenador) {
        this.coordenador = coordenador;
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
