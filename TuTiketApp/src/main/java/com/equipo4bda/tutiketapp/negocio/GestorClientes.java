/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.persistencia.ClienteDAO;
import com.equipo4bda.tutiketapp.persistencia.ConexionBD;
import com.equipo4bda.tutiketapp.persistencia.CuentaClienteDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class GestorClientes {

    private final ClienteDAO clienteDAO;
    private final CuentaClienteDAO cuentaClienteDAO;

    public GestorClientes() {
        clienteDAO = new ClienteDAO();
        cuentaClienteDAO = new CuentaClienteDAO();
    }

    public void registrarCliente(String nombres, String paterno, String usuario, String contrasena, LocalDate fechaNacimiento) throws Exception {
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (paterno == null || paterno.isBlank()) {
            throw new IllegalArgumentException("El apellido paterno es obligatorio.");
        }

        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        if (clienteDAO.existeUsuario(usuario)) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombres(nombres);
        cliente.setPaterno(paterno);
        cliente.setUsuario(usuario);
        cliente.setFechaNacimiento(fechaNacimiento);
        cliente.setContrasenaHash(ContrasenaUtil.encriptar(contrasena));

        Connection conexion = null;

        try {
            conexion = ConexionBD.crearConexion();
            conexion.setAutoCommit(false);

            int idCliente = clienteDAO.insertar(conexion, cliente);

            for (int i = 1; i <= 3; i++) {
                String numeroCuenta = generarNumeroCuenta();
                cuentaClienteDAO.insertar(conexion, idCliente, "Banco " + i, numeroCuenta, new BigDecimal("1000.00"));
            }

            conexion.commit();

        } catch (Exception e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            throw e;

        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String generarNumeroCuenta() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}
