package main.easybussro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import main.easybussro.models.Route;
import main.easybussro.models.Station;
import main.easybussro.services.RoutesService;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;
import main.easybussro.utils.SceneSwitcher;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class AllRoutesController {
    Globe store = Globe.getGlobe();
    Vector<Route> storeRoutes = (Vector<Route>) store.getContext(ContextEnum.AVAIlABLE_ROUTES).getState("BUSS-ROUTES");

    @FXML
    protected ListView allRoutesList;

    @FXML
    protected void onBack() throws IOException {
        SceneSwitcher dashBoardScene = new SceneSwitcher(allRoutesList.getScene(), "/main.easybussro/dashBoard-view.fxml", new DashBoardController());
        dashBoardScene.loadScene();
    }

    @FXML
    public void initialize() throws ExecutionException, InterruptedException {
        List<String> toBeDisplayed = new ArrayList<>();

        if(storeRoutes.size() > 0){
            for(Route currentRoute: storeRoutes){
                String concatonatedStationNames = "";
                for(Object currentStation: currentRoute.stations){
                    String stationName = "";
                    String arrivalTime = "";
                    if(currentStation instanceof HashMap<?,?>){
                        HashMap<String, Object> currentStationHash =(HashMap<String, Object>) currentStation;
                        Station parsedStation = RoutesService.getStationFromHashMap(currentStationHash);
                        arrivalTime = parsedStation.arrivalTime;
                        stationName = parsedStation.stationName;
                    }
                    if(currentStation instanceof Station){
                        arrivalTime = ((Station) currentStation).arrivalTime;
                        stationName = ((Station) currentStation).stationName;
                    }

                    concatonatedStationNames = concatonatedStationNames + (concatonatedStationNames == "" ? "" : " - ") + stationName + "("+ arrivalTime+")";
                }
                toBeDisplayed.add(concatonatedStationNames);
            }
        }else{
            toBeDisplayed.add("Nu au fost gasite rute");
        }

        allRoutesList.getItems().setAll(toBeDisplayed);
    }
}
