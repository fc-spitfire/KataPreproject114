package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String user = "root";
    private static String password = "password";
    private static String url = "jdbc:mysql://localhost:3306/mysql";
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("connected");
        return connection;
    }
    // реализуйте настройку соеденения с БД
}
