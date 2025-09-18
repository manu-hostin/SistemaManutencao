package org.example.dao;

import org.example.model.Maquinas;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinasDAO {

    public void criarMaquina (Maquinas maq) throws SQLException {
        String query = """
                INSERT INTO Maquina 
                (nome, setor, status)
                VALUES 
                (?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maq.getNome());
            stmt.setString(2, maq.getSetor());
            stmt.setString(3, maq.getStatus());
            stmt.executeUpdate();

            System.out.println("Máquina adicionada com sucesso!");
        }
    }
    public boolean verificarExistencia (String nome, String setor) throws SQLException {
        String query = """
                SELECT COUNT(*) FROM Maquina WHERE nome = ? AND setor = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, nome);
            stmt.setString(2, setor);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    public List<Maquinas> listarMaquinas () throws SQLException {
        String query = """
                SELECT * FROM Maquina 
                WHERE status = 'OPERACIONAL'
                """;

        List<Maquinas> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Maquinas m = new Maquinas(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("setor"),
                        rs.getString("status")
                );
                lista.add(m);
            }

        }
        return lista;
    }
    public void atualizarStatus (int id) throws SQLException{
        String query = "UPDATE Maquina SET status = 'EM_ANDAMENTO' WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public void atualizarStatusOperacional (int id) throws SQLException{
        String query = "UPDATE Maquina SET status = 'OPERACIONAL' WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();

        }
    }
}
