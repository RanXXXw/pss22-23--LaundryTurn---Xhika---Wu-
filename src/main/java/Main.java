// import javax.swing.SwingUtilities;

// import controller.UserController;
// import view.LoginView;

// import controller.ProfileController;
// import view.ProfileView;

// public class Main {
//     public static void main(String[] args) {
//         // Inizializzazione del controller
//         UserController userController = new UserController();

//         // Start LoginView
//         SwingUtilities.invokeLater(() -> {
//             LoginView loginView = new LoginView(userController, (loggedInUser) -> {
//                 showProfileView(new ProfileController(loggedInUser, userController)); // Passa UserController
//             });
//             loginView.setVisible(true);
//         });
//     }

//     // Avvia la vista del profilo dell'utente
//     public static void showProfileView(ProfileController profileController) {
//         SwingUtilities.invokeLater(() -> {
//             ProfileView profileView = new ProfileView(profileController, profileController.getUserController());
//             profileView.setVisible(true);
//             profileView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//         });
//     }
// }

import javax.swing.SwingUtilities;

import controller.ReservationController;
import model.Washer;
import view.ReservationView;

public class Main {
    public static void main(String[] args) {
        // Inizializza il gestore delle prenotazioni
        ReservationController manager = new ReservationController();

        // Aggiungi lavatrici
        manager.addWasher(new Washer("Lavatrice 1", true));
        manager.addWasher(new Washer("Lavatrice 2", true));
        manager.addWasher(new Washer("Lavatrice 3", true));

        // Avvia l'interfaccia utente
        SwingUtilities.invokeLater(() -> new ReservationView(manager));
    }
}