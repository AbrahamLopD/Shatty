package com.example.shatty.backend;

import com.example.shatty.HelloController;
import com.example.shatty.actions.ManagerChat;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

public class GestionBBDDMain {
    public static void main(String[] args) {
        GestionBBDD.conectar();

//        GestionBBDD.cambiarContrasenya();

        GestionBBDD.borrarTablasSiExisten();

        GestionBBDD.crearTablas("Contactos", new String[]{"IDContacto INT PRIMARY KEY AUTO_INCREMENT", "Nombre VARCHAR(55)", "Apellidos VARCHAR(55)",
                "Avatar VARCHAR(255)", "Password VARCHAR(64)"});
        GestionBBDD.crearTablas("Chats", new String[]{"IDChat INT PRIMARY KEY AUTO_INCREMENT", "NombreChat VARCHAR(25)", "Bloqueado TINYINT"});
        GestionBBDD.crearTablas("Mensajes", new String[]{"IDMensaje INT PRIMARY KEY AUTO_INCREMENT", "Texto VARCHAR(4000)", "IDEmisor INT", "IDChat INT",
                "Fecha Date", "Recibido TINYINT", "Leido TINYINT",
                "FOREIGN KEY (IDEmisor) REFERENCES Contactos(IDContacto) ON DELETE CASCADE ON UPDATE CASCADE", "FOREIGN KEY (IDChat) REFERENCES Chats(IDChat) ON DELETE CASCADE ON UPDATE CASCADE"});
        GestionBBDD.crearTablas("Contactos_Chat", new String[]{"IDChat INT", "IDContacto INT",
                "CONSTRAINT PK_Chat_Contacto PRIMARY KEY (IDChat, IDContacto)",
                "FOREIGN KEY (IDChat) REFERENCES Chats(IDChat) ON DELETE CASCADE ON UPDATE CASCADE", "FOREIGN KEY (IDContacto) REFERENCES Contactos(IDContacto) ON DELETE CASCADE ON UPDATE CASCADE" });

        GestionBBDD.insertar(new File(".\\src\\main\\java\\com\\example\\shatty\\backend\\users.sql"));
        ManagerChat.onCrearOEditarChat(1, "Semi y Abraham", false, new int[]{1, 2});

        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Contactos", new String[]{"Nombre", "Apellidos"}, "ORDER BY Nombre"));
        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Mensajes", new String[]{"*"}));
        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Chats", new String[]{"*"}));
        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Contactos_Chat", new String[]{"*"}));
    }


}
