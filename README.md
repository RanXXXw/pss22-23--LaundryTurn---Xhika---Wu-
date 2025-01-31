# Relazione per Progetto "LaudryTurn"

# Analisi

## Requisiti
L'obiettivo principale del progetto è sviluppare un software per la gestione dei turni delle lavatrici, consentendo agli utenti di effettuare, modificare e cancellare prenotazioni. Il sistema sarà integrato con un modulo di autenticazione e registrazione per garantire un accesso personalizzato alle funzionalità.

### Requisiti funzionali

- [ ] <b>Effettuare una prenotazione</b>: Un utente autenticato può prenotare una lavatrice scegliendo tra le fasce orarie disponibili. La prenotazione può essere effettuata per un giorno compreso nei sette successivi.

- [ ] <b>Cancallare una prenotazione</b>: Gli utenti hanno la possibilità di eliminare una prenotazione precedentemente effettuata.

- [ ] <b>Modificare l'orario di una prenotazione</b>:  Gli utenti possono modificare l'orario di una prenotazione solo se:
Il nuovo orario non supera un'ora dall'orario attuale.
La nuova fascia oraria scelta è disponibile e non prenotata da altri utenti.

- [ ] <b>Autenticazione e registrazione</b>: Il sistema deve consentire agli utenti di registrarsi e accedere per gestire le proprie prenotazioni.

### Requisiti non funzionali

- [ ] <b>Interfaccia Grafica</b>: L'applicazione utilizzerà Java Swing per la gestione dell'interfaccia utente, garantendo un'esperienza intuitiva e accessibile.

## Analisi e modello del dominio
All'interno del sistema di gestione turni per lavanderie, entrano in gioco diverse entità fondamentali. Il sistema ha lo scopo di organizzare e ottimizzare l'uso delle lavatrici, permettendo agli utenti di prenotare turni e gestire il tempo di utilizzo in modo equo ed efficiente.

# Design

## Architettura
L'architettura del sistema di gestione turni lavanderie segue il pattern MVC (Model-View-Controller), garantendo una separazione chiara tra i diversi aspetti dell'applicazione per una gestione efficiente e scalabile.
Il Model rappresenta i dati e la logica di business, comprendendo entità come Utente, Turno e Lavatrice, oltre alla logica di gestione delle prenotazioni e dello stato delle lavatrici.
Il Controller gestisce le interazioni dell'utente e coordina il flusso dell'applicazione. Quando un utente effettua un'azione, come la prenotazione di un turno, il controller elabora la richiesta, aggiorna il modello e notifica la vista con i nuovi dati.
La View si occupa della rappresentazione grafica dell'applicazione. Ogni volta che lo stato del sistema cambia (ad esempio, una nuova prenotazione viene effettuata o una lavatrice diventa disponibile), il modello notifica il controller, che a sua volta aggiorna l’interfaccia utente. Questo approccio consente di modificare o sostituire la GUI senza alterare la logica sottostante dell'applicazione.
Questa architettura garantisce modularità e facilità di manutenzione, permettendo di espandere il sistema in futuro con nuove funzionalità o interfacce grafiche diverse senza compromettere il funzionamento del backend.

## Design dettagliato

### Xiaoran Wu
Il mio compito principale all'interno del gruppo è lo sviluppo della sezione dedicata alla gestione delle prenotazioni. Dopo aver definito la struttura iniziale, ho iniziato a implementare le funzionalità per la creazione, modifica e cancellazione delle prenotazioni. Inoltre, mi sono occupato dell'interfaccia grafica utilizzando Java Swing per garantire un'esperienza utente intuitiva.
Una volta completato lo sviluppo della parte utente da parte della mia collega, mi sono concentrato sull'integrazione tra le due sezioni, assicurandomi che ogni utente potesse visualizzare e gestire le proprie prenotazioni in modo efficace.

**Collegamento tra utente e pronotazione**

**Problema**  
Ogni utente poteva visualizzare tutti le pronotazioni anche quelle non relative.

**Soluzione**
Aggiungendo nuovo campo user nella sezione prenotazione e creare un filtro per i dati della prenotazione.

### Ergisa Xhika

**titolo**
**Problema**
**Soluzione**

# Sviluppo

## Note di sviluppo

### Xiaoran Wu

#### Utilizzo della libreria swing
**Dove**: parte di view
**Permalink**: https://github.com/RanXXXw/pss22-23--LaundryTurn---Xhika---Wu-/blob/main/src/main/java/view/ReservationView.java
**Snippet**
```java
public class ReservationView extends JFrame {
    private ReservationController manager;
    private JComboBox<String> dateDropdown;
    private JComboBox<String> washerDropdown;
    private JComboBox<String> timeSlotDropdown;
    private JTextArea statusArea;

    private JPanel cards;
    private CardLayout cardLayout;
}
```
#### Utilizzo della libreria Stream
**Dove**: reservation controller
**Permalink**: https://github.com/RanXXXw/pss22-23--LaundryTurn---Xhika---Wu-/blob/main/src/main/java/controller/ReservationController.java
**Snippet**
```java
    public List<Reservation> getReservationsForLoggedInUser() {
        return reservations.stream()
                .filter(reservation -> reservation.getUser().getUsername().equals(loggedInUser.getUsername()))
                .collect(Collectors.toList());
    }
```

### Ergisa Xhika


# Commenti finali


## Autovalutazione e lavori futuri

### Xiaoran Wu
### Ergisa Xhika
