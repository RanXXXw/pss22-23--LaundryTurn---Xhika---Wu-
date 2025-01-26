package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.ProfileController;
import controller.UserController;

public class ProfileView extends JFrame {
    public ProfileView(ProfileController profileController, UserController userController) {
        // Imposta titolo e layout
        setTitle("Profilo Utente");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        // Ottieni l'utente loggato
        var loggedInUser = profileController.getLoggedInUser();

        // Campi per visualizzare e modificare i dati
        JTextField usernameField = new JTextField(loggedInUser.getUsername());
        JTextField emailField = new JTextField(loggedInUser.getEmail());
        JPasswordField passwordField = new JPasswordField(loggedInUser.getPassword());

        JButton saveButton = new JButton("Salva Modifiche");
        JButton cancelButton = new JButton("Annulla Modifiche");

        // Etichette e campi di input
        add(new JLabel("Modifica Username:"));
        add(usernameField);
        add(new JLabel("Modifica Email:"));
        add(emailField);
        add(new JLabel("Modifica Password:"));
        add(passwordField);

        // Pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        // Azione per salvare i dati
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newEmail = emailField.getText();
                String newPassword = new String(passwordField.getPassword());

                if (newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(ProfileView.this, "Tutti i campi devono essere compilati!");
                    return;
                }

                // Aggiorna i dati nell'oggetto utente loggato
                loggedInUser.setUsername(newUsername);
                loggedInUser.setEmail(newEmail);
                loggedInUser.setPassword(newPassword);

                // Salva le modifiche nel file tramite UserController
                userController.updateUser(loggedInUser);

                JOptionPane.showMessageDialog(ProfileView.this, "Dati aggiornati con successo!");
            }
        });

        // Azione per annullare
        cancelButton.addActionListener(e -> {
            // Ripristina i valori originali
            usernameField.setText(loggedInUser.getUsername());
            emailField.setText(loggedInUser.getEmail());
            passwordField.setText(loggedInUser.getPassword());
        });
    }
}
