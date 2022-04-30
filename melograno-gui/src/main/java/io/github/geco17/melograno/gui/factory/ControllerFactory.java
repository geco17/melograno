package io.github.geco17.melograno.gui.factory;

import io.github.geco17.melograno.gui.controller.AppController;
import javafx.util.Callback;

public class ControllerFactory {

    private final AppController appController;

    public ControllerFactory(AppController appController) {
        this.appController = appController;
    }

    public Callback<Class<?>, Object> callback() {
        return clazz -> {
            if (AppController.class.equals(clazz)) {
                return appController;
            }
            return null;
        };
    }
}
