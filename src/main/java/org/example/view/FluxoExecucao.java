package org.example.view;

import org.example.dao.MaquinasDAO;
import org.example.dao.OrdemManutencaoDAO;
import org.example.dao.PecasReposicaoDAO;
import org.example.dao.TecnicosDAO;
import org.example.model.Maquinas;
import org.example.model.OrdemManutencao;
import org.example.model.PecasReposicao;
import org.example.model.Tecnicos;
import org.example.service.Interface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FluxoExecucao {

    public void Fluxo(int opcao, Interface atendimento) {
        switch (opcao) {
            case 1:
                criarMaquina();
                break;
            case 2:
                criarTecnico();
                break;
            case 3:
                criarPeca();
                break;
            case 4:
                criarOrdemManutencao();
                break;
            case 5:
                associarPecas();
                break;
            case 6:
                executarManutencao();
                break;
            case 0:
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    public static void criarMaquina() {
        System.out.println("\n=== CADASTRAR MÁQUINA ===");

        String nome = Interface.cadastrarNomeMaq();
        String setor = Interface.cadastrarSetorMaq();
        String status = "OPERACIONAL";

        MaquinasDAO dao = new MaquinasDAO();

        try {

            if (dao.verificarExistencia(nome, setor)) {
                System.out.println("Máquina já existe, favor cadastrar outra!");

            } else {
                Maquinas maq = new Maquinas(nome, setor, status);
                dao.criarMaquina(maq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarTecnico() {
        System.out.println("\n=== CADASTRAR TÉCNICO ===");

        String nome = Interface.cadastrarNomeTec();
        String especialidade = Interface.cadastrarEspecialidadeTec();

        TecnicosDAO dao = new TecnicosDAO();

        try {

            if (dao.verificarExistencia(nome, especialidade)) {
                System.out.println("Este técnico já está cadastrado!");

            } else {
                Tecnicos tec = new Tecnicos(nome, especialidade);
                dao.criarTecnico(tec);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarPeca () {
        System.out.println("\n=== CADASTRAR PEÇA ===");

        String nome = Interface.cadastrarNomePeca();
        int quantidade = Interface.cadastrarQuantidadePeca();

        PecasReposicaoDAO dao = new PecasReposicaoDAO();

        try {

            if (dao.validarPeca(nome, quantidade)) {
                System.out.println("Peça já cadastrada!");

            } else {
                PecasReposicao peca = new PecasReposicao(nome, quantidade);
                dao.criarPeca(peca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarOrdemManutencao(){
        System.out.println("\n=== CRIAR ORDEM MANUTENÇÃO ===");

        try {

            MaquinasDAO maqDAO = new MaquinasDAO();
            List<Maquinas> listaMaq = maqDAO.listarMaquinas();

            for (int i = 0; i < listaMaq.size(); i++){
                Maquinas m = listaMaq.get(i);
                System.out.println(i + " - " + m.getNome() + " | Setor: " + m.getSetor() + " | Status: " + m.getStatus());
            }
            System.out.println("");
            int idMaq = Interface.escolherMaq();

            if (idMaq < 0 || idMaq >= listaMaq.size()){
                System.out.println("Opção inválida, tente novamente!");
                return;
            }
            Maquinas maqSelecionada = listaMaq.get(idMaq);


            TecnicosDAO tecDAO = new TecnicosDAO();
            List<Tecnicos> listaTec = tecDAO.listarTecnicos();

            System.out.println("");
            for (int i = 0; i < listaTec.size(); i++){
                Tecnicos t = listaTec.get(i);
                System.out.println(i + " - " + t.getNome() + " | Especialidade: " + t.getEspecialidade());
            }

            System.out.println("");
            int idTec = Interface.escolherTec();

            if(idTec < 0 || idTec >= listaTec.size()){
                System.out.println("Opção inválida, tente novamente!");
                return;
            }
            Tecnicos tecSelecionado = listaTec.get(idTec);


            OrdemManutencao ordem = new OrdemManutencao();
            ordem.setId_tecnico(tecSelecionado);
            ordem.setId_maquina(maqSelecionada);
            ordem.setDataSolicitacao(LocalDate.now());
            ordem.setStatus("PENDENTE");

            OrdemManutencaoDAO dao = new OrdemManutencaoDAO();
            dao.criarOrdem(ordem);

            MaquinasDAO maq = new MaquinasDAO();
            maq.atualizarStatus(maqSelecionada.getCadastro());


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void associarPecas (){
        System.out.println("\n=== ASSOCIAR PEÇAS À ORDEM ===");

        OrdemManutencaoDAO dao = new OrdemManutencaoDAO();
        PecasReposicaoDAO pecasDAO = new PecasReposicaoDAO();

        try {

            List<OrdemManutencao> ordensPendentes = dao.listarOrdensPendentes();

            if (ordensPendentes .isEmpty()) {
                System.out.println("Não há ordens pendentes para associar peças.");
                return;
            }

            System.out.println("\nORDENS DISPNÍVEIS -----");
            for (int i = 0; i < ordensPendentes.size(); i++) {
                OrdemManutencao o = ordensPendentes.get(i);

                System.out.println(i + " - Ordem ID: " + o.getCadastro() + " | Máquina: " + o.getId_maquina().getNome() + " | Técnico: "+o.getId_tecnico().getNome());
                System.out.println("");
            }

            int escolhaOrdem = Interface.escolherOrdem();

            if(escolhaOrdem < 0 || escolhaOrdem >= ordensPendentes.size()){
                System.out.println("Opção inválida, tente novamente!");
                return;
            }
            OrdemManutencao ordemSelecionada = ordensPendentes .get(escolhaOrdem);


            List<PecasReposicao> pecasDisponiveis = pecasDAO.listarPecas();

            System.out.println("\nPEÇAS DISPONÍVEIS -----");
            for (int i = 0; i < pecasDisponiveis.size(); i++) {
                PecasReposicao p = pecasDisponiveis.get(i);
                System.out.println("");
                System.out.println(i + " - " + p.getNome() + " | Estoque: " + p.getEstoque());
                System.out.println("");
            }

            int escolhaPeca = Interface.escolherPeca();

            if(escolhaPeca < 0 || escolhaPeca >= pecasDisponiveis.size()){
                System.out.println("Escolha indisponível, tente novamente!");
                return;
            }

            PecasReposicao pecaEscolhida = pecasDisponiveis.get(escolhaPeca);


            int quantidade = Interface.escolherQntdd();
            if (quantidade <= 0 || quantidade > pecaEscolhida.getEstoque()){
                System.out.println("Quantidade inválida!");
                return;
            }

            dao.associarPeca(ordemSelecionada.getCadastro(), pecaEscolhida, quantidade);

            System.out.println("Peça associada com sucesso!");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void executarManutencao (){
        System.out.println("\n=== EXECUTAR MANUTENÇÃO ===");

        OrdemManutencaoDAO dao = new OrdemManutencaoDAO();
        PecasReposicaoDAO pecasDAO = new PecasReposicaoDAO();
        MaquinasDAO maquinasDAO = new MaquinasDAO();

        try{

            List<OrdemManutencao> lista = dao.listarOrdensPendentes();
            if (lista.isEmpty()){
                System.out.println("Nenhuma ordem disponível!");
            }

            System.out.println("\nORDENS PENDENTES -----");
            for (int i = 0; i < lista.size(); i++){
                OrdemManutencao o = lista.get(i);
                System.out.println("--------------------------------------------------");
                System.out.println("ID: "+(o.getCadastro()-1)+ " |ID da Máquina: "+o.getId_maquina().getCadastro()+" |ID do Técnico: "+o.getId_tecnico().getCadastro()+"\nData de solicitação: "+o.getDataSolicitacao()+" |Status: "+o.getStatus());
            }
            System.out.println("");

            int escolha = Interface.escolherOrdem();

            if (escolha < 0 || escolha >= lista.size()) {
                System.out.println("Opção inválida, tente novamente!");
                return;
            }

            OrdemManutencao ordemSelecionada = lista.get(escolha);

            boolean estoqueSuficiente = pecasDAO.verificarEstoque(ordemSelecionada.getCadastro());

            if (!estoqueSuficiente){
                System.out.println("Estoque insuficiente!");
                return;
            }

            pecasDAO.atualizarEstoque(ordemSelecionada.getCadastro());
            dao.atualizarStatus(ordemSelecionada.getCadastro(),"EXECUTADA");
            maquinasDAO.atualizarStatusOperacional(ordemSelecionada.getCadastro());

            System.out.println("Manutenção realizada com sucesso!");

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
