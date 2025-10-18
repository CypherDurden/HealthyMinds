package com.mindcare.service;

public class DiarioService {

    public static boolean isValidDiario(String titulo, String conteudo) {
        return titulo != null && !titulo.trim().isEmpty()
                && conteudo != null && !conteudo.trim().isEmpty();
    }
}
