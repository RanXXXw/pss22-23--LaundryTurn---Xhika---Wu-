import javax.swing.SwingUtilities;

import controller.ReservationController;
import model.Washer;
import view.AddReservationView;

import controller.UserController;
import view.AvailabilityView;
import model.User;

public class Main {
    public static void main(String[] args) {
        // Inizializza il gestore delle prenotazioni
        ReservationController manager = new ReservationController();

        // Aggiungi lavatrici
        manager.addWasher(new Washer("Lavatrice 1", true));
        manager.addWasher(new Washer("Lavatrice 2", true));
        manager.addWasher(new Washer("Lavatrice 3", true));

        // Avvia l'interfaccia utente
        SwingUtilities.invokeLater(() -> new AddReservationView(manager));

        // utente
        User user = new User("username", "password");

        // Creazione del controller e della vista
        AvailabilityView availabilityView = new AvailabilityView();
        UserController userController = new UserController(user, availabilityView);

        // Verifico l'autenticazione
        if (userController.authenticate("username", "password")) {
            System.out.println("Autenticazione riuscita");

            // Se è autenticato mostra le informazioni dell'utente
            userController.updateUser("username", "password");
        } else {
            System.out.println("Autenticazione fallita.");
        }

    }
}
