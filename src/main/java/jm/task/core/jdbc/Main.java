package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        // реализуйте алгоритм здесь

        User user1 = new User("Mark", "Benz", (byte) 29);
        User user2 = new User("Peter", "Kotov", (byte) 23);
        User user3 = new User("Christopher", "Dudin", (byte) 67);
        User user4 = new User("Ivan", "Draga", (byte) 50);

        runJDBC(user1, user2, user3, user4);
//        runHibernate(user1, user2, user3, user4);
    }

    private static void runJDBC(User... users) {
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        for (User user : users) {
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        userDao.getAllUsers().forEach(System.out::println);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }

    private static void runHibernate(User... users) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        for (User user : users) {
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        (userDao.getAllUsers()).forEach(System.out::println);
        userDao.removeUserById(2);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
