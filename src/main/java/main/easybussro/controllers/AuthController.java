package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.easybussro.constants.MessageCodes;
import main.easybussro.models.Route;
import main.easybussro.models.RouteDistance;
import main.easybussro.models.User;
import main.easybussro.services.AuthService;
import main.easybussro.services.RouteDistancesService;
import main.easybussro.services.RoutesService;
import main.easybussro.state.Context;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;
import main.easybussro.utils.SceneSwitcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthController {
    Globe state = Globe.getGlobe();

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
            this.handleSuccessAuth();
        }else if(response == MessageCodes.ALREADY_EXISTS){
            errorLabel.setText("User already exists");
        }else{
            errorLabel.setText("Something went wrong");
        }
    }

    @FXML
    protected void onLogin() throws ExecutionException, InterruptedException, IOException {
//        Vector<Station> stations = new Vector<>();
//
//        stations.add(new Station("Suceava", "20:00", 1));
//        stations.add(new Station("Falticeni", "21:00", 2));
//        stations.add(new Station("Roman", "22:00", 3));
//        stations.add(new Station("Bacau", "23:15", 4));
//        stations.add(new Station("Adjud", "01:00", 5));
//        stations.add(new Station("Tecuci", "01:30", 6));
//        stations.add(new Station("Galati", "02:10", 7));
//        stations.add(new Station("Braila", "03:30", 8));
//        stations.add(new Station("Harsova", "04:00", 9));
//        stations.add(new Station("Constanta", "05:00", 10));
//        Route route = new Route(stations);
//

        User newUser = new User(usernameField.getText(), passwordField.getText());
        AuthService authService = new AuthService();

        MessageCodes response = authService.loginUser(newUser);

        if(response == MessageCodes.SUCCESS){
           this.handleSuccessAuth();
        }else if(response == MessageCodes.NOT_FOUND){
            errorLabel.setText("User does not exists");
        }else if(response == MessageCodes.INVALID_PASSWORD){
            errorLabel.setText("Wrong password");
        }else{
            errorLabel.setText("Something went wrong");
        }
    }

    private void preloadRouteDistances() throws ExecutionException, InterruptedException {
        RouteDistancesService routeDistancesService = new RouteDistancesService();

        HashMap<String, RouteDistance> foundDistances = routeDistancesService.getAllDistances();

        Context< HashMap<String, RouteDistance>> authContext = new Context();
        authContext.putItem("DISTANCES", foundDistances);
        state.putContext(ContextEnum.ROUTE_DISTANCES, authContext);
    }

    private void preloadUserMetadata(){
        Context<String> authContext = new Context();
        authContext.putItem("USERNAME", usernameField.getText());
        System.out.println(usernameField.getText());
        state.putContext(ContextEnum.AUTHENTICATED_USER, authContext);
    }

    private void preloadAppBussRoutes() throws ExecutionException, InterruptedException {
        RoutesService routesService = new RoutesService();
        List<Route> routes = routesService.getAllRoutes();

        Context<List<Route>> routesContext = new Context();
        routesContext.putItem("BUSS-ROUTES", routes);
        state.putContext(ContextEnum.AVAIlABLE_ROUTES, routesContext);
    }

    private void handleSuccessAuth() throws IOException, ExecutionException, InterruptedException {
        preloadUserMetadata();
        preloadAppBussRoutes();
        preloadRouteDistances();

        SceneSwitcher dashBoardScene = new SceneSwitcher(usernameField.getScene(), "/main.easybussro/dashBoard-view.fxml", new DashBoardController());
        dashBoardScene.loadScene();
    }
}
