package gui;

import beans.Bundesland;
import beans.Patient;
import bl.*;
import beans.Geschlecht;
import beans.Krankenkasse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GUIShowPatient {
    private static JFrame frame;
    private JLabel Geschlecht;
    private JComboBox cbAnrede;
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
    private JComboBox cbGeschlecht;
    private JComboBox cbKrankenkasse;
    private JTextField tfBundesland;
    private GUI gui;
    private Patient patient;

    public GUIShowPatient() {
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Bist du sicher, dass du löschen möchtest?", // Nachricht
                        "Bestätigung", // Titel
                        JOptionPane.YES_NO_OPTION, // Optionen (Ja/Nein)
                        JOptionPane.WARNING_MESSAGE // Symbol (Warnung)
                );

                // Reaktion auf die Auswahl
                if (result == JOptionPane.YES_OPTION) {
                    DAOPatient.deletePatient(patient.getPatientID());
                    gui.getAllPatientsFromDatabase();
                    gui.setTableModel();
                    frame.dispose();
                }


            }
        });
        btBefunde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIBefunde showBefunde = new GUIBefunde();
                showBefunde.showBefunde(patient);
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int bundeslandId = -1;
                int geschlechtId = -1;
                int krankenkasseId = -1;

                for(Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechter())
                {
                    if(geschlecht.getBezeichnung().equals(cbGeschlecht.getSelectedItem().toString()))
                    {
                        geschlechtId = geschlecht.getGeschlechtID();
                    }
                }

                for(Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassen())
                {
                    if(krankenkasse.getBezeichnung().equals(cbKrankenkasse.getSelectedItem().toString()))
                    {
                        krankenkasseId = krankenkasse.getKrankenkasseID();
                    }
                }

                int bundeslandID = -1;

                for(Bundesland bundesland : DAOBundesland.getAllBundeslaender())
                {
                    if(bundesland.getBezeichnung().equals(tfBundesland.getText()))
                    {
                        bundeslandID = bundesland.getBundeslandID();
                    }
                }

                if(bundeslandID == -1)
                {
                    DAOBundesland.addBundesland(tfBundesland.getText());
                    for(Bundesland bundesland : DAOBundesland.getAllBundeslaender())
                    {
                        if(bundesland.getBezeichnung().equals(tfBundesland.getText()))
                        {
                            bundeslandID = bundesland.getBundeslandID();
                        }
                    }
                }


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try
                {
                    DAOPatient.updatePatient(Integer.parseInt(tfID.getText()),tfVorname.getText(),tfNachname.getText(),cbAnrede.getSelectedItem().toString(),tfGeburtsdatum.getText(),tfStrasse.getText(),tfPlz.getText(),tfOrt.getText(),bundeslandID,tfTelefonnummer.getText(),geschlechtId,krankenkasseId,tfAnmerkung.getText());

                }
                catch (Exception ex)
                {
                    DAOPatient.updatePatient(Integer.parseInt(tfID.getText()),tfVorname.getText(),tfNachname.getText(),cbAnrede.getSelectedItem().toString(),null,tfStrasse.getText(),tfPlz.getText(),tfOrt.getText(),bundeslandID,tfTelefonnummer.getText(),geschlechtId,krankenkasseId,tfAnmerkung.getText());
                }


                gui.getAllPatientsFromDatabase();
                gui.setTableModel();
                frame.dispose();

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
        if(patient.getGeburtsdatum() != null)
        tfGeburtsdatum.setText(patient.getGeburtsdatum().toString());

        for(Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechter())
        {
            cbGeschlecht.addItem(geschlecht.getBezeichnung());

            if(geschlecht.getGeschlechtID() == patient.getGeschlechtID())
                cbGeschlecht.setSelectedItem(geschlecht.getBezeichnung());
        }

        for(Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassen())
        {
            cbKrankenkasse.addItem(krankenkasse.getBezeichnung());

            if(krankenkasse.getKrankenkasseID() == patient.getKrankenkasseID())
                cbKrankenkasse.setSelectedItem(krankenkasse.getBezeichnung());
        }

        for(Bundesland bundesland : DAOBundesland.getAllBundeslaender())
        {
            if(bundesland.getBundeslandID() == patient.getBundeslandID())
                tfBundesland.setText(bundesland.getBezeichnung());
        }

        cbAnrede.setSelectedItem(patient.getAnrede());
    }

}

