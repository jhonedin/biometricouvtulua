package Vista;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;
import com.digitalpersona.onetouch.verification.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Timestamp;
import Modelo.Huella;
import Modelo.HuellaDAO;


public class RegistroHuellaForm extends javax.swing.JFrame {
    
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification verifier = DPFPGlobal.getVerificationFactory().createVerification();
    private JLabel picture = new JLabel();
    private JTextField prompt = new JTextField();
    private JTextArea log = new JTextArea();
    private JTextField status = new JTextField("[status line]");
    private JComboBox<String> fingerComboBox;
    private JTextField cedulaField;
    private JLabel counterLabel = new JLabel("Capturas restantes: 4");
    private int remainingCaptures = 4; // contador de capturas restantes
    private boolean isVerificationPhase = false; // bandera para indicar la fase de verificación
    private DPFPTemplate storedTemplate; // template almacenado para la verificación
    
    /**
     * Creates new form RegistroHuellaForm
     */
    public RegistroHuellaForm(String cedula) {
        this.cedulaField = new JTextField(cedula);
        cedulaField.setVisible(false); // No mostrar en la UI
        setTitle("REGISTRO DE HUELLA DACTILAR");
        setLayout(new BorderLayout());
        setSize(750,450);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        picture.setPreferredSize(new Dimension(240, 280));
        picture.setBorder(BorderFactory.createLoweredBevelBorder());
        prompt.setEditable(false);
        prompt.setColumns(40);
        prompt.setMaximumSize(prompt.getPreferredSize());
        prompt.setBorder(BorderFactory.createTitledBorder("Prompt:"));
        log.setColumns(40);
        log.setEditable(false);
        log.setFont(UIManager.getFont("Panel.font"));
        JScrollPane logpane = new JScrollPane(log);
        logpane.setBorder(BorderFactory.createTitledBorder("Estado:"));
        
        status.setEditable(false);
        status.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        status.setFont(UIManager.getFont("Panel.font"));
        
        JButton quit = new JButton("Cerrar");
        quit.addActionListener(e -> setVisible(false));
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(prompt, BorderLayout.PAGE_START);
        right.add(logpane, BorderLayout.CENTER);
        
        JPanel center = new JPanel(new BorderLayout());
        center.add(right, BorderLayout.CENTER);
        center.add(picture, BorderLayout.LINE_START);
        center.add(status, BorderLayout.PAGE_END);
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottom.add(quit);
        
        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel fingerLabel = new JLabel("Seleccione un dedo:");
        fingerComboBox = new JComboBox<>(new String[]{"Pulgar-Derecho", 
                                                      "Indice-Derecho", 
                                                      "Dedo-Medio-Derecho", 
                                                      "Anular-Derecho", 
                                                      "Menique-Derecho",
                                                      "Pulgar-Izquierdo", 
                                                      "Indice-Izquierdo", 
                                                      "Dedo-Medio-Izquierdo", 
                                                      "Anular-Izquierdo", 
                                                      "Menique-Izquierdo"});
        topPanel.add(fingerLabel);
        topPanel.add(fingerComboBox);
        add(topPanel, BorderLayout.NORTH);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override public void componentShown(ComponentEvent e) {
                init();
                start();
            }
            @Override public void componentHidden(ComponentEvent e) {
                stop();
            }
        });  
        
        pack();
        setLocationRelativeTo(null);        
        initComponents();
    }
    
    protected void init() {
        capturer.addDataListener(new DPFPDataAdapter() {
            @Override public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(() -> {
                    makeReport("La muestra de huella digital ha sido capturada.");
                    setPrompt("Escanee la misma huella nuevamente.");
                    //process(e.getSample());
                    if (isVerificationPhase) {
                        processVerification(e.getSample());
                    } else {
                        processEnrollment(e.getSample());
                    }
                });
            }
        });
        capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El lector de huella digital está conectado."));
            }
            @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El lector de huella digital está desconectado."));
            }
        });
        capturer.addSensorListener(new DPFPSensorAdapter() {
            @Override public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El lector de huella digital ha sido tocado."));
            }
            @Override public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("El dedo ha sido retirado del lector de huella digital."));
            }
        });
        capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
            @Override public void onImageQuality(final DPFPImageQualityEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
                        makeReport("La calidad de la muestra de huella digital es buena.");
                    else
                        makeReport("La calidad de la muestra de huella digital es mala.");
                });
            }
        });
    }
