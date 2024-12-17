package gui;

import beans.Befund;
import beans.Patient;
import bl.DAOBefund;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class GUIBefunde {
    private JTextField tfName;
    private JTable tbBefund;
    private JLabel lbHeader;
    private JPanel panelBefund;
    private JFrame frame;


    public GUIBefunde() {
    }

    public void showBefunde(Patient p) {
        frame = new JFrame("Befunde");
        frame.setContentPane(panelBefund);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        tfName.setText(p.getNachname() + " " + p.getVorname());

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

        for(Befund befund : DAOBefund.getBefundeByPatientID(p.getPatientID()))
        {
            model.addRow(new Object[]{befund.getBefundID(),befund.getPfad(),befund.getDatum().toString()});
        }
    }


}
