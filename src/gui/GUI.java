package gui;

import beans.Patient;
import bl.DAOPatient;
import bl.DBAccess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame
{
    private JPanel jPanel;
    private JTextArea textTest;
    private JButton addButton;
    private JButton deleteButton;
    private JTable patientTable;


    public GUI() {
        DBAccess.connect();
        getAllPatientsInToTable();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Patient newPatient = new Patient();
                newPatient.setVorname("Julia");
                newPatient.setNachname("Müller");
                newPatient.setAnrede("Frau");
                newPatient.setGeburtsdatum(null);  // Geburtsdatum als LocalDate
                newPatient.setStrasse("Lindenstraße 42");
                newPatient.setPlz("80331");
                newPatient.setOrt("München");
                newPatient.setBundeslandID(2);  // 2 = Baden-Württemberg (angenommene ID aus der Tabelle `bundesland`)
                newPatient.setTelefon("089-9876543");
                newPatient.setGeschlechtID(2);  // 2 = Weiblich (angenommene ID aus der Tabelle `geschlecht`)
                newPatient.setKrankenkasseID(2);  // 2 = AOK Bayern (ID aus der Tabelle `krankenkasse`)
                newPatient.setSonstiges("Keine besonderen Anmerkungen");

                System.out.println(DAOPatient.addPatient(newPatient));
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = patientTable.getSelectedRow();

                if(index != -1) {
                    DAOPatient.deletePatient((Integer) patientTable.getValueAt(index,0));
                    getAllPatientsInToTable();
                }
            }
        });
    }

    public void getAllPatientsInToTable()
    {
        DAOPatient dao = new DAOPatient();

        List<Patient> patients = dao.getAllPatients();
        DefaultTableModel model = new DefaultTableModel(new String []{"ID","Anrede","Vorname","Nachname","Geburtstag","Straße","PLZ","Ort","Telefonnummer"},0){

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        patientTable.setModel(model);

        for(Patient patient : patients)
        {
            model.addRow(new Object[]{patient.getPatientID(),patient.getAnrede(),patient.getVorname(),patient.getNachname(),patient.getGeburtsdatum(),patient.getStrasse(),patient.getPlz(),patient.getOrt(),patient.getTelefon()});
        }

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Nuklean");
        frame.setMinimumSize(new Dimension(1000,200));
        frame.setContentPane(new GUI().jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
