package model;

public class User {
    private String username;
    private String password;

    // Costruttore per inizializzare l'oggetto user
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter per il nome utente
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}