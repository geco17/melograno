package io.github.geco17.melograno.service.impl;

import io.github.geco17.melograno.service.api.AppStatusService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AppStatusServiceImpl implements AppStatusService {

    private final AtomicBoolean modified = new AtomicBoolean(false);

    private final AtomicReference<Optional<File>> currentFile = new AtomicReference<>(Optional.empty());

    @Override
    public boolean isFileModified() {
        return modified.get();
    }

    @Override
    public void setFileModified(boolean fileModified) {
        modified.set(fileModified);
    }

    @Override
    public void saveAs(File file, byte[] bytes) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // TODO buffer
            fos.write(bytes);
            fos.flush();
            currentFile.set(Optional.of(file));
            setFileModified(false);
        } catch (IOException e) {
            // TODO custom exception and handling
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isNewFile() {
        return currentFile.get().isEmpty();
    }

    @Override
    public void save(byte[] bytes) {
        if (currentFile.get().isEmpty()) {
            // TODO custom exception and handling
            throw new RuntimeException("Current file not set");
        }
        saveAs(currentFile.get().get(), bytes);
    }
}
