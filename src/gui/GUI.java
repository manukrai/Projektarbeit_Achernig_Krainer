package gui;

import beans.Patient;
import bl.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUI extends JFrame
{
    private static JFrame frame;
    private JPanel jPanel;
    private JTable patientTable;
    private JTextField tfSearch;
    private JPanel panelHeader;
    private JPanel panelTable;
    private JButton btAddPatient;
    private List<Patient>  patients;
    TableRowSorter<TableModel> sorter;

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



                        int patientID = (Integer) patientTable.getValueAt(patientTable.getSelectedRow(),0);

                        GUIShowPatient showPatient = new GUIShowPatient();

                        for (Patient patient : patients) {
                            if(patient.getPatientID() == patientID) {
                                showPatient.editPatient(patient,GUI.this);
                            }
                        }



                }

                
            }
        });
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyPressed(e);

                String searchText = tfSearch.getText();
                if (searchText.trim().length() == 0) {
                    sorter.setRowFilter(null); // Filter zurücksetzen
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Filter anwenden
                }
            }
        });
        btAddPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIAddPatient guiAddPatient = new GUIAddPatient();
                guiAddPatient.showFrame(GUI.this);

                getAllPatientsFromDatabase();
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
        setPlaceholder(tfSearch,"Suche");

        DefaultTableModel model = new DefaultTableModel(null,new String []{"ID","Anrede","Vorname","Nachname"}){

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };


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


        patientTable.setModel(model);

        sorter = new TableRowSorter<>(patientTable.getModel());
        patientTable.setRowSorter(sorter);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Patient Managment System");
        frame.setMinimumSize(new Dimension(1000,200));
        frame.setContentPane(new GUI().jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public static void setPlaceholder(JTextField textField, String placeholder) {
        // Placeholder-Farbe
        Color placeholderColor = Color.GRAY;
        Color textColor = Color.BLACK;

        // Standardtext und Farbe setzen
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);

        // FocusListener hinzufügen
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Wenn der aktuelle Text der Placeholder ist -> löschen
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Wenn das Textfeld leer ist -> Placeholder anzeigen
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }

}
