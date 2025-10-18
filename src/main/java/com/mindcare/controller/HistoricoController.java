package com.mindcare.controller;

import com.mindcare.dao.ConsultaDAO;
import com.mindcare.dao.DiarioDAO;
import com.mindcare.model.Consulta;
import com.mindcare.model.Diario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

public class HistoricoController {

    @FXML
    private ListView<String> listaDiarios;

    @FXML
    private Button btnExcluirDiario;

    @FXML
    private ListView<String> listaConsultas;

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
    }

    private void atualizarListas() {
        List<Diario> diarios = diarioDAO.listar();
        List<Consulta> consultas = consultaDAO.listar();

        listaDiarios.setItems(FXCollections.observableArrayList(
                diarios.stream().map(Diario::toString).toList()
        ));
        listaConsultas.setItems(FXCollections.observableArrayList(
                consultas.stream().map(Consulta::toString).toList()
        ));
    }

    private void excluirDiario() {
        String selecionado = listaDiarios.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            List<Diario> diarios = diarioDAO.listar();
            Diario diarioParaRemover = diarios.stream()
                    .filter(d -> d.toString().equals(selecionado))
                    .findFirst()
                    .orElse(null);
            if (diarioParaRemover != null) {
                diarioDAO.deletar(diarioParaRemover.getId());
                listaDiarios.getItems().remove(selecionado);
            }
        }
    }

    private void excluirConsulta() {
        String selecionado = listaConsultas.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            List<Consulta> consultas = consultaDAO.listar();
            Consulta consultaParaRemover = consultas.stream()
                    .filter(c -> c.toString().equals(selecionado))
                    .findFirst()
                    .orElse(null);
            if (consultaParaRemover != null) {
                consultaDAO.deletar(consultaParaRemover.getId());
                listaConsultas.getItems().remove(selecionado);
            }
        }
    }
}
