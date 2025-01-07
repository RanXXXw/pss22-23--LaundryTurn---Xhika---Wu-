package view;

import model.User;

public class AvailabilityView {
    public AvailabilityView() {
    }

    // Metodo per le informazioni dell'utente
    public void updateView(User user) {
        System.out.println("User Info: " + user.toString());

    }
}
