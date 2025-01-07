package controller;

import model.User;
import view.AvailabilityView;

public class UserController {
    private User model;
    private AvailabilityView view;

    // Inizializzazione controller
    public UserController(User model, AvailabilityView view) {
        this.model = model;
        this.view = view;
    }

    // aggiorno i dati
    public void updateUser(String name, String email) {
        // nuovi valori
        model.setName(name);
        model.setEmail(email);
        view.updateView(model);
    }

    // public boolean authenticate(String string, String string2) {
    // return false;
    // }
    public boolean authenticate(String username, String password) {
        return model.getName().equals(username) && model.getEmail().equals(password);
    }

}
