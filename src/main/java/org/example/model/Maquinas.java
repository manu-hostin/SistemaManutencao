package org.example.model;

public class Maquinas {

    private int cadastro;
    private String nome;
    private String setor;
    private String status;

    public Maquinas(int id) {
        this.cadastro = id;
    }

    public int getCadastro() {
        return cadastro;
    }

    public void setCadastro(int cadastro) {
        this.cadastro = cadastro;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Maquinas(int cadastro, String nome, String setor, String status) {
        this.cadastro = cadastro;
        this.nome = nome;
        this.setor = setor;
        this.status = status;
    }

    public Maquinas(String nome, String setor, String status) {
        this.setor = setor;
        this.nome = nome;
        this.status = status;
    }
}
