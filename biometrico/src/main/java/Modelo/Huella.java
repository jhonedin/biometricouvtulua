package Modelo;

import java.sql.Timestamp;

public class Huella {
    private String userId;
    private String cedula;
    private byte[] huella;
    private Timestamp fecha;

    // Getters y setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public byte[] getHuella() { return huella; }
    public void setHuella(byte[] huella) { this.huella = huella; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}
