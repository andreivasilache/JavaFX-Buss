package main.easybussro.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import main.easybussro.models.Route;
import main.easybussro.models.Station;
import main.easybussro.services.RoutesService;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;

import java.util.*;

public class PlanificareCursaController {
    Globe store = Globe.getGlobe();
    Vector<Route> storeRoutes = (Vector<Route>) store.getContext(ContextEnum.AVAIlABLE_ROUTES).getState("BUSS-ROUTES");
    Map<String, Station> allStations = RoutesService.getAllStations(storeRoutes);

    @FXML
    protected ChoiceBox fromDropdown;
    ObservableList<String> fromDropdownList = FXCollections.observableArrayList();

    @FXML
    protected ChoiceBox toDropdown;
    ObservableList<String> toDropdownList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println(allStations.keySet());

        fromDropdownList.addAll(allStations.keySet());
        toDropdownList.addAll(allStations.keySet());

        fromDropdown.getItems().addAll(fromDropdownList);
        toDropdown.getItems().addAll(toDropdownList);
    }
}
