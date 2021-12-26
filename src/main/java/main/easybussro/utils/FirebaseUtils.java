//https://github.com/burhankhanzada/Java-FX-Chat-Application-With-Firebase/blob/440ad5a34c95d04924ad101ddde7c5c09c40120e/src/main/java/com/burhankhanzada/java/project/utils/FirebaseRealtimeDatabase.java#L14

package main.easybussro.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseUtils {

    public static FirebaseOptions getFirebaseOptions(InputStream serviceAccount) throws IOException {
        if(serviceAccount != null){
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://easybussro.firebaseio.com/")
                    .build();
            return options;

        }
        return null;
    }
}
