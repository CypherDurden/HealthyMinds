package com.mindcare.dao;

import com.mindcare.model.Consulta;
import com.mindcare.util.DataBaseUtils;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final String INSERT_SQL = "INSERT INTO consulta(id, estadoMental, dicasSobre, relato, tomConsulta, respostaIA, dataConsulta) VALUES(?,?,?,?,?,?,?)";
    private static final String SELECT_SQL = "SELECT id, estadoMental, dicasSobre, relato, tomConsulta, respostaIA, dataConsulta FROM consulta ORDER BY dataConsulta DESC";
    private static final String DELETE_SQL = "DELETE FROM consulta WHERE id = ?";

    public void salvar(Consulta consulta) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setString(1, consulta.getId());
            ps.setString(2, consulta.getEstadoMental());
            ps.setString(3, consulta.getDicasSobre());
            ps.setString(4, consulta.getRelato());
            ps.setString(5, consulta.getTomConsulta());
            ps.setString(6, consulta.getRespostaIA());
            ps.setString(7, formatData(consulta.getDataConsulta()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar consulta: " + consulta, e);
        }
    }

    public List<Consulta> listar() {
        return DataBaseUtils.executeQuery(SELECT_SQL, rs -> {
            List<Consulta> lista = new ArrayList<>();
            try {
                while (rs.next()) {
                    lista.add(mapResultSetParaConsulta(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return lista;
        });
    }

    public void deletar(String id) {
        DataBaseUtils.executeUpdate(DELETE_SQL, ps -> {
            try {
                ps.setString(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String formatData(LocalDateTime data) {
        return data.format(DATE_FORMATTER);
    }

    private Consulta mapResultSetParaConsulta(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getString("id"));
        consulta.setEstadoMental(rs.getString("estadoMental"));
        consulta.setDicasSobre(rs.getString("dicasSobre"));
        consulta.setRelato(rs.getString("relato"));
        consulta.setTomConsulta(rs.getString("tomConsulta"));
        consulta.setRespostaIA(rs.getString("respostaIA"));
        Timestamp timestamp = rs.getTimestamp("dataConsulta");
        if (timestamp != null) {
            consulta.setDataConsulta(timestamp.toLocalDateTime());
        }
        return consulta;
    }
}
