package gui;

import beans.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

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

        tfName.setText(p.getNachname());

        DefaultTableModel model = new DefaultTableModel(null,new String []{"ID","URL"}){

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
    }


}
