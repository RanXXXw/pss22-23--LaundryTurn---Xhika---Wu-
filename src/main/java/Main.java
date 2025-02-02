import javax.swing.SwingUtilities;
import controller.UserController;
import model.Washer;
import view.LoginView;
import controller.ProfileController;
import view.ProfileView;
import controller.ReservationController;
import view.ReservationView;

public class Main {
    public static void main(String[] args) {
        // Inizializzazione del controller
        UserController userController = new UserController();

        // Avvio della LoginView
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(userController, (loggedInUser) -> {
                showProfileView(new ProfileController(loggedInUser, userController)); // Passa UserController
            });
            loginView.setVisible(true);
        });
    }

    // Avvia la vista del profilo dell'utente
    public static void showProfileView(ProfileController profileController) {
        SwingUtilities.invokeLater(() -> {
            ProfileView profileView = new ProfileView(profileController,
                    profileController.getUserController());
            profileView.setVisible(true);
            profileView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            ReservationController manager = new ReservationController(profileController.getLoggedInUser());
            // Aggiungi lavatrici
            manager.addWasher(new Washer("Lavatrice 1", true));
            manager.addWasher(new Washer("Lavatrice 2", true));
            manager.addWasher(new Washer("Lavatrice 3", true));

            // Avvio della vista di prenotazione
            showReservationView(manager);
        });
    }

    // Avvia la vista di prenotazione
    public static void showReservationView(ReservationController reservationController) {
        SwingUtilities.invokeLater(() -> {
            ReservationView reservationView = new ReservationView(reservationController);
            reservationView.setVisible(true);
            reservationView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        });
    }
}
