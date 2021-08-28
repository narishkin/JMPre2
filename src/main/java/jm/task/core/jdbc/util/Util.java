package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        final String URL = "jdbc:mysql://localhost:3306/mysqltest?useSSL=false";
        final String USER = "root";
        final String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
