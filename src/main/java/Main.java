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

        // avvia LoginView
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(userController);
            loginView.setVisible(true);
        });
    }

    // Avvia l'interfaccia reservation dopo il login
    public static void showReservationView(ReservationController manager) {
        SwingUtilities.invokeLater(() -> {
            AddReservationView reservationView = new AddReservationView(manager);
            reservationView.setVisible(true);
        });
    }
}
/*
 * // Avvia l'interfaccia utente
 * SwingUtilities.invokeLater(() -> new AddReservationView(manager));
 * }
 * }
 */