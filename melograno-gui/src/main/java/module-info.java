module melograno.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.logging;
    requires eu.mihosoft.monacofx;
    exports io.github.geco17.melograno.gui;
    exports io.github.geco17.melograno.gui.controller;
    opens io.github.geco17.melograno.gui to javafx.fxml;
    opens io.github.geco17.melograno.gui.controller to javafx.fxml;
}