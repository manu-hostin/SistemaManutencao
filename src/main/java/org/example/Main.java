package org.example;

import org.example.service.Interface;
import org.example.view.FluxoExecucao;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        FluxoExecucao fluxo = new FluxoExecucao();
        int opcao;

        do {
            opcao = Interface.inicio();
            fluxo.Fluxo(opcao, new Interface());

        } while (opcao != 0);

    }
}