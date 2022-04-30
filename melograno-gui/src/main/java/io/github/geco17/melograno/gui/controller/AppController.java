package io.github.geco17.melograno.gui.controller;

import io.github.geco17.melograno.gui.util.S;
import io.github.geco17.melograno.service.api.AppStatusService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppController implements Initializable {

    private final AppStatusService appStatusService;

    public AppController(AppStatusService appStatusService) {
        this.appStatusService = appStatusService;
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
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(S.val("dialog.about.title"));
        alert.setHeaderText(S.val("dialog.about.header.text"));
        alert.setContentText(S.val("dialog.about.content.text"));
        alert.show();
    }

    public void exitActionHandler(ActionEvent actionEvent) {
        var exit = new AtomicBoolean(true);
        if (appStatusService.isFileModified()) {
            var alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(S.val("app.title"));
            alert.setHeaderText(S.val("dialog.prompt.save.header.text"));
            alert.setContentText(S.val("dialog.prompt.save.content.text"));
            alert.getButtonTypes().setAll(
                    new ButtonType(
                            S.val("dialog.prompt.save.button.save"),
                            ButtonBar.ButtonData.YES),
                    new ButtonType(
                            S.val("dialog.prompt.save.button.dont_save"),
                            ButtonBar.ButtonData.NO),
                    new ButtonType(
                            S.val("dialog.prompt.save.button.cancel"),
                            ButtonBar.ButtonData.CANCEL_CLOSE));
            alert.showAndWait().ifPresent(buttonType -> {
                String typeCode = buttonType.getButtonData().getTypeCode();
                if (ButtonBar.ButtonData.YES.getTypeCode().equals(typeCode)) {

                } else if (ButtonBar.ButtonData.NO.getTypeCode().equals(typeCode)) {
                    exit.set(true);
                } else if (ButtonBar.ButtonData.CANCEL_CLOSE.getTypeCode().equals(typeCode)) {
                    exit.set(false);
                }
            });
        }
        if (exit.get()) {
            ((Stage) menuBar.getScene().getWindow()).close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // bug or feature? the context menu must be initialized first, then the edit menu can be populated
        editorContextMenu.getItems()
                .forEach(item -> editMenu.getItems().add(item));
        textEditor.textProperty().addListener((observableValue, s, t1) -> appStatusService.setFileModified(true));
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
        System.out.println("new clicked");
    }
}
