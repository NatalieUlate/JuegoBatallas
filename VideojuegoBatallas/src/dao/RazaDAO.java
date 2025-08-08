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
import java.util.ArrayList;
import java.util.List;
import persistence.ConexionBD;
/**
 *
 * @author Natalie Ulate Rojas
 */
public class RazaDAO {


    public List<Raza> obtenerTodasLasRazas() {
        List<Raza> razas = new ArrayList<>();
        String sql = "SELECT * FROM raza ORDER BY nombre"; 

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Raza raza = new Raza(
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                raza.setId(rs.getInt("id"));
                razas.add(raza);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las razas: " + e.getMessage());
            e.printStackTrace();
        }
        return razas;
    }
}
