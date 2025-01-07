package view;

import controller.ReservationController;
import model.Washer;

public class WasherView {
    public WasherView(ReservationController manager) {
    }

    public void updateView(Washer washer) {
        System.out.println("Lavatrice: " + washer.getName() + " - Disponibilità: "
                + (washer.isAvailable() ? "Disponibile" : "Non Disponibile"));
    }
}
