package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static long idCounter = 1L;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sf = Util.getSessionFactory("create");
        sf.close();
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sf = Util.getSessionFactory("drop");
        sf.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sf = Util.getSessionFactory("update");
        Session session = sf.openSession();
        session.beginTransaction();
        User user = new User(name,lastName,age);
        user.setId(idCounter);
        session.save(user);
        session.getTransaction().commit();
        idCounter++;
        session.close();
        sf.close();
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sf = Util.getSessionFactory("update");
        Session session = sf.openSession();
        User user = session.load(User.class,id);
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sf = Util.getSessionFactory("none");
        Session session = sf.openSession();
        List<User> list = session.createQuery("from User ", User.class).list();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sf = Util.getSessionFactory("update");
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
        session.close();
        sf.close();
    }
}
