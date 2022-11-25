package com.example.shatty;

import com.example.shatty.backend.GestionBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Declaramos los campos de texto para asignarlos a la view
    //region Login FXMLs
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasenya;
    //endregion
    //region Chat FXMLs
    @FXML
    private ListView<String> listViewChat;
    @FXML
    private ImageView avatarChat;
    @FXML
    private Label nombreChat;
    @FXML
    private Label ultimoMensajeChat;
    //endregion

    protected void onEnviarMensaje() {
        //GestionBBDD.insertar("");
    }
}