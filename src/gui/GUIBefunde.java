package gui;

import beans.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBefunde {
    private JTextField tfName;
    private JTable Table;
    private JLabel panelHeader;
    private JPanel Panel;
    private JButton btCancel;
    private JFrame frame;


    public GUIBefunde() {
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public void showBefunde(Patient p) {
        frame = new JFrame("Befunde");
        frame.setContentPane(new GUIBefunde().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        setTableModel(p);
    }

    public void setTableModel(Patient p)
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
