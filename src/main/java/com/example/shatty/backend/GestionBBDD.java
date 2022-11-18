package com.example.shatty.backend;

import java.io.*;
import java.sql.*;

public class GestionBBDD {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String servidor = "jdbc:mysql://dns11036.phdns11.es/";
    private static final String baseDeDatos = "ad2223_alopez";
//    private static final String user = "alopez";
    private static final String user = "ad2223_alopez";
    private static final String password = "alopez688g";

    private static Connection connection;
    private static Statement st = null;

    //region Getters y setters
    private static void setConnection(Connection con) {
        connection = con;
    }

    private static void setStatement(Statement statement) {
        st = statement;
    }
    //endregion

    //region Conexion
    /**
     * Método que sirve para conectarse a la base de datos "ad2223_alopez"
     */
    public static void conectar() {
        conectarConex(null, null, null);
    }

    /**
     * Método que sirve para conectarse a cualquier base de datos
     * @param bbdd Deberá ser válido
     */
    public static void conectar(String bbdd) {
        conectarConex(bbdd, null, null);
    }

    /**
     * Método que sirve para conectarse a cualquier base de datos con cualquier usuario
     * @param bbdd Deberá ser válido
     */
    public static void conectar(String bbdd, String usuario, String contrasenya) {
        conectarConex(bbdd, usuario, contrasenya);
    }

