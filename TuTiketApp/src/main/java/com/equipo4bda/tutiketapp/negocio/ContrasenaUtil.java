/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
import org.mindrot.jbcrypt.BCrypt;

public class ContrasenaUtil {

    private ContrasenaUtil() {
    }

    public static String encriptar(String contrasena) {
        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }

    public static boolean verificar(String contrasena, String hashGuardado) {
        if (contrasena == null || contrasena.isBlank() || hashGuardado == null || hashGuardado.isBlank()) {
            return false;
        }

        try {
            return BCrypt.checkpw(contrasena, hashGuardado);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
