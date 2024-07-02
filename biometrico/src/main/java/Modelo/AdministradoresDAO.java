package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradoresDAO {
     private final Conexion conexion = new Conexion();
     
     public List<Administradores> obtenerAdministradores() {
        List<Administradores> lista = new ArrayList<>();
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM administradores";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Administradores administrador = new Administradores();
                administrador.setIdAdministrador(rs.getString("idadministrador"));
                administrador.setNombre(rs.getString("nombre"));
                administrador.setPassword(rs.getString("password"));
               
                lista.add(administrador);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener docentes: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }
}
