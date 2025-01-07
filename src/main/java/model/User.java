package model;

public class User {
    private String name;
    private String email;

    // Costruttore per inizializzare l'oggetto user
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter per il nome utente
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Metodo getter per ottenere l'email
    public String getEmail() {
        return email;
    }

    // imposta l'email dell'utente
    public void setEmail(String email) {
        this.email = email;
    }

    // Stampa le informazioni dell'utente
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }
}
