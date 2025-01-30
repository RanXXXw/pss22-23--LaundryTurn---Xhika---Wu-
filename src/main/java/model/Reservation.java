package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Washer washer;
    private User user;

    public Reservation(int id, LocalDateTime startTime, Washer washer, User user) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(1);
        this.washer = washer;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Washer getWasher() {
        return washer;
    }

    public void setStartTime(LocalDateTime newStartTime) {
        this.startTime = newStartTime;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        // Formatta data e orario in un formato leggibile
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        String dateFormatted = startTime.format(dateFormatter); // La data è la stessa per l'inizio e la fine
        String startFormatted = startTime.format(timeFormatter);
        String endFormatted = endTime.format(timeFormatter);

        return user + " " + dateFormatted + " - " + washer.getName() + " dalle " + startFormatted + " alle "
                + endFormatted;
    }

    public boolean canModify() {
        return startTime.isAfter(LocalDateTime.now().plusHours(1));
    }
}
