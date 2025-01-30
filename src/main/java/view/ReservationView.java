package view;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import controller.ReservationController;
import model.Reservation;
import model.Washer;

public class ReservationView extends JFrame {
    private ReservationController manager;
    private JComboBox<String> dateDropdown;
    private JComboBox<String> washerDropdown;
    private JComboBox<String> timeSlotDropdown;
    private JTextArea statusArea;

    private JPanel cards;
    private CardLayout cardLayout;

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
        JPanel reservationListPanel = (JPanel) cards.getComponent(1);

        // Recupera il modello della lista prenotazioni
        @SuppressWarnings("unchecked")
        DefaultListModel<String> model = (DefaultListModel<String>) ((JList<String>) ((JScrollPane) reservationListPanel
                .getComponent(0)).getViewport().getView()).getModel();

        model.clear();
        for (Reservation reservation : manager.getReservationsForLoggedInUser()) {
            model.addElement(reservation.toString());
        }
    }

    private JPanel createReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel dateLabel = new JLabel("Seleziona data:");
        dateDropdown = new JComboBox<>(generateNext7Days());

        JLabel washerLabel = new JLabel("Seleziona lavatrice:");
        washerDropdown = new JComboBox<>();
        updateWasherDropdown();

        JLabel timeSlotLabel = new JLabel("Seleziona fascia oraria:");
        timeSlotDropdown = new JComboBox<>();

        // Pulsante per visualizzare le prenotazioni
        JButton reservationsListButton = new JButton("Visualizza Prenotazioni");
        reservationsListButton.addActionListener(e -> cardLayout.show(cards, "ReservationList"));

        topPanel.add(dateLabel);
        topPanel.add(dateDropdown);
        topPanel.add(washerLabel);
        topPanel.add(washerDropdown);
        topPanel.add(timeSlotLabel);
        topPanel.add(timeSlotDropdown);
        topPanel.add(reservationsListButton);

        // Pannello centrale (stato e messaggi)
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);

        // Pannello inferiore (pulsante di prenotazione)
        JButton bookButton = new JButton("Prenota");
        bookButton.addActionListener(e -> handleBooking());

        // Aggiungi i pannelli
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bookButton, BorderLayout.SOUTH);

        // Aggiungi listeners per aggiornare i time slot
        dateDropdown.addActionListener(e -> {
            String selectedDateStr = (String) dateDropdown.getSelectedItem();
            if (selectedDateStr != null && washerDropdown.getSelectedItem() != null) {
                LocalDate selectedDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String selectedWasherName = (String) washerDropdown.getSelectedItem();
                Washer selectedWasher = manager.getAvailableWasher()
                        .stream()
                        .filter(washer -> washer.getName().equals(selectedWasherName))
                        .findFirst()
                        .orElse(null);
                updateTimeSlots(selectedWasher, selectedDate);
            }
        });

        washerDropdown.addActionListener(e -> {
            String selectedWasherName = (String) washerDropdown.getSelectedItem();
            if (selectedWasherName != null && dateDropdown.getSelectedItem() != null) {
                LocalDate selectedDate = LocalDate.parse((String) dateDropdown.getSelectedItem(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Washer selectedWasher = manager.getAvailableWasher()
                        .stream()
                        .filter(washer -> washer.getName().equals(selectedWasherName))
                        .findFirst()
                        .orElse(null);
                updateTimeSlots(selectedWasher, selectedDate);
            }
        });

        return panel;
    }

    private JPanel createReservationListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

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
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> {
            int selectedIndex = reservationList.getSelectedIndex();
            if (selectedIndex != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Sei sicuro di voler cancellare la prenotazione selezionata?",
                        "Conferma Cancellazione",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Reservation reservation = manager.getReservationsForLoggedInUser().get(selectedIndex);
                    manager.cancelReservation(reservation);
                    updateReservationList();
                    JOptionPane.showMessageDialog(this, "Prenotazione cancellata con successo.");
                }
            }
        });
        buttonPanel.add(deleteButton);

        reservationList.addListSelectionListener(e -> deleteButton.setEnabled(!reservationList.isSelectionEmpty()));

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void updateWasherDropdown() {
        washerDropdown.removeAllItems();
        for (Washer washer : manager.getAvailableWasher()) {
            washerDropdown.addItem(washer.getName());
        }
    }

    private String[] generateNext7Days() {
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            dates.add(LocalDate.now().plusDays(i).format(formatter));
        }
        return dates.toArray(new String[0]);
    }

    private void updateTimeSlots(Washer selectedWasher, LocalDate selectedDate) {
        List<String> availableSlots = new ArrayList<>();
        List<String> unavailableSlots = new ArrayList<>();

        // Generate available and unavailable slots
        for (int i = 8; i <= 22; i++) {
            String slot = String.format("%02d:00 - %02d:00", i, i + 1);
            LocalTime startTime = LocalTime.of(i, 0);
            LocalTime endTime = LocalTime.of(i + 1, 0);

            LocalDateTime startDateTime = LocalDateTime.of(selectedDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(selectedDate, endTime);

            if (manager.isWasherAvailable(selectedWasher, startDateTime, endDateTime)) {
                availableSlots.add(slot);
            } else {
                unavailableSlots.add(slot);
            }
        }

        // Update the dropdown with available and unavailable slots
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String slot : availableSlots) {
            model.addElement(slot);
        }

        // Disable unavailable slots and change their color to gray
        timeSlotDropdown.setModel(model);
        timeSlotDropdown.setRenderer(new ListCellRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = new JLabel(value);
                if (unavailableSlots.contains(value)) {
                    label.setForeground(Color.GRAY); // Set gray color for unavailable slots
                    label.setEnabled(false); // Make unavailable slots unselectable
                } else {
                    label.setForeground(Color.BLACK);
                    label.setEnabled(true);
                }
                return label;
            }
        });
    }

    private void handleBooking() {
        String selectedDateStr = (String) dateDropdown.getSelectedItem();
        String selectedWasherName = (String) washerDropdown.getSelectedItem();
        String selectedTimeSlot = (String) timeSlotDropdown.getSelectedItem();

        if (selectedDateStr == null || selectedWasherName == null || selectedTimeSlot == null) {
            statusArea.append("Seleziona una data, una lavatrice e una fascia oraria.\n");
            return;
        }

        LocalDate selectedDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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

        LocalDateTime startDateTime = LocalDateTime.of(selectedDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(selectedDate, endTime);

        // Effettua prenotazione
        if (manager.isWasherAvailable(selectedWasher, startDateTime, endDateTime)) {
            manager.addReservation(selectedWasher, startDateTime);
            statusArea.append("Prenotazione effettuata: " + selectedWasher.getName() + " il " + selectedDateStr
                    + " dalle " + startTime + " alle " + endTime + ".\n");

            // Aggiorna la lista delle prenotazioni
            updateReservationList();

            // Aggiorna anche il menu a tendina delle lavatrici
            updateWasherDropdown();
        } else {
            statusArea.append(
                    "Fascia oraria non disponibile per " + selectedWasher.getName() + " il " + selectedDateStr + ".\n");
        }
    }
}
