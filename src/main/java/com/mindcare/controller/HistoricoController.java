package com.mindcare.controller;

import com.mindcare.dao.ConsultaDAO;
import com.mindcare.dao.DiarioDAO;
import com.mindcare.model.Consulta;
import com.mindcare.model.Diario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

//import java.awt.event.MouseEvent;
import javafx.scene.input.MouseEvent;
import java.util.List;




import static com.mindcare.util.AlertUtils.showAlert;

public class HistoricoController {

    @FXML
    private ListView<Diario> listaDiarios;

    @FXML
    private Button btnExcluirDiario;

    @FXML
    private ListView<Consulta> listaConsultas;

    @FXML
    private Button btnExcluirConsulta;

    @FXML
    private Button btnAtualizar;

    private DiarioDAO diarioDAO = new DiarioDAO();
    private ConsultaDAO consultaDAO = new ConsultaDAO();

    @FXML
    public void initialize() {
        atualizarListas();

        btnExcluirDiario.setOnAction(event -> excluirDiario());
        btnExcluirConsulta.setOnAction(event -> excluirConsulta());
        btnAtualizar.setOnAction(event -> atualizarListas());

        // Ao clicar, mostra detalhes Diário
        listaDiarios.setOnMouseClicked((MouseEvent e) -> {
            Diario sel = listaDiarios.getSelectionModel().getSelectedItem();
            if (sel != null && e.getClickCount() == 2) {
                showAlert("Detalhes do Diário",
                        "Título: " + sel.getTitulo() +
                                "\nData: " + sel.getDataCriacao() +
                                "\n\n" + sel.getConteudo());
            }



        });

        // Ao clicar, mostra detalhes Consulta
        listaConsultas.setOnMouseClicked(e -> {
            Consulta sel = listaConsultas.getSelectionModel().getSelectedItem();
            if (sel != null && e.getClickCount() == 2) {
                showAlert("Detalhes da Consulta",
                        "Estado Mental: " + sel.getEstadoMental() +
                                "\nTom: " + sel.getTomConsulta() +
                                "\nData: " + sel.getDataConsulta() +
                                "\n\nRelato:\n" + sel.getRelato() +
                                "\n\nResposta IA:\n" + sel.getRespostaIA());
            }
        });


    }

    private void atualizarListas() {
        List<Diario> diarios = diarioDAO.listar();
        List<Consulta> consultas = consultaDAO.listar();

        listaDiarios.setItems(FXCollections.observableArrayList(diarios));
        listaConsultas.setItems(FXCollections.observableArrayList(consultas));

    }


    private void excluirDiario() {
        Diario selecionado = listaDiarios.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            diarioDAO.deletar(selecionado.getId());
            listaDiarios.getItems().remove(selecionado);
        }
    }


    private void excluirConsulta() {
        Consulta selecionado = listaConsultas.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            consultaDAO.deletar(selecionado.getId());
            listaConsultas.getItems().remove(selecionado);
        }
    }

}
