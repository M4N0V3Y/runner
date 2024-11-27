package com.assinador;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import java.io.IOException;

import com.assinador.LoadCertificates;

/**
 * JavaFX App
 */
public class App extends JFrame implements ActionListener {

    private static App frame;
    private static JLabel label;
    private static JComboBox<String> comboBox;
    private static JButton closeButton;
    private static JTextArea textArea;
    private static String[] items; // = { "Item 1", "Item 2", "Item 3" };

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
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        label = new JLabel("lblAssinador");
        label.setText("Assinador");
        textArea = new JTextArea();
        textArea.setText("log stack");

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            comboBoxAction();
        }
        if (e.getSource() == closeButton) {
            closeButtonAction();
        }
    }

    public static void main(String[] args) {
        try {

            // loadCertificates = new LoadCertificates();
            LoadCertificates.load();
            items = (String[]) LoadCertificates.getCertificateNames().toArray();

            frame = new App("Assinador");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 480);
            frame.setLayout(null);

            // Set bounds for components
            label.setBounds(20, 45, 100, 30);
            frame.add(label);

            comboBox.setBounds(20, 85, 100, 30);
            frame.add(comboBox);

            closeButton.setBounds(520, 400, 80, 30);
            frame.add(closeButton);

            textArea.setBounds(20, 145, 200, 100); // x, y, width, height
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            frame.add(textArea);

            frame.setLocationRelativeTo(null); // This line centers the frame
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * public void windowOpened(WindowEvent e) {
     * super.windowOpened(e);
     * };
     * 
     * public void windowActivated(WindowEvent e) {
     * super.windowActivated(e);
     * };
     * 
     * public void windowIconified(WindowEvent e) {
     * super.windowIconified(e);
     * };
     * 
     * public void windowDeiconified(WindowEvent e) {
     * super.windowDeiconifie(e);
     * };
     * 
     * public void windowDeactivated(WindowEvent e) {
     * super.windowDeactivated(e);
     * };
     * 
     * public void windowClosed(WindowEvent e) {
     * super.windowClosed(e);
     * };
     */
}