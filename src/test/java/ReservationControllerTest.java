import model.Reservation;
import model.User;
import model.Washer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ReservationController;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationControllerTest {

    private ReservationController reservationController;
    private User user;
    private Washer washer1;
    private Washer washer2;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        // Creazione utente e lavatrice
        user = new User("testuser", "password", null);
        washer1 = new Washer("Washer1", true);
        washer2 = new Washer("Washer2", true);

        // Inizializza controller
        reservationController = new ReservationController(user);
    }

    @Test
    public void testAddReservationSuccess() {
        // Effettua prenotazione per lavatrice 1
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        reservation = reservationController.addReservation(washer1, startTime);

        // controlla la prenotazione effettuata
        assertNotNull(reservation);
        assertEquals(user, reservation.getUser());
        assertEquals(washer1, reservation.getWasher());
    }

    @Test
    public void testAddReservationWasherNotAvailable() {
        // Segna che lavatrice 1 non à disponibile
        washer1.setAvailable(false);

        // Prova ad effettuare una prenotazione con lavatrice che non è libera
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        reservation = reservationController.addReservation(washer1, startTime);

        // Verificare che la prenotazione non è andata buon fine
        assertNull(reservation);
    }

    @Test
    public void testCancelReservation() {
        // Effettua prenotazione per lavatrice 1
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        reservation = reservationController.addReservation(washer1, startTime);

        // Test cancellazione della prenotazione
        boolean result = reservationController.cancelReservation(reservation);

        // Verifica stato cancellazione
        assertTrue(result);
        assertTrue(washer1.isAvailable());
    }

    @Test
    public void testCancelReservationNotExist() {
        // Prova ad cancellare una prenotazione non esistente
        reservation = null;

        // Test del risultato
        boolean result = reservationController.cancelReservation(reservation);

        // Verifica del risultato
        assertFalse(result);
    }

    @Test
    public void testModifyReservation() {
        // Effettua prenotazione per lavatrice 1
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        reservation = reservationController.addReservation(washer1, startTime);

        // Modificare l'orario della prenotazione
        LocalDateTime newStartTime = startTime.plusHours(1);
        boolean result = reservationController.modifyReservation(reservation, newStartTime);

        // Verifica il risultato della modifica
        assertTrue(result);
        assertEquals(newStartTime, reservation.getStartTime());
    }

    @Test
    public void testModifyReservationCannotModify() {
        // Effettua una prenotazione per lavatrice 1 che inizia tra 30 minuti
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(30);
        reservation = reservationController.addReservation(washer1, startTime);

        // Modificare la prenotazione passata
        LocalDateTime newStartTime = LocalDateTime.now().plusHours(1);

        // Modifica la prenotazione
        boolean result = reservationController.modifyReservation(reservation, newStartTime);

        // Verifica della modifica fallita
        assertFalse(result);
    }

    @Test
    public void testIsWasherAvailable() {
        // Test disponibilità della lavatrice 1
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        LocalDateTime endTime = startTime.plusHours(1);

        // Effettua prenotazione per lavatrice 1
        reservationController.addReservation(washer1, startTime);

        // Test se la lavatrice 1 è disponibile nella fascia oraria già selezionata
        boolean available = reservationController.isWasherAvailable(washer1, startTime, endTime);

        // Verifica della non disponibilità
        assertFalse(available);
    }

    @Test
    public void testGetAvailableWashers() {
        // Creazione lavatrici
        washer1.setAvailable(true);
        washer2.setAvailable(false);

        reservationController.addWasher(washer1);
        reservationController.addWasher(washer2);

        // Lista lavatrici disponibili
        List<Washer> availableWashers = reservationController.getAvailableWasher();

        // Verifica della disponibilità della singola lavatrice
        assertEquals(1, availableWashers.size());
        assertTrue(availableWashers.contains(washer1));
        assertFalse(availableWashers.contains(washer2));
    }
}
