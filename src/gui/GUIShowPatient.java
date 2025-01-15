/**
 * Klasse repräsentiert die GUI für die Anzeige und Bearbeitung von Patientendaten
 * Umfasst Funktionen zum Speichern, Löschen und Bearbeiten der Patientendaten
 */
package gui;

import beans.Bundesland;
import beans.Patient;
import bl.*;
import beans.Geschlecht;
import beans.Krankenkasse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;


public class GUIShowPatient {
    /**
     * Hauptfenster der GUI
     */
    private static JFrame frame;
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

    /**
     * Konstruktor zur Initialisierung der GUI und ihrer Komponenten
     */
    public GUIShowPatient() {
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Bist du sicher, dass du löschen möchtest?",
                        "Bestätigung",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    DAOPatient.deletePatientAsync(patient.getPatientID()).join();
                    try {
                        gui.getAllPatientsFromDatabase();
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    gui.setTableModel();
                    frame.dispose();
                }


            }
        });
        btBefunde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIBefunde showBefunde = new GUIBefunde();
                try {
                    showBefunde.showBefunde(patient);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int geschlechtId = -1;
                int krankenkasseId = -1;

                try {
                    for (Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechterAsync().get()) {
                        if (geschlecht.getBezeichnung().equals(cbGeschlecht.getSelectedItem().toString())) {
                            geschlechtId = geschlecht.getGeschlechtID();
                        }
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    for (Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassenAsync().get()) {
                        if (krankenkasse.getBezeichnung().equals(cbKrankenkasse.getSelectedItem().toString())) {
                            krankenkasseId = krankenkasse.getKrankenkasseID();
                        }
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }

                int bundeslandID = -1;

                try {
                    for (Bundesland bundesland : DAOBundesland.getAllBundeslaenderAsync().get()) {
                        if (bundesland.getBezeichnung().equals(tfBundesland.getText())) {
                            bundeslandID = bundesland.getBundeslandID();
                        }
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }

                if (bundeslandID == -1) {
                    try {
                        DAOBundesland.addBundeslandAsync(tfBundesland.getText()).get();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        for (Bundesland bundesland : DAOBundesland.getAllBundeslaenderAsync().get()) {
                            if (bundesland.getBezeichnung().equals(tfBundesland.getText())) {
                                bundeslandID = bundesland.getBundeslandID();
                            }
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                }



                try {
                    DAOPatient.updatePatientAsync(Integer.parseInt(tfID.getText()), tfVorname.getText(), tfNachname.getText(), cbAnrede.getSelectedItem().toString(), tfGeburtsdatum.getText(), tfStrasse.getText(), tfPlz.getText(), tfOrt.getText(), bundeslandID, tfTelefonnummer.getText(), geschlechtId, krankenkasseId, tfAnmerkung.getText());

                } catch (Exception ex) {
                    DAOPatient.updatePatientAsync(Integer.parseInt(tfID.getText()), tfVorname.getText(), tfNachname.getText(), cbAnrede.getSelectedItem().toString(), null, tfStrasse.getText(), tfPlz.getText(), tfOrt.getText(), bundeslandID, tfTelefonnummer.getText(), geschlechtId, krankenkasseId, tfAnmerkung.getText());
                }


                try {
                    gui.getAllPatientsFromDatabase();
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                gui.setTableModel();
                frame.dispose();

            }
        });
    }

    /**
     * Öffnet GUI zur Bearbeitung der Daten des angegebenen Patienten
     *
     * @param patient Patient, dessen Daten bearbeitet werden sollen
     * @param gui     Haupt-GUI-Instanz
     */
    public void editPatient(Patient patient, GUI gui) throws ExecutionException, InterruptedException {
        this.patient = patient;
        this.gui = gui;

        frame = new JFrame("Patient bearbeiten");
        frame.setContentPane(editPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        setTextFields();
    }

    /**
     * Füllt Eingabefelder mit aktuellen Daten des Patienten
     */
    public void setTextFields() throws ExecutionException, InterruptedException {
        tfID.setText(String.valueOf(patient.getPatientID()));
        tfVorname.setText(patient.getVorname());
        tfNachname.setText(patient.getNachname());
        tfStrasse.setText(patient.getStrasse());
        tfOrt.setText(patient.getOrt());
        tfPlz.setText(patient.getPlz());
        tfTelefonnummer.setText(patient.getTelefon());
        tfAnmerkung.setText(patient.getSonstiges());
        if (patient.getGeburtsdatum() != null)
            tfGeburtsdatum.setText(patient.getGeburtsdatum().toString());

        for (Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechterAsync().get()) {
            cbGeschlecht.addItem(geschlecht.getBezeichnung());

            if (geschlecht.getGeschlechtID() == patient.getGeschlechtID())
                cbGeschlecht.setSelectedItem(geschlecht.getBezeichnung());
        }

        for (Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassenAsync().get()) {
            cbKrankenkasse.addItem(krankenkasse.getBezeichnung());

            if (krankenkasse.getKrankenkasseID() == patient.getKrankenkasseID())
                cbKrankenkasse.setSelectedItem(krankenkasse.getBezeichnung());
        }

        for (Bundesland bundesland : DAOBundesland.getAllBundeslaenderAsync().get()) {
            if (bundesland.getBundeslandID() == patient.getBundeslandID())
                tfBundesland.setText(bundesland.getBezeichnung());
        }

        cbAnrede.setSelectedItem(patient.getAnrede());
    }

}

