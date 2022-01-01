package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import main.easybussro.constants.RawTimeFilterKeys;
import main.easybussro.services.BoughtTripsService;
import main.easybussro.utils.SceneSwitcher;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TripsHistoryController {
    @FXML
    protected ListView pastTrips;

    @FXML
    protected void onBack() throws IOException {
        SceneSwitcher dashBoardScene = new SceneSwitcher(pastTrips.getScene(), "/main.easybussro/dashBoard-view.fxml", new DashBoardController());
        dashBoardScene.loadScene();
    }

    @FXML
    public void initialize() throws ExecutionException, InterruptedException {
        BoughtTripsService boughtTripsService = new BoughtTripsService();
        List<String> foundFutureTrips = boughtTripsService.getAllBoughtItemsLabels(RawTimeFilterKeys.PAST);
        pastTrips.getItems().setAll(foundFutureTrips);
    }
}
