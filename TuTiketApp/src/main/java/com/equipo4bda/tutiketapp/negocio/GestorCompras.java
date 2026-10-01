/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.persistencia.BitacoraDAO;
import com.equipo4bda.tutiketapp.persistencia.BoletoDAO;
import com.equipo4bda.tutiketapp.persistencia.ConexionBD;
import com.equipo4bda.tutiketapp.persistencia.CuentaClienteDAO;
import com.equipo4bda.tutiketapp.persistencia.TransaccionDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GestorCompras {

    private final CuentaClienteDAO cuentaClienteDAO = new CuentaClienteDAO();
    private final BoletoDAO boletoDAO = new BoletoDAO();
    private final TransaccionDAO transaccionDAO = new TransaccionDAO();
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    public BigDecimal realizarCompra(int idCliente, int idCuentaCliente, Evento evento, int cantidad) throws Exception {
        if (evento == null) {
            throw new IllegalArgumentException("No hay un evento seleccionado.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de boletos debe ser mayor a cero.");
        }

        BigDecimal precioUnitario = BigDecimal.valueOf(evento.getPrecioBoleto());
        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        BigDecimal cargoServicio = subtotal.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(cargoServicio).setScale(2, RoundingMode.HALF_UP);

        BigDecimal precioFinalPorBoleto = total.divide(BigDecimal.valueOf(cantidad), 2, RoundingMode.HALF_UP);

        Connection conexion = null;

        try {
            conexion = ConexionBD.crearConexion();
            conexion.setAutoCommit(false);

            List<Integer> boletos = boletoDAO.obtenerBoletosDisponibles(conexion, evento.getIdEvento(), cantidad);

            if (boletos.size() < cantidad) {
                throw new IllegalStateException("No hay suficientes boletos disponibles.");
            }

            boolean descuentoRealizado = cuentaClienteDAO.descontarSaldo(conexion, idCuentaCliente, idCliente, total);

            if (!descuentoRealizado) {
                throw new IllegalStateException("Saldo insuficiente.");
            }

            for (int idBoleto : boletos) {
                boletoDAO.marcarComoComprado(conexion, idBoleto);
                transaccionDAO.insertar(conexion, idCliente, idBoleto, precioFinalPorBoleto);
            }

            BigDecimal saldoRestante = cuentaClienteDAO.obtenerSaldo(conexion, idCuentaCliente);

            bitacoraDAO.insertar(conexion, idCuentaCliente, "COMPRA", total, saldoRestante);

            conexion.commit();

            return total;

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
}
