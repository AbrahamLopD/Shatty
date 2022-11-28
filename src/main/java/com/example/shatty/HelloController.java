package com.example.shatty;

import com.example.shatty.actions.ManagerChat;
import com.example.shatty.actions.LoginUtilities;
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

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * Evento que inserta un mensaje en la base de datos
     * Nota: Cada parámetro deberá pasarse como su tipo especificado
     * @param texto Tipo String
     * @param idEmisor Tipo int
     * @param idChat Tipo int
     * @param recibido Tipo boolean
     * @param leido Tipo boolean
     */
    protected void onEnviarMensaje(String texto, int idEmisor, int idChat, boolean recibido, boolean leido) {
        long millis = System.currentTimeMillis();
        Date fecha = new Date(millis);
        String tinyintRecibido = recibido ? "1" : "0";
        String tinyintLeido = leido ? "1" : "0";
        GestionBBDD.insertar("Mensajes", new String[]{"Texto", "IDEmisor", "IDChat", "Fecha", "Recibido", "Leido"}, new String[]{texto, Integer.toString(idEmisor),
                Integer.toString(idChat), fecha.toString(), tinyintRecibido, tinyintLeido});
    }


    /**
     * Evento que llama al método onCrearOEditarChat action ManagerChat
     * @param idChat
     * @param nombreChat
     * @param bloqueado
     */
    protected static void onCrearOEditarChat(int idChat, String nombreChat, boolean bloqueado, int[] idContactos) {
        ManagerChat.onCrearOEditarChat(idChat, nombreChat, bloqueado, idContactos);
    }

    //region Cambio escena
    public void switchToSceneChat(ActionEvent event) throws IOException {
        String error = "";
        boolean correcto = false;
        if (usuario.getText().length() > 0 && contrasenya.getText().length() > 0) {
            int idContacto = LoginUtilities.GetContactoByNombreYPassword(usuario.getText(), contrasenya.getText());
            if(idContacto != -1) {
                correcto = true;
                root = FXMLLoader.load(getClass().getResource("chat-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1006, 702);
                stage.setScene(scene);
                stage.show();
                mostrarChats(idContacto);
            } else {
                error = "El usuario y la contraseña no coinciden";
            }
        } else {
            error = "Debe rellenar ambos campos de texto";
        }

        if(!correcto) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Advertencia");
            alert.setTitle("Datos no introducidos");
            alert.setContentText(error);
            alert.showAndWait();
        }

    }
    //endregion

    //region Contactos
    private void mostrarChats(int id){
        List<Map<String, String>> mapas = ManagerChat.getAllChatsInformationByYourId(id);

        for (Map<String, String> mapa: mapas) {
            avatarChat.setImage(new Image(mapa.get("Avatar")));
            nombreChat.setText(mapa.get("Nombre"));
            ultimoMensajeChat.setText(mapa.get("UltimoMensaje"));
        }
    }

    //endregion
}