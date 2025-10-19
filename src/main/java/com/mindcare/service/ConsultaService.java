package com.mindcare.service;

import com.mindcare.model.Consulta;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConsultaService {

    public static boolean camposPreenchidos(String estado, String dicas, String relato, String tom) {
        return isNotEmpty(estado) && isNotEmpty(dicas) && isNotEmpty(relato) && isNotEmpty(tom);
    }

    public static Consulta criarConsulta(String estado, String dicas, String relato, String tom, String respostaIA) {
        Consulta consulta = new Consulta();
        consulta.setId(UUID.randomUUID().toString());
        consulta.setEstadoMental(estado);
        consulta.setDicasSobre(dicas);
        consulta.setRelato(relato);
        consulta.setTomConsulta(tom);
        consulta.setRespostaIA(respostaIA);
        consulta.setDataConsulta(LocalDateTime.now());
        return consulta;
    }

    private static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
