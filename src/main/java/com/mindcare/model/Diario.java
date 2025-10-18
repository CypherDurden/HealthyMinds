package com.mindcare.model;

import java.time.LocalDateTime;

public class Diario {
    private String id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataCriacao;

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public String toString() {
        String t = titulo != null ? titulo : "(sem título)";
        String d = dataCriacao.toString();
        return t + " — " + d;
    }
}
