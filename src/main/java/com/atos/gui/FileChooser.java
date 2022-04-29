package com.atos.gui;

import com.atos.modal.ReadExcelData;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class FileChooser implements ActionListener {
    private static JFrame frame = new JFrame();
    private static JDialog dialog = new JDialog(frame, "Choose File", true);
    JButton button = new JButton("Upload a file");
    static ReadExcelData data = new ReadExcelData();
    File file;

    public FileChooser(){
        button.setBounds(100,160,200,40);
        button.setFocusable(false);
        button.addActionListener(this);

        dialog.add(button);
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


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            JFileChooser jFileChooser = new JFileChooser();
            int response = jFileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                data.setFilePath(file);
                frame.dispose();

            }
        }
    }


}
