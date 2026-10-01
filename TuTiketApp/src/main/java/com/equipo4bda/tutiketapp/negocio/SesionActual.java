/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo4bda.tutiketapp.negocio;

/**
 *
 * @author armen
 */
public class SesionActual {

     private static SesionUsuario sesion;
    private static Evento eventoSeleccionado;

    private SesionActual() {
    }

    public static SesionUsuario getSesion() {
        return sesion;
    }

    public static void setSesion(SesionUsuario sesion) {
        SesionActual.sesion = sesion;
    }

    public static Evento getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public static void setEventoSeleccionado(Evento eventoSeleccionado) {
        SesionActual.eventoSeleccionado = eventoSeleccionado;
    }

    public static void cerrarSesion() {
        sesion = null;
        eventoSeleccionado = null;
    }
}
