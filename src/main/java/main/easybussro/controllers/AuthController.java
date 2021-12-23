package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.easybussro.utils.SceneSwitcher;

import java.io.IOException;

public class AuthController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    protected void onRegister() throws IOException {
        SceneSwitcher dashBoardScene = new SceneSwitcher(emailField.getScene(), "/main.easybussro/dashBoard-view.fxml", new DashBoardController());
        dashBoardScene.loadScene();
        welcomeText.setText(emailField.getText());
    }

    @FXML
    protected void onLogin() {
        welcomeText.setText("register");
    }
}
