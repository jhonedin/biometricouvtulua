package Modelo;

import java.sql.Timestamp;

public class RegistroAsistencia {
    private int idRegistro;
    private String cedula;
    private Timestamp horaIngreso;
    private Timestamp horaSalida;

    // Getters y setters
    public int getIdRegistro() { return idRegistro; }
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public Timestamp getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(Timestamp horaIngreso) { this.horaIngreso = horaIngreso; }

    public Timestamp getHoraSalida() { return horaSalida; }
    public void setHoraSalida(Timestamp horaSalida) { this.horaSalida = horaSalida; }
}
