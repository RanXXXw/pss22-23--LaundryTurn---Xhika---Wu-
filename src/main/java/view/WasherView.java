package view;

import model.Washer;

public class WasherView {
        public void updateView(Washer washer) {
        System.out.println("Lavatrice: " + washer.getName() + " - Disponibilità: " + (washer.isAvailable() ? "Disponibile" : "Non Disponibile"));
    }
}
