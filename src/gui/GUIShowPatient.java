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
    private GUI gui;
    private Patient patient;

    public GUIShowPatient() {
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAOPatient.deletePatient(patient.getPatientID());
                gui.getAllPatientsFromDatabase();
                gui.setTableModel();
                frame.dispose();
            }
        });
        btBefunde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIBefunde showBefunde = new GUIBefunde();
                showBefunde.showBefunde(patient);
            }
        });
    }


    public void editPatient(Patient patient,GUI gui) {
        this.patient = patient;
        this.gui = gui;

        frame = new JFrame("Patient bearbeiten");
        frame.setContentPane(editPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        setTextFields();
    }

    public void setTextFields()
    {
        tfID.setText(String.valueOf(patient.getPatientID()));
        tfVorname.setText(patient.getVorname());
        tfNachname.setText(patient.getNachname());
        tfStrasse.setText(patient.getStrasse());
        tfOrt.setText(patient.getOrt());
        tfPlz.setText(patient.getPlz());
        tfTelefonnummer.setText(patient.getTelefon());
        tfAnmerkung.setText(patient.getSonstiges());
        tfGeburtsdatum.setText(patient.getGeburtsdatum().toString());
    }

}

