package view;

import javax.swing.*;
import controller.UserController;
import model.User;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PasswordRecoveryView extends JFrame {
    private UserController userController;

    public PasswordRecoveryView(UserController userController) {
        this.userController = userController;
        setTitle("Recupero Password");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel emailLabel = new JLabel("Inserisci l'email associata al tuo account:");
        JTextField emailField = new JTextField();
        JButton recoverButton = new JButton("Reimposta password");

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(recoverButton);

        add(panel);

        recoverButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            User user = userController.findUserByEmail(email);

            if (user != null) {
                String newPassword = JOptionPane.showInputDialog(this, "Inserisci una nuova password:");

                if (newPassword != null && !newPassword.trim().isEmpty()) {
                    // Controllo lunghezza password
                    if (newPassword.length() < 6) {
                        JOptionPane.showMessageDialog(this, "La password deve essere lunga almeno 6 caratteri.");
                    } else {
                        boolean success = userController.resetPassword(user, newPassword);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Password aggiornata con successo!");
                            dispose(); // Chiude la finestra
                        } else {
                            JOptionPane.showMessageDialog(this, "Errore durante l'aggiornamento della password.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La password non puo essere vuota.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email non trovata!");
            }
        });
    }
}
