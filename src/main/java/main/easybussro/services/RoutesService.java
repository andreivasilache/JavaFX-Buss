package main.easybussro.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import main.easybussro.models.Route;

public class RoutesService {
    Firestore db = FirestoreClient.getFirestore();
    public static final String routesCollection = "routesCollection";

    public void addRoutes(Route route){
        String id = db.collection(routesCollection).document().getId();
        db.collection(routesCollection).document(id).set(route);
    }
}
