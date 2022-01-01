// https://cloud.google.com/firestore/docs/query-data/get-data#java

package main.easybussro.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.api.core.ApiFuture;

import main.easybussro.constants.MessageCodes;
import main.easybussro.models.User;

import java.util.concurrent.ExecutionException;

public class AuthService {
    Firestore db = FirestoreClient.getFirestore();
    public static final String usersCollection = "users";

    public MessageCodes registerUser(User userAuthData) throws ExecutionException, InterruptedException {
        try{
            Object res = this.getUser(userAuthData.username);
            if(res == MessageCodes.NOT_FOUND){
                ApiFuture<WriteResult> future = db.collection(usersCollection).document(userAuthData.username).set(userAuthData);
                System.out.println("Update time : " + future.get().getUpdateTime());
                return MessageCodes.SUCCESS;
            }else if(res instanceof User){
                return MessageCodes.ALREADY_EXISTS;
            }else{
                return MessageCodes.SOMETHING_IS_WRONG;
            }
        }catch(RuntimeException e){
            return MessageCodes.SOMETHING_IS_WRONG;
        }
    }

    public MessageCodes loginUser(User userAuthData) throws ExecutionException, InterruptedException {

        try{
            Object res = this.getUser(userAuthData.username);
            if(res == MessageCodes.NOT_FOUND){
                return MessageCodes.NOT_FOUND;
            }else if(res instanceof User){
                if(userAuthData.password.equals(((User) res).password)){
                    return MessageCodes.SUCCESS;
                }else{
                    return MessageCodes.INVALID_PASSWORD;
                }
            }else{
                return MessageCodes.SOMETHING_IS_WRONG;
            }
        }catch(RuntimeException e){
            return MessageCodes.SOMETHING_IS_WRONG;
        }
    }

    public Object getUser(String username) throws RuntimeException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(usersCollection).document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return new User((String) document.getData().get("username"), (String) document.getData().get("password"));
        } else {
            return MessageCodes.NOT_FOUND;
        }
    }
}
