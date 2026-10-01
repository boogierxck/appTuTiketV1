/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.persistencia;

/**
 *
 * @author armen
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/tu_ticket"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";


    private static final String USUARIO = "root";
    private static final String CONTRASENA = "1234";

    private ConexionBD() {
    }

    public static Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }
}
