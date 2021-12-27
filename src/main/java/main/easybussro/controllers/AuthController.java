package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.easybussro.models.User;
import main.easybussro.services.UserService;
import main.easybussro.state.Context;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AuthController {
    Globe state = Globe.getGlobe();

    @FXML
    private Label welcomeText;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    protected void onRegister() throws IOException, ExecutionException, InterruptedException {
        // todo: add server check.
        User newUser = new User(usernameField.getText(), passwordField.getText());
        UserService userService = new UserService();
        userService.registerUser(newUser);

        Context authContext = new Context();
        authContext.putItem("USERNAME", usernameField.getText());
        state.putContext(ContextEnum.AUTHENTICATED_USER, authContext);

        System.out.println(state.getContext(ContextEnum.AUTHENTICATED_USER).getState("USERNAME"));

//        SceneSwitcher dashBoardScene = new SceneSwitcher(usernameField.getScene(), "/main.easybussro/dashBoard-view.fxml", new DashBoardController());
//        dashBoardScene.loadScene();
    }

    @FXML
    protected void onLogin() {
        welcomeText.setText("register");
    }
}
