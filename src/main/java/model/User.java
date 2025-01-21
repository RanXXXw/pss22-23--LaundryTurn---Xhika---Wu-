package model;

public class User {
    private String username;
    private String password;

    // Costruttore per inizializzare l'oggetto user
    public User(String username, String password, String email) {
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

    public void setUsername(String username2) {
    }

    public void setPassword(String password2) {
    }

    public String getEmail() {
        return null;
    }

    public void setEmail(String email) {
    }
}