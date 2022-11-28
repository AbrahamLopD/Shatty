package com.example.shatty;

import com.example.shatty.backend.GestionBBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene sceneLogin = new Scene(root,600,450);
        stage.setScene(sceneLogin);
        stage.show();
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(""));
        Scene scene = new Scene(fxmlLoader.load(), 1006, 720);
        stage.setTitle("Shatty");
        stage.setScene(scene);
        login();*/
        GestionBBDD.conectar();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void login() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);

    }
}