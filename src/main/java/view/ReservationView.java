package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.ReservationController;
import model.Reservation;
import model.Washer;

public class ReservationView extends JFrame {
    private ReservationController manager;
    private JComboBox<String> washerDropdown;
    private JComboBox<String> timeSlotDropdown;
    private JTextArea statusArea;

    private JPanel cards; // Questo è il contenitore principale con CardLayout
    private CardLayout cardLayout; // Layout che consente di cambiare "pagine"

    public ReservationView(ReservationController manager) {
        this.manager = manager;
        setTitle("Gestione Prenotazioni Lavatrici");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta CardLayout come layout principale
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Pannello di prenotazione
        JPanel reservationPanel = createReservationPanel();
        // Pannello della lista prenotazioni
        JPanel reservationListPanel = createReservationListPanel();

        // Aggiungi i pannelli (diversi "schermate") al contenitore
        cards.add(reservationPanel, "Reservation");
        cards.add(reservationListPanel, "ReservationList");

        // Imposta il CardLayout per cambiare pagina
        add(cards);

        updateReservationList();

        setVisible(true);
    }

    private void updateReservationList() {
        // Recupera il pannello delle prenotazioni
        JPanel reservationListPanel = (JPanel) cards.getComponent(1); // "ReservationList" è il secondo pannello

        // Recupera il modello della lista prenotazioni
        @SuppressWarnings("unchecked")
        DefaultListModel<String> model = (DefaultListModel<String>) ((JList<String>) ((JScrollPane) reservationListPanel
                .getComponent(0)).getViewport().getView()).getModel();

        // Aggiungi tutte le prenotazioni attuali al modello
        model.clear(); // Pulisce il modello per evitare duplicati
        for (Reservation reservation : manager.getReservations()) {
            model.addElement(reservation.toString());
        }
    }

    private JPanel createReservationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Pannello superiore (selezione lavatrice e fascia oraria)
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // Modifica per 3 righe
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel washerLabel = new JLabel("Seleziona lavatrice:");
        washerDropdown = new JComboBox<>();
        updateWasherDropdown();

        JLabel timeSlotLabel = new JLabel("Seleziona fascia oraria:");
        timeSlotDropdown = new JComboBox<>(generateTimeSlots());

        // Pulsante per visualizzare le prenotazioni
        JButton reservationsListButton = new JButton("Visualizza Prenotazioni");
        reservationsListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "ReservationList"); // Cambia alla vista della lista delle prenotazioni
            }
        });

        topPanel.add(washerLabel);
        topPanel.add(washerDropdown);
        topPanel.add(timeSlotLabel);
        topPanel.add(timeSlotDropdown);
        topPanel.add(reservationsListButton); // Aggiungi il pulsante al pannello superiore

        // Pannello centrale (stato e messaggi)
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);

        // Pannello inferiore (pulsante di prenotazione)
        JButton bookButton = new JButton("Prenota");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });

        // Aggiungi i pannelli
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bookButton, BorderLayout.SOUTH); // Il pulsante di prenotazione rimane alla fine

        return panel;
    }

    private JPanel createReservationListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Lista delle prenotazioni
        JList<String> reservationList = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        reservationList.setModel(model);

        JScrollPane listScrollPane = new JScrollPane(reservationList);
        panel.add(listScrollPane, BorderLayout.CENTER);

        // Pannello inferiore con pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Pulsante per tornare alla schermata principale
        JButton backButton = new JButton("Torna alla Prenotazione");
        backButton.addActionListener(e -> cardLayout.show(cards, "Reservation"));
        buttonPanel.add(backButton);

        // Pulsante per cancellare la prenotazione
        JButton deleteButton = new JButton("Cancella Prenotazione");
        deleteButton.setEnabled(false); // Disabilitato inizialmente
        deleteButton.addActionListener(e -> {
            int selectedIndex = reservationList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Mostra una conferma prima di cancellare
                int confirm = javax.swing.JOptionPane.showConfirmDialog(
                        this,
                        "Sei sicuro di voler cancellare la prenotazione selezionata?",
                        "Conferma Cancellazione",
                        javax.swing.JOptionPane.YES_NO_OPTION);

                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                    Reservation reservation = manager.getReservations().get(selectedIndex);
                    manager.cancelReservation(reservation);
                    updateReservationList(); // Aggiorna la lista dopo la cancellazione
                    javax.swing.JOptionPane.showMessageDialog(this, "Prenotazione cancellata con successo.");
                }
            }
        });
        buttonPanel.add(deleteButton);

        // Abilita il pulsante di cancellazione solo se c'è una selezione
        reservationList.addListSelectionListener(e -> {
            deleteButton.setEnabled(!reservationList.isSelectionEmpty());
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void updateWasherDropdown() {
        washerDropdown.removeAllItems();
        for (Washer washer : manager.getAvailableWasher()) {
            washerDropdown.addItem(washer.getName());
        }
    }

    private String[] generateTimeSlots() {
        List<String> slots = new ArrayList<>();
        for (int i = 8; i <= 22; i++) {
            String slot = String.format("%02d:00 - %02d:00", i, i + 1);
            slots.add(slot);
        }
        return slots.toArray(new String[0]);
    }

    private void handleBooking() {
        String selectedWasherName = (String) washerDropdown.getSelectedItem();
        String selectedTimeSlot = (String) timeSlotDropdown.getSelectedItem();

        if (selectedWasherName == null || selectedTimeSlot == null) {
            statusArea.append("Seleziona una lavatrice e una fascia oraria.\n");
            return;
        }

        Washer selectedWasher = manager.getAvailableWasher()
                .stream()
                .filter(washer -> washer.getName().equals(selectedWasherName))
                .findFirst()
                .orElse(null);

        if (selectedWasher == null) {
            statusArea.append("Lavatrice non disponibile.\n");
            return;
        }

        // Parse fascia oraria
        String[] times = selectedTimeSlot.split(" - ");
        LocalTime startTime = LocalTime.parse(times[0]);
        LocalTime endTime = LocalTime.parse(times[1]);

        LocalDateTime startDateTime = LocalDateTime.now().withHour(startTime.getHour()).withMinute(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(endTime.getHour()).withMinute(0);

        // Effettua prenotazione
        if (manager.isWasherAvailable(selectedWasher, startDateTime, endDateTime)) {
            manager.addReservation(selectedWasher, startDateTime);
            statusArea.append("Prenotazione effettuata: " + selectedWasher.getName() + " dalle " + startTime + " alle "
                    + endTime + ".\n");

            // Aggiorna la lista delle prenotazioni
            updateReservationList();

            // Aggiorna anche il menu a tendina delle lavatrici
            updateWasherDropdown();
        } else {
            statusArea.append("Fascia oraria non disponibile per " + selectedWasher.getName() + ".\n");
        }
    }

}
