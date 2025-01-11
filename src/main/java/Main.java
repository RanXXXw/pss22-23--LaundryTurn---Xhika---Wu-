import javax.swing.SwingUtilities;

import controller.ReservationController;
import model.Washer;
import view.AddReservationView;

import controller.UserController;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // Initialize controllers
        UserController userController = new UserController();

        userController.addUserForTesting("testUser", "testPassword");

        // Inizializza il gestore delle prenotazioni
        ReservationController manager = new ReservationController();

        // Aggiungi lavatrici
        manager.addWasher(new Washer("Lavatrice 1", true));
        manager.addWasher(new Washer("Lavatrice 2", true));
        manager.addWasher(new Washer("Lavatrice 3", true));

        /*
         * // Avvia l'interfaccia utente
         * SwingUtilities.invokeLater(() -> new AddReservationView(manager));
         * }
         * }
         */

        // Runnable to show reservation view
        Runnable showReservationView = () -> SwingUtilities.invokeLater(() -> {
            AddReservationView reservationView = new AddReservationView(manager);
            reservationView.setVisible(true);
        });

        // Launch LoginView and pass the navigation logic
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(userController, showReservationView);
            loginView.setVisible(true);
        });
    }
}