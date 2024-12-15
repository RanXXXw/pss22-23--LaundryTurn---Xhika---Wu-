package model;


public class Washer {
    // dichiarazione campi
    private final String name;
    private boolean availability;

    // costruttore
    public Washer(final String name, boolean availability) {
        this.name = name;
        this.availability = availability;
    }

    // metodi
    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }

}
