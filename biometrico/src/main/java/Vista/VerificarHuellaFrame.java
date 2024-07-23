package Vista;

import java.awt.Color;
import java.awt.Font;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Modelo.DocenteDAO;
import Modelo.Huella;
import Modelo.HuellaDAO;
import Modelo.RegistroAsistencia;
import Modelo.RegistroAsistenciaDAO;
import com.digitalpersona.onetouch.DPFPCaptureFeedback;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Image;
import javax.swing.UIManager;

public class VerificarHuellaFrame extends javax.swing.JFrame {
    private JLabel tituloLabel;
    private JLabel subtituloLabel;
    private JLabel docenteLabel;
    private JLabel horaEntradaLabel;
    private JLabel horaSalidaLabel;
    private JLabel huellasRegistradas;
    private JButton cerrarButton;
    private JTextField docenteIdentificado;
    private JTextField horaEntrada;
    private JTextField horaSalida;
    private JComboBox<String> huellasComboBox;
    private JLabel picture = new JLabel();
    private JTextArea log = new JTextArea();
    private JTextField status = new JTextField("[status line]");
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
    private String cedula; 
      
    public VerificarHuellaFrame(){
    
    }
    
    public VerificarHuellaFrame(String cedula) {
        this.cedula = cedula;
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
        docenteIdentificado.setText(getNombreDocenteIdentificado(cedula));
        docenteIdentificado.setEditable(false);
        
        // Ajusto los parametros del JLabel horaEntrada
        horaEntradaLabel = new JLabel("Hora Entrada:");
        horaEntradaLabel.setBounds(330,200,300, 20);
        horaEntradaLabel.setForeground(Color.RED);
        horaEntradaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Ajusto los parametros del campo de texto JTextField horaEntrada
        horaEntrada = new JTextField();
        horaEntrada.setBounds(460,190, 290, 40);
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
        horaSalida.setBounds(460,260, 290, 40);
        horaSalida.setFont(new Font("Arial", Font.BOLD, 16));
        horaSalida.setText(getHoraSalidaDocenteIdentificado());
        horaSalida.setEditable(false);
        
        // Ajusto los parametros del JLabel huellasRegistradas
        huellasRegistradas = new JLabel("Huellas registradas:");
        huellasRegistradas.setBounds(330,345,300, 20);
        huellasRegistradas.setForeground(Color.RED);
        huellasRegistradas.setFont(new Font("Arial", Font.BOLD, 18));
        
         // Ajusto los parametros del JComboBox huellasComboBox para leer de la BD las huellas registradas
        huellasComboBox = new JComboBox<>(new String[]{"Sin registro de Huella"});
        huellasComboBox.setBounds(510,340,220, 30);
        huellasComboBox.setBackground(Color.WHITE);
        huellasComboBox.setFont(new Font("Arial", Font.BOLD, 13));
        
        
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
        panelVerificarHuella.add(huellasRegistradas);
        panelVerificarHuella.add(huellasComboBox);
        
        // Cargar huellas
        cargarHuellas(cedula);
        
        /////////////////////////////
        // Componentes adicionales para la imagen de la huella y el log
        picture.setBounds(40, 100, 240, 280);
        picture.setBorder(BorderFactory.createLoweredBevelBorder());
        add(picture);

        log.setBounds(40, 400, 400, 100);
        log.setEditable(false);
        log.setFont(UIManager.getFont("Panel.font"));
        JScrollPane logpane = new JScrollPane(log);
        logpane.setBounds(40, 400, 400, 100);
        logpane.setBorder(BorderFactory.createTitledBorder("Estado del Proceso"));
        add(logpane);

        status.setBounds(40, 510, 400, 30);
        status.setEditable(false);
        status.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        status.setFont(UIManager.getFont("Panel.font"));
        add(status);        
        
        // Iniciar captura de huella
        init();
        start();
        
        // Ajustar el FAR a un nivel medio de seguridad
        verificator.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
    
        // Agregar el panel al marco
        this.add(panelVerificarHuella);
        
        // Hacer visible el marco
        this.setVisible(true);
        
        initComponents();
    }
    
    public String getNombreDocenteIdentificado(String cedula){
        DocenteDAO docentedao = new DocenteDAO();
        String primerNombre = docentedao.obtenerDocentePorCedula(cedula).getPrimerNombre();
        String segundoNombre = docentedao.obtenerDocentePorCedula(cedula).getSegundoNombre();
        if(segundoNombre==null || "nan".equals(segundoNombre) ){
            segundoNombre = "";
        }
        String primerApellido = docentedao.obtenerDocentePorCedula(cedula).getPrimerApellido();
        String segundoApellido = docentedao.obtenerDocentePorCedula(cedula).getSegundoApellido();
        if(segundoApellido==null || "nan".equals(segundoApellido) ){
            segundoApellido = "";
        }
        
        return primerNombre+" "+segundoNombre+" "+primerApellido+" "+segundoApellido;
    }
    
