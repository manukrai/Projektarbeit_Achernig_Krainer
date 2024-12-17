package gui;

import beans.Patient;
import bl.DAOPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIShowPatient {
    private static JFrame frame;
    private JLabel Geschlecht;
    private JComboBox cbGeschlecht;
    private JTextField tfVorname;
    private JTextField tfNachname;
    private JTextField tfGeburtsdatum;
    private JTextField tfStrasse;
    private JTextField tfOrt;
    private JTextField tfPlz;
    private JTextField tfTelefonnummer;
    private JTextField tfAnmerkung;
    private JButton btSave;
    private JButton btDelete;
    private JTextField tfID;
    private JPanel editPanel;
    private JButton btBefunde;

    public GUIShowPatient() {
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAOPatient.deletePatient(91);
                frame.dispose();
            }
        });
        btBefunde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIBefunde showBefunde = new GUIBefunde();
                showBefunde.showBefunde(null);
            }
        });
    }


    public void editPatient(Patient patient) {

        frame = new JFrame("Patient bearbeiten");
        frame.setContentPane(new GUIShowPatient().editPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        setTextFields(patient);

    }

    public void setTextFields(Patient patient)
    {
        tfID.setText(String.valueOf(patient.getPatientID()));
        tfVorname.setText(patient.getVorname());
        tfNachname.setText(patient.getNachname());
        tfStrasse.setText(patient.getStrasse());
        tfOrt.setText(patient.getOrt());
        tfPlz.setText(patient.getPlz());
        tfTelefonnummer.setText(patient.getTelefon());
        tfAnmerkung.setText(patient.getSonstiges());
    }

}

