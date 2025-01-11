package view;

import controller.UserController;
import controller.ReservationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import controller.ReservationController;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView(UserController userController) {
        setTitle("Login/Registrazione");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        // Username
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // Password
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        panel.add(loginButton);

        // Register Button
        registerButton = new JButton("Registrati");
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (userController.validateUser(username, password)) {
                    JOptionPane.showMessageDialog(LoginView.this, "Login effettuato!");
                    dispose(); // Close the login window
                    // Main.showReservationView(new ReservationController());
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Credenziali errate.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (userController.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(LoginView.this, "Registrazione completata!");
                } else {
                    JOptionPane.showMessageDialog(LoginView.this,
                            "Errore nella registrazione (username gia esistente?).");
                }
            }
        });
    }
}