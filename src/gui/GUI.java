package gui;

import beans.Patient;
import bl.DAOPatient;
import bl.DBAccess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JPanel panelHeader;
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
        btSortieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(patients);
                setTableModel();
            }
        });
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(e.getClickCount() == 2 && patientTable.getSelectedRow() != -1) {
                    int patientID = (Integer) patientTable.getValueAt(patientTable.getSelectedRow(),0);

                    System.out.println(patientID);

                    GUIShowPatient showPatient = new GUIShowPatient();

                    for (Patient patient : patients) {
                        if(patient.getPatientID() == patientID) {
                            showPatient.editPatient(patient);
                        }
                    }
                }

                
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
        DefaultTableModel model = new DefaultTableModel(null,new String []{"ID","Anrede","Vorname","Nachname","Geburtstag","Straße","PLZ","Ort","Telefonnummer","Anmerkung"}){

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        patientTable.setModel(model);

        JTableHeader header = patientTable.getTableHeader();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        panelHeader.add(header);


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
