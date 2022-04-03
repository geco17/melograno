module melograno.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    exports io.github.geco17.melograno.gui;
    exports io.github.geco17.melograno.gui.controller;
    opens io.github.geco17.melograno.gui to javafx.fxml;
}