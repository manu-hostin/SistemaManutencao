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
    public List<OrdemManutencao> listarOrdens () throws SQLException {
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
        String query = "INSERT INTO OrdemPeca (idOrdem, idPeca, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, peca.getCadastro());
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();

            // Atualiza o estoque da peça
            String atualizarEstoque = "UPDATE Peca SET estoque = estoque - ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(atualizarEstoque)) {
                ps.setInt(1, quantidade);
                ps.setInt(2, peca.getCadastro());
                ps.executeUpdate();
            }
        }
    }

}
