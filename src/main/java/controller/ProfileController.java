package controller;

import model.User;

public class ProfileController {
    private User user;

    public ProfileController(User user) {
        this.user = user;
    }

    // Ottieni l'utente
    public User getUser() {
        return user;
    }

    // Aggiorna i dati dell'utente
    public void updateUser(String username, String password, String email) {
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
    }
}
