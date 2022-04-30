package io.github.geco17.melograno.service.api;

import java.io.File;

public interface AppStatusService {

    boolean isFileModified();

    void setFileModified(boolean fileModified);

    boolean saveAs(File file);

    boolean isNewFile();

    void save();
}
