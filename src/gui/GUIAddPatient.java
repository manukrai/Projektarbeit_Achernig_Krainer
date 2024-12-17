package gui;

import beans.Patient;
import bl.DAOPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

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
    private GUI gui;


    public GUIAddPatient() {
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Patient newPatient = new Patient();

                newPatient.setVorname(tfVorname.getText());
                newPatient.setNachname(tfNachname.getText());
                newPatient.setAnrede(cbGeschlecht.getSelectedItem().toString());
                newPatient.setStrasse(tfStrasse.getText());
                newPatient.setPlz(tfPlz.getText());
                newPatient.setOrt(tfOrt.getText());
                newPatient.setBundeslandID(2);
                newPatient.setTelefon(tfTelefonnummer.getText());
                newPatient.setGeschlechtID(2);
                newPatient.setKrankenkasseID(2);
                newPatient.setSonstiges(tfAnmerkung.getText());
                DAOPatient.addPatient(newPatient);

                try
                {
                    newPatient.setGeburtsdatum(tfGeburtsdatum.getText());
                }
                catch(DateTimeParseException ex)
                {
                    JOptionPane.showMessageDialog(frame, "Datums Format nicht passend!");
                }

                DAOPatient.addPatient(newPatient);

                gui.getAllPatientsFromDatabase();
                gui.setTableModel();
                frame.dispose();
            }
        });
    }

    public void showFrame(GUI gui)
    {
        this.gui = gui;

        frame = new JFrame("Patient hinzufügen");
        frame.setContentPane(addPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
