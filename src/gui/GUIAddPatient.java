package gui;

import beans.Bundesland;
import beans.Geschlecht;
import beans.Krankenkasse;
import beans.Patient;
import bl.DAOBundesland;
import bl.DAOGeschlecht;
import bl.DAOKrankenkasse;
import bl.DAOPatient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasse implementiert GUI fpr das Hinzufügen eines neuen Patienten
 * Benutzer kann über Textfelder und Auswahlboxen die Patientendaten eingeben und speichern
 */
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
    private JComboBox cbAnrede;
    private JPanel addPanel;
    private JTextField tfOrt;
    private JComboBox cbGeschlecht;
    private JTextField tfBundesland;
    private JComboBox cbKrankenkasse;
    private GUI gui;

    /**
     * Konstruktor fügt ActionListener für den Hinzufügen- Button hinzu, um Patienten zu erfassen und zu speichern
     */
    public GUIAddPatient() {
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Patient newPatient = new Patient();

                newPatient.setVorname(tfVorname.getText());
                newPatient.setNachname(tfNachname.getText());
                newPatient.setAnrede(cbAnrede.getSelectedItem().toString());
                newPatient.setStrasse(tfStrasse.getText());
                newPatient.setPlz(tfPlz.getText());
                newPatient.setOrt(tfOrt.getText());
                newPatient.setTelefon(tfTelefonnummer.getText());
                newPatient.setKrankenkasseID(2);
                newPatient.setSonstiges(tfAnmerkung.getText());

                try {
                    newPatient.setGeburtsdatum(tfGeburtsdatum.getText());
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Datums Format nicht passend!");
                    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
                    logger.setLevel(Level.ALL);
                    logger.warning("Falsches Datums Format!");
                }

                List<Geschlecht> geschlechtList = null;
                try {
                    geschlechtList = DAOGeschlecht.getAllGeschlechterAsync().get();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }

                for (Geschlecht geschlecht : geschlechtList) {
                    if (geschlecht.getBezeichnung().equals(cbGeschlecht.getSelectedItem().toString())) {
                        newPatient.setGeschlechtID(geschlecht.getGeschlechtID());
                    }
                }

                try {
                    for (Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassenAsync().get()) {
                        if (krankenkasse.getBezeichnung().equals(cbKrankenkasse.getSelectedItem().toString())) {
                            newPatient.setKrankenkasseID(krankenkasse.getKrankenkasseID());
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

                newPatient.setBundeslandID(bundeslandID);

                DAOPatient.addPatientAsync(newPatient).join();

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
     * Zeigt das Fenster zum Hinzufügen eines Patienten an
     *
     * @param gui Referenz zur Haupt-GUI, um die Patiententabelle nach dem Hinzufügen zu aktualisieren.
     */
    public void showFrame(GUI gui) throws ExecutionException, InterruptedException {
        this.gui = gui;

        frame = new JFrame("Patient hinzufügen");
        frame.setContentPane(addPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        for (Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechterAsync().get()) {
            cbGeschlecht.addItem(geschlecht.getBezeichnung());
        }

        for (Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassenAsync().get()) {
            cbKrankenkasse.addItem(krankenkasse.getBezeichnung());
        }

        setPlaceholder(tfGeburtsdatum, "yyyy-mm-dd");
    }

    /**
     * Setzt einen Placeholder-Text in ein JTextField
     * Placeholder-Text wird angezeigt, solange das Textfeld leer ist
     *
     * @param textField   Das JText-Field, in dem der Placeholder angezeigt werden soll
     * @param placeholder Der Placeholder-Text
     */
    public static void setPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = Color.GRAY;
        Color textColor = Color.BLACK;

        textField.setText(placeholder);
        textField.setForeground(placeholderColor);


        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }
}
