package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import model.Reservation;
import model.Washer;

public class ReservationController {
    private List<Washer> washers;
    private List<Reservation> reservations;

    // costruttore
    public ReservationController() {
        washers = new ArrayList<>();
        reservations = new ArrayList<>();
        loadReservationsFromFile(); // Carica i dati salvati
    }

    // Aggiungi una prenotazione
    public Reservation addReservation(Washer washer, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusHours(1); // Durata fissa di 1 ora

        // Controlla se la lavatrice è disponibile nella fascia richiesta
        if (isWasherAvailable(washer, startTime, endTime)) {
            Reservation reservation = new Reservation(reservations.size() + 1, startTime,
                    washer);
            reservations.add(reservation);
            saveReservationsToFile();
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
            saveReservationsToFile();
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

    public void saveReservationsToFile() {
        String filename = "reservation.txt";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Reservation reservation : reservations) {
                String dateFormatted = reservation.getStartTime().format(dateFormatter);
                String startFormatted = reservation.getStartTime().format(timeFormatter);
                String endFormatted = reservation.getEndTime().format(timeFormatter);

                String line = dateFormatted + " - " + reservation.getWasher().getName() + " dalle " + startFormatted
                        + " alle " + endFormatted;
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Prenotazioni salvate con successo su " + filename);
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio delle prenotazioni: " + e.getMessage());
        }
    }

    public void loadReservationsFromFile() {
        String filename = "reservation.txt";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ITALIAN);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm", Locale.ITALIAN);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reservations = reader.lines()
                    .map(line -> {
                        try {
                            // Split line into components
                            String[] dateParts = line.split(" - ", 2);
                            String[] washerAndTime = dateParts[1].split(" dalle ");
                            String startTimeStr = washerAndTime[1].split(" alle ")[0];

                            // Parse date and time
                            LocalDateTime startTime = LocalDateTime.of(
                                    LocalDate.parse(dateParts[0].trim(), dateFormatter),
                                    LocalTime.parse(startTimeStr.trim(), timeFormatter));

                            // Get or create washer
                            Washer washer = washers.stream()
                                    .filter(w -> w.getName().equals(washerAndTime[0].trim()))
                                    .findFirst()
                                    .orElseGet(() -> {
                                        Washer newWasher = new Washer(washerAndTime[0].trim(), true);
                                        washers.add(newWasher);
                                        return newWasher;
                                    });

                            return new Reservation(reservations.size() + 1, startTime, washer);
                        } catch (Exception e) {
                            System.out.println("Error processing line: " + line);
                            return null;
                        }
                    })
                    .filter(reservation -> reservation != null)
                    .collect(Collectors.toList());

            System.out.println("Total reservations loaded: " + reservations.size());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            reservations = new ArrayList<>();
        }
    }
}