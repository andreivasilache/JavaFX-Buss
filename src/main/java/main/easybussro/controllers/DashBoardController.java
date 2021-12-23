package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashBoardController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onTest() {
        welcomeText.setText("register");
    }

}
