package io.github.geco17.melograno.gui.service;

import io.github.geco17.melograno.gui.factory.DialogFactory;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class AppControllerService {

    private final DialogFactory dialogFactory;

    private final FileUIService fileUIService;

    public AppControllerService(DialogFactory dialogFactory, FileUIService fileUIService) {
        this.dialogFactory = dialogFactory;
        this.fileUIService = fileUIService;
    }

    public enum SaveSaveAsResult {
        SAVE_NOT_NECESSARY,
        SAVE_YES,
        SAVE_NO,
        SAVE_CANCEL
    }

    public SaveSaveAsResult saveSaveAsAction(Stage stage, byte[] bytes) {
        if (!fileUIService.isFileModified()) {
            return SaveSaveAsResult.SAVE_NOT_NECESSARY;
        }
        var promptResult = dialogFactory.savePrompt().showAndWait();
        if (promptResult.isPresent()) {
            String typeCode = promptResult.get().getButtonData().getTypeCode();
            return toYesNoCancelSaveSaveAsResult(typeCode, stage, bytes);
        }
        throw new IllegalStateException("No save result");
    }

    public void setFileModified(boolean fileModified) {
        fileUIService.setFileModified(fileModified);
    }

    public void aboutAction() {
        dialogFactory.aboutDialog().show();
    }

    public void openAction(Stage stage, byte[] bytes, Function<Path, Boolean> callback) {
        Optional<Path> pathOpt = openPath(stage, bytes);
        if (pathOpt.isPresent()) {
            if (callback.apply(pathOpt.get())) {
                setCurrentFile(pathOpt.get().toFile());
            } else {
                openError(pathOpt.get());
            }
        }
    }

    private void setCurrentFile(File file) {
        fileUIService.setCurrentFile(file);
    }

    private Optional<Path> openPath(Stage stage, byte[] bytes) {
        var showOpenDialog = new AtomicBoolean(false);
        if (!fileUIService.isFileModified()) {
            showOpenDialog.set(true);
        } else {
            dialogFactory.savePrompt().showAndWait().ifPresent(buttonType -> {
                String typeCode = buttonType.getButtonData().getTypeCode();
                var result = toYesNoCancelSaveSaveAsResult(typeCode, stage, bytes);
                switch (result) {
                    case SAVE_YES:
                    case SAVE_NO:
                    case SAVE_NOT_NECESSARY:
                        showOpenDialog.set(true);
                        break;
                    case SAVE_CANCEL:
                        showOpenDialog.set(false);
                        break;
                    default:
                        throw new IllegalStateException("Unknown save / save as result: " + result);
                }

            });
        }
        if (showOpenDialog.get()) {
            Optional<File> fileOpt = fileUIService.doOpen(stage);
            return fileOpt.map(File::toPath);
        }
        return Optional.empty();
    }

    private SaveSaveAsResult toYesNoCancelSaveSaveAsResult(String typeCode, Stage stage, byte[] bytes) {
        if (ButtonBar.ButtonData.YES.getTypeCode().equals(typeCode)) {
            if (fileUIService.doSaveSaveAs(stage, bytes)) {
                return SaveSaveAsResult.SAVE_YES;
            } else {
                return SaveSaveAsResult.SAVE_CANCEL;
            }
        } else if (ButtonBar.ButtonData.NO.getTypeCode().equals(typeCode)) {
            return SaveSaveAsResult.SAVE_NO;
        } else if (ButtonBar.ButtonData.CANCEL_CLOSE.getTypeCode().equals(typeCode)) {
            return SaveSaveAsResult.SAVE_CANCEL;
        } else {
            throw new IllegalStateException("Unknown button type code: " + typeCode);
        }
    }


    public void openError(Path path) {
        dialogFactory.errorDialog(
                "dialog.error.open.file",
                path.toAbsolutePath().toString()).show();
    }

    public void noPromptSaveAction(Stage stage, byte[] bytes) {
        fileUIService.doSaveSaveAs(stage, bytes);
    }

    public void noPromptSaveAsAction(Stage stage, byte[] bytes) {
        fileUIService.doSaveAs(stage, bytes);
    }
}
