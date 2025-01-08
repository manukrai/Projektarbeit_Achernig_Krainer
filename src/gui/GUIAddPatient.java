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
            public void actionPerformed(ActionEvent e)
            {
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

                try
                {
                    newPatient.setGeburtsdatum(tfGeburtsdatum.getText());
                }
                catch(DateTimeParseException ex)
                {
                    JOptionPane.showMessageDialog(frame, "Datums Format nicht passend!");
                }

                List<Geschlecht> geschlechtList = DAOGeschlecht.getAllGeschlechter();

                for(Geschlecht geschlecht : geschlechtList)
                {
                    if(geschlecht.getBezeichnung().equals(cbGeschlecht.getSelectedItem().toString()))
                    {
                        newPatient.setGeschlechtID(geschlecht.getGeschlechtID());
                    }
                }

                for(Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassen())
                {
                    if(krankenkasse.getBezeichnung().equals(cbKrankenkasse.getSelectedItem().toString()))
                    {
                        newPatient.setKrankenkasseID(krankenkasse.getKrankenkasseID());
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

                newPatient.setBundeslandID(bundeslandID);

                DAOPatient.addPatient(newPatient);

                gui.getAllPatientsFromDatabase();
                gui.setTableModel();
                frame.dispose();
            }
        });
    }

    /**
     * Zeigt das Fenster zum Hinzufügen eines Patienten an
     * @param gui Referenz zur Haupt-GUI, um die Patiententabelle nach dem Hinzufügen zu aktualisieren.
     */
    public void showFrame(GUI gui)
    {
        this.gui = gui;

        frame = new JFrame("Patient hinzufügen");
        frame.setContentPane(addPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



        for(Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechter())
        {
            cbGeschlecht.addItem(geschlecht.getBezeichnung());
        }

        for(Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassen())
        {
            cbKrankenkasse.addItem(krankenkasse.getBezeichnung());
        }

        setPlaceholder(tfGeburtsdatum,"yyyy-mm-dd");
    }

    /**
     * Setzt einen Placeholder-Text in ein JTextField
     * Placeholder-Text wird angezeigt, solange das Textfeld leer ist
     * @param textField Das JText-Field, in dem der Placeholder angezeigt werden soll
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
