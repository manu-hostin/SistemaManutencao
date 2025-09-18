package org.example.model;

public class PecasReposicao {

    private int cadastro;
    private String nome;
    private int estoque;

    public int getCadastro() {
        return cadastro;
    }

    public void setCadastro(int cadastro) {
        this.cadastro = cadastro;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PecasReposicao(int cadastro, String nome, int estoque) {
        this.cadastro = cadastro;
        this.nome = nome;
        this.estoque = estoque;
    }

    public PecasReposicao(String nome, int estoque) {
        this.nome = nome;
        this.estoque = estoque;
    }
}
