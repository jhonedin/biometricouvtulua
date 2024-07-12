package Vista;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Timestamp;
import Modelo.Huella;
import Modelo.HuellaDAO;


public class RegistroHuellaForm extends javax.swing.JFrame {
    
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private JLabel picture = new JLabel();
    private JTextField prompt = new JTextField();
    private JTextArea log = new JTextArea();
    private JTextField status = new JTextField("[status line]");
    private JComboBox<String> fingerComboBox;
    private JTextField cedulaField;
    
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
        logpane.setBorder(BorderFactory.createTitledBorder("Status:"));
        
        status.setEditable(false);
        status.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        status.setFont(UIManager.getFont("Panel.font"));
        
        JButton quit = new JButton("Close");
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
                    makeReport("The fingerprint sample was captured.");
                    setPrompt("Scan the same fingerprint again.");
                    process(e.getSample());
                });
            }
        });
        capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("The fingerprint reader was connected."));
            }
            @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("The fingerprint reader was disconnected."));
            }
        });
        capturer.addSensorListener(new DPFPSensorAdapter() {
            @Override public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("The fingerprint reader was touched."));
            }
            @Override public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(() -> makeReport("The finger was removed from the fingerprint reader."));
            }
        });
        capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
            @Override public void onImageQuality(final DPFPImageQualityEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
                        makeReport("The quality of the fingerprint sample is good.");
                    else
                        makeReport("The quality of the fingerprint sample is poor.");
                });
            }
        });
    }

    protected void process(DPFPSample sample) {
        drawPicture(convertSampleToBitmap(sample));

        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (features != null) {
            try {
                DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
                template.deserialize(features.serialize());

                Huella huella = new Huella();
                String finger = (String) fingerComboBox.getSelectedItem();
                huella.setUserId(cedulaField.getText() + "-" + finger);
                huella.setCedula(cedulaField.getText());
                huella.setHuella(template.serialize());
                huella.setFecha(new Timestamp(System.currentTimeMillis()));
                
                HuellaDAO huellaDAO = new HuellaDAO();
                if (huellaDAO.agregarHuella(huella)) {
                    makeReport("The fingerprint has been saved to the database.");
                } else {
                    makeReport("Error saving the fingerprint to the database.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        /*
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (features != null) {
            try {
                DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
                template.deserialize(features.serialize());

                Huella huella = new Huella();
                String finger = (String) fingerComboBox.getSelectedItem();
                huella.setUserId(cedulaField.getText() + "-" + finger);
                huella.setCedula(cedulaField.getText());
                huella.setHuella(template.serialize());
                huella.setFecha(new Timestamp(System.currentTimeMillis()));
                
                HuellaDAO huellaDAO = new HuellaDAO();
                if (huellaDAO.agregarHuella(huella)) {
                    makeReport("The fingerprint has been saved to the database.");
                } else {
                    makeReport("Error saving the fingerprint to the database.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        */
    }

    public void start() {
        capturer.startCapture();
        setPrompt("Using the fingerprint reader, scan your fingerprint.");
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
        //picture.setIcon(new ImageIcon(image));
        //repaint();
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
