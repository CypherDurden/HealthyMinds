package com.mindcare.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.util.logging.Logger;

public class GeminiService {

    private final Client client;

    private static final Logger logger = Logger.getLogger(GeminiService.class.getName());

    public GeminiService() {
        this.client = Client.builder()
                .apiKey("AIzaSyBuvviScdmG2Zk7ueHcq3D-yGwIVLaUJNc") // TODO esconder a secret
                .build();
    }

    public String gerarPrompt(String estadoMental, String dicasSobre, String relato, String tom) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você é um assistente virtual especializado em saúde mental.\n");
        sb.append("Forneça respostas empáticas, respeitosas e úteis com base nas informações fornecidas pelo usuário.\n\n");
        sb.append("O usuário se sente: ").append(estadoMental).append(".\n");
        sb.append("O tom da resposta deve ser: ").append(tom).append(".\n");
        sb.append("O usuário quer dicas sobre: ").append(dicasSobre).append(".\n");
        sb.append("O usuário relata: \"").append(relato).append("\".\n\n");
        sb.append("Forneça uma resposta curta, prática e acolhedora. ");
        sb.append("Se perceber que o usuário precisa de ajuda profissional, sugira procurar apoio adequado.");
        return sb.toString();
    }

    public String gerarResposta(String prompt) {
        try {
            logger.info("Iniciando chamada para API do Gemini...");
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
            );
            logger.info("Resposta recebida da Gemini");
            return response.text();
        } catch (Exception e) {
            logger.warning("Erro ao gerar resposta via Gemini: " + e.getMessage());
            return "Não foi possível gerar a resposta. Tente novamente mais tarde.";
        }
    }
}
