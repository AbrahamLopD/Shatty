package com.example.shatty;

import com.example.shatty.actions.ManagerChat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SceneController {

    public int idContacto;

    //region Chat FXMLs
    @FXML
    private ListView<String> listViewChat;
    //endregion

    //region Mensajes FXMLs
    @FXML
    private ListView<String> listViewMensajes;

    @FXML
    private Label headerLabel;//xd
    //endregion

    //region Contactos
    public void mostrarChats(int id) {
        List<Map<String, String>> mapas = ManagerChat.getAllChatsInformationByYourId(id);

        System.out.println("Scene controller:" + mapas.get(0));
        for (Map<String, String> mapa : mapas) {
            listViewChat.getItems().add(mapa.get("NombreChat"));
            //listViewChat.getItems().add(mapa.get("Avatar"));
            System.out.println(listViewChat);

        }
    }


    //endregion
}
