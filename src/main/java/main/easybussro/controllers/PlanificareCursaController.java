package main.easybussro.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
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
    protected ListView foundRoutesView;

    @FXML
    public void initialize() {
        System.out.println(allStations.keySet());

        fromDropdownList.addAll(allStations.keySet());
        toDropdownList.addAll(allStations.keySet());

        fromDropdown.getItems().addAll(fromDropdownList);
        toDropdown.getItems().addAll(toDropdownList);

        fromDropdown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                toDropdown.getItems().removeAll();
                toDropdown.getItems().addAll(toDropdownList);
                String selectedValue = (String) fromDropdown.getItems().get((Integer) number2);
                toDropdown.getItems().remove(selectedValue);
            }
        });
    }

    @FXML
    protected void onRouteSearch(){
        foundRoutesView.setDisable(false);
        foundRoutesView.getItems().removeAll();
        String fromSelectedStation = (String) fromDropdown.getSelectionModel().getSelectedItem();
        String toSelectedStation = (String) toDropdown.getSelectionModel().getSelectedItem();

        HashMap<String,Route> foundRoutesRaw = RoutesService.searchForRoute(storeRoutes, fromSelectedStation, toSelectedStation);

        Collection<Route> foundRoutes = foundRoutesRaw.values();

        if(foundRoutes.size() > 0){
            for(Route currentRoute: foundRoutes){
                String concatonatedStationNames = "";
                for(Station currentStation: currentRoute.stations){
                    concatonatedStationNames = concatonatedStationNames + (" - "+currentStation.stationName);
                }
                foundRoutesView.getItems().add(concatonatedStationNames);
            }
        }else{
            foundRoutesView.getItems().add("Nu au fost gasite rezultate");
            foundRoutesView.setDisable(true);
        }

    }
}
