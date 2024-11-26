package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class guiLogin {
    private JButton btConnect;
    private JPanel loginPanel;
    private JButton btCancel;
    private JTextField textUsername;
    private JPasswordField textPasswort;

    public static void main(String[] args) {
        JFrame frame = new JFrame("PatientDB login");
        frame.setContentPane(new guiLogin().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public guiLogin() {
        btConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:postgresql://localhost:5432/Patient";
                String user = textUsername.getText();
                String password = textPasswort.getText();

                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                }

                try {
                    Connection connection = DriverManager.getConnection(url, user, password);

                    if(connection != null) {
                        System.out.println("Connected to PostgreSQL database");
                    }

                } catch (SQLException ex) {
                    System.out.println(ex);
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
