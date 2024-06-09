//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.logging.Logger;
//
//public class UserDaoJDBCImpl implements UserDao {
//    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
//
//    public UserDaoJDBCImpl() {
//    }
//
//    public void createUsersTable() {
//        String sql = """
//                CREATE TABLE users (
//                  id INT NOT NULL AUTO_INCREMENT,
//                  name VARCHAR(45) NOT NULL,
//                  lastname VARCHAR(45) NOT NULL,
//                  age TINYINT NOT NULL,
//                  PRIMARY KEY (id)
//                )
//                """;
//        try (Statement statement = Objects.requireNonNull(Util.getConnection()).createStatement()) {
//            statement.execute(sql);
//            LOGGER.fine("Table created");
//        } catch (SQLException e) {
//            LOGGER.severe("Error creating users table:" + e.getMessage());
//        }
//    }
//
//    public void dropUsersTable() {
//        String sql = "DROP TABLE `users`";
//        try (Statement statement = Objects.requireNonNull(Util.getConnection()).createStatement()) {
//            statement.execute(sql);
//            LOGGER.fine("Users table dropped");
//        } catch (SQLException e) {
//            LOGGER.severe("Error: drop users table:" + e.getMessage());
//        }
//    }
//
//    public void saveUser(String name, String lastName, byte age) {
//        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
//        try (Connection connection = Util.getConnection();
//             PreparedStatement preparedStatement = Objects.requireNonNull(connection).prepareStatement(sql)) {
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, lastName);
//            preparedStatement.setByte(3, age);
//
//            preparedStatement.executeUpdate();
//            LOGGER.fine("User saved");
//        } catch (SQLException e) {
//            LOGGER.severe("Error saving user: " + e.getMessage());
//        }
//    }
//
//    public void removeUserById(long id) {
//        String sql = String.format("DELETE FROM users WHERE id = %s", id);
//        try (Statement statement = Objects.requireNonNull(Util.getConnection()).createStatement()) {
//            statement.execute(sql);
//            LOGGER.fine("User delete");
//        } catch (SQLException e) {
//            LOGGER.severe("Error user delete:" + e.getMessage());
//        }
//    }
//
//    public List<User> getAllUsers() {
//        String sql = "SELECT * FROM users";
//        List<User> users = new ArrayList<>();
//
//        try (Statement statement = Objects.requireNonNull(Util.getConnection()).createStatement()) {
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setName(resultSet.getString("name"));
//                user.setLastName(resultSet.getString("lastName"));
//                user.setAge(resultSet.getByte("age"));
//                users.add(user);
//            }
//            LOGGER.fine("Get all users complete");
//        } catch (SQLException e) {
//            LOGGER.severe("Error get all users:" + e.getMessage());
//        }
//
//        return users;
//    }
//
//    public void cleanUsersTable() {
//        String sql = "DROP TABLE IF EXISTS users";
//        try (Statement statement = Objects.requireNonNull(Util.getConnection()).createStatement()) {
//            statement.execute(sql);
//            createUsersTable();
//            LOGGER.fine("Users table clean");
//        } catch (SQLException e) {
//            LOGGER.severe("Error clean users table:" + e.getMessage());
//        }
//    }
//}