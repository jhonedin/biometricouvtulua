package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroAsistenciaDAO {
    private Conexion conexion = new Conexion();

    public boolean agregarRegistroAsistencia(RegistroAsistencia registro) {
        Connection conn = conexion.getConnection();
        String sql = "INSERT INTO registrosasistencia (cedula, horaingreso, horasalida) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, registro.getCedula());
            stmt.setTimestamp(2, registro.getHoraIngreso());
            stmt.setTimestamp(3, registro.getHoraSalida());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar registro de asistencia: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public List<RegistroAsistencia> obtenerRegistrosAsistencia() {
        List<RegistroAsistencia> lista = new ArrayList<>();
        Connection conn = conexion.getConnection();
        String sql = "SELECT * FROM registrosasistencia";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                RegistroAsistencia registro = new RegistroAsistencia();
                registro.setIdRegistro(rs.getInt("idregistro"));
                registro.setCedula(rs.getString("cedula"));
                registro.setHoraIngreso(rs.getTimestamp("horaingreso"));
                registro.setHoraSalida(rs.getTimestamp("horasalida"));
                lista.add(registro);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener registros de asistencia: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }

    public boolean actualizarRegistroAsistencia(RegistroAsistencia registro) {
        Connection conn = conexion.getConnection();
        String sql = "UPDATE registrosasistencia SET cedula = ?, horaingreso = ?, horasalida = ? WHERE idregistro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, registro.getCedula());
            stmt.setTimestamp(2, registro.getHoraIngreso());
            stmt.setTimestamp(3, registro.getHoraSalida());
            stmt.setInt(4, registro.getIdRegistro());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar registro de asistencia: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }

    public boolean eliminarRegistroAsistencia(int idRegistro) {
        Connection conn = conexion.getConnection();
        String sql = "DELETE FROM registrosasistencia WHERE idregistro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRegistro);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar registro de asistencia: " + e.getMessage());
            return false;
        } finally {
            conexion.closeConnection();
        }
    }
}

