package io.github.geco17.melograno.gui;

import io.github.geco17.melograno.gui.controller.AppController;
import io.github.geco17.melograno.gui.factory.ControllerFactory;
import io.github.geco17.melograno.gui.factory.DialogFactory;
import io.github.geco17.melograno.gui.service.AppControllerService;
import io.github.geco17.melograno.gui.service.FileUIService;
import io.github.geco17.melograno.gui.util.S;
import io.github.geco17.melograno.service.impl.AppStatusServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private final ControllerFactory controllerFactory;

    public App(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public App() {
        controllerFactory = new ControllerFactory(
                new AppController(
                        new AppControllerService(
                                new DialogFactory(),
                                new FileUIService(
                                        new AppStatusServiceImpl()))));
    }

    /**
     * Start the melograno gui.
     * @param stage The main stage.
     * @throws IOException
     */
    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle(S.val("app.title"));
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/main.fxml"));
        loader.setResources(S.bundle());
        loader.setControllerFactory(controllerFactory.callback());
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setResizable(true);
        stage.setMinHeight(320);
        stage.setMinWidth(200);
        stage.show();
    }

    /**
     * melograno gui entry point.
     * @param args The application arguments.
     */
    public static void main(final String[] args) {
        launch();
    }

}
