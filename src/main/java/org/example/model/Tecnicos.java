package org.example.model;

public class Tecnicos {

    private int cadastro;
    private String especialidade;
    private String nome;

    public Tecnicos(int id) {
        this.cadastro = id;
    }

    public int getCadastro() {
        return cadastro;
    }

    public void setCadastro(int cadastro) {
        this.cadastro = cadastro;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tecnicos(int cadastro, String nome, String especialidade) {
        this.cadastro = cadastro;
        this.especialidade = especialidade;
        this.nome = nome;
    }

    public Tecnicos(String nome, String especialidade) {
        this.especialidade = especialidade;
        this.nome = nome;
    }
}
