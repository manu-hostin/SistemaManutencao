package org.example.service;

import java.util.Scanner;

public class Interface {
    static Scanner SC = new Scanner(System.in);

    public static int inicio(){
        System.out.println("\n=== SEJA BEM-VINDO ===");
        System.out.println("""
                            1. Cadastrar máquina;
                            2. Cadastrar técnico;
                            3. Cadastrar peça;
                            4. Criar ordem de manutenção;
                            5. Associar peças à ordem;
                            6. Executar manutenção;
                            
                            0. Sair.
                            """);
        System.out.print ("Digite sua opção: ");
        int opcao = SC.nextInt();
        SC.nextLine();
        return opcao;
    }
    public static String cadastrarNomeMaq (){
        System.out.print("Digite o nome da máquina que deseja cadastrar: ");
        String nome = SC.nextLine();
        return nome;
    }
    public static String cadastrarSetorMaq(){
        System.out.print("Digite o setor ao qual a máquina pertence: ");
        String setor = SC.nextLine();
        return setor;
    }
    public static String cadastrarNomeTec (){
        System.out.print("Digite o nome do técnico que deseja cadastrar: ");
        String nome = SC.nextLine();
        return nome;
    }
    public static String cadastrarEspecialidadeTec(){
        System.out.print("Digite a especialidade do técnico: ");
        String especialidade = SC.nextLine();
        return especialidade;
    }
    public static String cadastrarNomePeca (){
        System.out.print("Digite o nome da peça que deseja cadastrar: ");
        String nome = SC.nextLine();
        return nome;
    }
    public static int cadastrarQuantidadePeca(){
        System.out.print("Digite a quantidade de peças: ");
        int qntdd = SC.nextInt();
        return qntdd;
    }
    public static int escolherMaq (){
        System.out.print ("Digite o id da máquina que deseja escolher: ");
        int idMaq = SC.nextInt();
        SC.nextLine();
        return idMaq;
    }
    public static int escolherTec (){
        System.out.print ("Digite o id do técnico que deseja escolher: ");
        int idTec = SC.nextInt();
        SC.nextLine();
        return idTec;
    }
    public static int escolherOrdem (){
        System.out.print ("Digite o id da ordem que deseja escolher: ");
        int id = SC.nextInt();
        SC.nextLine();
        return id;
    }
    public static int escolherPeca (){
        System.out.print ("Digite o id da peça que deseja escolher: ");
        int id = SC.nextInt();
        SC.nextLine();
        return id;
    }
    public static int escolherQntdd (){
        System.out.print ("Digite a quantidade de peças: ");
        int qntd = SC.nextInt();
        SC.nextLine();
        return qntd;
    }


}
