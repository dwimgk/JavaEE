package wat.jeet.damyandatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitialiser {
    private static final String DB_URL = "jdbc:h2:~/test";
    
    public static void initialize() {
        try (Connection connection = DriverManager.getConnection(DB_URL, "sa", "");
            Statement statement = connection.createStatement()) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL
                    )
                """;
            statement.execute(createTableSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}