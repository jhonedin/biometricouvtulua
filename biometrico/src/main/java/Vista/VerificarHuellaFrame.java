package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class VerificarHuellaFrame extends javax.swing.JFrame {
    private JLabel tituloLabel;
    private JLabel subtituloLabel;
    private JLabel docenteLabel;
    private JLabel horaEntradaLabel;
    private JLabel horaSalidaLabel;
    private JButton cerrarButton;
    private JTextField docenteIdentificado;
    private JTextField horaEntrada;
    private JTextField horaSalida;
    
    public VerificarHuellaFrame() {
        this.setSize(800,600);
        this.setResizable(false);
        this.setTitle("VERIFICAR HUELLA");
        this.setBackground(Color.darkGray);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Crear el panel
        JPanel panelVerificarHuella = new JPanel();
        panelVerificarHuella.setLayout(null); // Establecer el diseño nulo para usar setBounds
        
        // Ajusto los parametros del titulo
        tituloLabel = new JLabel("SISTEMA BIOMÉTRICO DE CONTROL DE ASISTENCIA DOCENTE");
        tituloLabel.setBounds(80, 15,650, 20);
        tituloLabel.setForeground(Color.RED);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del titulo
        subtituloLabel = new JLabel("Verificación de Huella - Registro de Ingreso y Salida");
        subtituloLabel.setBounds(220,45,500, 20);
        subtituloLabel.setForeground(Color.RED);
        subtituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del label docente
        docenteLabel = new JLabel("Docente:");
        docenteLabel.setBounds(330,130,100, 20);
        docenteLabel.setForeground(Color.RED);
        docenteLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Ajusto los parametros del campo de texto JTextField docenteIdentificado
        docenteIdentificado = new JTextField();
        docenteIdentificado.setBounds(420,120, 330, 40);
        docenteIdentificado.setFont(new Font("Arial", Font.BOLD, 16));
        docenteIdentificado.setText(getNombreDocenteIdentificado());
        docenteIdentificado.setEditable(false);
        
        // Ajusto los parametros del JLabel horaEntrada
        horaEntradaLabel = new JLabel("Hora Entrada:");
        horaEntradaLabel.setBounds(330,200,300, 20);
        horaEntradaLabel.setForeground(Color.RED);
        horaEntradaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Ajusto los parametros del campo de texto JTextField horaEntrada
        horaEntrada = new JTextField();
        horaEntrada.setBounds(460,190, 150, 40);
        horaEntrada.setFont(new Font("Arial", Font.BOLD, 16));
        horaEntrada.setText(getHoraEntradaDocenteIdentificado());
        horaEntrada.setEditable(false);
        
        // Ajusto los parametros del JLabel horaSalida
        horaSalidaLabel = new JLabel("Hora Salida:");
        horaSalidaLabel.setBounds(330,270,300, 20);
        horaSalidaLabel.setForeground(Color.RED);
        horaSalidaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Ajusto los parametros del campo de texto JTextField horaSalida
        horaSalida = new JTextField();
        horaSalida.setBounds(460,260, 150, 40);
        horaSalida.setFont(new Font("Arial", Font.BOLD, 16));
        horaSalida.setText(getHoraSalidaDocenteIdentificado());
        horaSalida.setEditable(false);
        
        // Ajusto los parametros del boton de cerrar
        cerrarButton = new JButton("Cerrar");
        cerrarButton.setBounds(580,480, 150, 30);
        cerrarButton.setBackground(Color.RED);
        cerrarButton.setForeground(Color.WHITE);
        cerrarButton.setFocusPainted(false);
        
        // Agregar ActionListener al botón cerrar
        cerrarButton.addActionListener(ev -> this.dispose());
        
        panelVerificarHuella.add(tituloLabel);
        panelVerificarHuella.add(subtituloLabel);
        panelVerificarHuella.add(docenteLabel);
        panelVerificarHuella.add(cerrarButton);
        panelVerificarHuella.add(docenteIdentificado);
        panelVerificarHuella.add(horaEntradaLabel);
        panelVerificarHuella.add(horaSalidaLabel);
        panelVerificarHuella.add(horaEntrada);
        panelVerificarHuella.add(horaSalida);
        
        // Agregar el panel al marco
        this.add(panelVerificarHuella);
        
        // Hacer visible el marco
        this.setVisible(true);
        
        initComponents();
    }
    
    public String getNombreDocenteIdentificado(){
        return "JHON EDINSON BLANDON QUINTERO";
    }
    
    public String getHoraEntradaDocenteIdentificado(){
        return "NO REGISTRADO";
    }
    
    public String getHoraSalidaDocenteIdentificado(){
        return "NO REGISTRADO";
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
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(VerificarHuellaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerificarHuellaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerificarHuellaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerificarHuellaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerificarHuellaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
