package edu.ccd.appUI;

import edu.ccd.MainWindow;
import edu.ccd.model.database.InventoryDatabaseMySQL;
import edu.ccd.model.security.SecurityContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog implements ActionListener{
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private InventoryDatabaseMySQL myDB;

    public Login(InventoryDatabaseMySQL theDB) {
        myDB = theDB;
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userNameField = new JTextField(20);
        userNameField.setBounds(100, 10, 160, 25);
        panel.add(userNameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 40, 160, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 100, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);
        panel.getRootPane().setDefaultButton(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 80, 100, 25);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() instanceof JButton ) {
            if( ((JButton) e.getSource()).getText() == "Login") {
                if( myDB.Login( getUserNameField() ) ) {
                    MainWindow.the().setApplicationSecurityContext(getUserNameField(), getPasswordField());
                    this.dispose();
                }
                else {
                    setUserNameField("INVALID USER");
                }
            }else {
                System.out.println("Cancel");
                System.exit(0);
            }
        }
    }

    public String getUserNameField() {
        return userNameField.getText().trim();
    }

    public String getPasswordField() {
        return passwordField.getPassword().toString().trim();
    }

    public void setUserNameField(String text) {
        userNameField.setText(text);
    }
}
