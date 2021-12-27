package main.easybussro;
import com.google.firebase.FirebaseApp;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.easybussro.constants.Sizes;
import main.easybussro.utils.FirebaseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Root extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Root.class.getResource("/main.easybussro/auth-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Sizes.APP_WIDTH, Sizes.APP_HEIGHT);
        stage.setTitle("EasyBusRO");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        InputStream serviceAccount = Root.class.getResourceAsStream("/main.easybussro/serviceAccountKey.json");
        FirebaseOptions options = FirebaseUtils.getFirebaseOptions(serviceAccount);

        if(options != null){
            FirebaseApp.initializeApp(options);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}