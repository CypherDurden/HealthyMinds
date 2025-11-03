package com.mindcare.controller;

import com.mindcare.dao.ConsultaDAO;
import com.mindcare.model.Consulta;
import com.mindcare.service.ConsultaService;
import com.mindcare.service.GeminiService;
import com.mindcare.util.AlertUtils;
import javafx.application.Platform;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.logging.Logger;

public class ConsultaController {

    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private TextField tfDicas;

    @FXML
    private TextArea taRelato;

    @FXML
    private ComboBox<String> cbTom;

    @FXML
    private Button btnEnviar;

    @FXML
    private TextArea taResposta;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private VBox rootContainer;

    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final GeminiService geminiService = new GeminiService();
    private static final Logger logger = Logger.getLogger(ConsultaController.class.getName());

    @FXML
    public void initialize() {
        cbEstado.getItems().addAll("Feliz", "Triste","Ansioso", "Estressado");
        cbTom.getItems().addAll("Leve", "Moderado", "Humorado", "Intenso");

        btnEnviar.setOnAction(event -> enviarConsulta());
    }

    private void enviarConsulta() {
        if (!ConsultaService.camposPreenchidos(
                cbEstado.getValue(),
                tfDicas.getText(),
                taRelato.getText(),
                cbTom.getValue()
        )) {
            logger.warning("Preenchimento dos campos inválido, gerando alerta...");
            AlertUtils.showAlert("Erro", "Todos os campos devem ser preenchidos");
            return;
        }

        prepararEnvio();

        new Thread(() -> {
            String respostaIA = gerarPromptEObterResposta();

            Platform.runLater(() -> {
                Consulta consulta = ConsultaService.criarConsulta(
                        cbEstado.getValue(),
                        tfDicas.getText(),
                        taRelato.getText(),
                        cbTom.getValue(),
                        respostaIA
                );

                consultaDAO.salvar(consulta);
                atualizarInterfaceAposEnvio(respostaIA);
                logger.info("Consulta finalizada com sucesso.");
            });
        }).start();
    }


    private void prepararEnvio() {
        loadingIndicator.setVisible(true);
        btnEnviar.setDisable(true);
        taResposta.clear();

        rootContainer.setEffect(new GaussianBlur(10));
        rootContainer.setDisable(true);
    }


    private void atualizarInterfaceAposEnvio(String respostaIA) {
        taResposta.setText(respostaIA);
        loadingIndicator.setVisible(false);
        btnEnviar.setDisable(false);

        rootContainer.setEffect(null);
        rootContainer.setDisable(false);

        cbEstado.getSelectionModel().clearSelection();
        tfDicas.clear();
        taRelato.clear();
        cbTom.getSelectionModel().clearSelection();

        AlertUtils.showAlert("Sucesso", "Consulta feita com sucesso!");
    }

    private String gerarPromptEObterResposta() {
        logger.info("Gerando Prompt...");
        String prompt = geminiService.gerarPrompt(
                cbEstado.getValue(),
                tfDicas.getText(),
                taRelato.getText(),
                cbTom.getValue()
        );
        return geminiService.gerarResposta(prompt);
    }
}
