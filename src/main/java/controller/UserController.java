package controller;

import java.util.ArrayList;
import java.util.List;

import java.io.*;

public class UserController {
    private static final String USER_FILE_PATH = "users.txt"; // Percorso per il file di testo

    private List<User> users;

    // Inizializzazione controller
    public UserController() {
        users = loadUsers(); // Carica gli utenti dal file di testo all'avvio
    }

    // registrazione nuovo user
    public boolean registerUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // User already exists
            }
        }
        users.add(new User(username, password));
        // Salva la lista aggiornata nel file di testo
        saveUsers();
        return true;
    }

    // Verifica le credenziali di un utente
    public boolean validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Utente valido
            }
        }
        return false; // Utente non trovato
    }

    // Salva gli utenti nel file di testo
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            for (User user : users) {
                // Scrivi ogni utente su una nuova riga nel formato "username,password"
                writer.write(user.getUsername() + "," + user.getPassword());
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
                // Per ogni riga, separa username e password e crea un nuovo oggetto User
                String[] userParts = line.split(",");
                if (userParts.length == 2) {
                    loadedUsers.add(new User(userParts[0], userParts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }

    // Classe interna per rappresentare un utente
    private static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}