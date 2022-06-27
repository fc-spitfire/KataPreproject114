package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    public static final String CREATE = "CREATE TABLE IF NOT EXISTS USERS ";
    private static final String TABLE = "(id INTEGER not NULL AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(30), age INTEGER, PRIMARY KEY ( id ))";

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(CREATE + TABLE).executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS USERS").executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User %s added\n", name);
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
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
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createNativeQuery("SELECT * FROM USERS", User.class).getResultList();
            transaction.commit();
            return users;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM USERS").executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
