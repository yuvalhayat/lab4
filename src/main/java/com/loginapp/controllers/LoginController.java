
package main.java.com.loginapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.loginapp.CheckBlockedUserThread;
import main.java.com.loginapp.IncreaseFailedAttemptsThread;
import main.java.com.loginapp.User;
import main.java.com.loginapp.UserDataBase;
import main.java.com.loginapp.model.SceneSwitch;

public class LoginController {

    int n, t;
    UserDataBase db;

    public LoginController() {
        this.n = 1;
        this.t = 0;
    }
    public LoginController(int n, int t) {
        this.n = n;
        this.t = t;
    }

    @FXML
    private Text errorMessage;

    @FXML
    private AnchorPane loginScreenAnchorPane;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        UserDataBase db = UserDataBase.getInstance();

        String usernameInput = this.username.getText();
        String passwordInput = this.password.getText();

        int index = -1;
        for (int i = 0; i < db.getUsers().size(); i++) {
            if (db.getUsers().get(i).getEmail().equals(usernameInput)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            errorMessage.setOpacity(1);
            errorMessage.setText("User doesnt exist");
            return;
        }
        User user = db.getUsers().get(index);

        if (!user.getPassword().equals(passwordInput)) { //username is correct but password isn't - increase the number of failed attempts
            IncreaseFailedAttemptsThread incUserFails = new IncreaseFailedAttemptsThread(user.getEmail(), user.getFailedAttempts(), this.n, this.t);
            incUserFails.start();
            try {
                incUserFails.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(user.getFailedAttempts() > this.n ) {
                loadBlockedScreen(user);
            }
            if(user.getFailedAttempts() == this.n) {
                errorMessage.setText("Wrong password!,the user has been blocked");
                errorMessage.setOpacity(1);
            }
            else{
                errorMessage.setText("Wrong password!");
                errorMessage.setOpacity(1);
            }

        } else {
            try {
                CheckBlockedUserThread checkUser = new CheckBlockedUserThread(user);
                checkUser.start();
                checkUser.join();
                if(checkUser.getBlocked()){
                    loadBlockedScreen(user);
                }
                else{
                    new SceneSwitch(loginScreenAnchorPane,"/com.loginapp/wellcomeScreen.fxml");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void loadBlockedScreen(User user) {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.loginapp/userBlockedScreen.fxml"));
        try{
            Parent root = loader.load();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.show();
            System.out.println("User " + user.getEmail() + " is blocked!");
        }
        catch(Exception e){
            System.out.println("couldn't load");
        }
    }

}