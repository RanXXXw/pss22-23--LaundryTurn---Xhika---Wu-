// import java.time.LocalDateTime;

// public class TestReservationSystem {
//     public static void main(String[] args) {
//         // Crea un gestore di prenotazioni
//         ReservationManager manager = new ReservationManager();

//         // Aggiungi alcune lavatrici al sistema
//         Washer washer1 = new Washer("Lavatrice 1", true);
//         Washer washer2 = new Washer("Lavatrice 2", true);
//         Washer washer3 = new Washer("Lavatrice 3", false);

//         manager.addWasher(washer1);
//         manager.addWasher(washer2);
//         manager.addWasher(washer3);

//         // Visualizza le lavatrici disponibili
//         System.out.println("---Lista lavatrici disponibili:");
//         for (Washer washer : manager.getAvailableWasher()) {
//             System.out.println(washer.getName());
//         }

//         // Prenota una lavatrice
//         Reservation reservation = new Reservation(1, LocalDateTime.of(2024, 12, 13, 12, 0), washer1);
//         manager.addReservation(reservation.getWasher(), reservation);

//         // Visualizza prenotazione effettuate
//         System.out.println("---Lista prenotazione:" + manager.getReservations());

//         // Visualizza le lavatrici disponibili dopo la prenotazione
//         System.out.println("---Lavatrici disponibili dopo la prenotazione:");
//         for (Washer washer : manager.getAvailableWasher()) {
//             System.out.println(washer.getName());
//         }

//         // Modifica della prenotazione
//         manager.modifyReservation(reservation, LocalDateTime.of(2024, 12, 13, 22, 10, 0));

//         // Visualizza prenotazione dopo la modifica
//         System.out.println("---Lista prenotazione dopo la modifica:" + manager.getReservations());

//         // Cancella una prenotazione
//         boolean cancelled = manager.cancelReservation(reservation);
//         if (cancelled) {
//             System.out.println("---Prenotazione cancellata: " + reservation);
//         } else {
//             System.out.println("Non è possibile cancellare la prenotazione.");
//         }

//         // Visualizza prenotazione effettuate dopo la cancellazione
//         System.out.println("---Lista prenotazione dopo la cancellazione:" + manager.getReservations());

//         // Visualizza le lavatrici disponibili dopo la cancellazione
//         System.out.println("---Lavatrici disponibili dopo la cancellazione:");
//         for (Washer washer : manager.getAvailableWasher()) {
//             System.out.println(washer.getName());
//         }
//     }
// }
