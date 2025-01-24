package com.assinador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.Color;

//import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
//import javax.swing.border.Border;

//import javafx.scene.paint.Color;

/**
 * JavaFX assinacertificado
 */
public class assinacertificado extends JFrame implements ActionListener, BackEndObserver {
    private static assinacertificado frame;
    private static JLabel label;
    private static JComboBox<String> comboBox;
    private static JProgressBar pbar;
    private static JButton closeButton;
    private static JButton signButton;
    private static JTextArea textArea;
    static JScrollPane sp;
    private static String[] items; // = { "Item 1", "Item 2", "Item 3" };
    // private static Certificate[] certificates;
    @SuppressWarnings("unused")
    private static Map<String, Certificate> mapCertificates;

    private static String fullFilePath;
    private static String fullFilePath2;
    private static String fullFilePathDoc;
    private static String fullFilePathSecond;
    private static LoadCertificates loadCertificates;
    private static SignatureWrapper signatureWrapper;
    private static SignDocumentFromWeb signDocumentFromWeb;

    static final int MY_MINIMUM = 0;
    static final int MY_MAXIMUM = 100;

    private static PdfSigner pdfSigner;
    // private static LoadCertificates loadCertificates;
    /*
     * public static void main(String[] args) {
     * launch();
     * }
     */

    public assinacertificado(String title) {
        super(title);

    }

    private static Map<String, Certificate> zipToMap(List<String> keys, List<Certificate> values) {

        Map<String, Certificate> mapCertif = new HashMap<String, Certificate>();
        String[] arrKeys = keys.toArray(new String[0]);
        Certificate[] arrValues = values.toArray(new Certificate[0]);
        for (int i = 0; i < arrKeys.length; i++) {
            mapCertif.put(arrKeys[i], arrValues[i]);
        }
        return mapCertif;
    }

    private static void comboBoxAction() {

        /*
         * String selectedItem = (String) comboBox.getSelectedItem();
         * String originalStr = textArea.getText();
         * textArea.setText(originalStr + "\n" + selectedItem);
         */
        String selectedItem = (String) comboBox.getSelectedItem();
        localUpdate("Certificado selecionado: []" + selectedItem + " ]");

    }

    private static void closeButtonAction() {
        frame.dispose();
        System.exit(0);
    }

    private static void signButtonAction() {
        String selectedItem = (String) comboBox.getSelectedItem();
        // String originalStr = textArea.getText();

        localUpdate("[INFO ⚠ ] - Assinar um documento com o certificado: [" + selectedItem + " ]");

        try {
            pdfSigner = new PdfSigner(frame);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
        pbar.setValue(MY_MINIMUM + 20);
        String fileDirName = "C:\\Temp\\entrada.pdf";
        String fileDirOutName = "C:\\Temp\\entrada.pdf.p7s";
        // LoadFileNInfo ldFileNInfo = new LoadFileNInfo(frame);
        try {
            PDDocument document = PDDocument.load(new File(fileDirName));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            pbar.setValue(MY_MINIMUM + 30);
            byte[] fileToSign = byteArrayOutputStream.toByteArray(); // Base64.encodeBase64(byteArrayOutputStream.toByteArray());
            byte[] signedPdf = pdfSigner.genP7s(fileToSign, selectedItem);
            pbar.setValue(MY_MINIMUM + 60);

            FileOutputStream fos = new FileOutputStream(fileDirOutName);
            fos.write(signedPdf);
            pbar.setValue(MY_MINIMUM + 80);
            fos.close();

            pbar.setValue(MY_MAXIMUM);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            comboBoxAction();
        }
        if (e.getSource() == closeButton) {
            closeButtonAction();
        }

        if (e.getSource() == signButton) {
            signButtonAction();
        }
    }

    /**
     * assinacertificado entry point
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {

            // Set the default encoding to UTF-8 - Force pt_BR to show correct Brazilian
            // Portuguese charset
            System.setProperty("file.encoding", "UTF-8");

            InitComponents();
            comboBox.addActionListener(frame);
            closeButton.addActionListener(frame);
            signButton.addActionListener(frame);
            signatureWrapper = new SignatureWrapper(frame);
            signDocumentFromWeb = new SignDocumentFromWeb(frame, args[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 
     */
    private static void InitComponents() {
        try {
            frame = new assinacertificado("Assinador");
            Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 480);
            frame.setLayout(null);

            loadCertificates = new LoadCertificates(frame);
            // LoadCertificates.load();
            items = LoadCertificates.getCertificateNames().toArray(new String[0]);

            mapCertificates = zipToMap(LoadCertificates.getCertificateNames(), LoadCertificates.getCertificates());

            comboBox = new JComboBox<>(items);
            closeButton = new JButton("Retornar");
            signButton = new JButton("Assinar");

            label = new JLabel("lblAssinador");
            label.setText("Assinador");
            textArea = new JTextArea();
            textArea.setText("log stack");
            // While not using WebService...
            // c:\temp\entrada.pdf
            // c:\temp\entrada.pdf.p7s <-- fazer um loop para assinar 3 vezes - mesmo
            // certificado
            // CAdES, XADeS e PADeS --
            // 1 arquivo
            fullFilePath = "c:\\temp\\entrada.pdf";

            fullFilePath2 = "c:\\temp\\entrada-assinado.pdf";

            fullFilePathDoc = "c:\\temp\\Entrada.doc";
            fullFilePathSecond = "c:\\temp\\entrada.pdf.p7s";

            // certificates = LoadCertificates.getCertificates().toArray(new
            // Certificate[0]);

            // items = (String[]) certLstProto.toArray();
            // Set bounds for components
            label.setBounds(20, 45, 100, 30);
            frame.add(label);

            comboBox.setBounds(20, 85, 350, 30);
            frame.add(comboBox);

            closeButton.setBounds(500, 390, 100, 30);
            frame.add(closeButton);

            signButton.setBounds(380, 85, 80, 30);
            frame.add(signButton);

            textArea.setBounds(20, 145, 550, 100); // x, y, width, height
            textArea.setRows(15);
            textArea.setColumns(65);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            sp = new JScrollPane(textArea);

            sp.setBounds(20, 145, 550, 100);
            sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            frame.getContentPane().add(sp);
            // frame.add(statusBar, BorderLayout.SOUTH);

            // initialize Progress Bar
            pbar = new JProgressBar();
            pbar.setMinimum(MY_MINIMUM);
            pbar.setMaximum(MY_MAXIMUM);
            pbar.setBackground(Color.LIGHT_GRAY);
            pbar.setForeground(Color.GREEN);
            pbar.setBounds(20, 390, 480, 30);
            pbar.setBorder(border);
            pbar.setValue(MY_MINIMUM);
            // add to JPanel
            frame.add(pbar);

            frame.setLocationRelativeTo(null); // This line centers the frame
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * frontend level updates
     * 
     * @param status
     */
    private static void localUpdate(String status) {

        String originalStr = textArea.getText();
        textArea.setText(originalStr + "\n" + status + " ] ");
        // statusBar.setText(status);

    }

    /**
     * backend level updates
     * 
     * @param value
     */
    // @Override
    public void update_status(int value) {

        try {
            pbar.setValue(value);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    @Override
    public void update(String status) {
        //
        // if(pbar.getValue()<MY_MAXIMUM){
        localUpdate(status);
        // update_status(int value)
        // }
    }

}