package com.mindcare.dao;

import com.mindcare.model.Diario;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiarioDAO {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final String INSERT_SQL = "INSERT INTO diario(id, titulo, conteudo, dataCriacao) VALUES(?,?,?,?)";
    private static final String SELECT_SQL = "SELECT id, titulo, conteudo, dataCriacao FROM diario ORDER BY dataCriacao DESC";
    private static final String DELETE_SQL = "DELETE FROM diario WHERE id = ?";

    public void salvar(Diario diario) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setString(1, diario.getId());
            ps.setString(2, diario.getTitulo());
            ps.setString(3, diario.getConteudo());
            ps.setString(4, formatData(diario.getDataCriacao()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar diário: " + diario, e);
        }
    }

    public List<Diario> listar() {
        List<Diario> diarios = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                diarios.add(mapResultSetParaDiario(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar diários", e);
        }
        return diarios;
    }

    public void deletar(String id) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar diário com id: " + id, e);
        }
    }

    private String formatData(LocalDateTime data) {
        return data.format(DATE_FORMATTER);
    }

    private Diario mapResultSetParaDiario(ResultSet rs) throws SQLException {
        Diario diario = new Diario();
        diario.setId(rs.getString("id"));
        diario.setTitulo(rs.getString("titulo"));
        diario.setConteudo(rs.getString("conteudo"));
        diario.setDataCriacao(rs.getTimestamp("dataCriacao").toLocalDateTime());
        return diario;
    }
}
