package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import main.easybussro.services.BoughtTripsService;
import main.easybussro.constants.RawTimeFilterKeys;
import main.easybussro.state.Globe;
import main.easybussro.utils.SceneSwitcher;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashBoardController {
    Globe store = Globe.getGlobe();

    @FXML
    protected ListView foundRoutesView;

    @FXML
    protected void onTripPlan() throws IOException {
        new SceneSwitcher(foundRoutesView.getScene(), "/main.easybussro/tripsPlan-view.fxml", new TripsPlanController()).loadScene();
    }

    @FXML
    protected void onLogout() throws IOException {
        store.emptyGlobe();
        new SceneSwitcher(foundRoutesView.getScene(), "/main.easybussro/auth-view.fxml").loadScene();
    }

    @FXML
    protected void goToTripsHistory() throws IOException {
        new SceneSwitcher(foundRoutesView.getScene(), "/main.easybussro/tripsHistroy-view.fxml", new TripsHistoryController()).loadScene();
    }

    @FXML
    protected void goToAllRoutes() throws IOException {
        new SceneSwitcher(foundRoutesView.getScene(), "/main.easybussro/all-routes-view.fxml", new AllRoutesController()).loadScene();
    }

    @FXML
    public void initialize() throws ExecutionException, InterruptedException {
        BoughtTripsService boughtTripsService = new BoughtTripsService();
        List<String> foundFutureTrips = boughtTripsService.getAllBoughtItemsLabels(RawTimeFilterKeys.FUTURE);
        foundRoutesView.getItems().setAll(foundFutureTrips);
    }
}
