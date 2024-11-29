package com.assinador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * JavaFX App
 */
public class App extends JFrame implements ActionListener {

    private static App frame;
    private static JLabel label;
    private static JComboBox<String> comboBox;
    private static JButton closeButton;
    private static JButton signButton;
    private static JTextArea textArea;
    static JScrollPane sp;
    private static String[] items; // = { "Item 1", "Item 2", "Item 3" };
    //private static Certificate[] certificates;
    private static Map<String,Certificate> mapCertificates;

    private static String fullFilePath;
    private static String fullFilePathSecond;
        
// private static LoadCertificates loadCertificates;
/*
* public static void main(String[] args) {
* launch();
* }
*/
        
    public App(String title) {
            super(title);
            // setLayout(new FlowLayout());
            // addWindowListener(this);
            comboBox = new JComboBox<>(items);
            comboBox.addActionListener(this);
            closeButton = new JButton("Retornar");
            signButton =  new JButton("Assinar");
            closeButton.addActionListener(this);
            signButton.addActionListener(this);
            label = new JLabel("lblAssinador");
            label.setText("Assinador");
            textArea = new JTextArea();
            textArea.setText("log stack");        
    
        }
        
        private static Map<String, Certificate> zipToMap(List<String> keys, List<Certificate> values) {
                
            Map<String,Certificate> mapCertif = new HashMap<String, Certificate>();
            String[] arrKeys =  keys.toArray(new String[0]);
            Certificate[] arrValues =  values.toArray(new Certificate[0]);
            for(int i=0; i< arrKeys.length; i++){
                        mapCertif.put(arrKeys[i], arrValues[i]);}
            return mapCertif;
        }

        private static void comboBoxAction() {
    
            String selectedItem = (String) comboBox.getSelectedItem();
            String originalStr = textArea.getText();
            textArea.setText(originalStr + "\n" + selectedItem);
        }
        
        private static void closeButtonAction() {
            frame.dispose();
            System.exit(0);
        }
    
        private static void signButtonAction() {
            String selectedItem = (String) comboBox.getSelectedItem();
            String originalStr = textArea.getText();
    
            textArea.setText(originalStr + "\n"+"Assinar documento com o certificado [ "+selectedItem+" ] \n");

            //JOptionPane.showMessageDialog(null, "Assinar documento com o certificado [ "+selectedItem+" ] " , "Assinar",
            //JOptionPane.INFORMATION_MESSAGE);

            Certificate certificate = mapCertificates.get(selectedItem);

            textArea.setText(certificate.toString() + "\n");

            SignatureWrapper signatureWrapper = new SignatureWrapper();

            signatureWrapper.SignnWithPolicy(certificate, prvKey, fullFilePath); // vem do webservice - pro teste 

            signatureWrapper.SignnWithPolicy(certificate, prvKey, fullFilePath); // vem do webservice - segunda assinatura

            signatureWrapper.SignnWithPolicy(certificate, prvKey, fullFilePath); // vem do webservice - terceira assinatura


            //  c:\temp\entrada.pdf
            //  c:\temp\entrada.pdf.p7s  <-- fazer um loop para assinar 3 vezes - mesmo certificado
            // CAdES, XADeS e PADeS --  
            // 1 arquivo
            

        }
        
        
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
        
            public static void main(String[] args) {
                try {
        
                    // While not using WebService...
                    //  c:\temp\entrada.pdf
                    //  c:\temp\entrada.pdf.p7s  <-- fazer um loop para assinar 3 vezes - mesmo certificado
                    // CAdES, XADeS e PADeS --  
                    // 1 arquivo                
                    fullFilePath = "c:\\temp\\entrada.pdf";
                fullFilePathSecond ="c:\\temp\\entrada.pdf.p7s";

                // loadCertificates = new LoadCertificates();
                LoadCertificates.load();
                items  = LoadCertificates.getCertificateNames().toArray(new String[0]);

                mapCertificates = zipToMap(LoadCertificates.getCertificateNames(), LoadCertificates.getCertificates());

                //certificates  = LoadCertificates.getCertificates().toArray(new Certificate[0]);
    

                //items = (String[]) certLstProto.toArray();
                frame = new App("Assinador");
    
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(640, 480);
                frame.setLayout(null);
    
                // Set bounds for components
                label.setBounds(20, 45, 100, 30);
                frame.add(label);
    
                comboBox.setBounds(20, 85, 350, 30);
                frame.add(comboBox);
    
                closeButton.setBounds(500, 400, 100, 30);
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

                frame.setLocationRelativeTo(null); // This line centers the frame
                frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

  
}