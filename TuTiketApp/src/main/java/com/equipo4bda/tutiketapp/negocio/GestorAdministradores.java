/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.persistencia.AdministradorDAO;

public class GestorAdministradores {

    private final AdministradorDAO administradorDAO;

    public GestorAdministradores() {
        administradorDAO = new AdministradorDAO();
    }

    public void registrarAdministrador(int idPromotora, String nombres, String paterno, String materno, String usuario, String contrasena) throws Exception {
        if (nombres.isBlank() || paterno.isBlank() || materno.isBlank() || usuario.isBlank() || contrasena.isBlank()) {
            throw new IllegalArgumentException("Completa todos los campos.");
        }

        Administrador administrador = new Administrador();

        administrador.setIdPromotora(idPromotora);
        administrador.setNombres(nombres);
        administrador.setPaterno(paterno);
        administrador.setMaterno(materno);
        administrador.setUsuario(usuario);
        administrador.setContrasenaHash(ContrasenaUtil.encriptar(contrasena));

        administradorDAO.insertar(administrador);
    }
}
