package com.atos.gui;

import com.atos.modal.ReadExcelData;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Complete {

    private static JFrame frame = new JFrame();
    private static JDialog dialog = new JDialog(frame, "Success", true);
    JLabel label = new JLabel("Tickets created successufully");

    public Complete(){
        label.setBounds(100,160,200,40);
        label.setFocusable(false);

        dialog.add(label);
        dialog.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(420,420);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }


}
