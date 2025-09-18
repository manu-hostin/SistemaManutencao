package org.example.model;

import java.time.LocalDate;
import java.util.Date;

public class OrdemManutencao {

    private int cadastro;
    private LocalDate dataSolicitacao;
    private String status;
    private Maquinas id_maquina;
    private Tecnicos id_tecnico;

    public OrdemManutencao(int cadastro, LocalDate dataSolicitacao, String status, Maquinas id_maquina, Tecnicos id_tecnico) {
        this.cadastro = cadastro;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.id_maquina = id_maquina;
        this.id_tecnico = id_tecnico;
    }

    public OrdemManutencao(LocalDate dataSolicitacao, String status, Maquinas id_maquina, Tecnicos id_tecnico) {
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.id_maquina = id_maquina;
        this.id_tecnico = id_tecnico;
    }

    public OrdemManutencao() {

    }


    public OrdemManutencao(int cadastro, int id_maquina, int id_tecnico, LocalDate dataSolicitacao, String status) {
        this.cadastro = cadastro;
        this.id_maquina = new Maquinas(id_maquina);  // cria objeto só com ID
        this.id_tecnico = new Tecnicos(id_tecnico);  // cria objeto só com ID
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }


    public int getCadastro() {
        return cadastro;
    }

    public void setCadastro(int cadastro) {
        this.cadastro = cadastro;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Maquinas getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(Maquinas id_maquina) {
        this.id_maquina = id_maquina;
    }

    public Tecnicos getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(Tecnicos id_tecnico) {
        this.id_tecnico = id_tecnico;
    }
}
