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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Integer> obtenerBoletosDisponibles(Connection conexion, int idEvento, int cantidad) throws SQLException {
        List<Integer> boletos = new ArrayList<>();

        String sql = "SELECT id_boleto FROM boleto WHERE id_evento = ? AND estatus = 'DISPONIBLE' ORDER BY id_boleto LIMIT ? FOR UPDATE";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idEvento);
            ps.setInt(2, cantidad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boletos.add(rs.getInt("id_boleto"));
                }
            }
        }

        return boletos;
    }
    
    public void marcarComoComprado(Connection conexion, int idBoleto) throws SQLException {
        String sql = "UPDATE boleto SET estatus = 'COMPRADO' WHERE id_boleto = ? AND estatus = 'DISPONIBLE'";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idBoleto);

            if (ps.executeUpdate() != 1) {
                throw new SQLException("El boleto ya no está disponible.");
            }
        }
    }
}