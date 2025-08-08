/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Timestamp;
/**
 *
 * @author Natalie Ulate Rojas
 */
public class HistorialPartida {
    private int id;
    private int idJugador1;
    private int idJugador2;
    private int idPersonajeJ1;
    private int idPersonajeJ2;
    private int idGanador;
    private Timestamp fechaPartida;

    public HistorialPartida(int idJugador1, int idJugador2, int idPersonajeJ1, int idPersonajeJ2, int idGanador) {
        this.idJugador1 = idJugador1;
        this.idJugador2 = idJugador2;
        this.idPersonajeJ1 = idPersonajeJ1;
        this.idPersonajeJ2 = idPersonajeJ2;
        this.idGanador = idGanador;
    }
    
    public int getIdJugador1() { return idJugador1; }
    public int getIdJugador2() { return idJugador2; }
    public int getIdPersonajeJ1() { return idPersonajeJ1; }
    public int getIdPersonajeJ2() { return idPersonajeJ2; }
    public int getIdGanador() { return idGanador; }
}
