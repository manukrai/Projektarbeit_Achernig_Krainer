package gui;

import beans.Bundesland;
import beans.Geschlecht;
import beans.Krankenkasse;
import beans.Patient;
import bl.*;

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
    private static JFrame frame;
    private JPanel jPanel;
    private JTable patientTable;
    private JTextField tfSearch;
    private JPanel panelHeader;
    private JPanel panelTable;
    private List<Patient>  patients;

    public void showPanel()
    {

        frame = new JFrame("Patient Management System");
        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        DBAccess.connect();
        getAllPatientsFromDatabase();
        setTableModel();
    }


    public GUI() {
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(e.getClickCount() == 2 && patientTable.getSelectedRow() != -1) {

                    if(patientTable.getValueAt(patientTable.getSelectedRow(),0) == "...") {
                        GUIAddPatient guiAddPatient = new GUIAddPatient();
                        guiAddPatient.showFrame(GUI.this);

                        getAllPatientsFromDatabase();
                        setTableModel();
                    }
                    else
                    {
                        int patientID = (Integer) patientTable.getValueAt(patientTable.getSelectedRow(),0);

                        GUIShowPatient showPatient = new GUIShowPatient();

                        for (Patient patient : patients) {
                            if(patient.getPatientID() == patientID) {
                                showPatient.editPatient(patient,GUI.this);
                            }
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
        DefaultTableModel model = new DefaultTableModel(null,new String []{"ID","Anrede","Vorname","Nachname"}){

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
        patientTable.getTableHeader().setReorderingAllowed(false);



        for(Patient patient : patients)
        {
            model.addRow(new Object[]{patient.getPatientID(),patient.getAnrede(),patient.getVorname(),patient.getNachname()});
        }

        model.addRow(new Object[]{"...","Patient","hinzufügen","...","..."});
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
