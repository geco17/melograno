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
        var result = appControllerService.saveSaveAsAction(stage, document());
        switch (result) {
            case SAVE_YES:
            case SAVE_NO:
            case SAVE_NOT_NECESSARY:
                stage.close();
                break;
            case SAVE_CANCEL:
                // do nothing - they cancelled
                break;
            default:
                throw new IllegalStateException("Unknown save result: " + result);
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
        var result = appControllerService.saveSaveAsAction(stage(), document());
        switch (result) {
            case SAVE_YES:
            case SAVE_NO:
            case SAVE_NOT_NECESSARY:
                textEditor.clear();
                appControllerService.setFileModified(false);
                break;
            case SAVE_CANCEL:
                // do nothing - they cancelled
                break;
            default:
                throw new IllegalStateException("Unknown save result: " + result);
        }
    }

    public void openActionHandler(ActionEvent actionEvent) {
        appControllerService.openAction(
                stage(),
                document(),
                path -> {
                    try {
                        textEditor.setText(Files.readString(path));
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                });
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
