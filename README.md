# Relazione per Progetto "LaundryTurn"

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
L’applicazione gestisce la prenotazione delle lavatrici in una lavanderia. Gli utenti possono prenotare lavatrici per fasce orarie predefinite. Il sistema garantisce che una lavatrice non venga prenotata da più utenti nello stesso orario e permette la modifica o la cancellazione di una prenotazione solo entro un certo limite di tempo.

***User (Utente)***
L'utente rappresenta una persona che interagisce con l'applicazione per prenotare una lavatrice. Ogni utente ha una serie di attributi che lo identificano (es. username, password, email) e può avere un insieme di prenotazioni. L'utente può effettuare, modificare o cancellare una prenotazione.

***Washer (Lavatrice)***
Una lavatrice è un oggetto che può essere prenotato. Ha attributi come il nome e la disponibilità. La disponibilità indica se la lavatrice è libera in un determinato intervallo temporale. Le lavatrici sono risorse limitate e devono essere gestite per evitare conflitti tra gli utenti.

***Reservation (Prenotazione)***
Una prenotazione rappresenta l'operazione di un utente che ha riservato una lavatrice per un determinato intervallo di tempo. Una prenotazione contiene informazioni come l'utente che ha effettuato la prenotazione, la lavatrice prenotata, l'orario di inizio e di fine.

```mermaid
classDiagram

    class User {
        - String username
        - String password
        - String email
    }

    class Washer {
        - String name
        - boolean availability
    }

    class Reservation {
        - int id
        - LocalDateTime startTime
        - LocalDateTime endTime
        - User user
        - Washer washer
    }

    User  -->  Reservation 
    Washer  -->  Reservation 
```



Una delle sfide principali è la gestione della disponibilità delle lavatrici. In un determinato intervallo di tempo, una lavatrice deve essere prenotata da un solo utente, e quindi la disponibilità deve essere aggiornata in tempo reale, evitando che due utenti possano prenotare la stessa lavatrice nello stesso orario.

Un altro aspetto critico riguarda la gestione delle modifiche o cancellazioni delle prenotazioni. Il sistema deve permettere agli utenti di modificare o cancellare le prenotazioni solo entro un certo limite di tempo (es. prima di un'ora dalla prenotazione). Questo richiede una gestione precisa dei tempi e delle regole di business.

# Design

## Architettura
L'architettura del sistema di gestione turni lavanderie segue il pattern MVC (Model-View-Controller), garantendo una separazione chiara tra i diversi aspetti dell'applicazione per una gestione efficiente e scalabile.

Il Model rappresenta i dati e la logica di business, comprendendo entità come Utente, Turno e Lavatrice, oltre alla logica di gestione delle prenotazioni e dello stato delle lavatrici.

Il Controller gestisce le interazioni dell'utente e coordina il flusso dell'applicazione. Quando un utente effettua un'azione, come la prenotazione di un turno, il controller elabora la richiesta, aggiorna il modello e notifica la vista con i nuovi dati.

La View si occupa della rappresentazione grafica dell'applicazione. Ogni volta che lo stato del sistema cambia (ad esempio, una nuova prenotazione viene effettuata o una lavatrice diventa disponibile), il modello notifica il controller, che a sua volta aggiorna l’interfaccia utente. Questo approccio consente di modificare o sostituire la GUI senza alterare la logica sottostante dell'applicazione.

Questa architettura garantisce modularità e facilità di manutenzione, permettendo di espandere il sistema in futuro con nuove funzionalità o interfacce grafiche diverse senza compromettere il funzionamento del backend.

```mermaid
classDiagram

    class Model {
        Reservation
        User
        Washer
    }

    class Controller {
        ReservationController
        UserController
        WasherController
        ProfileController
    }

    class View {
        ReservationView
        WasherView
        LoginView
        ProfileView
    }
Model -- View 
View -- Controller
```

## Design dettagliato

### Xiaoran Wu
Il mio compito principale all'interno del gruppo è lo sviluppo della sezione dedicata alla gestione delle prenotazioni. Dopo aver definito la struttura iniziale, ho iniziato a implementare le funzionalità per la creazione, modifica e cancellazione delle prenotazioni. Inoltre, mi sono occupato dell'interfaccia grafica utilizzando Java Swing per garantire un'esperienza utente intuitiva.
Una volta completato lo sviluppo della parte utente da parte della mia collega, mi sono concentrato sull'integrazione tra le due sezioni, assicurandomi che ogni utente potesse visualizzare e gestire le proprie prenotazioni in modo efficace.

**Navigazione tra schermate**

**Problema**  La finestra principale avava dei problemi per passare dalla schermata di prenotazione a quella della lista prenotazioni. Il passaggio tra queste due viste inizialmente non funzionava correttamente.

**Soluzione** Utilizzo del CardLayout, attraverso cardLayout.show(cards, "Reservation") e cardLayout.show(cards, "ReservationList") per garantire un cambio di schermata fluido.
Il pulsante "Visualizza Prenotazioni" è stato collegato alla schermata corretta e la lista delle prenotazioni viene aggiornata prima di mostrarla.

**Aggiornamento della lista delle prenotazioni**

**Problema** Dopo aver effettuato o cancellato una prenotazione, l'interfaccia non aggiornava correttamente la lista delle prenotazioni per l'utente loggato.

**Soluzione** Dopo ogni prenotazione o cancellazione, il metodo updateWasherDropdown() viene richiamato per aggiornare il JComboBox delle lavatrici disponibili.
Questo assicura che l'utente possa selezionare solo lavatrici non occupate.

### Ergisa Xhika

**titolo**
**Problema**
**Soluzione**

# Sviluppo
## Test automatizzato
Nel progetto è stato utilizzato JUnit 5 per automatizzare il processo di verifica delle funzionalità e garantire la qualità del codice. I test automatizzati sono fondamentali per assicurarsi che l'applicazione si comporti correttamente anche dopo modifiche e aggiornamenti al codice.

- **ReservationControllerTest**: Questo test suite si concentra sulla verifica delle principali funzionalità legate alla gestione delle prenotazioni all'interno del sistema.  

## Note di sviluppo

### Xiaoran Wu

#### Utilizzo della libreria Swing
**Dove**: src\main\java\view\ReservationView.java

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

**Descrizione**: Utilizzo dei componenti appartenenti alla Java Swing

#### Utilizzo della libreria Stream
**Dove**: src\main\java\controller\ReservationController.java

**Permalink**: https://github.com/RanXXXw/pss22-23--LaundryTurn---Xhika---Wu-/blob/main/src/main/java/controller/ReservationController.java

**Snippet**
```java
    public List<Reservation> getReservationsForLoggedInUser() {
        return reservations.stream()
                .filter(reservation -> reservation.getUser().getUsername().equals(loggedInUser.getUsername()))
                .collect(Collectors.toList());
    }
```
**Descrizione**: Utilizzo degli stream per rendere il codice più conciso e facilitare le operazioni sulle liste

### Ergisa Xhika


# Commenti finali


## Autovalutazione e lavori futuri

### Xiaoran Wu
Il progetto finale che ho realizzato è funzionante e durante lo sviluppo non ho incontrato particolari difficoltà; una volta definita la struttura iniziale, il flusso di lavoro è stato molto scorrevole. Ritengo di aver svolto completamente il lavoro, riuscendo a implementare correttamente le funzionalità principali del progetto.

### Ergisa Xhika
