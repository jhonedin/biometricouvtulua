package com.univalletulua.biometrico;


import Modelo.Docente;
import Modelo.DocenteDAO;
import Modelo.ProgramaDAO;
import Vista.MainFrame;
import java.util.List;

import javax.swing.*;



/**
 *
 * @author Ing.JHON EDINSON BLANDON QUINTERO
 */


public class Biometrico extends JFrame {
    
   
    public static void main (String[] args) {
        System.out.println("HOLA JHON EDINSON");
        
        //ProgramaDAO programadao = new ProgramaDAO();
        //System.out.println(programadao.getIdProgramaByName("TECNOLOGIA EN ELECTRONICA INDUSTRIAL"));
        //DocenteDAO docentedao = new DocenteDAO();
        //boolean existe = docentedao.existeDocente("12345678");
        //System.out.println(existe);
        /*
        //////////
        Docente undocente = new Docente();
        undocente.setCedula("12345678");
        undocente.setPrimerNombre("CRISTIANO");
        undocente.setSegundoNombre("RONALDO");
        undocente.setPrimerApellido("DOS SANTOS");
        undocente.setSegundoApellido("AVEIRO");
        undocente.setCorreo("cristiano.ronaldo@correounivalle.edu.co");
        undocente.setTelefono("987654123");
        undocente.setIdPrograma("2725");
        undocente.setActivo("S");
        
        docentedao.agregarDocente(undocente);
        Docente otrodocente = new Docente();
        otrodocente = docentedao.obtenerDocentePorCedula("12345678");
        System.out.println("Cedula: "+otrodocente.getCedula());
        System.out.println("Primer Nombre: "+otrodocente.getPrimerNombre());
        */
                
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        
    }
    
}

