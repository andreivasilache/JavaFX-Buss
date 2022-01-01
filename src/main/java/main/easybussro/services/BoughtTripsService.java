package main.easybussro.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import main.easybussro.constants.RawTimeFilterKeys;
import main.easybussro.models.BoughtTrip;
import main.easybussro.state.ContextEnum;
import main.easybussro.state.Globe;


import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BoughtTripsService {
    Globe store = Globe.getGlobe();
    Firestore db = FirestoreClient.getFirestore();
    public static final String boughtItemsKey = "bought_trips";


    public void addBoughtRoute(BoughtTrip route) throws ExecutionException, InterruptedException {
        String id = db.collection(boughtItemsKey).document().getId();
        ApiFuture<WriteResult> event = db.collection(boughtItemsKey).document(id).set(route);
        event.get().getUpdateTime();
    }

    private ApiFuture<QuerySnapshot> getFirebaseFilterByQuery(RawTimeFilterKeys filter){
        long currentTimestamp = System.currentTimeMillis();

        if(filter == RawTimeFilterKeys.FUTURE){
            return  db.collection(boughtItemsKey).whereGreaterThan("dateTimestamp", currentTimestamp).get();
        }else if(filter == RawTimeFilterKeys.PAST){
            return db.collection(boughtItemsKey).whereLessThan("dateTimestamp", currentTimestamp).get();
        }else{
            return db.collection(boughtItemsKey).get();
        }
    }

    public List<String> getAllBoughtItemsLabels(RawTimeFilterKeys filter) throws ExecutionException, InterruptedException {
        String currentUserID = (String) store.getContext(ContextEnum.AUTHENTICATED_USER).getState("USERNAME");
        List<String> toBeReturned = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = getFirebaseFilterByQuery(filter);
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {

            String userID =  (String) document.getData().get("userID");
            if(userID.equals(currentUserID)){
                long serverDate = (long) document.getData().get("dateTimestamp");

                String currentLabel = (String) document.getData().get("label");

                String parsedLocalDate = Instant.ofEpochMilli(serverDate).atZone(ZoneId.systemDefault()).toLocalDate().toString();
                toBeReturned.add("["+parsedLocalDate+ "]"+currentLabel);
            }


        }

        return toBeReturned;
    }
}
