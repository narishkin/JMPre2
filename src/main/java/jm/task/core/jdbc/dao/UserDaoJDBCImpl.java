package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
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
            Connection connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("create table Users (" +
                    "id int(5), " +
                    "name VARCHAR(20), " +
                    "lastname VARCHAR(20), " +
                    "age int(3), " +
                    "primary key(id))");
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE Users");
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Connection connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("insert into users (id, name, lastname,age) values(" + idCounter + ", '" + name + "','" + lastName + "'," + age + ")");
            System.out.println("User с именем " + name + " добавлен в базу данных.");
            idCounter++;
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            Connection connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("delete from users where id=" + id);
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Connection connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(id);
                list.add(user);
            }
            connection.commit();
        } catch (SQLException | ClassNotFoundException throwables) {
//            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            Connection connection = Util.getMySQLConnection();
            statement = connection.createStatement();
            statement.executeUpdate("delete from users");
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
        }
    }
}
