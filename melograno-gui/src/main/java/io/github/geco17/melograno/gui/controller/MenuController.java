package io.github.geco17.melograno.gui.controller;

import io.github.geco17.melograno.gui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;

import java.util.ResourceBundle;

public class MenuController {

    @FXML
    private MenuBar menuBar;

    public void handleAboutAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(App.resourceBundle().getString("dialog.about.title"));
        alert.setHeaderText(App.resourceBundle().getString("dialog.about.header.text"));
        alert.setContentText(App.resourceBundle().getString("dialog.about.content.text"));
        alert.show();
    }
}
