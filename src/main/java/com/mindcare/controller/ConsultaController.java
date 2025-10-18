package com.mindcare.controller;

import com.mindcare.dao.ConsultaDAO;
import com.mindcare.model.Consulta;
import com.mindcare.service.ConsultaService;
import com.mindcare.service.MockIAService;
import com.mindcare.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.UUID;


import java.time.LocalDateTime;

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

    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    private final MockIAService mockIAService = new MockIAService();

    private final Consulta consulta = new Consulta();

    @FXML
    public void initialize() {
        cbEstado.getItems().addAll("Feliz", "Triste", "Ansioso", "Estressado");
        cbTom.getItems().addAll("Leve", "Moderado", "Intenso");

        btnEnviar.setOnAction(event -> enviarConsulta());
    }

    private void enviarConsulta() {
        String resposta = mockIAService.gerarResposta(
                cbEstado.getValue(),
                tfDicas.getText(),
                taRelato.getText(),
                cbTom.getValue()
        );

        if (!ConsultaService.camposPreenchidos(cbEstado.getValue(), tfDicas.getText(), taRelato.getText(), cbTom.getValue())) {
            AlertUtils.showAlert("Erro", "Todos os campos devem ser preenchidos");
            return;
        }

        consulta.setId(UUID.randomUUID().toString());
        consulta.setEstadoMental(cbEstado.getValue());
        consulta.setDicasSobre(tfDicas.getText());
        consulta.setRelato(taRelato.getText());
        consulta.setTomConsulta(cbTom.getValue());
        consulta.setRespostaIA(resposta);
        consulta.setDataConsulta(LocalDateTime.now());

        consultaDAO.salvar(consulta);

        taResposta.setText(resposta);

        cbEstado.getSelectionModel().clearSelection();
        tfDicas.clear();
        taRelato.clear();
        cbTom.getSelectionModel().clearSelection();
        AlertUtils.showAlert("Sucesso", "Consulta Feita com sucesso!");
    }
}
