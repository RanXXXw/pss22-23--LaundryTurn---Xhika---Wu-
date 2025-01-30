package controller;

import model.Washer;
import view.WasherView;

public class WasherController {
    private Washer model;
    private WasherView view;

    public WasherController(Washer model, WasherView view) {
        this.model = model;
        this.view = view;
    }

    public void updateWasherStatus() {
        // Modifica lo stato di disponibilità della lavatrice
        model.setAvailable(!model.isAvailable());
        view.updateView(model);
    }

}
