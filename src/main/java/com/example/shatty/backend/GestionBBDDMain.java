package com.example.shatty.backend;

public class GestionBBDDMain {
    public static void main(String[] args) {
        GestionBBDD.conectar();

//        GestionBBDD.cambiarContrasenya();

        GestionBBDD.borrarTablasSiExisten();

        GestionBBDD.crearTablas("Contactos", new String[]{"IDContacto INT PRIMARY KEY AUTO_INCREMENT", "Nombre VARCHAR(55)", "Apellidos VARCHAR(55)", "Bloqueado TINYINT",
                "Avatar LONGBLOB"});
        GestionBBDD.crearTablas("Chat", new String[]{"IDChat INT PRIMARY KEY AUTO_INCREMENT", "NombreChat VARCHAR(25)"});
        GestionBBDD.crearTablas("Mensajes", new String[]{"IDMensaje INT PRIMARY KEY AUTO_INCREMENT", "Texto VARCHAR(4000)", "IDEmisor INT", "IDChat INT",
                "Fecha DATE", "Hora DATE", "Recibido TINYINT", "Leido TINYINT",
                "FOREIGN KEY (IDEmisor) REFERENCES Contactos(IDContacto) ON DELETE CASCADE ON UPDATE CASCADE", "FOREIGN KEY (IDChat) REFERENCES Chat(IDChat) ON DELETE CASCADE ON UPDATE CASCADE"});
        GestionBBDD.crearTablas("Contactos_Chat", new String[]{"IDChat INT", "IDContacto INT",
                "CONSTRAINT PK_Chat_Contacto PRIMARY KEY (IDChat, IDContacto)",
                "FOREIGN KEY (IDChat) REFERENCES Chat(IDChat) ON DELETE CASCADE ON UPDATE CASCADE", "FOREIGN KEY (IDContacto) REFERENCES Contactos(IDContacto) ON DELETE CASCADE ON UPDATE CASCADE" });

//        GestionBBDD.insertar(new File(".\\src\\unidad3_1\\Pacientes.sql"));
//        GestionBBDD.insertar(new File(".\\src\\unidad3_1\\Recetas.sql"));
//        GestionBBDD.insertar(new File(".\\src\\unidad3_1\\Medicamentos.sql"));

        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Contactos", new String[]{"Nombre", "Apellidos"}, "ORDER BY Nombre"));
        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Mensajes", new String[]{"*"}));
        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Chat", new String[]{"*"}));
        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Contactos_Chat", new String[]{"*"}));

//        GestionBBDD.modificar("Medicamentos", "Composicion", new String[]{"'Flan de Vainilla'", "WHERE Composicion = 'Corn Grain'"});
//        GestionBBDD.listar(GestionBBDD.ejecutarSelect("Medicamentos", new String[]{"*"}, "WHERE idMedicamento = 27"));
    }
}
