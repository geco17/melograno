package io.github.geco17.melograno.gui.controller;

import io.github.geco17.melograno.gui.service.AppControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private final AppControllerService appControllerService;

    public AppController(AppControllerService appControllerService) {
        this.appControllerService = appControllerService;
    }

    @FXML
    private Menu editMenu;
    @FXML
    private ContextMenu editorContextMenu;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextArea textEditor;

    public void aboutActionHandler(ActionEvent actionEvent) {
        appControllerService.aboutAction();
    }

    public void exitActionHandler(ActionEvent actionEvent) {
        var stage = stage();
        if (appControllerService.saveSaveAsAction(
                stage, document())) {
            stage.close();
        }
    }

    private byte[] document() {
        return textEditor.getText()
                .getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // bug or feature? the context menu must be initialized first, then the edit menu can be populated
        editorContextMenu.getItems()
                .forEach(item -> editMenu.getItems().add(item));
        textEditor.textProperty().addListener((observableValue, s, t1) -> appControllerService.setFileModified(true));
    }

    public void editDeleteActionHandler(ActionEvent actionEvent) {
        textEditor.deleteText(textEditor.getSelection());
    }

    public void editPasteActionHandler(ActionEvent actionEvent) {
        textEditor.paste();
    }

    public void editCopyActionHandler(ActionEvent actionEvent) {
        textEditor.copy();
    }

    public void editCutActionHandler(ActionEvent actionEvent) {
        textEditor.cut();
    }

    public void editSelectAllActionHandler(ActionEvent actionEvent) {
        textEditor.selectAll();
    }

    public void newActionHandler(ActionEvent actionEvent) {
        if (appControllerService.saveSaveAsAction(stage(), document())) {
            textEditor.clear();
        }
    }

    public void openActionHandler(ActionEvent actionEvent) {
        Optional<Path> pathOpt = appControllerService.openAction(stage(), document());
        if (pathOpt.isPresent()) {
            try {
                textEditor.setText(Files.readString(pathOpt.get()));
            } catch (IOException e) {
                appControllerService.openError(pathOpt.get());
            }
        }
    }

    public void saveActionHandler(ActionEvent actionEvent) {
        appControllerService.noPromptSaveAction(stage(), document());
    }

    public void saveAsActionHandler(ActionEvent actionEvent) {
        appControllerService.noPromptSaveAsAction(stage(), document());
    }

    private Stage stage() {
        return (Stage) menuBar.getScene().getWindow();
    }

}
