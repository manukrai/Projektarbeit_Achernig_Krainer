package gui;

import beans.Patient;
import bl.DAOPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAddPatient {
    private static JFrame frame;
    private JTextField tfVorname;
    private JTextField tfNachname;
    private JTextField tfGeburtsdatum;
    private JTextField tfStrasse;
    private JTextField tfPlz;
    private JTextField tfTelefonnummer;
    private JTextField tfAnmerkung;
    private JButton btAdd;
    private JComboBox cbGeschlecht;
    private JButton btCancel;
    private JPanel addPanel;
    private JTextField tfOrt;


    public GUIAddPatient() {
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Patient newPatient = new Patient();

                newPatient.setVorname(tfVorname.getText());
                newPatient.setNachname(tfNachname.getText());
                newPatient.setAnrede(cbGeschlecht.getSelectedItem().toString());
                newPatient.setGeburtsdatum(null);
                newPatient.setStrasse(tfStrasse.getText());
                newPatient.setPlz(tfPlz.getText());
                newPatient.setOrt(tfOrt.getText());
                newPatient.setBundeslandID(2);
                newPatient.setTelefon(tfTelefonnummer.getText());
                newPatient.setGeschlechtID(2);
                newPatient.setKrankenkasseID(2);
                newPatient.setSonstiges(tfAnmerkung.getText());


                DAOPatient.addPatient(newPatient);
                frame.dispose();
            }
        });
    }

    public static void showFrame()
    {
        frame = new JFrame("Patient hinzufügen");
        frame.setContentPane(new GUIAddPatient().addPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
