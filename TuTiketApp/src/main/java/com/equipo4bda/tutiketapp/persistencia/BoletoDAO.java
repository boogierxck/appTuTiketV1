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
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoletoDAO {

    public void generarBoletos(Connection conexion, int idEvento, int cantidad) throws SQLException {
        String sql = "INSERT INTO boleto (id_evento, folio, estatus) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (int i = 1; i <= cantidad; i++) {
                String folio = String.format("EV%05d-%05d", idEvento, i);

                ps.setInt(1, idEvento);
                ps.setString(2, folio);
                ps.setString(3, "DISPONIBLE");
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
}