package org.example.dao;

import org.example.model.PecasReposicao;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecasReposicaoDAO {

    public void criarPeca (PecasReposicao peca) throws SQLException{
        String query = """
                INSERT INTO Peca
                (nome, estoque)
                VALUES 
                (?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, peca.getNome());
            stmt.setInt(2, peca.getEstoque());
            stmt.executeUpdate();

            System.out.println("Peça cadastrada com sucesso!");
        }

        }

    public boolean validarPeca(String nome, int quantidade) throws SQLException {
        String query = "SELECT COUNT(*) FROM Peca WHERE nome = ? AND estoque = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    public List<PecasReposicao> listarPecas() throws SQLException {
        String query = "SELECT * FROM Peca WHERE estoque > 0";

        List<PecasReposicao> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PecasReposicao p = new PecasReposicao(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("estoque")
                );
                lista.add(p);
            }
        }
        return lista;
    }
}
