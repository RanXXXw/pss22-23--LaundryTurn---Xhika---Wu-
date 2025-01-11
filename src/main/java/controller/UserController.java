package controller;

import model.User;
//import view.LoginView;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users;

    // Inizializzazione controller
    public UserController() {
        this.users = new ArrayList<>();
    }

    // registrazione nuovo user
    public boolean registerUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // User already exists
            }
        }
        users.add(new User(username, password));
        return true;
    }

    // user login
    public boolean validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // add user for testing
    public void addUserForTesting(String username, String password) {
        users.add(new User(username, password));
    }
}