/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.persistencia.BoletoDAO;
import com.equipo4bda.tutiketapp.persistencia.ConexionBD;
import com.equipo4bda.tutiketapp.persistencia.EventoDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GestorEventos {

    private final EventoDAO eventoDAO;
    private final BoletoDAO boletoDAO;

    public GestorEventos() {
        eventoDAO = new EventoDAO();
        boletoDAO = new BoletoDAO();
    }

    public List<Evento> obtenerEventos() throws SQLException {
        return eventoDAO.obtenerTodos();
    }

    public void crearEvento(int idPromotora, int idTipoEvento, String nombre, int cantidadBoletos) throws Exception {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del evento es obligatorio.");
        }

        if (cantidadBoletos <= 0) {
            throw new IllegalArgumentException("La cantidad de boletos debe ser mayor a cero.");
        }

        Evento evento = new Evento();
        evento.setIdPromotora(idPromotora);
        evento.setIdTipoEvento(idTipoEvento);
        evento.setNombreEvento(nombre);
        evento.setCantidadBoletos(cantidadBoletos);

        Connection conexion = null;

        try {
            conexion = ConexionBD.crearConexion();
            conexion.setAutoCommit(false);

            int idEvento = eventoDAO.insertar(conexion, evento);
            boletoDAO.generarBoletos(conexion, idEvento, cantidadBoletos);

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
    
    public void modificarEvento(Evento evento) throws SQLException {
        if (evento == null) {
            throw new IllegalArgumentException("No hay un evento seleccionado.");
        }

        if (evento.getNombreEvento() == null || evento.getNombreEvento().isBlank()) {
            throw new IllegalArgumentException("El nombre del evento es obligatorio.");
        }

        if (evento.getCantidadBoletos() <= 0) {
            throw new IllegalArgumentException("La cantidad de boletos debe ser mayor a cero.");
        }

        eventoDAO.actualizar(evento);
    }
}
