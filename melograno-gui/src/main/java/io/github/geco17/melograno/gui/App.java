package io.github.geco17.melograno.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class App extends Application {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("gui");

    public static ResourceBundle resourceBundle() {
        return BUNDLE;
    }

    /**
     * Start the melograno gui.
     * @param stage The main stage.
     * @throws IOException
     */
    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle("Melograno");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/main.fxml"));
        loader.setResources(resourceBundle());
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
