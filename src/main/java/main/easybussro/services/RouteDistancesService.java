package main.easybussro.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import main.easybussro.models.RouteDistance;
import main.easybussro.models.Station;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RouteDistancesService {
    Firestore db = FirestoreClient.getFirestore();
    public static final String distanceService = "ROUTE_DISTANCES";


    public String getKeyNameFromDistance(RouteDistance routeDistance){
        return routeDistance.from+"-"+routeDistance.to;
    }

    public void addRouteDistance(RouteDistance routeDistance){
        String id = db.collection(distanceService).document().getId();
        db.collection(distanceService).document(getKeyNameFromDistance(routeDistance)).set(routeDistance);
    }

    public HashMap<String, RouteDistance> getAllDistances() throws ExecutionException, InterruptedException {
        HashMap<String, RouteDistance> toBeReturned = new HashMap<>();

        ApiFuture<QuerySnapshot> future= db.collection(distanceService).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();


        for (QueryDocumentSnapshot document : documents) {
            String from = (String) document.getData().get("from");
            String to = (String) document.getData().get("to");
            int distance = ((Long) document.getData().get("distance")).intValue();

            toBeReturned.put(document.getId(), new RouteDistance(from, to, distance));
        }

        return toBeReturned;
    }

    public int getDistanceOfRoutes(List<Station> routes, HashMap<String, RouteDistance> distancesHash){
        int toBeReturned = 0;
        for(int i=0; i<routes.size(); i++){
            if(i-1 >= 0){
                String prevStationName = routes.get(i-1).stationName;
                String currentStationName = routes.get(i).stationName;
                toBeReturned += distancesHash.get(prevStationName + "-"+currentStationName).distance;
            }
        }
        return toBeReturned;
    }
}
