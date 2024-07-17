package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HuellaDAO {
    private Conexion conexion = new Conexion();

    public boolean agregarHuella(Huella huella) {
        Connection conn = conexion.getConnection();
        String sql = "INSERT INTO huellas (userid, cedula, huella, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, huella.getUserId());
            stmt.setString(2, huella.getCedula());
            stmt.setBytes(3, huella.getHuella());
            stmt.setTimestamp(4, huella.getFecha());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar huella: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public List<Huella> obtenerHuellas() {
        List<Huella> lista = new ArrayList<>();
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM huellas";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Huella huella = new Huella();
                huella.setUserId(rs.getString("userid"));
                huella.setCedula(rs.getString("cedula"));
                huella.setHuella(rs.getBytes("huella"));
                huella.setFecha(rs.getTimestamp("fecha"));
                lista.add(huella);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener huellas: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }
    
    public Huella obtenerHuellaPorId(String userId) {
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM huellas WHERE userid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Huella huella = new Huella();
                huella.setUserId(rs.getString("userid"));
                huella.setCedula(rs.getString("cedula"));
                huella.setHuella(rs.getBytes("huella"));
                huella.setFecha(rs.getTimestamp("fecha"));
                return huella;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener huella: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return null;
    }  
    
    public List<Huella> obtenerHuellasPorCedula(String cedula) {
        List<Huella> huellas = new ArrayList<>();
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM huellas WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Huella huella = new Huella();
                huella.setUserId(rs.getString("userid"));
                huella.setCedula(rs.getString("cedula"));
                huella.setHuella(rs.getBytes("huella"));
                huella.setFecha(rs.getTimestamp("fecha"));
                huellas.add(huella);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener huellas: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return huellas;
    }    
    

    public boolean actualizarHuella(Huella huella) {
        Connection conn = conexion.getConnection();
        String sql = "UPDATE huellas SET cedula = ?, huella = ?, fecha = ? WHERE userid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, huella.getCedula());
            stmt.setBytes(2, huella.getHuella());
            stmt.setTimestamp(3, huella.getFecha());
            stmt.setString(4, huella.getUserId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar huella: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public boolean eliminarHuella(String userId) {
        Connection conn = conexion.getConnection();
        String sql = "DELETE FROM huellas WHERE userid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar huella: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }
}
