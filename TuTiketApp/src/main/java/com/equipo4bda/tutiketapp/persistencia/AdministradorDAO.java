/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.persistencia;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.negocio.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO {

    public Administrador buscarPorUsuario(String usuario) throws SQLException {
        String sql = "SELECT id_admin, id_promotora, nombres, paterno, materno, usuario, `contraseña_hash` FROM administrador WHERE usuario = ?";

        try (Connection conexion = ConexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Administrador administrador = new Administrador();
                    administrador.setIdAdmin(rs.getInt("id_admin"));
                    administrador.setIdPromotora(rs.getInt("id_promotora"));
                    administrador.setNombres(rs.getString("nombres"));
                    administrador.setPaterno(rs.getString("paterno"));
                    administrador.setMaterno(rs.getString("materno"));
                    administrador.setUsuario(rs.getString("usuario"));
                    administrador.setContrasenaHash(rs.getString("contraseña_hash"));
                    return administrador;
                }
            }
        }

        return null;
    }
}
