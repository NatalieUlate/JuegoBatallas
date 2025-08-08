/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Raza;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import persistence.ConexionBD;
/**
 *
 * @author Natalie Ulate Rojas
 */

public class PersonajeDAO {


    public Map<String, Object> obtenerPlantillaPorRaza(String nombreRaza) {
        String sql = "SELECT p.id, p.nombre, p.fuerza, p.energia, p.vida_actual, p.id_arma_inicial, " +
                     "r.id as id_raza, r.nombre as nombre_raza, r.descripcion " +
                     "FROM personaje p JOIN raza r ON p.id_raza = r.id " +
                     "WHERE r.nombre = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreRaza);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Map<String, Object> plantilla = new HashMap<>();
                plantilla.put("id_personaje_plantilla", rs.getInt("id"));
                plantilla.put("nombre_plantilla", rs.getString("nombre"));
                plantilla.put("fuerza", rs.getInt("fuerza"));
                plantilla.put("energia", rs.getInt("energia"));
                plantilla.put("vida_base", rs.getInt("vida_actual"));
                plantilla.put("id_arma_inicial", rs.getInt("id_arma_inicial"));
                
                Raza raza = new Raza(rs.getString("nombre_raza"), rs.getString("descripcion"));
                raza.setId(rs.getInt("id_raza"));
                plantilla.put("raza_obj", raza);
                
                return plantilla;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener plantilla de personaje: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
