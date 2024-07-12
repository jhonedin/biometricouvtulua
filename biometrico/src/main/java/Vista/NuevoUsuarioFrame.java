package Vista;
import Modelo.AdministradoresDAO;
import Modelo.Administradores;
import Modelo.Docente;
import Modelo.DocenteDAO;
import Modelo.Programa;
import Modelo.ProgramaDAO;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Vista.RegistroHuellaForm;

public class NuevoUsuarioFrame extends javax.swing.JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    JTextField cedulaField;
    JTextField primerNombreField;
    JTextField segundoNombreField;
    JTextField primerApellidoField;
    JTextField segundoApellidoField;
    JTextField correoField;
    JTextField telefonoField;
    JComboBox<String> activoComboBox;
    private JComboBox<String> programaComboBox;
    private Programa programa;
    
    
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
        cardLayout.show(mainPanel, "RegisterPanel"); //cardLayout.show(mainPanel, "LoginPanel");
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
        
        JButton loginButton = new JButton("Ingresar");
        loginButton.setBounds(390,350,280,40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del boton de cerrar
        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.setBounds(680,480, 150, 30);
        cerrarButton.setBackground(Color.RED);
        cerrarButton.setForeground(Color.WHITE);
        
        cerrarButton.setFocusPainted(false);
        
        // Agregar ActionListener al botón cerrar
        cerrarButton.addActionListener(ev -> this.dispose());
        
        panelLogin.add(tituloLabel);
        panelLogin.add(subtituloLabel);
        panelLogin.add(usuarioLabel);
        panelLogin.add(usuarioTxt);
        panelLogin.add(passwordLabel);
        panelLogin.add(passwordField);
        panelLogin.add(loginButton);
        panelLogin.add(cerrarButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usuarioTxt.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    cardLayout.show(mainPanel, "RegisterPanel");
                } else {
                    JOptionPane.showMessageDialog(NuevoUsuarioFrame.this, "Usuario o Contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panelLogin;
    }    

    private JPanel createRegisterPanel() {
        // Crear el panel
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(null); // Establecer el diseño nulo para usar setBounds
        
        // Ajusto los parametros del titulo
        JLabel tituloLabel = new JLabel("SISTEMA BIOMÉTRICO DE CONTROL DE ASISTENCIA DOCENTE");
        tituloLabel.setBounds(140, 15,650, 20);
        tituloLabel.setForeground(Color.RED);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Ajusto los parametros del subtitulo
        JLabel subtituloLabel = new JLabel("Registro de Nuevo Usuario - Datos del Docente");
        subtituloLabel.setBounds(260,45,500, 20);
        subtituloLabel.setForeground(Color.RED);
        subtituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel cedulaLabel
        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setBounds(100,150,200, 20);
        cedulaLabel.setForeground(Color.RED);
        cedulaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField cedulaField
        cedulaField = new JTextField();
        cedulaField.setBounds(165,145,200,30);
        cedulaField.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel primerNombreLabel
        JLabel primerNombreLabel = new JLabel("Primer Nombre:");
        primerNombreLabel.setBounds(40,200,200, 20);
        primerNombreLabel.setForeground(Color.RED);
        primerNombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField primerNombreField
        primerNombreField = new JTextField();
        primerNombreField.setBounds(165,195,200,30);
        primerNombreField.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel segundoNombreLabel
        JLabel segundoNombreLabel = new JLabel("Segundo Nombre:");
        segundoNombreLabel.setBounds(22,250,200, 20);
        segundoNombreLabel.setForeground(Color.RED);
        segundoNombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField primerNombreField
        segundoNombreField = new JTextField();
        segundoNombreField.setBounds(165,245,200,30);
        segundoNombreField.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel primerApellidoLabel
        JLabel primerApellidoLabel = new JLabel("Primer Apellido:");
        primerApellidoLabel.setBounds(40,300,200, 20);
        primerApellidoLabel.setForeground(Color.RED);
        primerApellidoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField primerApellidoField
        primerApellidoField = new JTextField();
        primerApellidoField.setBounds(165,295,200,30);
        primerApellidoField.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel segundoApellidoLabel
        JLabel segundoApellidoLabel = new JLabel("Segundo Apellido:");
        segundoApellidoLabel.setBounds(22,350,200, 20);
        segundoApellidoLabel.setForeground(Color.RED);
        segundoApellidoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField segundoApellidoField
        segundoApellidoField = new JTextField();
        segundoApellidoField.setBounds(165,345,200,30);
        segundoApellidoField.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel correoLabel
        JLabel correoLabel = new JLabel("Correo:");
        correoLabel.setBounds(450,150,200, 20);
        correoLabel.setForeground(Color.RED);
        correoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField correoField
        correoField = new JTextField();
        correoField.setBounds(515,145,280,30);
        correoField.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Ajusto los parametros del JLabel telefonoLabel
        JLabel telefonoLabel = new JLabel("Telefono:");
        telefonoLabel.setBounds(435,200,200, 20);
        telefonoLabel.setForeground(Color.RED);
        telefonoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JTextField telefonoField
        telefonoField = new JTextField();
        telefonoField.setBounds(515,195,280,30);
        telefonoField.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JLabel programaLabel
        JLabel programaLabel = new JLabel("Programa:");
        programaLabel.setBounds(428,250,200, 20);
        programaLabel.setForeground(Color.RED);
        programaLabel.setFont(new Font("Arial", Font.BOLD, 16));
       
        programaComboBox = new JComboBox();
        cargarProgramas(); // se cargan los programas automaticamente desde la base de datos, en el JComboBox.
        programaComboBox.setBounds(515,245,280, 30);
        programaComboBox.setBackground(Color.WHITE);
        programaComboBox.setFont(new Font("Arial", Font.BOLD, 10));
        
        // Ajusto los parametros del JLabel programaLabel
        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(455,300,200, 20);
        activoLabel.setForeground(Color.RED);
        activoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JComboBox activoComboBox
        activoComboBox = new JComboBox<>(new String[]{"SI", "NO"});
        activoComboBox.setBounds(515,295,280, 30);
        activoComboBox.setBackground(Color.WHITE);
        activoComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        
         // Ajusto los parametros del JLabel Huellas Registradas
        JLabel huellasLabel = new JLabel("Huellas Reg:");
        huellasLabel.setBounds(410,350,200, 20);
        huellasLabel.setForeground(Color.RED);
        huellasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajusto los parametros del JComboBox huellasComboBox para leer de la BD las huellas registradas
        JComboBox<String> huellasComboBox = new JComboBox<>(new String[]{"cedula-indice-derecho", "cedula-indice-izquierdo"});
        huellasComboBox.setBounds(515,345,280, 30);
        huellasComboBox.setBackground(Color.WHITE);
        huellasComboBox.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Ajusto los parametros del JButton consultar
        JButton consultarButton = new JButton("Consultar");
        consultarButton.setBounds(50,480, 120, 30);
        consultarButton.setFont(new Font("Arial", Font.BOLD, 16));
        consultarButton.setFocusPainted(false);
        
        // Ajusto los parametros del JButton registrar
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(175,480, 120, 30);
        registrarButton.setFont(new Font("Arial", Font.BOLD, 16));
        registrarButton.setFocusPainted(false);
        
        // Ajusto los parametros del JButton modificar
        JButton modificarButton = new JButton("Modificar");
        modificarButton.setBounds(300,480, 120, 30);
        modificarButton.setFont(new Font("Arial", Font.BOLD, 16));
        modificarButton.setFocusPainted(false);
        
        // Ajusto los parametros del JButton Enrolar Huella
        JButton enrolarButton = new JButton("Enrolar Huella");
        enrolarButton.setBounds(430,480, 150, 30);
        enrolarButton.setBackground(Color.CYAN);
        enrolarButton.setFont(new Font("Arial", Font.BOLD, 16));
        enrolarButton.setFocusPainted(false);
        
        // Ajusto los parametros del JButton cerrar
        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.setBounds(650,480, 150, 30);
        cerrarButton.setBackground(Color.RED);
        cerrarButton.setForeground(Color.WHITE);
        cerrarButton.setFocusPainted(false);
        
        // Ajusto los parametros del JButton borrarCamposButton
        JButton borrarCamposButton = new JButton("Borrar Campos");
        borrarCamposButton.setBounds(650, 410, 150, 30); // Misma posición X que cerrarButton, 40px antes en Y
        borrarCamposButton.setBackground(Color.ORANGE);
        borrarCamposButton.setFont(new Font("Arial", Font.BOLD, 13));
        borrarCamposButton.setFocusPainted(false);
        
        // Agregar ActionListener al botón cerrar
        cerrarButton.addActionListener(ev -> this.dispose());
        
        // ActionListener para el botón borrarCamposButton
        borrarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cedulaField.setText("");
                primerNombreField.setText("");
                segundoNombreField.setText("");
                primerApellidoField.setText("");
                segundoApellidoField.setText("");
                correoField.setText("");
                telefonoField.setText("");
                programaComboBox.setSelectedIndex(0);
                activoComboBox.setSelectedIndex(0);
            }
         });    
        
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                String cedula = cedulaField.getText();
                DocenteDAO docenteDAO = new DocenteDAO();
                Docente docente = docenteDAO.obtenerDocentePorCedula(cedula);

                if (docente == null) {
                    JOptionPane.showMessageDialog(null, "Docente no registrado");
                } else {
                    if ("N".equals(docente.getActivo())) JOptionPane.showMessageDialog(null, "Docente No Activo");
                    // Llenar campos con información del docente
                    primerNombreField.setText(docente.getPrimerNombre());
                    segundoNombreField.setText(docente.getSegundoNombre());
                    primerApellidoField.setText(docente.getPrimerApellido());
                    segundoApellidoField.setText(docente.getSegundoApellido());
                    correoField.setText(docente.getCorreo());
                    telefonoField.setText(docente.getTelefono());
                    ProgramaDAO programaDAO = new ProgramaDAO();
                    programa = programaDAO.obtenerProgramaPorId(docente.getIdPrograma());
                    programaComboBox.setSelectedItem(programa.getNombrePrograma());
                    activoComboBox.setSelectedItem("N".equals(docente.getActivo()) ? "NO" : "SI");
                }
            }
        });
        
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDocente();
            }
        });
        
        // ActionListener para el botón modificar
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDocente();
            }
        });
        
        // ActionListener para el botón enrollar la huella
        enrolarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = cedulaField.getText().toString();
                DocenteDAO docdao = new DocenteDAO();
                Docente doc = new Docente();
                doc = docdao.obtenerDocentePorCedula(cedula);
                if(cedula.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Docente no identificado no se puede Enrolar Huella");
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Enrolar huella para el docente: "+
                            doc.getCedula()+" "+
                            doc.getPrimerNombre()+" "+
                            doc.getPrimerApellido()
                            );
                    // Instanciar el formulario de registro de huella
                    RegistroHuellaForm huellaForm = new RegistroHuellaForm(cedula);
                    huellaForm.setVisible(true);
                    huellaForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    //FingerprintRegistrationForm form = new FingerprintRegistrationForm(cedula);
                    //form.setVisible(true);
                } 
            }
        });
        
        panelRegistro.add(tituloLabel);
        panelRegistro.add(subtituloLabel);
        panelRegistro.add(cedulaLabel);
        panelRegistro.add(cedulaField);
        panelRegistro.add(cedulaField);
        panelRegistro.add(primerNombreLabel);
        panelRegistro.add(primerNombreField);
        panelRegistro.add(segundoNombreLabel);
        panelRegistro.add(segundoNombreField);
        panelRegistro.add(primerApellidoLabel);
        panelRegistro.add(primerApellidoField);
        panelRegistro.add(segundoApellidoLabel);
        panelRegistro.add(segundoApellidoField);
        panelRegistro.add(correoLabel);
        panelRegistro.add(correoField);
        panelRegistro.add(telefonoLabel);
        panelRegistro.add(telefonoField);
        panelRegistro.add(programaLabel);
        panelRegistro.add(programaComboBox);
        panelRegistro.add(activoLabel);
        panelRegistro.add(activoComboBox);
        panelRegistro.add(huellasLabel);
        panelRegistro.add(huellasComboBox);
        panelRegistro.add(consultarButton);
        panelRegistro.add(registrarButton);
        panelRegistro.add(modificarButton);
        panelRegistro.add(enrolarButton);
        panelRegistro.add(cerrarButton);
        panelRegistro.add(borrarCamposButton);
        
        return panelRegistro;
       
    }

    private boolean validateLogin(String username, String password) {
        AdministradoresDAO administrador = new AdministradoresDAO();
        Administradores adminValidado = new Administradores();
        String nombreAdministrador = null;
        if(administrador.autenticarAdministrador(username, password)){
            adminValidado = administrador.obtenerAdministradorPorId(username);
            nombreAdministrador = adminValidado.getNombre();
            JOptionPane.showMessageDialog(null, "Usted se ha autenticado como: " + nombreAdministrador);
            return true;
        } else {
            return false;
        }
    }    
    
    public void cargarProgramas() {
        ProgramaDAO programaDAO = new ProgramaDAO();
        List<Programa> programas = programaDAO.obtenerProgramas();
        
        programaComboBox.removeAllItems();
        
        for (Programa programa : programas) {
            if ("S".equals(programa.getActivoPrograma())) {
                programaComboBox.addItem(programa.getNombrePrograma());
            }
        }
    }
    
    private void registrarDocente() {
        // Validar que el campo cedula no esté vacío
        String cedulavalidacion = cedulaField.getText().trim();
        if (cedulavalidacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: el campo cédula no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Obtención de datos desde los campos de texto
        String cedula = cedulaField.getText();
        String primerNombre = primerNombreField.getText();
        String segundoNombre = segundoNombreField.getText();
        String primerApellido = primerApellidoField.getText();
        String segundoApellido = segundoApellidoField.getText();
        String correo = correoField.getText();
        String telefono = telefonoField.getText();
        String nombrePrograma = (String) programaComboBox.getSelectedItem();
        String idPrograma = getIdPrograma(nombrePrograma);
        String activo = (String) activoComboBox.getSelectedItem();

        // Crear una instancia de Docente
        Docente undocente = new Docente();
        undocente.setCedula(cedula);
        undocente.setPrimerNombre(primerNombre);
        undocente.setSegundoNombre(segundoNombre);
        undocente.setPrimerApellido(primerApellido);
        undocente.setSegundoApellido(segundoApellido);
        undocente.setCorreo(correo);
        undocente.setTelefono(telefono);
        undocente.setIdPrograma(idPrograma);
        if("SI".equals(activo)) undocente.setActivo("S");
        if("NO".equals(activo)) undocente.setActivo("N");
        // Instancia de DocenteDAO para manejar la base de datos
        DocenteDAO docenteDAO = new DocenteDAO();

        // Verificar si el docente ya existe
        if (docenteDAO.existeDocente(cedula)) {
            JOptionPane.showMessageDialog(this, "El docente ya existe en la base de datos.");
        } else {
            // Intentar agregar el docente
            if (docenteDAO.agregarDocente(undocente)) {
                JOptionPane.showMessageDialog(this, "Docente registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el docente.");
            }
        }
       
    }
    
    private String getIdPrograma(String nombrePrograma) {
        ProgramaDAO programaDAO = new ProgramaDAO();
        return programaDAO.getIdProgramaByName(nombrePrograma);
    }

    //////////////////////
    private void modificarDocente() {
        // Validar que el campo cedula no esté vacío
        String cedulavalidacion = cedulaField.getText().trim();
        if (cedulavalidacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: el campo cédula no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Obtención de datos desde los campos de texto
        String cedula = cedulaField.getText();
        String primerNombre = primerNombreField.getText();
        String segundoNombre = segundoNombreField.getText();
        String primerApellido = primerApellidoField.getText();
        String segundoApellido = segundoApellidoField.getText();
        String correo = correoField.getText();
        String telefono = telefonoField.getText();
        String nombrePrograma = (String) programaComboBox.getSelectedItem();
        String idPrograma = getIdPrograma(nombrePrograma);
        String activo = (String) activoComboBox.getSelectedItem();

        // Crear una instancia de Docente
        Docente docente = new Docente();
        docente.setCedula(cedula);
        docente.setPrimerNombre(primerNombre);
        docente.setSegundoNombre(segundoNombre);
        docente.setPrimerApellido(primerApellido);
        docente.setSegundoApellido(segundoApellido);
        docente.setCorreo(correo);
        docente.setTelefono(telefono);
        docente.setIdPrograma(idPrograma);
        docente.setActivo("SI".equals(activo) ? "S" : "N");

        // Instancia de DocenteDAO para manejar la base de datos
        DocenteDAO docenteDAO = new DocenteDAO();

        // Intentar actualizar el docente
        if (docenteDAO.actualizarDocente(docente)) {
            JOptionPane.showMessageDialog(this, "Docente actualizado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el docente.");
        }
    }
    //////////////////////

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
