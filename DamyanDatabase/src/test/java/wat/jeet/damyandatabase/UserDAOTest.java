package wat.jeet.damyandatabase; // match your package

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        DatabaseInitialiser.initialize();
        userDAO = new UserDAO();

        // Clear the users table before each test
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAndRetrieveUsers() {
        userDAO.addUser("John", "john@example.com");
        userDAO.addUser("Alice", "alice@example.com");

        List<String> users = userDAO.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains("John- john@example.com"));
        assertTrue(users.contains("Alice- alice@example.com"));
    }
}
