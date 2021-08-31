package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static long idCounter = 1L;
    SessionFactory sf;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        sf = Util.getSf();
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("create table if not exists Users (" +
                    "id int(5), " +
                    "name VARCHAR(20), " +
                    "lastname VARCHAR(20), " +
                    "age int(3), " +
                    "primary key(id))").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        sf = Util.getSf();
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sf = Util.getSf();
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            user.setId(idCounter);
            session.save(user);
            session.getTransaction().commit();
            idCounter++;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        sf = Util.getSf();
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            User user = session.load(User.class, id);
            tx = session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();

            }
        }
    }
//here
    @Override
    public List<User> getAllUsers() {
        sf = Util.getSf();
        List<User> list = null;
        try (Session session = sf.openSession()) {
            session.getTransaction().begin();
            list = session.createQuery("from User ", User.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        sf = Util.getSf();
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
