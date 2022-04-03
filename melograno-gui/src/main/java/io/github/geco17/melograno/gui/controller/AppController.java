package io.github.geco17.melograno.gui.controller;

import eu.mihosoft.monacofx.MonacoFX;
import io.github.geco17.melograno.gui.util.S;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;

public class AppController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MonacoFX textEditor;

    public void aboutActionHandler(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(S.val("dialog.about.title"));
        alert.setHeaderText(S.val("dialog.about.header.text"));
        alert.setContentText(S.val("dialog.about.content.text"));
        alert.show();
    }

    public void exitActionHandler(ActionEvent actionEvent) {
        ((Stage) menuBar.getScene().getWindow()).close();
    }

}
