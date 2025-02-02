package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import controller.UserController;
import model.User;

public class LoginView extends JFrame {
    public interface LoginCallback {
        void onLogin(User loggedInUser);
    }

    public LoginView(UserController userController, LoginCallback callback) {
        // Imposta titolo e layout
        setTitle("Login e Registrazione");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Campi di input
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JCheckBox showPasswordCheckBox = new JCheckBox("Mostra password");

        // Pulsanti
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registrati");
        JButton recoverPasswordButton = new JButton("Recupero Password");

        // Aggiungi i componenti alla vista
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(showPasswordCheckBox);
        add(loginButton);
        add(registerButton);
        add(recoverPasswordButton);

        // Azione per il pulsante "Mostra password"
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Mostra password
            } else {
                passwordField.setEchoChar('*'); // Nasconde password
            }
        });

        // Azione per il pulsante Login
        loginButton.addActionListener(e -> handleLogin(userController, usernameField, passwordField, callback));

        // Premere Invio per fare login
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin(userController, usernameField, passwordField, callback);
                }
            }
        });

        // Azione per il pulsante Recupero Password
        recoverPasswordButton.addActionListener(e -> {
            PasswordRecoveryView recoveryView = new PasswordRecoveryView(userController);
            recoveryView.setVisible(true);
        });

        // Azione per il pulsante Registrati
        registerButton.addActionListener(e -> openRegistrationDialog(userController));
    }

    private void handleLogin(UserController userController, JTextField usernameField, JPasswordField passwordField,
            LoginCallback callback) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User user = userController.validateUser(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login riuscito!");
            callback.onLogin(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate. Riprova.");
        }
    }

    private void openRegistrationDialog(UserController userController) {
        JTextField regUsernameField = new JTextField();
        JPasswordField regPasswordField = new JPasswordField();
        JTextField regEmailField = new JTextField();

        int result = JOptionPane.showConfirmDialog(this, new Object[] {
                "Username:", regUsernameField,
                "Password:", regPasswordField,
                "Email:", regEmailField
        }, "Registrazione", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = regUsernameField.getText();
            String password = new String(regPasswordField.getPassword());
            String email = regEmailField.getText();
            String registrationResult = userController.registerUser(username, password, email);
            JOptionPane.showMessageDialog(this, registrationResult);
        }
    }
}