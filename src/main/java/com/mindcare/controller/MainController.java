package com.mindcare.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane tabDiarioPane;

    @FXML
    private AnchorPane tabConsultaPane;

    @FXML
    private AnchorPane tabHistoricoPane;

    @FXML
    public void initialize() {
        loadView(tabDiarioPane, "/fxml/DiarioView.fxml");
        loadView(tabConsultaPane, "/fxml/ConsultaView.fxml");
        loadView(tabHistoricoPane, "/fxml/HistoricoView.fxml");
    }

    private void loadView(AnchorPane pane, String fxmlPath) {
        try {
            Parent content = FXMLLoader.load(getClass().getResource(fxmlPath));

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

            pane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
