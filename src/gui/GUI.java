package gui;

import javax.swing.*;

public class GUI extends JFrame
{
    private JPanel jPanel;


    public static void main(String[] args) {
        JFrame frame = new JFrame("PatientDB login");
        frame.setContentPane(new GUI().jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }
}
