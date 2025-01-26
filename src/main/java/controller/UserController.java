package controller;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final String USER_FILE_PATH = "users.txt"; // Percorso per il file di testo

    private List<User> users;

    // Inizializzazione del controller e carica utenti dal file di testo all'avvio
    public UserController() {
        users = loadUsers();
    }

    // Registrazione di un nuovo utente
    public boolean registerUser(String username, String password, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // L'utente esiste già
            }
        }
        users.add(new User(username, password, email)); // Aggiungi il nuovo utente
        saveUsers();
        return true;
    }

    // Validazione delle credenziali di un utente
    public User validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Utente valido
            }
        }
        return null; // Credenziali non valide
    }

    // Aggiorna un utente nel file users.txt
    public void updateUser(model.User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser); // Aggiorna l'utente nella lista
                break;
            }
        }
        saveUsers(); // Salva la lista aggiornata nel file
    }

    // Salva gli utenti nel file di testo
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carica gli utenti dal file di testo
    private List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(",");
                if (userParts.length == 3) {
                    loadedUsers.add(new User(userParts[0], userParts[1], userParts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }
}
