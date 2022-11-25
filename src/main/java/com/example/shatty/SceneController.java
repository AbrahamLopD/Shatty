package com.example.shatty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Declaramos los campos de texto para su comprobación
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasenya;

    public void switchToSceneChat(ActionEvent event) throws IOException {
        if (usuario.getText().length() > 0 && contrasenya.getText().length() > 0) {
            root = FXMLLoader.load(getClass().getResource("chat-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1006, 702);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Advertencia");
            alert.setTitle("Datos no introducidos");
            alert.setContentText("Debe rellenar ambos campos de texto");
            alert.showAndWait();
        }

    }
}
