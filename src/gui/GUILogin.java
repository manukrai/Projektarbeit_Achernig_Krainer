package gui;

import accountManagment.AccountManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasse implementiert die grafische Benutzeroberfläche für Login eines Patient Management Systems
 * Benutzer können ihre Anmeldedaten eingeben um Zugriff auf die Hauptanwendung zu erhalten
 * Bei flaschen Eingaben wird Fehlermeldung angezeigt
 */
public class GUILogin extends JFrame {
    private static JFrame frame;
    private JButton btConnect;
    private JPanel loginPanel;
    private JTextField textUsername;
    private JPasswordField textPasswort;


    /**
     * Einstiegspunkt für die Anwendung
     * Startet Login-Fenster und initialisiert GUI
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        frame = new JFrame("Patient Managment System");
        frame.setContentPane(new GUILogin().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    /**
     * Konstruktor für Klasse guiLogin
     * Initialisiert GUI-Komponenten, setzt Bild im Fenster und fügt Login:Logik hinzu
     */
    public GUILogin() {

        btConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AccountManager accountManager = new AccountManager();

                if (accountManager.login(textUsername.getText(), textPasswort.getText())) {

                    GUI gui = new GUI();

                    gui.showPanel();

                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Falscher Username oder Passwort!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }
}
