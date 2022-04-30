package io.github.geco17.melograno.gui.service;

import io.github.geco17.melograno.gui.util.S;
import io.github.geco17.melograno.service.api.AppStatusService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class FileUIService {

    private final AppStatusService appStatusService;

    public FileUIService(AppStatusService appStatusService) {
        this.appStatusService = appStatusService;
    }

    public boolean doSaveSaveAs(Stage stage, byte[] bytes) {
        if (appStatusService.isNewFile()) {
            return doSaveAs(stage, bytes);
        }
        appStatusService.save(bytes);
        return true;
    }

    public boolean doSaveAs(Stage stage, byte[] bytes) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle(S.val("dialog.prompt.save_as.title"));
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter(
                        S.val("dialog.prompt.extension_filter.all"),
                        "*.*"));
        var file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            appStatusService.saveAs(file, bytes);
            return true;
        } else {
            return false;
        }
    }

    public boolean isFileModified() {
        return appStatusService.isFileModified();
    }

    public void setFileModified(boolean fileModified) {
        appStatusService.setFileModified(fileModified);
    }

    public Optional<File> doOpen(Stage stage) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle(S.val("dialog.prompt.open.title"));
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter(
                        S.val("dialog.prompt.extension_filter.all"),
                        "*.*"));
        var file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return Optional.of(file);
        } else {
            return Optional.empty();
        }
    }
}
