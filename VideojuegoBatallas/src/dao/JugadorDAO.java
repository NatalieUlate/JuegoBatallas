/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import persistence.ConexionBD;

/**
 *
 * @author Natalie Ulate Rojas
 */

public class JugadorDAO {


    public Jugador obtenerOCrearJugador(String nombre) {
        String sqlSelect = "SELECT * FROM jugador WHERE nombre = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
            
            pstmtSelect.setString(1, nombre);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
           
                return construirJugadorDesdeResultSet(rs);
            } else {
             
                return crearNuevoJugador(nombre, conn);
            }

        } catch (SQLException e) {
            System.err.println("Error grave al obtener o crear el jugador: " + e.getMessage());
            e.printStackTrace();
        }
        return null; 
    }


    public void actualizarEstadisticas(Jugador jugador) {
        String sql = "UPDATE jugador SET partidas_ganadas = ?, partidas_perdidas = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jugador.getPartidasGanadas());
            pstmt.setInt(2, jugador.getPartidasPerdidas());
            pstmt.setInt(3, jugador.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar estadísticas del jugador: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM jugador ORDER BY partidas_ganadas DESC, partidas_perdidas ASC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                jugadores.add(construirJugadorDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los jugadores: " + e.getMessage());
            e.printStackTrace();
        }
        return jugadores;
    }


    private Jugador crearNuevoJugador(String nombre, Connection conn) throws SQLException {
        String sqlInsert = "INSERT INTO jugador (nombre) VALUES (?)";
        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            pstmtInsert.setString(1, nombre);
            pstmtInsert.executeUpdate();
            
            try (ResultSet generatedKeys = pstmtInsert.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Jugador nuevoJugador = new Jugador(nombre);
                    nuevoJugador.setId(generatedKeys.getInt(1));
                    System.out.println("[INFO] Nuevo jugador '" + nombre + "' creado.");
                    return nuevoJugador;
                }
            }
        }
   
        throw new SQLException("La creación del jugador falló, no se obtuvieron los IDs generados.");
    }
    
 
    private Jugador construirJugadorDesdeResultSet(ResultSet rs) throws SQLException {
        Jugador jugador = new Jugador(rs.getString("nombre"));
        jugador.setId(rs.getInt("id"));
        jugador.setPartidasGanadas(rs.getInt("partidas_ganadas"));
        jugador.setPartidasPerdidas(rs.getInt("partidas_perdidas"));
        return jugador;
    }
}



