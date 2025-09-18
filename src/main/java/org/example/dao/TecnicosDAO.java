package org.example.dao;

import org.example.model.Tecnicos;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicosDAO {

    public void criarTecnico (Tecnicos tec) throws SQLException{
        String query = """
                INSERT INTO Tecnico
                (nome, especialidade)
                VALUES
                (?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, tec.getNome());
            stmt.setString(2, tec.getEspecialidade());
            stmt.executeUpdate();
            System.out.println("Técnico adicionado com sucesso!");
        }

    }
    public boolean verificarExistencia (String nome, String especialidade) throws SQLException {
        String query = "SELECT COUNT(*) FROM Tecnico WHERE nome = ? AND especialidade = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, nome);
            stmt.setString(2, especialidade);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    public List<Tecnicos> listarTecnicos () throws SQLException{
        String query = """
                SELECT * FROM Tecnico
                """;

        List<Tecnicos> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Tecnicos t = new Tecnicos(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade")
                );
                lista.add(t);
            }
        }
        return lista;
    }
}
