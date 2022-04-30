package io.github.geco17.melograno.gui;

import io.github.geco17.melograno.gui.controller.AppController;
import io.github.geco17.melograno.gui.factory.ControllerFactory;
import io.github.geco17.melograno.gui.util.S;
import io.github.geco17.melograno.service.api.AppStatusService;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class GuiTest {

    private final AppStatusService appStatusService = mock(AppStatusService.class);

    private final ControllerFactory controllerFactory = new ControllerFactory(
            new AppController(appStatusService));

    @Start
    private void start(Stage stage) throws IOException {
        new App(controllerFactory).start(stage);
    }

    @Test
    public void testShowSaveDialogOnExitForModifiedFileAndCancelExit(FxRobot robot) {
        var windowSpy = spy(robot.window(S.val("app.title")));
        when(appStatusService.isFileModified()).thenReturn(true);
        robot.clickOn(S.val("menu.main.file"))
                .clickOn(S.val("menu.main.file.exit"));
        verify(appStatusService).isFileModified();
        robot.clickOn(S.val("dialog.prompt.save.button.cancel"));
        verifyNoInteractions(windowSpy);
    }

    @Test
    public void testShowSaveDialogOnExitForModifiedFileAndClickNo(FxRobot robot) {
        var stage = spy((Stage) robot.window(S.val("app.title")));
        when(appStatusService.isFileModified()).thenReturn(true);
        robot.clickOn(S.val("menu.main.file"))
                .clickOn(S.val("menu.main.file.exit"));
        verify(appStatusService).isFileModified();
        robot.clickOn(S.val("dialog.prompt.save.button.dont_save"));
        verify(stage).getOnCloseRequest();
    }
}
