package main.easybussro.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.easybussro.utils.SceneSwitcher;
import org.w3c.dom.events.EventTarget;

import java.io.IOException;

public class DashBoardController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void planificareCursa() throws IOException {
        new SceneSwitcher(welcomeText.getScene(), "/main.easybussro/planificareCursa-view.fxml", new PlanificareCursaController()).loadScene();
    }

}
