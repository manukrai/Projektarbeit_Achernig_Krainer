package gui;

import bl.DBAccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class guiLogin extends JFrame
{
    private static JFrame frame;
    private JButton btConnect;
    private JPanel loginPanel;
    private JButton btCancel;
    private JTextField textUsername;
    private JPasswordField textPasswort;

    public static void main(String[] args) {
        frame = new JFrame("PatientDB login");
        frame.setContentPane(new guiLogin().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public guiLogin() {
        btConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DBAccess db = DBAccess.getInstance();

                db.setUser(textUsername.getText());
                db.setPassword(textPasswort.getText());

                db.connect();

                if(DBAccess.connection != null)
                {
                    GUI secondForm = new GUI();
                    secondForm.setVisible(true);
                    frame.dispose();
                }

            }
        });
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
