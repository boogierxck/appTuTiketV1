/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
import com.equipo4bda.tutiketapp.persistencia.CuentaClienteDAO;
import java.sql.SQLException;
import java.util.List;

public class GestorCuentas {

    private final CuentaClienteDAO cuentaClienteDAO;

    public GestorCuentas() {
        cuentaClienteDAO = new CuentaClienteDAO();
    }

    public List<CuentaCliente> obtenerCuentasCliente(int idCliente) throws SQLException {
        return cuentaClienteDAO.obtenerPorCliente(idCliente);
    }
}
