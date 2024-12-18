package gui;

import beans.Befund;
import beans.Patient;
import bl.DAOBefund;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;

/**
 * Klasse implementiert die GUI für die Anzeige und das Hinzufügen von Befunden zu einem Patienten
 * Benutzer kann bestehende Befunde eines Patienten anzeigen lassen und neue Befunde hinzufügen
 */
public class GUIBefunde {
    private JTextField tfName;
    private JTable tbBefund;
    private JLabel lbHeader;
    private JPanel panelBefund;
    private JButton btAddBefund;
    private JTextField tfUrl;
    private JFrame frame;
    private Patient patient;

    /**
     * Konstruktor für die Klasse GUIBefunde
     * Fügt ActionListener zum Button hinzu um neue Befunde hinzuzufügen
     */
    public GUIBefunde() {
        btAddBefund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfUrl.getText().isEmpty() && !tfName.getText().equals("URL eingeben"))
                {
                    DAOBefund.addBefund(patient.getPatientID(),tfUrl.getText(), LocalDate.now());
                    updateTable();
                }

            }
        });
    }

    /**
     * Aktualisiert die Tabelle mit den Befunden des Patienten
     * Holt die Befunddaten aus der Datenbank und zeigt sie in der JTable an
     */
    public void updateTable()
    {
        DefaultTableModel model = new DefaultTableModel(null,new String []{"ID","URL","Datum"}){

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        tbBefund.setModel(model);

        JTableHeader header = tbBefund.getTableHeader();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        lbHeader.add(header);
        tbBefund.getTableHeader().setReorderingAllowed(false);

        for(Befund befund : DAOBefund.getBefundeByPatientID(patient.getPatientID()))
        {
            model.addRow(new Object[]{befund.getBefundID(),befund.getPfad(),befund.getDatum().toString()});
        }
    }

    /**
     * Zeigt GUI zur Anzeige und Bearbeitung der Befunde eines Patienten an
     * @param p Das Patientenobjekt dessen Befund angezeigt werden sollen
     */
    public void showBefunde(Patient p) {
        frame = new JFrame("Befunde");
        frame.setContentPane(panelBefund);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        this.patient = p;

        tfName.setText(p.getNachname() + " " + p.getVorname());

        updateTable();

        setPlaceholder(tfUrl,"URL eingeben");


    }

    /**
     * Setzt einen Placeholder-Text in ein JTextField
     * Placeholder-Text wird angezeigt, solange das Textfeld leer ist
     * @param textField Das JText-Field, in dem der Placeholder angezeigt werden soll
     * @param placeholder Der Placeholder-Text
     */
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
