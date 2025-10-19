package com.mindcare.controller;

import com.mindcare.dao.DiarioDAO;
import com.mindcare.model.Diario;
import com.mindcare.service.DiarioService;
import com.mindcare.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;

public class DiarioController {

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextArea taConteudo;

    private final DiarioDAO diarioDAO = new DiarioDAO();

    private static final Logger logger = Logger.getLogger(DiarioController.class.getName());

    @FXML
    private void salvarDiario() {
        String titulo = tfTitulo.getText().trim();
        String conteudo = taConteudo.getText().trim();

        if (!DiarioService.isValidDiario(titulo, conteudo)) {
            logger.warning("Campos inválidos, mostrando alerta...");
            AlertUtils.showAlert("Erro", "Título e conteúdo são obrigatórios.");
            return;
        }

        Diario diario = DiarioService.criarDiario(titulo, conteudo);

        diarioDAO.salvar(diario);

        limparCampos();
        AlertUtils.showAlert("Sucesso", "Diário salvo com sucesso!");
        logger.info("Diário salvo com sucesso: " + diario.getId());
    }

    private void limparCampos() {
        tfTitulo.clear();
        taConteudo.clear();
    }
}
