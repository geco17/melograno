package io.github.geco17.melograno.service.api;

import java.io.File;

public interface AppStatusService {

    boolean isFileModified();

    void setFileModified(boolean fileModified);

    void setCurrentFile(File file);

    void saveAs(File file, byte[] bytes);

    boolean isNewFile();

    void save(byte[] bytes);
}
