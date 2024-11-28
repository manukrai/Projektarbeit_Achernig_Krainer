package gui;

import beans.Patient;
import bl.DAOPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame
{
    private JPanel jPanel;
    private JButton button1;
    private JTextArea textTest;


    public GUI() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAOPatient dao = new DAOPatient();

                List<Patient> patients = dao.getAllPatients();

                for(Patient patient : patients)
                {
                    textTest.append(patient.getVorname() + " " + patient.getNachname());
                }
            }
        });
    }

    public static void main(String[] args) {

    }

    public static void showGUI()
    {
        JFrame frame = new JFrame("PatientDB login");
        frame.setContentPane(new GUI().jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
