package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;
import model.User;

public class LoginView extends JFrame {
    public interface LoginCallback {
        void onLogin(User loggedInUser);
    }

    public LoginView(UserController userController, LoginCallback callback) {
        // Imposta titolo e layout
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Campi di input
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registrati");

        // Aggiungi i componenti alla vista
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);

        // Azione per il pulsante Login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = userController.validateUser(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login riuscito!");
                callback.onLogin(user);
                dispose(); // Chiude la finestra di login
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali errate. Riprova.");
            }
        });

        // Azione per il pulsante Registrati
        registerButton.addActionListener(e -> {
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

                boolean isRegistered = userController.registerUser(username, password, email);
                if (isRegistered) {
                    JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!");
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: L'utente esiste già.");
                }
            }
        });
    }
}
