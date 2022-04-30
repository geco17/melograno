module melograno.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires melograno.lib;
    exports io.github.geco17.melograno.gui;
    exports io.github.geco17.melograno.gui.controller;
    exports io.github.geco17.melograno.gui.factory;
    opens io.github.geco17.melograno.gui to javafx.fxml;
    opens io.github.geco17.melograno.gui.controller to javafx.fxml;
}