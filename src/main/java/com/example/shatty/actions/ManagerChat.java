package com.example.shatty.actions;

import com.example.shatty.backend.GestionBBDD;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerChat {
    /**
     * Evento que crea o edita un chat al realizar pulsar en alguna operación que altere los parámetros del chat
     * @param idChat
     * @param nombreChat
     * @param bloqueado
     */
    public static void onCrearOEditarChat(int idChat, String nombreChat, boolean bloqueado, int[] idContactos) {
        String tinyintBloqueado = bloqueado ? "1" : "0";
        boolean existeChat = GestionBBDD.existe(idChat, GestionBBDD.ejecutarSelect("Chats", new String[]{"IDChat"}, "WHERE IDChat = " + idChat));
        if(!existeChat) {
            GestionBBDD.insertar("Chats", new String[]{"NombreChat", "Bloqueado"}, new String[]{nombreChat, tinyintBloqueado});
            for (int contacto : idContactos) {
                handleNuevoContactoEnChat(idChat, contacto);
            }
        }
        else {
            GestionBBDD.modificar("Chats", "Bloqueado", new String[]{tinyintBloqueado, "WHERE IDChat = '" + idChat + "'" });
        }
    }

    /**
     * Evento que inserta un nuevo contacto en el chat
     * @param idChat
     * @param idContacto
     */
    private static void handleNuevoContactoEnChat(int idChat, int idContacto) {
        GestionBBDD.insertar("Contactos_Chat", new String[]{"IDChat", "IDContacto"}, new String[]{Integer.toString(idChat), Integer.toString(idContacto)});
    }

    public static List<Map<String, String>> getAllChatsInformationByYourId(int id) {
        List<Map<String, String>> chats = new ArrayList<Map<String, String>>();

        List<String> idChats = getChatsIds(id);

        for (String idChat : idChats) {
            Map<String, String> chat;
             chat = GestionBBDD.devuelveFila(GestionBBDD.ejecutarSelect("Chats", new String[]{"NombreChat", "Bloqueado"},
                    "WHERE IDChat = '" + idChat + "'"));

             List<String> idContactos = getContactosIds(id, Integer.parseInt(idChat));

            for (String idContacto : idContactos) {
                chat.putAll(GestionBBDD.devuelveFila(GestionBBDD.ejecutarSelect("Contactos", new String[]{"Avatar"},
                        "WHERE IDContacto = '" + idContacto + "'")));
            }

            chats.add(chat);
        }

        return chats;
    }

    /**
     * Función que facilita la recogida de ids de los chats en los que participa en usuario con la id pasada por parámetro
     * @param id
     * @return
     */
    private static List<String> getChatsIds(int id) {
        List<String> idChats = GestionBBDD.devuelveColumna(GestionBBDD.ejecutarSelect("Chats", new String[]{"IDChat"},
                "WHERE IDContacto = '" + id + "'"));

        return idChats;
    }

    /**
     * Función que facilita la recogida de ids de los contactos en los chats idChat en los que participa en usuario con la idContacto pasada por parámetro
     * @param idContacto
     * @param idChat
     * @return
     */
    private static List<String> getContactosIds(int idContacto, int idChat) {
        List<String> idContactos = GestionBBDD.devuelveColumna(GestionBBDD.ejecutarSelect("Chats", new String[]{"IDContacto"},
                "WHERE IDChat = " + idChat + "' AND NOT IDContacto = '" + idContacto + "'"));

        return idContactos;
    }
}
