package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static int idCounter = 1;
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        connection = Util.getJDBC();
        try {
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.execute("create table Users (" +
                    "id int(5), " +
                    "name VARCHAR(20), " +
                    "lastname VARCHAR(20), " +
                    "age int(3), " +
                    "primary key(id))");
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        connection = Util.getJDBC();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.execute("DROP TABLE Users");
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = Util.getJDBC();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("insert into users (id, name, lastname,age) values(?,?,?,?)");
            preparedStatement.setLong(1, idCounter);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.execute();
            System.out.println("User с именем " + name + " добавлен в базу данных.");
            idCounter++;
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        connection = Util.getJDBC();
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("delete from users where id=?");
            preparedStatement.execute();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        connection = Util.getJDBC();
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("select * from users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(id);
                list.add(user);
            }
            connection.close();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        connection = Util.getJDBC();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate("delete from users");
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
