package main.easybussro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.easybussro.constants.Sizes;

import java.io.IOException;

public class Root extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Root.class.getResource("/main.easybussro/auth-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Sizes.APP_WIDTH, Sizes.APP_HEIGHT);
        stage.setTitle("EasyBusRO");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}