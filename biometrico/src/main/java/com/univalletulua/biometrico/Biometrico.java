package com.univalletulua.biometrico;

import Vista.MainFrame;
import Modelo.Conexion;
import Modelo.DocenteDAO;
import Modelo.Docente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 *
 * @author Ing.JHON EDINSON BLANDON QUINTERO
 */


public class Biometrico extends JFrame {
    
   
    public static void main (String[] args) {
        System.out.println("HOLA JHON EDINSON");
        /*
        Docente docente = new Docente();
        
        docente.setCedula("14890711");
        docente.setPrimerNombre("Hector");
        docente.setPrimerApellido("Garcia");
        docente.setSegundoApellido("Arana");
        docente.setCorreo("hector.garcia@correounivalle.edu.co");
        docente.setTelefono("3103857893");
        docente.setIdPrograma("2710");
        docente.setActivo("S");
        DocenteDAO docenteNew = new DocenteDAO();
        //docenteNew.agregarDocente(docente);
        docente.setActivo("S");
        docenteNew.actualizarDocente(docente);
        */
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        
    }
    
}

