/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.persistencia;

/**
 *
 * @author armen
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BitacoraDAO {

    public void insertar(Connection conexion, int idCuentaCliente, String operacion, BigDecimal monto, BigDecimal saldoRestante) throws SQLException {
        String sql = "INSERT INTO bitacora (id_cuenta_cliente, operacion, monto, saldo_restante) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCuentaCliente);
            ps.setString(2, operacion);
            ps.setBigDecimal(3, monto);
            ps.setBigDecimal(4, saldoRestante);

            ps.executeUpdate();
        }
    }
}
