package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDAO {
    private Conexion conexion = new Conexion();

    public boolean agregarPrograma(Programa programa) {
        Connection conn = conexion.getConnection();
        String sql = "INSERT INTO programas (idprograma, nombreprograma) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, programa.getIdPrograma());
            stmt.setString(2, programa.getNombrePrograma());
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
                lista.add(programa);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener programas: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }

    public boolean actualizarPrograma(Programa programa) {
        Connection conn = conexion.getConnection();
        String sql = "UPDATE programas SET nombreprograma = ? WHERE idprograma = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, programa.getNombrePrograma());
            stmt.setString(2, programa.getIdPrograma());
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

