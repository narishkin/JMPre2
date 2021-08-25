package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        User user1 = new User("Mark", "Benz", (byte)29);
        User user2 = new User("Peter", "Kotov", (byte)23);
        User user3 = new User("Christopher", "Dudin", (byte)67);
        User user4 = new User("Ivan", "Draga", (byte)50);

        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser(user1.getName(),user1.getLastName(),user1.getAge());
        userServiceImpl.saveUser(user2.getName(),user2.getLastName(),user2.getAge());
        userServiceImpl.saveUser(user3.getName(),user3.getLastName(),user3.getAge());
        userServiceImpl.saveUser(user4.getName(),user4.getLastName(),user4.getAge());
        System.out.println(userServiceImpl.getAllUsers().toString());
        userServiceImpl.removeUserById(2);
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