    public String getHoraEntradaDocenteIdentificado() {
        try {
            RegistroAsistenciaDAO registroAsistenciaDAO = new RegistroAsistenciaDAO();
            RegistroAsistencia registroAsistencia = registroAsistenciaDAO.obtenerRegistroAsistenciaPorCedulaYFecha(cedula, new java.sql.Date(System.currentTimeMillis()));
            if (registroAsistencia != null && registroAsistencia.getHoraIngreso() != null) {
                //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm:ss a");
                return sdf.format(registroAsistencia.getHoraIngreso());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO REGISTRADO";
    }
    
    public String getHoraSalidaDocenteIdentificado() {
        try {
            RegistroAsistenciaDAO registroAsistenciaDAO = new RegistroAsistenciaDAO();
            RegistroAsistencia registroAsistencia = registroAsistenciaDAO.obtenerRegistroAsistenciaPorCedulaYFecha(cedula, new java.sql.Date(System.currentTimeMillis()));
            if (registroAsistencia != null && registroAsistencia.getHoraSalida() != null) {
                //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm:ss a");
                return sdf.format(registroAsistencia.getHoraSalida());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO REGISTRADO";
    }
    
    private void cargarHuellas(String cedula) {
        HuellaDAO huellaDAO = new HuellaDAO();
        List<Huella> huellas = huellaDAO.obtenerHuellasPorCedula(cedula);

        huellasComboBox.removeAllItems();
        if (huellas.isEmpty()) {
            huellasComboBox.addItem("Sin registro de huella");
        } else {
            for (Huella huella : huellas) {
                huellasComboBox.addItem(huella.getUserId());
            }
        }
    }

    private void init() {
        capturer.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(() -> {
                    makeReport("La muestra de huella digital ha sido capturada.");
                    setPrompt("Escanee la misma huella nuevamente.");
                    process(e.getSample());
                });
            }
        });
        capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El lector de huella digital está conectado."));
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El lector de huella digital está desconectado."));
            }
        });
        capturer.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El lector de huella digital ha sido tocado."));
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El dedo ha sido retirado del lector de huella digital."));
            }
        });
        capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
            @Override
            public void onImageQuality(final DPFPImageQualityEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
                        makeReport("La calidad de la muestra de huella digital es buena.");
                    else
                        makeReport("La calidad de la muestra de huella digital es mala.");
                });
            }
        });
    }

    protected void process(DPFPSample sample) {
        drawPicture(convertSampleToBitmap(sample));
        System.out.println("Antes de extraer características");
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null) {
            System.out.println("Las features son distintas de null");
            try {
                DPFPVerificationResult result = null;
                HuellaDAO huellaDAO = new HuellaDAO();
                System.out.println("Antes de obtener las listas de las huellas");
                List<Huella> huellas = huellaDAO.obtenerHuellasPorCedula(cedula);
                System.out.println("Huellas obtenidas en process: " + huellas.size());

                for (Huella huella : huellas) {
                    System.out.println("Exploración de las huellas");
                    DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate(huella.getHuella());
                    System.out.println("Template: " + template.toString());
                    result = verificator.verify(features, template);
                    if (result.isVerified()) {
                        makeReport("La huella digital coincide con " + huella.getUserId());
                        verificarYRegistrarAsistencia(huella.getCedula());
                        horaEntrada.setText(getHoraEntradaDocenteIdentificado());
                        horaSalida.setText(getHoraSalidaDocenteIdentificado());
                        return;
                    }
                }
                if (result == null || !result.isVerified()) {
                    JOptionPane.showMessageDialog(this, "La huella digital no coincide con ninguna registrada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void verificarYRegistrarAsistencia(String cedula) {
        try {
            RegistroAsistenciaDAO registroAsistenciaDAO = new RegistroAsistenciaDAO();
            RegistroAsistencia registroAsistencia = registroAsistenciaDAO.obtenerRegistroAsistenciaPorCedulaYFecha(cedula, new java.sql.Date(System.currentTimeMillis()));

            if (registroAsistencia != null) {
                Timestamp horaIngreso = registroAsistencia.getHoraIngreso();
                Timestamp horaSalida = registroAsistencia.getHoraSalida();

                if (horaSalida == null) {
                    registroAsistenciaDAO.registrarSalida(registroAsistencia.getIdRegistro());
                    makeReport("Hora de salida registrada con éxito.");
                    JOptionPane.showMessageDialog(this, "Hora de salida registrada con éxito.", "Registro de Asistencia", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    makeReport("Ya se ha registrado la hora de salida para hoy.");
                    JOptionPane.showMessageDialog(this, "Ya se ha registrado la hora de salida para hoy.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                registroAsistenciaDAO.registrarIngreso(new RegistroAsistencia(cedula, new Timestamp(System.currentTimeMillis())));
                makeReport("Hora de ingreso registrada con éxito.");
                JOptionPane.showMessageDialog(this, "Hora de ingreso registrada con éxito.", "Registro de Asistencia", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar la asistencia.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void dispose() {
        stop();
        super.dispose();
    }
    
    protected void start() {
        capturer.startCapture();
        setPrompt("Usando el lector de huella digital, escanee su huella.");
    }

    protected void stop() {
        capturer.stopCapture();
    }

    public void setPrompt(String string) {
        status.setText(string);
    }

    public void makeReport(String string) {
        log.append(string + "\n");
    }

    public void setStatus(String string) {
        status.setText(string);
    }

    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void drawPicture(Image image) {
        Image scaledImage = image.getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_SMOOTH);
        picture.setIcon(new ImageIcon(scaledImage));
        repaint();
    }

    protected Image convertSampleToBitmap(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
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
