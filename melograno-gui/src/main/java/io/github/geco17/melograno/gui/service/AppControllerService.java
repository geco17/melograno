package io.github.geco17.melograno.gui.service;

import io.github.geco17.melograno.gui.factory.DialogFactory;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppControllerService {

    private final DialogFactory dialogFactory;

    private final FileUIService fileUIService;

    public AppControllerService(DialogFactory dialogFactory, FileUIService fileUIService) {
        this.dialogFactory = dialogFactory;
        this.fileUIService = fileUIService;
    }

    public boolean saveSaveAsAction(Stage stage) {
        if (!fileUIService.isFileModified()) {
            return true;
        }
        var result = new AtomicBoolean(false);
        dialogFactory.savePrompt().showAndWait().ifPresent(buttonType -> {
            String typeCode = buttonType.getButtonData().getTypeCode();
            result.set(toYesNoCancelSaveSaveAsResult(typeCode, stage));
        });
        return result.get();
    }

    boolean toYesNoCancelSaveSaveAsResult(String typeCode, Stage stage) {
        if (ButtonBar.ButtonData.YES.getTypeCode().equals(typeCode)) {
            return fileUIService.doSaveSaveAs(stage);
        } else if (ButtonBar.ButtonData.NO.getTypeCode().equals(typeCode)) {
            return true;
        } else if (ButtonBar.ButtonData.CANCEL_CLOSE.getTypeCode().equals(typeCode)) {
            return false;
        } else {
            throw new IllegalStateException("Unknown button type code: " + typeCode);
        }
    }

    public void setFileModified(boolean fileModified) {
        fileUIService.setFileModified(fileModified);
    }

    public void aboutAction() {
        dialogFactory.aboutDialog().show();
    }

    public Optional<Path> openAction(Stage stage) {
        var showOpenDialog = new AtomicBoolean(false);
        if (!fileUIService.isFileModified()) {
            showOpenDialog.set(true);
        } else {
            dialogFactory.savePrompt().showAndWait().ifPresent(buttonType -> {
                String typeCode = buttonType.getButtonData().getTypeCode();
                showOpenDialog.set(toYesNoCancelSaveSaveAsResult(typeCode, stage));
            });
        }
        if (showOpenDialog.get()) {
            Optional<File> fileOpt = fileUIService.doOpen(stage);
            return fileOpt.map(File::toPath);
        }
        return Optional.empty();
    }

    public void openError(Path path) {
        dialogFactory.errorDialog(
                "dialog.error.open.file",
                path.toAbsolutePath().toString()).show();
    }
}
