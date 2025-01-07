// package view;

// import model.Reservation;
// import javax.swing.*;
// import java.util.List;

// public class ReservationListView {
//     private JFrame frame;
//     private JList<String> reservationList;

//     public ReservationListView(List<Reservation> reservations) {
//         frame = new JFrame("Lista Prenotazioni");
//         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         frame.setSize(400, 300);
//         frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

//         // Crea la lista delle prenotazioni
//         reservationList = new JList<>();
//         updateReservationList(reservations);
//         frame.add(new JScrollPane(reservationList));

//         frame.setVisible(true);
//     }

//     public void updateReservationList(List<Reservation> reservations) {
//         DefaultListModel<String> model = new DefaultListModel<>();
//         for (Reservation reservation : reservations) {
//             model.addElement(reservation.toString());
//         }
//         reservationList.setModel(model);
//     }
// }
