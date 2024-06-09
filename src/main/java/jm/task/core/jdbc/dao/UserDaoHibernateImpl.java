package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.logging.Logger;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = (SessionFactory) new Util().getSessionFactory();
    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = """
                    CREATE TABLE users (
                    id INT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(45) NOT NULL,
                    lastname VARCHAR(45) NOT NULL,
                    age TINYINT NOT NULL,
                    PRIMARY KEY (id))
                    """;
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            LOGGER.fine("Table created");
        } catch (HibernateException e) {
            LOGGER.severe("Error creating users table:" + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            LOGGER.fine("Users table dropped");
        } catch (HibernateException e) {
            LOGGER.severe("Error dropping users table:" + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            LOGGER.fine("User saved");
        } catch (HibernateException e) {
            LOGGER.severe("Error saving user: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            LOGGER.fine("User removed");
        } catch (HibernateException e) {
            LOGGER.severe("Error user delete: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createNativeQuery("SELECT * FROM users", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            LOGGER.severe("Error getting all users: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
            LOGGER.fine("Table cleaned");
        } catch (HibernateException e) {
            LOGGER.severe("Error cleaning table: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}
