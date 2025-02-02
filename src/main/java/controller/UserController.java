package controller;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final String USER_FILE_PATH = "users.txt"; // Percorso per il file di testo

    private List<User> users;

    public UserController() {
        users = loadUsers();
        System.out.println("Utenti caricati dal file:");
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getEmail() + " - " + user.getPassword());
        }
    }

    // Registrazione di un nuovo utente
    public String registerUser(String username, String password, String email) {
        // Controllo lunghezza username
        if (username.length() < 3) {
            return "Errore: L'username deve avere almeno 3 caratteri.";
        }

        // Controllo lunghezza password
        if (password.length() < 6) {
            return "Errore: La password deve avere almeno 6 caratteri.";
        }

        // Controllo email valida
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            return "Errore: Inserisci un'email valida.";
        }

        // Controllo se l'username o l'email sono gia in uso
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "Errore: L'username e gia in uso.";
            }

            if (user.getEmail().equals(email)) {
                return "Errore: L'email e gia stata registrata.";
            }

        }

        // Registra l'utente
        users.add(new User(username, password, email));
        saveUsers();
        return "Registrazione avvenuta con successo!";
    }

    // Validazione delle credenziali di un utente
    public User validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Utente valido
            }
        }
        return null;
    }

    // Aggiorna utente nel file users.txt
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser); // Aggiorna l'utente nella lista
                break;
            }
        }
        saveUsers(); // Salva la lista aggiornata nel file
    }

    // Trova utente per email
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    // Reimposta la password
    public boolean resetPassword(User user, String newPassword) {
        if (user != null) {
            user.setPassword(newPassword);
            saveUsers(); // Salva i dati aggiornati
            return true;
        }
        return false;
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

    public List<User> getUsers() {
        return users; // Restituisce la lista degli utenti
    }
}
