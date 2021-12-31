package main.easybussro.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import main.easybussro.models.Route;
import main.easybussro.models.Station;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class RoutesService {
    Firestore db = FirestoreClient.getFirestore();
    public static final String routesCollection = "routesCollection";

    public void addRoutes(Route route){
        String id = db.collection(routesCollection).document().getId();
        db.collection(routesCollection).document(id).set(route);
    }

    public List<Route> getAllRoutes() throws ExecutionException, InterruptedException {
        List<Route> toBeReturned = new Vector<>();

        ApiFuture<QuerySnapshot> future= db.collection(routesCollection).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot  document : documents) {
            ArrayList<Station> currentStationList = (ArrayList<Station>) document.getData().get("stations");
            toBeReturned.add(new Route(currentStationList, document.getId()));
        }

        return toBeReturned;
    }

    private static Station getStationFromHashMap(Map<String, Object> toBeParsed){
        String stationName = (String) toBeParsed.get("stationName");
        String arrivalTime = (String) toBeParsed.get("arrivalTime");
        int index = ((Long) toBeParsed.get("index")).intValue();
        return new Station( stationName, arrivalTime, index);
    }

     public static Map<String, Station> getAllStations(List<Route> toBeParsedStations){
         Map<String, Station> toBeReturned = new HashMap<>();

        for(Route currentRoute: toBeParsedStations){
            for(Object currentStation: currentRoute.stations){
                if(currentStation instanceof HashMap<?,?>){
                    HashMap<String, Object> currentStationHash =(HashMap<String, Object>) currentStation;
                    Station parsedStation = getStationFromHashMap(currentStationHash);

                    toBeReturned.put(parsedStation.stationName, parsedStation);
                }
                if(currentStation instanceof  Station){
                    toBeReturned.put(((Station) currentStation).stationName, (Station) currentStation);
                }

            }
        }

         return toBeReturned;
    }

    public static HashMap<String,Route> searchForRoute(List<Route> toBeParsedStations,String from, String to){
        HashMap<String,Route> toBeReturned = new HashMap<>();

        for(Route currentRoute: toBeParsedStations) {
            List<Station> currentFoundStations = new ArrayList<>();
            int index = 0;
            int startFoundIndex = -1;
            int endFoundIndex = -1;

            for (Object currentStation : currentRoute.stations) {
                HashMap<String, Object> currentStationHash =(HashMap<String, Object>) currentStation;
                String stationName = (String) currentStationHash.get("stationName");

                if(startFoundIndex != -1){
                    if(to.equals(stationName)){
                        endFoundIndex = index;
                        Station parsedStation = getStationFromHashMap(currentStationHash);
                        currentFoundStations.add((Station) parsedStation);
                    }

                    if(endFoundIndex != -1){
                        toBeReturned.put(currentRoute.routeID, new Route(currentFoundStations, currentRoute.routeID));
                    }else{
                        Station parsedStation = getStationFromHashMap(currentStationHash);
                        currentFoundStations.add((Station) parsedStation);
                    }

                }else{
                    if(from.equals(stationName)){
                        startFoundIndex = index;
                        Station parsedStation = getStationFromHashMap(currentStationHash);
                        currentFoundStations.add(0,(Station) parsedStation);
                    }
                }

                index++;

                    if(index == currentRoute.stations.size() && currentFoundStations.size() > 0){
                        Collections.sort(currentFoundStations, Comparator.comparingInt(o -> o.index));
                    }

                }

            }
        return toBeReturned;
    }
}
