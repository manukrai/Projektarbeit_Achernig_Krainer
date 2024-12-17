package gui;

import beans.Patient;

import javax.swing.*;

public class GUIShowPatient {
    private JLabel Geschlecht;
    private JComboBox cbGeschlecht;
    private JButton btCancel;
    private JTextField tfVorname;
    private JTextField tfNachname;
    private JTextField tfGeburtsdatum;
    private JTextField tfStrasse;
    private JTextField tfOrt;
    private JTextField tfPlz;
    private JTextField tfTelefonnummer;
    private JTextField tfAnmerkung;
    private JButton btSave;

    public void editPatient(Patient patient)
    {
        tfVorname.setText(patient.getVorname());
        tfNachname.setText(patient.getNachname());
        tfStrasse.setText(patient.getStrasse());
        tfOrt.setText(patient.getOrt());
        tfPlz.setText(patient.getOrt());
        tfTelefonnummer.setText(patient.getTelefon());
        tfAnmerkung.setText(patient.getSonstiges());

}

