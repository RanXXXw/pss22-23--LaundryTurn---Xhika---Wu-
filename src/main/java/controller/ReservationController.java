package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Reservation;
import model.Washer;

public class ReservationController {
    private List<Washer> washers;
    private List<Reservation> reservations;

    // costruttore
    public ReservationController() {
        washers = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    // Aggiungi una prenotazione
    public Reservation addReservation(Washer washer, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusHours(1); // Durata fissa di 1 ora

        // Controlla se la lavatrice è disponibile nella fascia richiesta
        if (isWasherAvailable(washer, startTime, endTime)) {
            Reservation reservation = new Reservation(reservations.size() + 1, startTime,
                    washer);
            reservations.add(reservation);
            return reservation;
        } else {
            System.out.println("Lavatrice non disponibile in questa fascia oraria!");
            return null;
        }
    }

    // cancella
    public boolean cancelReservation(Reservation reservation) {
        if (reservation == null) {
            System.out.println("La prenotazione non esiste!");
            return false; // Non è possibile cancellare una prenotazione nulla
        } else {
            reservations.remove(reservation);
            reservation.getWasher().setAvailable(true);
            return true;
        }
    }

    // modifica
    public boolean modifyReservation(Reservation reservation, LocalDateTime newStartTime) {
        if (reservation == null || !reservations.contains(reservation)) {
            System.out.println("La prenotazione non esiste o non è valida.");
            return false;
        }
        if (!reservation.canModify()) {
            System.out.println("Non è possibile modificare la prenotazione (fuori tempo massimo).");
            return false;
        }
        // Modifica la prenotazione esistente
        reservation.setStartTime(newStartTime);
        System.out.println("Prenotazione modificata con successo!");
        return true;
    }

    // Controlla disponibilità della lavatrice in una fascia oraria
    public boolean isWasherAvailable(Washer washer, LocalDateTime startTime, LocalDateTime endTime) {
        for (Reservation reservation : reservations) {
            if (reservation.getWasher().equals(washer)) {
                LocalDateTime reservationStart = reservation.getStartTime();
                LocalDateTime reservationEnd = reservationStart.plusHours(1);
                if (startTime.isBefore(reservationEnd) && endTime.isAfter(reservationStart)) {
                    return false;
                }
            }
        }
        return true;
    }

    // controlla disponibilità di tutti lavatrici
    public List<Washer> getAvailableWasher() {
        List<Washer> availableWashers = new ArrayList<>();
        for (Washer washer : washers) {
            if (washer.isAvailable()) {
                availableWashers.add(washer);
            }
        }
        return availableWashers;
    }

    // ottenere tutti prenotazioni
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addWasher(Washer washer) {
        washers.add(washer);
    }

}