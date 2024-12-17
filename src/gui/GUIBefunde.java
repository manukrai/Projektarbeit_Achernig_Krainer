package gui;

import beans.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class GUIBefunde {
    private JTextField tfName;
    private JTable Table;
    private JLabel panelHeader;
    private JPanel Panel;
    private static JFrame frame;


    public void showBefunde() {
        frame = new JFrame("Befunde");
        frame.setContentPane(new GUIBefunde().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setTableModel()
    {
        DefaultTableModel model = new DefaultTableModel(null,new String []{"ID","URL"}){

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        Table.setModel(model);

        JTableHeader header = Table.getTableHeader();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        panelHeader.add(header);

    }

}
