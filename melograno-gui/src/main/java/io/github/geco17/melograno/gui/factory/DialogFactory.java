package io.github.geco17.melograno.gui.factory;

import io.github.geco17.melograno.gui.util.S;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

public class DialogFactory {

    public Alert savePrompt() {
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
        return alert;
    }

    public Alert aboutDialog() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(S.val("dialog.about.title"));
        alert.setHeaderText(S.val("dialog.about.header.text"));
        alert.setContentText(S.val("dialog.about.content.text"));
        return alert;
    }

    public Alert errorDialog(String key, String...args) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(S.val("app.title"));
        alert.setHeaderText(S.val("dialog.error.header.text"));
        alert.setContentText(S.val(key, args));
        return alert;
    }
}
