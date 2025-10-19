package com.mindcare.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static String DB_URL;

    static {
        try (InputStream input = Database.class.getResourceAsStream("/application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            DB_URL = prop.getProperty("db.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            // criar tabela diario
            st.execute("CREATE TABLE IF NOT EXISTS diario (" +
                    "id TEXT PRIMARY KEY," +
                    "titulo TEXT NOT NULL," +
                    "conteudo TEXT NOT NULL," +
                    "dataCriacao TEXT NOT NULL" +
                    ");");

            // criar tabela consulta
            st.execute("CREATE TABLE IF NOT EXISTS consulta (" +
                    "id TEXT PRIMARY KEY," +
                    "estadoMental TEXT," +
                    "dicasSobre TEXT," +
                    "relato TEXT," +
                    "tomConsulta TEXT," +
                    "respostaIA TEXT," +
                    "dataConsulta TEXT" +
                    ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
