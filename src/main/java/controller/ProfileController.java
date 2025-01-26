package controller;

import model.User;

public class ProfileController {
    private User loggedInUser;
    private UserController userController;

    // costruttore
    public ProfileController(User loggedInUser, UserController userController) {
        this.loggedInUser = loggedInUser;
        this.userController = userController;
    }

    // Getter per l'utente loggato
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Getter per UserController
    public UserController getUserController() {
        return userController;
    }
}
