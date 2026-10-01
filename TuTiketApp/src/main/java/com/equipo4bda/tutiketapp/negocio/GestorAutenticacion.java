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
import com.equipo4bda.tutiketapp.persistencia.ClienteDAO;

public class GestorAutenticacion {

    private final ClienteDAO clienteDAO;
    private final AdministradorDAO administradorDAO;

    public GestorAutenticacion() {
        clienteDAO = new ClienteDAO();
        administradorDAO = new AdministradorDAO();
    }

    public SesionUsuario iniciarSesion(String usuario, String contrasena) throws Exception {
        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("Ingresa el usuario.");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("Ingresa la contraseña.");
        }

        Cliente cliente = clienteDAO.buscarPorUsuario(usuario);

        if (cliente != null && ContrasenaUtil.verificar(contrasena, cliente.getContrasenaHash())) {
            SesionUsuario sesion = new SesionUsuario();
            sesion.setTipo(SesionUsuario.Tipo.CLIENTE);
            sesion.setIdUsuario(cliente.getIdCliente());
            sesion.setNombre(cliente.getNombres());
            sesion.setUsuario(cliente.getUsuario());
            return sesion;
        }

        Administrador administrador = administradorDAO.buscarPorUsuario(usuario);

        if (administrador != null && ContrasenaUtil.verificar(contrasena, administrador.getContrasenaHash())) {
            SesionUsuario sesion = new SesionUsuario();
            sesion.setTipo(SesionUsuario.Tipo.ADMINISTRADOR);
            sesion.setIdUsuario(administrador.getIdAdmin());
            sesion.setIdPromotora(administrador.getIdPromotora());
            sesion.setNombre(administrador.getNombres());
            sesion.setUsuario(administrador.getUsuario());
            return sesion;
        }

        throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
    }
}
