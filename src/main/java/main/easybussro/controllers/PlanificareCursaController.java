package main.easybussro.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import main.easybussro.models.Route;
import main.easybussro.models.RouteDistance;
import main.easybussro.models.Station;
import main.easybussro.services.RouteDistancesService;
import main.easybussro.services.RoutesService;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;

import java.time.LocalDate;
import java.util.*;

public class PlanificareCursaController {
    Globe store = Globe.getGlobe();
    Vector<Route> storeRoutes = (Vector<Route>) store.getContext(ContextEnum.AVAIlABLE_ROUTES).getState("BUSS-ROUTES");
    Map<String, Station> allStations = RoutesService.getAllStations(storeRoutes);

    HashMap<String, RouteDistance> storeRouteDistances = (HashMap<String, RouteDistance>) store.getContext(ContextEnum.ROUTE_DISTANCES).getState("DISTANCES");
    RouteDistancesService routeDistancesService = new RouteDistancesService();


    @FXML
    protected ChoiceBox fromDropdown;
    ObservableList<String> fromDropdownList = FXCollections.observableArrayList();

    @FXML
    protected ChoiceBox toDropdown;
    ObservableList<String> toDropdownList = FXCollections.observableArrayList();

    @FXML
    protected ListView foundRoutesView;
    HashMap<String,Route> lastFoundRoutesAfterSearch = null;

    @FXML
    protected DatePicker datePicker;

    private void prepopulateInputs(){
        fromDropdownList.addAll(allStations.keySet());
        toDropdownList.addAll(allStations.keySet());

        fromDropdown.getItems().addAll(fromDropdownList);
        toDropdown.getItems().addAll(toDropdownList);

        if (!fromDropdown.getItems().isEmpty()) {
            fromDropdown.getSelectionModel().select(0);
        }
        if (toDropdown.getItems().size() >= 1) {
            toDropdown.getSelectionModel().select(1);
        }
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void initialize() {
        System.out.println(allStations.keySet());

        prepopulateInputs();

        fromDropdown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                toDropdown.getItems().setAll(toDropdownList);
                String selectedValue = (String) fromDropdown.getItems().get((Integer) number2);
                toDropdown.getItems().remove(selectedValue);
                if (!toDropdown.getItems().isEmpty()) {
                    toDropdown.getSelectionModel().select(0);
                }
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

        List<String> toBeDisplayed = new ArrayList<>();

        if(foundRoutes.size() > 0){
            lastFoundRoutesAfterSearch = foundRoutesRaw;
            for(Route currentRoute: foundRoutes){
                String concatonatedStationNames = "";
                int currentRouteDistance = routeDistancesService.getDistanceOfRoutes(currentRoute.stations, storeRouteDistances);
                for(Station currentStation: currentRoute.stations){
                    concatonatedStationNames = concatonatedStationNames + ((concatonatedStationNames == "" ? "["+currentRouteDistance+"km - "+currentRouteDistance*0.75+"RON] " : " - ") +currentStation.stationName + "("+ currentStation.arrivalTime+")");
                }
                toBeDisplayed.add(concatonatedStationNames);
            }
        }else{
            toBeDisplayed.add("Nu au fost gasite rezultate");
            foundRoutesView.setDisable(true);
            lastFoundRoutesAfterSearch = null;
        }
        foundRoutesView.getItems().setAll(toBeDisplayed);
    }

    @FXML
    protected void onBuyTrip(){
        int selectedItemIndex =foundRoutesView.getSelectionModel().getSelectedIndex();

        Object selectedItemHashKey = lastFoundRoutesAfterSearch.keySet().toArray()[selectedItemIndex];
        Route selectedRoute = lastFoundRoutesAfterSearch.get(selectedItemHashKey);
    }
}
