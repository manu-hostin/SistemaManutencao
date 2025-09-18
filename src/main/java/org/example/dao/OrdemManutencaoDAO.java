package org.example.dao;

import org.example.model.Maquinas;
import org.example.model.OrdemManutencao;
import org.example.model.PecasReposicao;
import org.example.model.Tecnicos;
import org.example.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemManutencaoDAO {

    public void criarOrdem (OrdemManutencao ordem) throws SQLException{
        String query = """
                INSERT INTO OrdemManutencao
                (idMaquina, idTecnico, dataSolicitacao, status)
                VALUES ( ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordem.getId_maquina().getCadastro());
            stmt.setInt(2, ordem.getId_tecnico().getCadastro());
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(4, ordem.getStatus());
            stmt.executeUpdate();
            System.out.println("Ordem de manutenção criada com sucesso!");
        }
    }
    public List<OrdemManutencao> listarOrdensPendentes () throws SQLException {
        String query = "SELECT * FROM OrdemManutencao WHERE status = 'PENDENTE'";

        List<OrdemManutencao> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Maquinas maquina = new Maquinas(rs.getInt("idMaquina"));
                Tecnicos tecnico = new Tecnicos(rs.getInt("idTecnico"));
                OrdemManutencao ordem = new OrdemManutencao(
                        rs.getInt("id"),
                        rs.getDate("dataSolicitacao").toLocalDate(),
                        rs.getString("status"),
                        maquina,
                        tecnico
                );

                lista.add(ordem);
            }
        }
        return lista;
    }
    public void associarPeca(int idOrdem, PecasReposicao peca, int quantidade) throws SQLException {
        try (Connection conn = Conexao.conectar()) {

            String verificar = "SELECT quantidade FROM OrdemPeca WHERE idOrdem = ? AND idPeca = ?";
            try (PreparedStatement ps = conn.prepareStatement(verificar)) {
                ps.setInt(1, idOrdem);
                ps.setInt(2, peca.getCadastro());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    int novaQtd = rs.getInt("quantidade") + quantidade;
                    String atualizar = "UPDATE OrdemPeca SET quantidade = ? WHERE idOrdem = ? AND idPeca = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(atualizar)) {
                        updateStmt.setInt(1, novaQtd);
                        updateStmt.setInt(2, idOrdem);
                        updateStmt.setInt(3, peca.getCadastro());
                        updateStmt.executeUpdate();
                    }
                } else {

                    String inserir = "INSERT INTO OrdemPeca (idOrdem, idPeca, quantidade) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(inserir)) {
                        insertStmt.setInt(1, idOrdem);
                        insertStmt.setInt(2, peca.getCadastro());
                        insertStmt.setInt(3, quantidade);
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public List<OrdemManutencao> listarOrdens () throws SQLException{
        String query = """
                SELECT * FROM OrdemManutencao WHERE id >= 0
                """;

        List<OrdemManutencao> lista = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Maquinas maq = new Maquinas(rs.getInt("idMaquina"));
                Tecnicos tec = new Tecnicos(rs.getInt("idTecnico"));
                OrdemManutencao ordem = new OrdemManutencao(
                        rs.getInt("id"),
                        rs.getDate("dataSolicitacao").toLocalDate(),
                        rs.getString("status"),
                        maq,
                        tec
                );
                lista.add(ordem);
            }
        }
        return lista;
    }
    public void atualizarStatus (int idOrdem, String status) throws SQLException {
        String query = "UPDATE OrdemManutencao SET status = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, idOrdem);
            stmt.executeUpdate();

        }
    }

}
