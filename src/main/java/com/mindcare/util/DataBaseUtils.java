package com.mindcare.util;

import com.mindcare.dao.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataBaseUtils {

    public static void executeUpdate(String sql, Consumer<PreparedStatement> consumer) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            consumer.accept(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar update SQL: " + sql, e);
        }
    }

    public static <T> T executeQuery(String sql, Function<ResultSet, T> mapper) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return mapper.apply(rs);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar query SQL: " + sql, e);
        }
    }
}
