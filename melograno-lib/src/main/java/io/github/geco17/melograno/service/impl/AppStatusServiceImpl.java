package io.github.geco17.melograno.service.impl;

import io.github.geco17.melograno.service.api.AppStatusService;

import java.io.File;

public class AppStatusServiceImpl implements AppStatusService {
    @Override
    public boolean isFileModified() {
        return false;
    }

    @Override
    public void setFileModified(boolean fileModified) {

    }

    @Override
    public boolean save(File file) {
        return false;
    }
}
