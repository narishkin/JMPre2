package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public Statement statement;
    private static int idCounter = 1;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate("create table Users (" +
                    "id int(5), " +
                    "name VARCHAR(20), " +
                    "lastname VARCHAR(20), " +
                    "age int(3), " +
                    "primary key(id))");
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate("DROP TABLE Users");
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate("insert into users (id, name, lastname,age) values(" + idCounter + ", '" + name + "','" + lastName + "'," + age + ")");
            System.out.println("User с именем " + name + " добавлен в базу данных.");
            idCounter++;
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate("delete from users where id="+id);
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(id);
                list.add(user);
            }
        } catch (SQLException throwables) {

//            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate("delete from users");
        } catch (ClassNotFoundException | SQLException e) {

//            e.printStackTrace();
        }
    }
}
