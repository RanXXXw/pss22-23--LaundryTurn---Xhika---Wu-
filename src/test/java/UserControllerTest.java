import controller.UserController;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(); // Inizializza UserController prima di ogni test
    }

    @Test
    public void testRegisterUser() {
        // Test di registrazione
        String result = userController.registerUser("testUser", "testPassword123", "testEmail@example.com");
        assertEquals("Registrazione avvenuta con successo!", result);

        // Verifica che l'utente sia stato aggiunto
        User user = userController.getUsers().stream()
                .filter(u -> u.getUsername().equals("testUser"))
                .findFirst()
                .orElse(null);
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testValidateUser() {
        // Registrazione di un utente
        userController.registerUser("testUser2", "testPassword123", "testEmail2@example.com");

        // Verifica delle credenziali valide
        User user = userController.validateUser("testUser2", "testPassword123");
        assertNotNull(user);
        assertEquals("testUser2", user.getUsername());

        // Verifica delle credenziali errate
        User invalidUser = userController.validateUser("testUser2", "wrongPassword");
        assertNull(invalidUser);
    }

    @Test
    public void testFindUserByEmail() {
        // Registrazione di un utente
        userController.registerUser("testUser3", "testPassword123", "testEmail3@example.com");

        // Trova utente per email
        User user = userController.findUserByEmail("testEmail3@example.com");
        assertNotNull(user);
        assertEquals("testEmail3@example.com", user.getEmail());

        // Prova a trovare un utente inesistente
        User notFoundUser = userController.findUserByEmail("nonexistentEmail@example.com");
        assertNull(notFoundUser);
    }

    @Test
    public void testResetPassword() {
        // Registrazione di un utente
        userController.registerUser("testUser4", "testPassword123", "testEmail4@example.com");

        // Verifica che la password venga aggiornata
        User user = userController.getUsers().stream()
                .filter(u -> u.getUsername().equals("testUser4"))
                .findFirst()
                .orElse(null);
        assertNotNull(user);

        boolean resetSuccess = userController.resetPassword(user, "newPassword123");
        assertTrue(resetSuccess);

        // Verifica che la password sia stata effettivamente cambiata
        assertEquals("newPassword123", user.getPassword());
    }
}
