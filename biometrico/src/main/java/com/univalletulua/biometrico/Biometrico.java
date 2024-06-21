package com.univalletulua.biometrico;

import Vista.MainFrame;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Ing.JHON EDINSON BLANDON QUINTERO
 */



public class Biometrico extends JFrame {
    
   
    public static void main (String[] args) {
        System.out.println("HOLA JHON EDINSON");
        System.out.println("AQUI VAMOS DE NUEVO");
        //MainFrame mainframe = new MainFrame();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
       
    }
    
}

