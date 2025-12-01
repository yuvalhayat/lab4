package main.java.com.loginapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.loginapp.controllers.LoginController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main extends Application {

    static int n,t;

    @Override
    public void start(Stage stage) throws Exception {
        try{
            UserDataBase db = new UserDataBase();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com.loginapp/LoginScreen.fxml"));
            fxmlLoader.setControllerFactory(c -> {
                return new LoginController(n,t);
            });

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Login Example");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter maximum amount of failed login attempts: ");
        n = Integer.parseInt(scanner.nextLine());

        System.out.print("Please enter temporary account lockout time(in seconds): ");
        t = Integer.parseInt(scanner.nextLine());

        // Launch the JavaFX application
        launch(args);
    }
}
