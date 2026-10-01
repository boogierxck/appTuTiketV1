/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.persistencia;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.negocio.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public int insertar(Connection conexion, Evento evento) throws SQLException {
        String sql = "INSERT INTO evento (id_promotora, id_tipo_evento, nombre_evento, cantidad_boletos, edad_minima, precio_boleto) VALUES (?, ?, ?, ?, ?, ?)";        
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, evento.getIdPromotora());
            ps.setInt(2, evento.getIdTipoEvento());
            ps.setString(3, evento.getNombreEvento());
            ps.setInt(4, evento.getCantidadBoletos());
            ps.setInt(5, evento.getEdadMinima());
            ps.setDouble(6, evento.getPrecioBoleto());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException("No se pudo obtener el ID del evento.");    
    }

    public List<Evento> obtenerTodos() throws SQLException {
        List<Evento> eventos = new ArrayList<>();

        String sql = """
                SELECT e.id_evento,
                       e.id_promotora,
                       e.id_tipo_evento,
                       e.nombre_evento,
                       e.cantidad_boletos,
                       e.edad_minima,
                       e.precio_boleto,
                       c.nombre_tipo_evento,
                       (SELECT COUNT(*) FROM boleto b WHERE b.id_evento = e.id_evento AND b.estatus = 'DISPONIBLE') AS disponibles
                FROM evento e
                INNER JOIN catalogo_tipo_evento c ON c.id_tipo_evento = e.id_tipo_evento
                ORDER BY e.nombre_evento
                """;

        try (Connection conexion = ConexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
           while (rs.next()) {
                Evento evento = new Evento();

                evento.setIdEvento(rs.getInt("id_evento"));
                evento.setIdPromotora(rs.getInt("id_promotora"));
                evento.setIdTipoEvento(rs.getInt("id_tipo_evento"));
                evento.setNombreEvento(rs.getString("nombre_evento"));
                evento.setCantidadBoletos(rs.getInt("cantidad_boletos"));
                evento.setEdadMinima(rs.getInt("edad_minima"));
                evento.setPrecioBoleto(rs.getDouble("precio_boleto"));
                evento.setNombreTipoEvento(rs.getString("nombre_tipo_evento"));
                evento.setBoletosDisponibles(rs.getInt("disponibles"));

                eventos.add(evento);
            }
        }

        return eventos;
    }
    
    public void actualizar(Evento evento) throws SQLException {
    String sql = "UPDATE evento SET id_tipo_evento = ?, nombre_evento = ?, cantidad_boletos = ? WHERE id_evento = ?";

    try (Connection conexion = ConexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, evento.getIdTipoEvento());
        ps.setString(2, evento.getNombreEvento());
        ps.setInt(3, evento.getCantidadBoletos());
        ps.setInt(4, evento.getIdEvento());
        ps.executeUpdate();
    }
}
}
