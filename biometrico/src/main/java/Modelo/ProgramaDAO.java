package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDAO {
    private Conexion conexion = new Conexion();

    public boolean agregarPrograma(Programa programa) {
        Connection conn = conexion.getConnection();
        String sql = "INSERT INTO programas (idprograma, nombreprograma, activo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, programa.getIdPrograma());
            stmt.setString(2, programa.getNombrePrograma());
            stmt.setString(3, programa.getActivoPrograma());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar programa: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public List<Programa> obtenerProgramas() {
        List<Programa> lista = new ArrayList<>();
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM programas";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Programa programa = new Programa();
                programa.setIdPrograma(rs.getString("idprograma"));
                programa.setNombrePrograma(rs.getString("nombreprograma"));
                programa.setActivoPrograma(rs.getString("activo"));
                lista.add(programa);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener programas: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }
    
    public Programa obtenerProgramaPorId(String idPrograma) {
        String sql = "SELECT idprograma, nombreprograma, activo FROM programas WHERE idprograma = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPrograma);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Programa programa = new Programa();
                    programa.setIdPrograma(rs.getString("idprograma"));
                    programa.setNombrePrograma(rs.getString("nombreprograma"));
                    programa.setActivoPrograma(rs.getString("activo"));
                    return programa;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIdProgramaByName(String nombrePrograma) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String idPrograma = null;

        try {
            conn = conexion.getConnection();
            String sql = "SELECT idprograma FROM programas WHERE nombreprograma = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombrePrograma);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idPrograma = rs.getString("idprograma");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idPrograma;
    }

    public boolean actualizarPrograma(Programa programa) {
        Connection conn = conexion.getConnection();
        String sql = "UPDATE programas SET nombreprograma,activo = ? WHERE idprograma = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, programa.getNombrePrograma());
            stmt.setString(2, programa.getActivoPrograma());
            stmt.setString(3, programa.getIdPrograma());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar programa: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public boolean eliminarPrograma(String idPrograma) {
        Connection conn = conexion.getConnection();
        String sql = "DELETE FROM programas WHERE idprograma = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPrograma);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar programa: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }
}

