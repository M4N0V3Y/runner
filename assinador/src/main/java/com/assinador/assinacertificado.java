package com.assinador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Color;

//import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private static JLabel statusBar;
    private static JComboBox<String> comboBox;
    private static JButton closeButton;
    private static JButton signButton;
    private static JTextArea textArea;
    static JScrollPane sp;
    private static String[] items; // = { "Item 1", "Item 2", "Item 3" };
    // private static Certificate[] certificates;
    private static Map<String, Certificate> mapCertificates;

    private static String fullFilePath;
    private static String fullFilePathDoc;
    private static String fullFilePathSecond;
    private static LoadCertificates loadCertificates;
    private static SignatureWrapper signatureWrapper;
    private static SignDocumentFromWeb signDocumentFromWeb;
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

        Certificate[] certificates = LoadCertificates.getCertificateChain(selectedItem);

        char[] pswString = null;

        PrivateKey prvKey = LoadCertificates.getPrivateKey(selectedItem, pswString);

        if (certificates != null && prvKey != null) {
            // textArea.setText(certificate.toString() + "\n");
            localUpdate("[INFO ⚠ ] - Assinar um documento com o certificado: [" + certificates.toString() + " ]");

            Certificate cert = mapCertificates.get(selectedItem);

            try {
                SignatureWrapper.SignnWithPolicy(cert, certificates, prvKey, fullFilePath); // vem do webservice - pro
                // SignatureWrapper.SignnWithPolicy(cert, certificates, prvKey,
                // fullFilePathDoc); // teste
                // SignatureWrapper.SignnWithPolicy(certificate, prvKey, fullFilePathSecond); //
                // vem do webservice -
                // segunda
                // assinatura
                // SignatureWrapper.SignnWithPolicy(certificate, prvKey, fullFilePathSecond); //
                // vem do webservice -
                // terceira
                // assinatura
                // c:\temp\entrada.pdf
                // c:\temp\entrada.pdf.p7s <-- fazer um loop para assinar 3 vezes - mesmo
                // certificado
                // CAdES, XADeS e PADeS --
                // 1 arquivo
            } catch (Exception e) {

                localUpdate("[EXCEÇÃO ❌] - Erro ao tentar assinar um documento com o certificado: ["
                        + selectedItem + " ]\n Contate o suporte");

            }

        } else {
            localUpdate("[ERROR ❌] - Erro ao tentar assinar um documento com o certificado: [" + selectedItem + " ]");

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
            // setLayout(new FlowLayout());
            // addWindowListener(this);
            loadCertificates = new LoadCertificates(frame);
            // LoadCertificates.load();
            items = LoadCertificates.getCertificateNames().toArray(new String[0]);

            mapCertificates = zipToMap(LoadCertificates.getCertificateNames(), LoadCertificates.getCertificates());

            comboBox = new JComboBox<>(items);
            closeButton = new JButton("Retornar");
            signButton = new JButton("Assinar");

            statusBar = new JLabel("Status: Pronto");
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

            statusBar.setBounds(20, 390, 480, 30);
            statusBar.setBorder(border);
            statusBar.setBackground(Color.cyan);
            statusBar.setForeground(Color.DARK_GRAY);
            statusBar.setBorder(border);

            frame.add(statusBar, BorderLayout.SOUTH);

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
        statusBar.setText(status);

    }

    /**
     * backend level updates
     * 
     * @param status
     */
    @Override
    public void update(String status) {

        try {
            String originalStr = textArea.getText();
            textArea.setText(originalStr + "\n" + status + " ] ");
            statusBar.setText(status);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

}