package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        final String URL = "jdbc:mysql://localhost:3306/mysqltest?useSSL=false";
        final String USER = "root";
        final String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static SessionFactory getSessionFactory(String s) {
        try {
            Properties properties = new Properties();
            properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mysqltest");
            properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.setProperty(Environment.USER, "root");
            properties.setProperty(Environment.PASS, "root");
            properties.setProperty(Environment.HBM2DDL_AUTO, s);
            properties.setProperty(Environment.SHOW_SQL, "true");
            properties.setProperty(Environment.FORMAT_SQL, "true");
            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            return configuration.buildSessionFactory(standardServiceRegistry);
        } catch (Exception e) {
            System.out.println("failed to build SessionFactory..." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
