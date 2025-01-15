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
import java.util.concurrent.ExecutionException;

/**
 * Die Klasse implementiert das Hauptfenster der Patientenverwaltung
 * Sie enthält eine grafische Benutzeroberfläche zur Anzeige und Verwaltung von Patientenformationen
 */
public class GUI extends JFrame {
    private static JFrame frame;
    private JPanel jPanel;
    private JTable patientTable;
    private JTextField tfSearch;
    private JPanel panelHeader;
    private JPanel panelTable;
    private JButton btAddPatient;
    private List<Patient> patients;
    private TableRowSorter<TableModel> sorter;

    /**
     * Zeigt Hauptfenster der Anwendung
     * Stellt Verbindung zur Datenbank her und lädt alle Patienten in die Tabelle
     */

    public void showPanel() throws ExecutionException, InterruptedException {

        frame = new JFrame("Patient Management System");
        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        DBAccess.connect();
        getAllPatientsFromDatabase();
        setTableModel();
    }

    /**
     * Konstruktor initialisiert dei grafischen Komponenten und fügt Listener für Benutzerinteraktionen hinzu
     */
    public GUI() {
        /**
         * Mouse-Listener für die Tabelle
         */
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2 && patientTable.getSelectedRow() != -1) {


                    int patientID = (Integer) patientTable.getValueAt(patientTable.getSelectedRow(), 0);

                    GUIShowPatient showPatient = new GUIShowPatient();

                    for (Patient patient : patients) {
                        if (patient.getPatientID() == patientID) {
                            try {
                                showPatient.editPatient(patient, GUI.this);
                            } catch (ExecutionException ex) {
                                throw new RuntimeException(ex);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }


                }


            }
        });
        /**
         * Key-Listener für die Suchfunktion im Textfeld
         */
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Überprüfen, ob die gedrückte Taste die Enter-Taste ist
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = tfSearch.getText();
                    if (searchText.trim().length() == 0) {
                        sorter.setRowFilter(null); // Filter zurücksetzen
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Filter anwenden
                    }
                }
            }
        });
        btAddPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIAddPatient guiAddPatient = new GUIAddPatient();
                try {
                    guiAddPatient.showFrame(GUI.this);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    getAllPatientsFromDatabase();
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setTableModel();
            }
        });
    }

    /**
     * Lädt alle Patienten aus der Datenbank in die Patientenliste
     */
    public void getAllPatientsFromDatabase() throws ExecutionException, InterruptedException {
        DAOPatient dao = new DAOPatient();
        patients = dao.getAllPatientsAsync().get();
    }

    /**
     * Setzt das Tabellenmodell für die Patiententabelle und lädt die Patientendaten in die Tabelle
     */
    public void setTableModel() {
        setPlaceholder(tfSearch, "Suche [Enter]");

        DefaultTableModel model = new DefaultTableModel(null, new String[]{"ID", "Anrede", "Vorname", "Nachname"}) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTableHeader header = patientTable.getTableHeader();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        panelHeader.add(header);
        patientTable.getTableHeader().setReorderingAllowed(false);

        for (Patient patient : patients) {
            model.addRow(new Object[]{patient.getPatientID(), patient.getAnrede(), patient.getVorname(), patient.getNachname()});
        }


        patientTable.setModel(model);

        sorter = new TableRowSorter<>(patientTable.getModel());
        patientTable.setRowSorter(sorter);

    }

    /**
     * Setzt einen Platzhalter-Text in ein Textfeld
     *
     * @param textField   Textfeld, in dem der Platzhalter angezeigt wird
     * @param placeholder Der anzuzeigende Platzhalter-Text
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
