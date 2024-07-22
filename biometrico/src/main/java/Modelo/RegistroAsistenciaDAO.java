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
    
    public RegistroAsistencia obtenerRegistroAsistenciaPorCedulaYFecha(String cedula, Date fecha) {
        RegistroAsistencia registroAsistencia = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexion.getConnection();
            String query = "SELECT * FROM registrosasistencia WHERE cedula = ? AND DATE(horaingreso) = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, cedula);
            stmt.setDate(2, fecha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                registroAsistencia = new RegistroAsistencia();
                registroAsistencia.setIdRegistro(rs.getInt("idregistro"));
                registroAsistencia.setCedula(rs.getString("cedula"));
                registroAsistencia.setHoraIngreso(rs.getTimestamp("horaingreso"));

                Timestamp horaSalida = rs.getTimestamp("horasalida");
                if (horaSalida != null && !horaSalida.toString().equals("0000-00-00 00:00:00")) {
                    registroAsistencia.setHoraSalida(horaSalida);
                } else {
                    registroAsistencia.setHoraSalida(null); // O un valor adecuado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return registroAsistencia;
    }

    /*
    public RegistroAsistencia obtenerRegistroAsistenciaPorCedulaYFecha(String cedula, Date fecha) {
        RegistroAsistencia registroAsistencia = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexion.getConnection();
            String query = "SELECT * FROM registrosasistencia WHERE cedula = ? AND DATE(horaingreso) = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, cedula);
            stmt.setDate(2, fecha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                registroAsistencia = new RegistroAsistencia();
                registroAsistencia.setIdRegistro(rs.getInt("idregistro"));
                registroAsistencia.setCedula(rs.getString("cedula"));
                registroAsistencia.setHoraIngreso(rs.getTimestamp("horaingreso"));
                registroAsistencia.setHoraSalida(rs.getTimestamp("horasalida"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return registroAsistencia;
    }
    */
    
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
    
    public void registrarIngreso(RegistroAsistencia registroAsistencia) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = conexion.getConnection();
            String query = "INSERT INTO registrosasistencia (cedula, horaingreso) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, registroAsistencia.getCedula());
            stmt.setTimestamp(2, registroAsistencia.getHoraIngreso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void registrarSalida(int idRegistro) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = conexion.getConnection();
            String query = "UPDATE registrosasistencia SET horasalida = CURRENT_TIMESTAMP WHERE idregistro = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idRegistro);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<RegistroAsistencia> getAsistenciasPorRangoDeFechas(Date fechaInicial, Date fechaFinal) {
        List<RegistroAsistencia> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM registrosasistencia WHERE horaingreso BETWEEN ? AND ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(fechaInicial.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(fechaFinal.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroAsistencia asistencia = new RegistroAsistencia();
                asistencia.setIdRegistro(rs.getInt("idregistro"));
                asistencia.setHoraIngreso(rs.getTimestamp("horaingreso"));
                asistencia.setHoraSalida(rs.getTimestamp("horasalida"));
                asistencia.setCedula(rs.getString("cedula"));
                asistencias.add(asistencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asistencias;
    }

    public Timestamp getHoraEntradaActual(String cedulaDocente) {
        String sql = "SELECT horaingreso FROM registrosasistencia WHERE cedula = ? AND DATE(horaingreso) = CURDATE()";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedulaDocente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("horaingreso");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Timestamp getHoraSalidaActual(String cedulaDocente) {
        String sql = "SELECT horasalida FROM registrosasistencia WHERE cedula = ? AND DATE(horaingreso) = CURDATE()";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedulaDocente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("horasalida");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

