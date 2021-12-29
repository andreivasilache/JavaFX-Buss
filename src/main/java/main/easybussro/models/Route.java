package main.easybussro.models;

import java.util.Vector;

public class Route {
    public Vector<Station> stations;

    public Route(Vector<Station> stations) {
        this.stations = stations;
    }
}