    /**
     * Método que sirve para conectarse a la base de datos "ad2223_alopez"
     */
    public static void conectarConex(String bbdd, String usuario, String contrasenya) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(bbdd == null) bbdd = baseDeDatos;
            if(usuario == null) usuario = user;
            if(contrasenya == null) contrasenya = password;
            connection = DriverManager.getConnection(servidor + bbdd, usuario, contrasenya);
            if (connection != null) {
                st = connection.createStatement();
                System.out.println("Conexión a base de datos correcta");
                System.out.println(st.toString());
            }
            else {
                System.out.println("Conexión fallida");
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //endregion

    public static void crearTablas(String tabla, String []campos) {
        try {
            String sql = "CREATE TABLE " + tabla + "(";
            int i = 0;
            for (String campo : campos) {
                sql += campo;

                i++;
                if (i < campos.length) sql += ", ";
            }
            sql += ");";
            System.out.println(sql);
            st.execute(sql);
            System.out.println("Tabla " + tabla + " creada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertar(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                st.executeUpdate(line);
            }

            System.out.println("Inserciones realizadas con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para editar una base de datos
     * @param tabla
     * @param valueSet
     * @param condiciones Aquí deberá ir además de la condición, el valor entre comillas simples al cual cambiará el valueSet
     */
    public static void modificar(String tabla, String valueSet, String[] condiciones) {
        String sql = "UPDATE " + tabla + " SET " + valueSet + " = ";

        int i = 0;
        for (String condicion : condiciones) {
            sql += condicion;

            i++;
            if (i < condiciones.length) sql += " ";
        }

        sql += ";";

        try {
            System.out.println(sql);
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //region Ejecución y muestra de comandos
    /**
     * Método ejecutarSelect sin condiciones
     * @param tabla
     * @param seleccion
     * @return
     */
    public static ResultSet ejecutarSelect (String tabla, String[] seleccion) {
        return ejecutarComandoSELECT(tabla, seleccion, "");
    }

    /**
     * Método ejecutarSelect con condiciones
     * @param tabla
     * @param seleccion
     * @return
     */
    public static ResultSet ejecutarSelect (String tabla, String[] seleccion, String condicion) {
        return ejecutarComandoSELECT(tabla, seleccion, condicion);
    }

    /**
     * Método que se encarga de recibir un comando SQL SELECT y ejecutarlo
     * Precondición: Recibir una condición válida
     * @param tabla
     * @param seleccion La selección debe ser válida
     * @param condicion
     * @throws SQLException
     */
    private static ResultSet ejecutarComandoSELECT (String tabla, String[] seleccion, String condicion) {
        ResultSet rs = null;
        try {
//            if(condicion == null) condicion = "";
            if(seleccion.length == 0) seleccion[0] = "*";
            String sql = "SELECT ";

            for (int i = 0; i < seleccion.length; i++) {
                sql += seleccion[i];

                if(i < seleccion.length - 1) sql += ", ";
            }

            sql +=" FROM " + tabla + " " + condicion;

            System.out.println(sql);
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rs;
    }

    /**
     * Método que se encarga de listar
     * Precondición: Recibir un ResultSet válido
     * @param rs
     */
    public static void listar(ResultSet rs) {
        try {
            while (rs.next()) {
                ResultSetMetaData md = rs.getMetaData();
                int numColumns = md.getColumnCount();
                for (int i = 0; i < numColumns; i++) {
                    String columnName = md.getColumnName(i +1);
                    System.out.print(columnName + ": " + rs.getString(columnName) + " ");
                }
                System.out.println("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que muestra los nombres de las tablas existentes en la base de datos
     */
    public static void verTablas() {
        try {
            String sql = "SHOW FULL tables";
            PreparedStatement pd = connection.prepareStatement(sql);
            ResultSet rs;
            rs = pd.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Cambio de Contraseña
    /**
     * Método que cambia la contraseña a la dada por defecto "alopez688g"
     */
    public static void cambiarContrasenya() {
        setContrasenya(null);
    }

    /**
     * Método que cambia la contraseña a la pasada por parámetro
     * @param nuevaContrasenya
     */
    public static void cambiarContrasenya(String nuevaContrasenya) {
        setContrasenya(nuevaContrasenya);
    }

    public static void setContrasenya(String nuevaContrasenya) {
        if(nuevaContrasenya == null) nuevaContrasenya = "alopez688g";
        String sql = "SET PASSWORD FOR'" + user +"'@'%' = password('"+ nuevaContrasenya + "')";

        try {
            st.executeUpdate(sql);
            System.out.println("Contraseña cambiada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    /**
     * Método que borra las tres tblas de la base de datos dada (Recetas, Pacientes y Medicamentos).
     * Si alguna no fue creada correctamente en su proceso anterior, dará un error porque no pudo borrarlas todas.
     */
    public static void borrarTablasSiExisten() {
        try {
            DatabaseMetaData dbm = connection.getMetaData();

            ResultSet tablaContactos = dbm.getTables(null, null, "Contactos", null);
            ResultSet tablaChat = dbm.getTables(null, null, "Chat", null);
            ResultSet tablaMensajes = dbm.getTables(null, null, "Mensajes", null);
            ResultSet tablaContactosChat = dbm.getTables(null, null, "Contactos_Chat", null);
            if (tablaContactos.next() || tablaChat.next() || tablaMensajes.next() || tablaContactosChat.next()) {
                String sql = "DROP TABLE Contactos_Chat, Mensajes, Contactos, Chat";
                System.out.println(sql);
                st.executeUpdate(sql);
                System.out.println("Tablas borradas con éxito");
            }
        } catch (SQLSyntaxErrorException e) {
            System.out.println(ANSI_YELLOW + "WARNING: No se han borrado todas las tablas que se querían borrar.\n" +
                    "Es posible que no se creara la tabla anteriormente.\n" +
                    "Ya se han borrado las tablas restantes. Ahora puede continuar el código sin problemas :D" + ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El método borra absolutamente todas las entradas que hay en la tabla NO LO USES NUNCA
     * @param tabla
     * @param valueDrop Parámetro de la tabla
     * @param condicion  Aquí deberá ir el valor entre comillas simples al cual cambiará el valueDrop
     */
    public static void borrar(String tabla, String valueDrop, String condicion) {
        try {
            String sql = "DELETE FROM " + tabla + " WHERE " + valueDrop + " = " + condicion;
            System.out.println(sql);
            st.executeUpdate(sql);
            System.out.println("Estamento borrado con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hace una transacción con posible rollback
     * @throws SQLException
     */
    public static void transaccion() {
        try {
            connection.setAutoCommit(false);	// Se desactiva el AutoCommit para poder realizar la transacción
            Statement st = connection.createStatement();
            String sql;
            sql="drop table if exists cliente;";
            st.execute(sql);	// Se elimina la tabla si existiera
            System.out.println("Elimina la tabla");
            sql="CREATE TABLE cliente(id int primary key, nombre varchar(45));";
            st.execute(sql);	// Se crea la tabla
            System.out.println("Crea la tabla");
            sql="insert into cliente VALUES (1,'UNO');";
            st.executeUpdate(sql);	// Inserta el registro 1
            System.out.println("Inserta registro 1");
            try{
                connection.commit();		// Comienza la transacción
                sql="insert into cliente VALUES (2,'DOS');";
                st.executeUpdate(sql);	// Inserta el registro 2
                System.out.println("Inserta registro 2");
                sql="insert into cliente VALUES (3,'TRES');";
                st.executeUpdate(sql);	// Inserta el registro 3
                System.out.println("Inserta registro 3");
                sql="insert into cliente VALUES (3,'CUATRO');";
                st.executeUpdate(sql);	// Intenta insertar el registro 3 en vez de 4
                System.out.println("No inserta registro al exister el ID 3");
            }catch(SQLException e) {
                connection.rollback();		// Deshace las dos últimas inserciones (2 y 3) ya que la última lanzó el error
            }
            connection.setAutoCommit(true);	// Se vuelve a activar el AutoCommit
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
