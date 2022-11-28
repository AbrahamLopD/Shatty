package com.example.shatty.actions;

import com.example.shatty.backend.GestionBBDD;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoginUtilities {
    /**
     *
     * @param nombre
     * @param password
     * @return
     */
    public static int GetContactoByNombreYPassword(String nombre, String password) {
        int idContacto = -1;

        password = LoginUtilities.handleEncriptarContrasenya(password);

        Map<String, String> resultadoColumna = GestionBBDD.devuelveFila(GestionBBDD.ejecutarSelect("Contactos", new String[]{"IDContacto", "Nombre", "Password"},
                "WHERE Nombre = '" + nombre + "' AND Password = '" + password + "'"));

        if(resultadoColumna.get("IDContacto") != null) idContacto = Integer.parseInt(resultadoColumna.get("IDContacto"));

        return idContacto;
    }

    public static String handleEncriptarContrasenya(String password) {
        //SHA-512
        MessageDigest md = null;
        try {
            md= MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
