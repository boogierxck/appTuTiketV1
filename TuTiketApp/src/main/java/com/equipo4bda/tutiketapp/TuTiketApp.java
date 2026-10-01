/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.equipo4bda.tutiketapp;

/**
 *
 * @author lui
 */

import com.equipo4bda.tutiketapp.gui.PantallaInicioSesion;
public class TuTiketApp {
public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            PantallaInicioSesion pantalla = new PantallaInicioSesion();

            pantalla.setLocationRelativeTo(null);

            pantalla.setVisible(true);
        });
    }
}
