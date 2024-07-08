package com.univalletulua.biometrico;

import Modelo.Programa;
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
        ProgramaDAO programadao = new ProgramaDAO();
        List<Programa> listaProgramas = programadao.obtenerProgramas();
        for(Programa programas : listaProgramas){
            System.out.println(programas.getNombrePrograma());
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        
    }
    
}

