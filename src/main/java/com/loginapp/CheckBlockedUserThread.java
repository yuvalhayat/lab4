package main.java.com.loginapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class CheckBlockedUserThread extends Thread{

    private User user;
    private boolean blocked;

    public CheckBlockedUserThread (User user) {
        this.user = user;
        blocked = true;
    }

    public boolean getBlocked(){return blocked;}

    @Override
    public void run() {
        UserDataBase db = UserDataBase.getInstance();
        int index = db.getUsers().indexOf(user);
        blocked = db.getUsers().get(index).getBlocked();

    }

}
