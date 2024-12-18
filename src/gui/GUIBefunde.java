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

public class GUIBefunde {
    private JTextField tfName;
    private JTable tbBefund;
    private JLabel lbHeader;
    private JPanel panelBefund;
    private JButton btAddBefund;
    private JTextField tfUrl;
    private JFrame frame;
    private Patient patient;


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
