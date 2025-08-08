/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.HistorialPartida;
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

public class HistorialPartidasDAO {


    public void registrarPartida(HistorialPartida partida) {
        
        String sql = "INSERT INTO historial_partidas (id_jugador1, id_jugador2, id_personaje_j1, id_personaje_j2, id_ganador) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, partida.getIdJugador1());
            pstmt.setInt(2, partida.getIdJugador2());
            pstmt.setInt(3, partida.getIdPersonajeJ1());
            pstmt.setInt(4, partida.getIdPersonajeJ2());
            pstmt.setInt(5, partida.getIdGanador());
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                 System.out.println("\n[INFO] Partida registrada correctamente en el historial.");
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar la partida en el historial: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<HistorialPartida> obtenerHistorialDeJugador(int idJugador) {
        List<HistorialPartida> historial = new ArrayList<>();
        
        String sql = "SELECT * FROM historial_partidas WHERE id_jugador1 = ? OR id_jugador2 = ? ORDER BY fecha_partida DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idJugador);
            pstmt.setInt(2, idJugador);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    historial.add(construirHistorialDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el historial del jugador: " + e.getMessage());
            e.printStackTrace();
        }
        return historial;
    }


    private HistorialPartida construirHistorialDesdeResultSet(ResultSet rs) throws SQLException {
        HistorialPartida partida = new HistorialPartida(
            rs.getInt("id_jugador1"),
            rs.getInt("id_jugador2"),
            rs.getInt("id_personaje_j1"),
            rs.getInt("id_personaje_j2"),
            rs.getInt("id_ganador")
        );
        return partida;
    }
}
