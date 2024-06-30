package Vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ing.JHON EDINSON BLANDON QUINTERO
 */
public class MainFrame extends javax.swing.JFrame {
    
    private JLabel titulo;
    private JLabel logoUnivalle;
    private JLabel labelSede;
    private JLabel labelSolicitudCedula;
    private JTextField textFieldCedula = null;
    private JButton btnVerificarHuella;
    private JButton btnRegistrarHuella;
    private JLabel labelMensaje;
    private JLabel relojLabel;
    private JButton btnConfiguracion;
    private JButton btnCerrar;
    
    
    
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        this.setSize(1350,720);
        this.setTitle("SISTEMA BIOMÉTRICO DE CONTROL DE ASISTENCIA DOCENTE");
        this.setBackground(Color.darkGray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear el panel
        JPanel panelMainFrame = new JPanel();
        panelMainFrame.setLayout(null); // Establecer el diseño nulo para usar setBounds
        // Arreglo los parametros del titulo
        titulo = new JLabel("SISTEMA BIOMÉTRICO DE CONTROL DE ASISTENCIA DOCENTE");
        titulo.setBounds(250, 10, 900, 80);
        titulo.setForeground(Color.RED);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        // Arreglo los parametros del logo de Univalle
        ImageIcon iconUnivalle = new ImageIcon("imagenes/logounivalle.png");
        logoUnivalle = new JLabel(iconUnivalle);
        logoUnivalle.setBounds(150,160, 281,400);
        //Arreglo los parametros del label de la sede tulua
        labelSede = new JLabel("Sede Tuluá");
        labelSede.setBounds(215,540,300,80);
        labelSede.setForeground(Color.RED);
        labelSede.setFont(new Font("Arial", Font.BOLD, 28));
        //Arreglo los parametros del label de solicitud de la cedula.
        labelSolicitudCedula = new JLabel("Inicie ingresando su cedula");
        labelSolicitudCedula.setBounds(700,130,400,80);
        labelSolicitudCedula.setForeground(Color.RED);
        labelSolicitudCedula.setFont(new Font("Arial", Font.BOLD, 24));
        // Arreglo los parametros del campo de texto de la cedula
        textFieldCedula = new JTextField();
        textFieldCedula.setBounds(710,215, 300, 50);
        textFieldCedula.setFont(new Font("Arial", Font.BOLD, 26));
        // Arreglo los parametros del boton de verificar huella
        btnVerificarHuella = new JButton("Verificar Huella");
        btnVerificarHuella.setBounds(710,280, 300, 50);
        btnVerificarHuella.setFont(new Font("Arial", Font.BOLD, 26));
        // Arreglo los parametros del boton de registrar huella
        btnRegistrarHuella = new JButton("Nuevo Usuario");
        btnRegistrarHuella.setBounds(710,400, 300, 50);
        btnRegistrarHuella.setFont(new Font("Arial", Font.BOLD, 26));
        // Arreglo los parametros del label del mensaje de para un nuevo registro
        labelMensaje = new JLabel("<html>Para registrar un nuevo usuario por favor ponerse en contacto con el monitor de Recursos Tecnológicos de turno.</html>");   
        labelMensaje.setBounds(710,435, 300, 100);
        labelMensaje.setForeground(Color.RED);
        labelMensaje.setFont(new Font("Arial", Font.BOLD, 14));
        labelMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        // Arreglo los ajustes del reloj de tiempo real.
        relojLabel = new JLabel();
        relojLabel.setBounds(470,620, 360, 30);
        relojLabel.setForeground(Color.RED);
        relojLabel.setFont(new Font("Arial", Font.BOLD, 20));
        relojLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Arreglo los ajustes del boton configuracion
        btnConfiguracion = new JButton("Configuración");
        btnConfiguracion.setBounds(1120,550, 150, 30);
        btnConfiguracion.setBackground(Color.GREEN);
        btnConfiguracion.setForeground(Color.WHITE);
        btnConfiguracion.setFocusPainted(false);
        // Arreglo los ajustes del boton de cerrar el frame y terminar programa
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(1120,620, 150, 30);
        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        
        // Agrego ActionListener al boton Configuraciones.
        btnConfiguracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar el nuevo JFrame
                JFrame newFrame = new JFrame("Configuracion");
                newFrame.setSize(700, 500);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Crear un JLabel en el nuevo JFrame
                JLabel newLabel = new JLabel("Este es el Frame de Configuraciones");
                newLabel.setBounds(100, 300, 200, 100);
                newFrame.add(newLabel);

                newFrame.setLayout(null); // Establecer el diseño nulo para usar setBounds
                newFrame.setVisible(true);
            }
        });
        
        //Agrego Actionlistener al boton Verificar Huella
        btnVerificarHuella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerificarHuellaFrame verificarHuella = new VerificarHuellaFrame();
                verificarHuella.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                verificarHuella.setVisible(true);
            }
        });
        
        //Agrego Actionlistener al boton Registrar Nuevo
        btnRegistrarHuella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar el nuevo JFrame
                JFrame newFrame = new JFrame("Enrolar nuevo usuario");
                newFrame.setSize(400, 300);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Crear un JLabel en el nuevo JFrame
                JLabel newLabel = new JLabel("Este es el nuevo JFrame");
                newLabel.setBounds(100, 100, 200, 30);
                newFrame.add(newLabel);

                newFrame.setLayout(null); // Establecer el diseño nulo para usar setBounds
                newFrame.setVisible(true);
            }
        });
        
        
        // Agregar ActionListener al botón cerrar
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Configurar el Timer para actualizar el reloj cada segundo
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm:ss a");
                Date now = new Date();
                relojLabel.setText(sdf.format(now));
            }
        });
        timer.start();
       
       
        // Agregar el JLabel al panel
        panelMainFrame.add(titulo);
        panelMainFrame.add(logoUnivalle);
        panelMainFrame.add(labelSede);
        panelMainFrame.add(labelSolicitudCedula);
        panelMainFrame.add(textFieldCedula);
        panelMainFrame.add(btnVerificarHuella);
        panelMainFrame.add(btnRegistrarHuella);
        panelMainFrame.add(labelMensaje);
        panelMainFrame.add(relojLabel);
        panelMainFrame.add(btnCerrar);
        panelMainFrame.add(btnConfiguracion);
        
        // Agregar el panel al marco
        this.add(panelMainFrame);
        
        // Hacer visible el marco
        this.setVisible(true);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1350, 720));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
