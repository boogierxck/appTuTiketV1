/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.persistencia;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.negocio.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {

    public boolean existeUsuario(String usuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cliente WHERE usuario = ?";

        try (Connection conexion = ConexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public Cliente buscarPorUsuario(String usuario) throws SQLException {
        String sql = "SELECT id_cliente, nombres, paterno, usuario, contrasena_hash, fecha_nacimiento FROM cliente WHERE usuario = ?";

        try (Connection conexion = ConexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNombres(rs.getString("nombres"));
                    cliente.setPaterno(rs.getString("paterno"));
                    cliente.setUsuario(rs.getString("usuario"));
                    cliente.setContrasenaHash(rs.getString("contrasena_hash"));
                    cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    return cliente;
                }
            }
        }

        return null;
    }

    public int insertar(Connection conexion, Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nombres, paterno, usuario, contrasena_hash, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getPaterno());
            ps.setString(3, cliente.getUsuario());
            ps.setString(4, cliente.getContrasenaHash());
            ps.setDate(5, Date.valueOf(cliente.getFechaNacimiento()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException("No se pudo obtener el ID del cliente.");
    }
}