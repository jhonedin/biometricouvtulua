package Modelo;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            System.err.println("Error al obtener administradores: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }
     
    public Administradores obtenerAdministradorPorId(String id) {
        Administradores administrador = null;
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM administradores WHERE idadministrador = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                administrador = new Administradores();
                administrador.setIdAdministrador(rs.getString("idadministrador"));
                administrador.setNombre(rs.getString("nombre"));
                administrador.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener administrador por ID: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return administrador;
    }     
     
    public boolean autenticarAdministrador(String id, String password) {
        Connection conn = conexion.getConnection();
        String sql = "SELECT password FROM administradores WHERE idadministrador = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return storedHash.equals(hashPassword(password));
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.err.println("Error al autenticar administrador: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return false;
    }    

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
     
}
