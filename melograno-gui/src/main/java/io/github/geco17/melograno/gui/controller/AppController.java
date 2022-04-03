package io.github.geco17.melograno.gui.controller;

import io.github.geco17.melograno.gui.util.S;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AppController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private TextArea textEditor;

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
