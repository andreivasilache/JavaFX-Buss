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

     public static Map<String, Station> getAllStations(List<Route> toBeParsedStations){
         Map<String, Station> toBeReturned = new HashMap<>();

        for(Route currentRoute: toBeParsedStations){
            for(Object currentStation: currentRoute.stations){
                HashMap<String, Object> currentStationHash =(HashMap<String, Object>) currentStation;
                String stationName = (String) currentStationHash.get("stationName");
                String arrivalTime = (String) currentStationHash.get("arrivalTime");
                int index = ((Long) currentStationHash.get("index")).intValue();

                toBeReturned.put(stationName, new Station( stationName, arrivalTime, index));
            }
        }

         return toBeReturned;
    }
}
