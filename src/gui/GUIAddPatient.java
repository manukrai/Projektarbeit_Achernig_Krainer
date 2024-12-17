package gui;

import beans.Geschlecht;
import beans.Krankenkasse;
import beans.Patient;
import bl.DAOGeschlecht;
import bl.DAOKrankenkasse;
import bl.DAOPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.List;

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
    private JButton btCancel;
    private JPanel addPanel;
    private JTextField tfOrt;
    private JComboBox cbGeschlecht;
    private JTextField tfBundesland;
    private JComboBox cbKrankenkasse;
    private GUI gui;


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
                newPatient.setBundeslandID(2);
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



        for(Geschlecht geschlecht : DAOGeschlecht.getAllGeschlechter())
        {
            cbGeschlecht.addItem(geschlecht.getBezeichnung());
        }

        for(Krankenkasse krankenkasse : DAOKrankenkasse.getAllKrankenkassen())
        {
            cbKrankenkasse.addItem(krankenkasse.getBezeichnung());
        }
    }
}
