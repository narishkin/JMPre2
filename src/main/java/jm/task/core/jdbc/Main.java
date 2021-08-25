package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        User user1 = new User("Mark", "Benz", (byte) 29);
        User user2 = new User("Peter", "Kotov", (byte) 23);
        User user3 = new User("Christopher", "Dudin", (byte) 67);
        User user4 = new User("Ivan", "Draga", (byte) 50);

        runJDBC(userServiceImpl, user1, user2, user3, user4);
    }

    private static void runJDBC(UserServiceImpl userServiceImpl, User... users) {
        userServiceImpl.createUsersTable();
        for (User user : users) {
            userServiceImpl.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        System.out.println(userServiceImpl.getAllUsers().toString());
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
