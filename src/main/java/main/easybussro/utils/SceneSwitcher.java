package main.easybussro.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.easybussro.constants.Sizes;

import java.io.IOException;

public class SceneSwitcher {
    Scene elementParentWindow;
    String FXMLResourcePath;
    Object controllerInstance;

    public SceneSwitcher(
            Scene elementParentWindow, String FXMLResourcePath, Object controllerInstance
    ) {
        this.elementParentWindow = elementParentWindow;
        this.FXMLResourcePath = FXMLResourcePath;
        this.controllerInstance = controllerInstance;
    }

    public void loadScene() throws IOException {
        Stage elementParentWindowScene = (Stage) elementParentWindow.getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.FXMLResourcePath));
        fxmlLoader.setController(this.controllerInstance);
        Scene scene = new Scene(fxmlLoader.load(), Sizes.APP_WIDTH, Sizes.APP_HEIGHT);
        elementParentWindowScene.setScene(scene);
    }
}
