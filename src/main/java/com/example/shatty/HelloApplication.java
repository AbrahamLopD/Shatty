package com.example.shatty;

import com.example.shatty.backend.GestionBBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("WhatsApp2");
        stage.setScene(scene);
        GestionBBDD.conectar();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}