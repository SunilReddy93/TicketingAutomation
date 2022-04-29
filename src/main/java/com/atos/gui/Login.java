package com.atos.gui;

import com.atos.modal.AutomateLogic;

import javax.swing.*;
import java.awt.event.*;

public class Login implements ActionListener {
    private static JFrame frame = new JFrame();
    private static JDialog dialog = new JDialog(frame, "Authentication", true);
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton cancelButton = new JButton("CANCEL");
    JCheckBox showPassword = new JCheckBox("Show Password");
    AutomateLogic logic = new AutomateLogic();

    public Login(){

        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        cancelButton.setBounds(200, 300, 100, 30);

        dialog.add(userLabel);
        dialog.add(passwordLabel);
        dialog.add(userTextField);
        dialog.add(passwordField);
        dialog.add(showPassword);
        dialog.add(loginButton);
        dialog.add(cancelButton);

        loginButton.addActionListener(this);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    logic.setUsername(userTextField.getText());
                    logic.setPassword(new String(passwordField.getPassword()));
                    frame.dispose();
                }

            }
        });


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
        if (e.getSource() == loginButton) {

            logic.setUsername(userTextField.getText());
            logic.setPassword(new String(passwordField.getPassword()));
            frame.dispose();
        }

    }
}
