package gui;

import accountManagment.AccountManager;
import bl.DBAccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class guiLogin extends JFrame
{
    private static JFrame frame;
    private JButton btConnect;
    private JPanel loginPanel;
    private JTextField textUsername;
    private JPasswordField textPasswort;

    public static void main(String[] args) {
        frame = new JFrame("Patient Managment System");
        frame.setContentPane(new guiLogin().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public guiLogin() {
        btConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AccountManager accountManager = new AccountManager();

                if (accountManager.login(textUsername.getText(), textPasswort.getText())) {

                    GUI gui = new GUI();

                    gui.showPanel();

                    frame.dispose();
                } else {
                    System.out.println("Falscher Benutzername oder Passwort.");
                }


            }
        });
    }
}
