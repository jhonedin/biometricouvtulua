package Vista;
import Modelo.AdministradoresDAO;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class NuevoUsuarioFrame extends javax.swing.JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public NuevoUsuarioFrame() {
        this.setSize(900,600);
        this.setResizable(false);
        this.setTitle("REGISTRAR NUEVO USUARIO");
        this.setBackground(Color.darkGray);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
         // Crear el CardLayout y el JPanel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
         // Crear y añadir los paneles
        JPanel loginPanel = createLoginPanel();
        JPanel registerPanel = createRegisterPanel();
        
        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(registerPanel, "RegisterPanel");

        // Añadir el mainPanel al JFrame
        this.add(mainPanel);
        // Mostrar el panel de login al inicio
        cardLayout.show(mainPanel, "LoginPanel");
       // Hacer visible el marco
        this.setVisible(true);
        initComponents();
    }    
    
    private JPanel createLoginPanel() {
        // Crear el panel
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(null); // Establecer el diseño nulo para usar setBounds
        
         // Ajusto los parametros del titulo
        JLabel tituloLabel = new JLabel("SISTEMA BIOMÉTRICO DE CONTROL DE ASISTENCIA DOCENTE");
        tituloLabel.setBounds(140, 15,650, 20);
        tituloLabel.setForeground(Color.RED);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del subtitulo
        JLabel subtituloLabel = new JLabel("Registro de Nuevo Usuario - Login de Administrador");
        subtituloLabel.setBounds(260,45,500, 20);
        subtituloLabel.setForeground(Color.RED);
        subtituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del label usuario
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setBounds(280,160,100, 20);
        usuarioLabel.setForeground(Color.RED);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del campo de texto JTextField usuarioTxt
        JTextField usuarioTxt = new JTextField();
        usuarioTxt.setBounds(390,150, 280, 40);
        usuarioTxt.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del label usuario
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(280,260,100, 20);
        passwordLabel.setForeground(Color.RED);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del JPasswordFlied passwordFlied
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(390,250, 280, 40);
        passwordField.setFont(new Font("Arial", Font.BOLD, 20));
        
        panelLogin.add(tituloLabel);
        panelLogin.add(subtituloLabel);
        panelLogin.add(usuarioLabel);
        panelLogin.add(usuarioTxt);
        panelLogin.add(passwordLabel);
        panelLogin.add(passwordField);
        
        return panelLogin;
        /*
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Usuario:");
        JTextField userField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Placeholder
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    cardLayout.show(mainPanel, "RegisterPanel");
                } else {
                    JOptionPane.showMessageDialog(NuevoUsuarioFrame.this, "Usuario o Contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
        */
    }    

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 2));

        JLabel cedulaLabel = new JLabel("Cédula:");
        JTextField cedulaField = new JTextField();
        JLabel firstNameLabel = new JLabel("Primer Nombre:");
        JTextField firstNameField = new JTextField();
        JLabel secondNameLabel = new JLabel("Segundo Nombre:");
        JTextField secondNameField = new JTextField();
        JLabel firstLastNameLabel = new JLabel("Primer Apellido:");
        JTextField firstLastNameField = new JTextField();
        JLabel secondLastNameLabel = new JLabel("Segundo Apellido:");
        JTextField secondLastNameField = new JTextField();
        JLabel emailLabel = new JLabel("Correo:");
        JTextField emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Teléfono:");
        JTextField phoneField = new JTextField();
        JLabel academicProgramLabel = new JLabel("Programa Académico:");
        JTextField academicProgramField = new JTextField();
        JLabel activeLabel = new JLabel("Activo:");
        JComboBox<String> activeComboBox = new JComboBox<>(new String[]{"SI", "NO"});

        panel.add(cedulaLabel);
        panel.add(cedulaField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(secondNameLabel);
        panel.add(secondNameField);
        panel.add(firstLastNameLabel);
        panel.add(firstLastNameField);
        panel.add(secondLastNameLabel);
        panel.add(secondLastNameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(academicProgramLabel);
        panel.add(academicProgramField);
        panel.add(activeLabel);
        panel.add(activeComboBox);

        return panel;
    }

    private boolean validateLogin(String username, String password) {
        // Validación de usuario y contraseña (simulada)
        return "admin".equals(username) && "password".equals(password);
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
        setPreferredSize(new java.awt.Dimension(900, 600));

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
            java.util.logging.Logger.getLogger(NuevoUsuarioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevoUsuarioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevoUsuarioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevoUsuarioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NuevoUsuarioFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
