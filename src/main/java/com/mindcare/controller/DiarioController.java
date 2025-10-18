package com.mindcare.controller;

import com.mindcare.dao.DiarioDAO;
import com.mindcare.model.Diario;
import com.mindcare.service.DiarioService;
import com.mindcare.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.util.UUID;


public class DiarioController {

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextArea taConteudo;

    private final DiarioDAO diarioDAO = new DiarioDAO();

    @FXML
    private void salvarDiario() {
        String titulo = tfTitulo.getText().trim();
        String conteudo = taConteudo.getText().trim();

        if (!DiarioService.isValidDiario(titulo, conteudo)) {
            AlertUtils.showAlert("Erro", "Título e conteúdo são obrigatórios.");
            return;
        }

        Diario diario = new Diario();
        diario.setId(UUID.randomUUID().toString());
        diario.setTitulo(titulo);
        diario.setConteudo(conteudo);
        diario.setDataCriacao(LocalDateTime.now());

        diarioDAO.salvar(diario);

        tfTitulo.clear();
        taConteudo.clear();
        AlertUtils.showAlert("Sucesso", "Diário salvo com sucesso!");
    }
}
