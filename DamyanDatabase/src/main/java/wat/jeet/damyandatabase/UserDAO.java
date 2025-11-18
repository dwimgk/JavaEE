package wat.jeet.damyandatabase;
 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String DB_URL = "jdbc:h2:~/test";

    public void addUser(String name, String email) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, "sa", "");
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
    try (Connection connection = DriverManager.getConnection(DB_URL, "sa", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            String user = resultSet.getString("name") + "- " +
            resultSet.getString("email");
            users.add(user);
        }
    } catch (Exception e) {
    e.printStackTrace();
    }
    return users;
    }
}