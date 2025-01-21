import javax.swing.SwingUtilities;

import controller.ReservationController;
import model.Washer;
import view.AddReservationView;

import controller.UserController;
import view.LoginView;

import controller.ProfileController;
import view.ProfileView;
import model.User;

public class Main {
    public static void main(String[] args) {
        // Initialize controllers
        UserController userController = new UserController();

        // Aggiungi un utente di test
        userController.registerUser("testUser", "testPassword");

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
        /*
         * // Avvia LoginView
         * SwingUtilities.invokeLater(() -> {
         * LoginView loginView = new LoginView(userController, () ->
         * showReservationView(manager));
         * loginView.setVisible(true);
         * });
         * }
         * 
         * // Avvia l'interfaccia reservation dopo il login
         * public static void showReservationView(ReservationController manager) {
         * SwingUtilities.invokeLater(() -> {
         * AddReservationView reservationView = new AddReservationView(manager);
         * reservationView.setVisible(true);
         * });
         * }
         * }
         */

        // Inizializza il controller del profilo con un utente di test
        User testUser = new User("testUser", "testPassword", "testUser@example.com");
        ProfileController profileController = new ProfileController(testUser);

        // Avvia LoginView
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(userController,
                    () -> showReservationView(manager, profileController) // Naviga alla gestione prenotazioni
            );
            loginView.setVisible(true);
            loginView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // chiude la finestra
                                                                                              // senza terminare l'app
        });
    }

    // Avvia la vista delle prenotazioni
    public static void showReservationView(ReservationController manager, ProfileController profileController) {
        SwingUtilities.invokeLater(() -> {
            AddReservationView reservationView = new AddReservationView(manager);
            reservationView.setVisible(true);
            reservationView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            showProfileView(profileController); // Visualizza la gestione del profilo
        });
    }

    // Avvia la vista del profilo dell'utente
    public static void showProfileView(ProfileController profileController) {
        SwingUtilities.invokeLater(() -> {
            ProfileView profileView = new ProfileView(profileController);
            profileView.setVisible(true);
            profileView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        });
    }
}