/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.persistencia;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.negocio.CuentaCliente;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaClienteDAO {

    public void insertar(Connection conexion, int idCliente, String banco, String numCuenta, BigDecimal saldo) throws SQLException {
        String sql = "INSERT INTO cuenta_cliente (id_cliente, banco, num_cuenta, saldo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setString(2, banco);
            ps.setString(3, numCuenta);
            ps.setBigDecimal(4, saldo);
            ps.executeUpdate();
        }
    }

    public List<CuentaCliente> obtenerPorCliente(int idCliente) throws SQLException {
        List<CuentaCliente> cuentas = new ArrayList<>();
        String sql = "SELECT id_cuenta_cliente, id_cliente, banco, num_cuenta, saldo FROM cuenta_cliente WHERE id_cliente = ?";

        try (Connection conexion = ConexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CuentaCliente cuenta = new CuentaCliente();
                    cuenta.setIdCuentaCliente(rs.getInt("id_cuenta_cliente"));
                    cuenta.setIdCliente(rs.getInt("id_cliente"));
                    cuenta.setBanco(rs.getString("banco"));
                    cuenta.setNumCuenta(rs.getString("num_cuenta"));
                    cuenta.setSaldo(rs.getBigDecimal("saldo"));
                    cuentas.add(cuenta);
                }
            }
        }

        return cuentas;
    }
}
