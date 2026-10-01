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

public class TransaccionDAO {

    public void insertar(Connection conexion, int idCliente, int idBoleto, BigDecimal precioFinal) throws SQLException {
        String sql = "INSERT INTO transaccion (id_cliente, id_boleto, precio_final, estatus) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idBoleto);
            ps.setBigDecimal(3, precioFinal);
            ps.setString(4, "COMPRADO");

            ps.executeUpdate();
        }
    }
}
