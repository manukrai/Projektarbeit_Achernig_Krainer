package gui;

import beans.Patient;
import bl.DAOPatient;
import bl.DBAccess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GUI extends JFrame
{
    private JPanel jPanel;
    private JButton btAdd;
    private JButton btDelete;
    private JTable patientTable;
    private JTextField tfSearch;
    private JButton btSearch;
    private JButton btSortieren;
    private JButton btAddBefund;
    private List<Patient>  patients;


    public GUI() {
        DBAccess.connect();
        getAllPatientsFromDatabase();
        setTableModel();


        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIAddPatient.showFrame();

                getAllPatientsFromDatabase();
                setTableModel();
            }
        });
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] indexes = patientTable.getSelectedRows();

                if(indexes != null && indexes.length > 0) {
                    for (int index : indexes) {
                        DAOPatient.deletePatient((Integer) patientTable.getValueAt(index,0));
                    }
                    getAllPatientsFromDatabase();
                    setTableModel();
                }
            }
        });
        btSortieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(patients);
                setTableModel();
            }
        });
    }

    public void getAllPatientsFromDatabase()
    {
        DAOPatient dao = new DAOPatient();

        patients = dao.getAllPatients();
    }

    public void setTableModel()
    {
        DefaultTableModel model = new DefaultTableModel(new String []{"ID","Anrede","Vorname","Nachname","Geburtstag","Straße","PLZ","Ort","Telefonnummer","Anmerkung"},0){

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        patientTable.setModel(model);

        for(Patient patient : patients)
        {
            model.addRow(new Object[]{patient.getPatientID(),patient.getAnrede(),patient.getVorname(),patient.getNachname(),patient.getGeburtsdatum(),patient.getStrasse(),patient.getPlz(),patient.getOrt(),patient.getTelefon(),patient.getSonstiges()});
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Patient Managment System");
        frame.setMinimumSize(new Dimension(1000,200));
        frame.setContentPane(new GUI().jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
