package com.example.shatty;

import com.example.shatty.backend.GestionBBDD;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    protected void onEnviarMensaje() {
        //GestionBBDD.insertar("");
    }
}