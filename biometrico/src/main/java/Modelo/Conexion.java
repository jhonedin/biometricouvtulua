package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
    private static String URL = "jdbc:mysql://192.168.14.85:3306/biometrico";
    private static String USER = "biometrico";
    private static String PASSWORD = "univalle**";
    //private static  String URL = "jdbc:mysql://localhost:3306/biometrico";
    //private static  String USER = "biometrico";
    //private static  String PASSWORD = "univalle**";
    
    private Connection connection = null;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                                          "No hay conexión a la base de datos.", 
                                          "Error de Conexión", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }
    
    public static String getUrl() {
        return URL;
    }

    public static void setUrl(String url) {
        URL = url;
    }

    public static String getUser() {
        return USER;
    }

    public static void setUser(String user) {
        USER = user;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static void setPassword(String password) {
        PASSWORD = password;
    }
}

