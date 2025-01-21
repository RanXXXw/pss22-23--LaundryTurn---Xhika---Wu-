package view;

import javax.swing.*;
import controller.ProfileController;
import model.User;

public class ProfileView extends JFrame {
    private ProfileController profileController;

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton updateButton;

    public ProfileView(ProfileController profileController) {
        this.profileController = profileController;

        // Impostazione del layout
        setTitle("Gestione Profilo");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inizializza i componenti
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        emailLabel = new JLabel("Email:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);

        updateButton = new JButton("Aggiorna Profilo");

        // Aggiungi i listener per il pulsante di aggiornamento
        updateButton.addActionListener(e -> updateProfile());

        // Layout della finestra
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(emailLabel);
        add(emailField);
        add(updateButton);

        // Carica i dati dell'utente
        loadUserData();
    }

    // Carica i dati dell'utente
    private void loadUserData() {
        User user = profileController.getUser();
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        emailField.setText(user.getEmail());
    }

    // Gestisce l'aggiornamento dei dati del profilo
    private void updateProfile() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        // aggiornare l'oggetto User con i nuovi dati
        profileController.updateUser(username, password, email);

        // Ricarica i dati
        loadUserData();
    }
}
