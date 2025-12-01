package main.java.com.loginapp.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import static java.util.Objects.requireNonNull;
//Scene switch class to switch from the login screen to the welcome screen
public class SceneSwitch {
    public SceneSwitch(AnchorPane currentAnchorPane,String fxml) throws Exception{
        AnchorPane nextAnchorPane = FXMLLoader.load(requireNonNull(getClass().getResource(fxml)));
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }
}
