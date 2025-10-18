package com.mindcare.service;

public class ConsultaService {

    public static boolean camposPreenchidos(String estado, String dicas, String relato, String tom) {
        return estado != null &&
                dicas != null && !dicas.isEmpty() &&
                relato != null && !relato.isEmpty() &&
                tom != null;
    }
}
