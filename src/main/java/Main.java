import javax.swing.SwingUtilities;

import controller.ReservationController;
import model.Washer;
import view.AddReservationView;
import view.WasherView;

public class Main {
    public static void main(String[] args) {
        // Inizializza il gestore delle prenotazioni
        ReservationController manager = new ReservationController();

        // Aggiungi lavatrici
        manager.addWasher(new Washer("Lavatrice 1", true));
        manager.addWasher(new Washer("Lavatrice 2", true));
        manager.addWasher(new Washer("Lavatrice 3", true));

        // Avvia l'interfaccia utente
        // SwingUtilities.invokeLater(() -> new AddReservationView(manager));
        SwingUtilities.invokeLater(() -> new WasherView());
    }
}
