package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {
    private final Conexion conexion = new Conexion();

    public boolean agregarDocente(Docente docente) {
        Connection conn = conexion.getConnection();
        String sql = "INSERT INTO docentes (cedula, primernombre, segundonombre, primerapellido, segundoapellido, correo, telefono, idprograma, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, docente.getCedula());
            stmt.setString(2, docente.getPrimerNombre());
            stmt.setString(3, docente.getSegundoNombre());
            stmt.setString(4, docente.getPrimerApellido());
            stmt.setString(5, docente.getSegundoApellido());
            stmt.setString(6, docente.getCorreo());
            stmt.setString(7, docente.getTelefono());
            stmt.setString(8, docente.getIdPrograma());
            stmt.setString(9, docente.getActivo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar docente: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }
    
    public boolean existeDocente(String cedula) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = conexion.getConnection();
            String sql = "SELECT * FROM docentes WHERE cedula = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cedula);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Docente> obtenerDocentes() {
        List<Docente> lista = new ArrayList<>();
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM docentes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Docente docente = new Docente();
                docente.setCedula(rs.getString("cedula"));
                docente.setPrimerNombre(rs.getString("primernombre"));
                docente.setSegundoNombre(rs.getString("segundonombre"));
                docente.setPrimerApellido(rs.getString("primerapellido"));
                docente.setSegundoApellido(rs.getString("segundoapellido"));
                docente.setCorreo(rs.getString("correo"));
                docente.setTelefono(rs.getString("telefono"));
                docente.setIdPrograma(rs.getString("idprograma"));
                docente.setActivo(rs.getString("activo"));
                lista.add(docente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener docentes: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }
    
    public Docente obtenerDocentePorCedula(String cedula) {
        Docente docente = null;
        String sql = "SELECT * FROM docentes WHERE cedula = ?";
        
        try (Connection conn = conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cedula);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    docente = new Docente();
                    docente.setCedula(rs.getString("cedula"));
                    docente.setPrimerNombre(rs.getString("primernombre"));
                    docente.setSegundoNombre(rs.getString("segundonombre"));
                    docente.setPrimerApellido(rs.getString("primerapellido"));
                    docente.setSegundoApellido(rs.getString("segundoapellido"));
                    docente.setCorreo(rs.getString("correo"));
                    docente.setTelefono(rs.getString("telefono"));
                    docente.setIdPrograma(rs.getString("idprograma"));
                    docente.setActivo(rs.getString("activo"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener docente por cédula: " + e.getMessage());
        }
        
        return docente;
    }

    public boolean actualizarDocente(Docente docente) {
        Connection conn = conexion.getConnection();
        String sql = "UPDATE docente SET primernombre = ?, segundonombre = ?, primerapellido = ?, segundoapellido = ?, correo = ?, telefono = ?, idprograma = ?, activo = ? WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, docente.getPrimerNombre());
            stmt.setString(2, docente.getSegundoNombre());
            stmt.setString(3, docente.getPrimerApellido());
            stmt.setString(4, docente.getSegundoApellido());
            stmt.setString(5, docente.getCorreo());
            stmt.setString(6, docente.getTelefono());
            stmt.setString(7, docente.getIdPrograma());
            stmt.setString(8, docente.getActivo());
            stmt.setString(9, docente.getCedula());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar docente: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public boolean eliminarDocente(String cedula) {
        Connection conn = conexion.getConnection();
        String sql = "DELETE FROM docentes WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar docente: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }
    
    
        
}
