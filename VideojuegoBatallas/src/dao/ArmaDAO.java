/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Arma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConexionBD;
/**
 *
 * @author Natalie Ulate Rojas
 */


public class ArmaDAO {


    public Arma obtenerArmaPorId(int idArma) {
        String sql = "SELECT * FROM arma WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idArma);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return construirArmaDesdeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el arma por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public List<Arma> obtenerArmasPorRaza(String nombreRaza) {
        List<Arma> armasDeRaza = new ArrayList<>();

        String sql = "SELECT a.* FROM arma a " +
                     "JOIN raza r ON a.id_raza_compatible = r.id " +
                     "WHERE r.nombre = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreRaza);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                armasDeRaza.add(construirArmaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener armas por raza: " + e.getMessage());
            e.printStackTrace();
        }
        return armasDeRaza;
    }

  
    private Arma construirArmaDesdeResultSet(ResultSet rs) throws SQLException {
        Arma arma = new Arma(
            rs.getString("nombre"),
            rs.getString("tipo"),
            rs.getInt("dano_minimo"),
            rs.getInt("dano_maximo"),
            rs.getString("modificadores")
        );
        arma.setId(rs.getInt("id"));
        return arma;
    }
}
