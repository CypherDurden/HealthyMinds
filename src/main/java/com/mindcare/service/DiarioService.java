package com.mindcare.service;

import com.mindcare.model.Diario;

import java.time.LocalDateTime;
import java.util.UUID;

public class DiarioService {

    public static boolean isValidDiario(String titulo, String conteudo) {
        return titulo != null && !titulo.isBlank() && conteudo != null && !conteudo.isBlank();
    }

    public static Diario criarDiario(String titulo, String conteudo) {
        Diario diario = new Diario();
        diario.setId(UUID.randomUUID().toString());
        diario.setTitulo(titulo);
        diario.setConteudo(conteudo);
        diario.setDataCriacao(LocalDateTime.now());
        return diario;
    }
}
