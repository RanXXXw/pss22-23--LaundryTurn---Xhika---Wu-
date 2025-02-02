import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import controller.UserController;
import model.User;
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
                askUserChoice(loggedInUser, userController);
            });
            loginView.setVisible(true);
        });
    }

    // Chiede all'utente se vuole aprire il profilo o la gestione prenotazioni
    public static void askUserChoice(User loggedInUser, UserController userController) {
        String[] options = { "Profilo Utente", "Gestione Prenotazioni" };
        int scelta = JOptionPane.showOptionDialog(
                null,
                "Login riuscito! Dove vuoi andare?",
                "Scelta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (scelta == 0) {
            showProfileView(new ProfileController(loggedInUser, userController));
        } else if (scelta == 1) {
            startReservationManagement(loggedInUser);
        }
    }

    // Avvia la vista del profilo dell'utente
    public static void showProfileView(ProfileController profileController) {
        SwingUtilities.invokeLater(() -> {
            ProfileView profileView = new ProfileView(profileController, profileController.getUserController());
            profileView.setVisible(true);
            profileView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        });
    }

    // Metodo per avviare la gestione delle prenotazioni
    public static void startReservationManagement(User loggedInUser) {
        SwingUtilities.invokeLater(() -> {
            ReservationController manager = new ReservationController(loggedInUser);
            manager.addWasher(new Washer("Lavatrice 1", true));
            manager.addWasher(new Washer("Lavatrice 2", true));
            manager.addWasher(new Washer("Lavatrice 3", true));

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
