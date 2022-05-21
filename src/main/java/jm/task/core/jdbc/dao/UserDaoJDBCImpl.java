package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() throws SQLException {
    }
    private Connection connection = Util.getConnection();

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS USERS " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(30), " +
                    " lastName VARCHAR(30), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            statement.executeUpdate(sql);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS USERS";
            statement.executeUpdate(sql);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO USERS(name, lastName, age) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User %s added\n", name);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM USERS WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM USERS";
            statement.executeUpdate(sql);
        }
    }
}
