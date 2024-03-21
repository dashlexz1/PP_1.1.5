package jm.task.core.jdbc.util;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mysq";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Dashlexz1!";

    public static Connection connection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("соединение есть");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ошибка.");
        }
        return connection;
    }
}