//////////////////////////////////////////////////////////////////////
    
    ///// Procesamiento de la huella leida desde el lector biometrico
        protected void processEnrollment(DPFPSample sample) {
        drawPicture(convertSampleToBitmap(sample));

        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (features != null) {
            try {
                enroller.addFeatures(features); // Añade las características al enrolador
                updateStatus();

                switch (enroller.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY: // Template listo
                        storedTemplate = enroller.getTemplate();
                        Huella huella = new Huella();
                        String finger = (String) fingerComboBox.getSelectedItem();
                        huella.setUserId(cedulaField.getText() + "-" + finger);
                        huella.setCedula(cedulaField.getText());
                        huella.setHuella(storedTemplate.serialize());
                        huella.setFecha(new Timestamp(System.currentTimeMillis()));

                        HuellaDAO huellaDAO = new HuellaDAO();
                        if (huellaDAO.obtenerHuellaPorId(huella.getUserId()) != null) {
                            JOptionPane.showMessageDialog(this, huella.getUserId() + " : " + "Esta huella ya está registrada.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (huellaDAO.agregarHuella(huella)) {
                                makeReport("La huella digital se ha guardado en la base de datos.");
                                JOptionPane.showMessageDialog(this, "Huella registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                // Pedir al usuario que lea la huella nuevamente para la verificación
                                prompt.setText("Escanee nuevamente la huella para verificarla.");
                                JOptionPane.showMessageDialog(this, "Por favor, escanee nuevamente la huella para verificarla.", "Verificación de Huella", JOptionPane.INFORMATION_MESSAGE);
                                remainingCaptures = 1; // Resetea el contador para la verificación
                                isVerificationPhase = true;
                            } else {
                                makeReport("Error al guardar la huella digital en la base de datos.");
                            }
                        }
                        enroller.clear(); // Limpiar enrolador para el próximo registro
                        break;

                    case TEMPLATE_STATUS_FAILED: // Fallo en la creación del template
                        enroller.clear();
                        stop();
                        updateStatus();
                        JOptionPane.showMessageDialog(this, "La plantilla de huella digital no es válida. Repita el proceso de enrolamiento.", "Error de Enrolamiento", JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                }
            } catch (DPFPImageQualityException ex) {
                ex.printStackTrace();
            }
        }
    }
    
//////////////////////////////////////////    

    protected void processVerification(DPFPSample sample) {
        drawPicture(convertSampleToBitmap(sample));

        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null && storedTemplate != null) {
            DPFPVerificationResult result = verifier.verify(features, storedTemplate);
            if (result.isVerified()) {
                makeReport("La huella ha sido verificada con éxito.");
                JOptionPane.showMessageDialog(this, "Verificación de huella exitosa.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                makeReport("La huella no pudo ser verificada.");
                JOptionPane.showMessageDialog(this, "Verificación de huella fallida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            isVerificationPhase = false;
        }
    }    
    
    private void updateStatus() {
        // Actualizar el estado del enrolamiento
        setStatus(String.format("Muestras de huella necesarias: %1$s", enroller.getFeaturesNeeded()));
        remainingCaptures = enroller.getFeaturesNeeded();
        counterLabel.setText("Capturas restantes: " + remainingCaptures);
    }
    
    public void start() {
        capturer.startCapture();
        setPrompt("Usando el lector de huellas digitales, escanee su huella digital.");
    }

    public void stop() {
        capturer.stopCapture();
    }

    public void setPrompt(String string) {
        prompt.setText(string);
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
        setPreferredSize(new java.awt.Dimension(750, 450));
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
            java.util.logging.Logger.getLogger(RegistroHuellaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroHuellaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroHuellaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroHuellaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(new Runnable() {
        //    public void run() {
                //new RegistroHuellaForm().setVisible(true);
        //    }
        //});
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
