package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.workshop.DBUtil;
import pl.coderslab.workshop.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    String INSERT_USER = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
    String CHANGE_USER = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ? ";
    String SELECT_USER = "SELECT * FROM users WHERE id = ?";
    String REMOVE_USER = "DELETE FROM users WHERE id = ?";
    String SELECT_ALLUSERS = "SELECT * FROM users";

    public User create(User user) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                long id = resultSet.getLong(1);
                System.out.println("ID nowego rekordu " + id);
            }
            System.out.println("Dodano rekord do bazy danych!");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read(int userId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_USER);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(CHANGE_USER);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, this.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
            System.out.println("Dane zostały zmienione!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(REMOVE_USER);
            statement.setInt(1, userId);
            statement.executeUpdate();
            System.out.println("Dane zostały usunięte!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }
    public User[] findAll () {
        try (Connection conn = DBUtil.connect()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(SELECT_ALLUSERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

}
