package com.example.shatty.actions;

import com.example.shatty.backend.GestionBBDD;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ManagerMensaje {

    /**
     * Función que devuelve todos los mensajes escritos por un contacto en un chat
     * @param idEmisor
     * @param idChat
     * @return
     */
    public List<Map<String, String>> getMensajesByIdEmisorAndIdhat(int idEmisor, int idChat) {
        return GestionBBDD.devuelveFilas(GestionBBDD.ejecutarSelect("Mensajes", new String[]{"*"},
                "WHERE IDEmisor = '" + idEmisor + "' AND IDChat" + idChat + "'"));
    }

    /**
     * Método que inserta un mensaje en la base de datos
     * @param mensaje
     * @param idEmisor
     * @param fecha
     * @param recibido
     * @param leido
     */
    public void insertarMensaje(String mensaje, int idEmisor, int idChat, Date fecha, boolean recibido, boolean leido) {
        int tinyintRecibido = recibido ? 1 : 0;
        int tinyintLeido = leido ? 1 : 0;
        GestionBBDD.insertar("Mensajes", new String[]{"Texto", "IDEmisor", "IDChat", "Fecha", "Recibido", "Leido"},
                new String[]{mensaje, Integer.toString(idEmisor), Integer.toString(idChat), fecha.toString(), Integer.toString(tinyintRecibido), Integer.toString(tinyintLeido)});
    }
}
