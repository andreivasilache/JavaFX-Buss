package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.easybussro.constants.MessageCodes;
import main.easybussro.models.User;
import main.easybussro.services.AuthService;
import main.easybussro.state.Context;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;
import main.easybussro.utils.SceneSwitcher;

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
    private Label errorLabel;

    @FXML
    protected void onRegister() throws IOException, ExecutionException, InterruptedException {
        // todo: add server check.
        User newUser = new User(usernameField.getText(), passwordField.getText());
        AuthService authService = new AuthService();

        MessageCodes response = authService.registerUser(newUser);

        if(response == MessageCodes.SUCCESS){
            this.handleSuccessAuth(newUser);
        }else if(response == MessageCodes.ALREADY_EXISTS){
            errorLabel.setText("User already exists");
        }else{
            errorLabel.setText("Something went wrong");
        }
    }

    @FXML
    protected void onLogin() throws ExecutionException, InterruptedException, IOException {
        User newUser = new User(usernameField.getText(), passwordField.getText());
        AuthService authService = new AuthService();

        MessageCodes response = authService.loginUser(newUser);

        if(response == MessageCodes.SUCCESS){
           this.handleSuccessAuth(newUser);
        }else if(response == MessageCodes.NOT_FOUND){
            errorLabel.setText("User does not exists");
        }else if(response == MessageCodes.INVALID_PASSWORD){
            errorLabel.setText("Wrong password");
        }else{
            errorLabel.setText("Something went wrong");
        }
        System.out.println(state.getContext(ContextEnum.AUTHENTICATED_USER).getState("USERNAME"));
    }

    private void handleSuccessAuth(User user) throws IOException {
        Context authContext = new Context();
        authContext.putItem("USERNAME", usernameField.getText());
        state.putContext(ContextEnum.AUTHENTICATED_USER, authContext);

        SceneSwitcher dashBoardScene = new SceneSwitcher(usernameField.getScene(), "/main.easybussro/dashBoard-view.fxml", new DashBoardController());
        dashBoardScene.loadScene();
    }
}
