package main.easybussro.services;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.api.core.ApiFuture;

import main.easybussro.models.User;

import java.util.concurrent.ExecutionException;

public class UserService {
    Firestore db = FirestoreClient.getFirestore();
    public static final String usersCollection = "users";

    public void registerUser(User userAuthData) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = db.collection(usersCollection).document(userAuthData.username).set(userAuthData);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }
}
