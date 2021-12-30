package main.easybussro.models;

import java.util.List;
import java.util.Vector;

public class Route {
    public List<Station> stations;
    public String routeID;

    public Route() {}

    public Route(List<Station> stations, String routeID) {
        this.stations = stations; this.routeID = routeID;
    }
}
