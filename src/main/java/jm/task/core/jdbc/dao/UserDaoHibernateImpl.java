package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("JpaQlInspection")
public class UserDaoHibernateImpl implements UserDao {
private static final SessionFactory sessionFactory = Util.getSessionFactory();

Transaction transaction;
User user;


@Override
public void createUsersTable() {
    try (Session session = sessionFactory.openSession()) {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
              ID INT NOT NULL AUTO_INCREMENT,
              NAME VARCHAR(100),
              LASTNAME VARCHAR(100),
              AGE INT,
              PRIMARY KEY (ID)
            )
            """;//пытаемся получить сессию, подключиться к базе
        session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}

@Override
public void dropUsersTable() {
    try (Session session = sessionFactory.openSession()) {//пытаемся получить сессию, подключиться к базе
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

}

@Override
public void saveUser(String name, String lastName, byte age) {
    try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }

    }
}

@Override
public void removeUserById(long id) {
    try (Session session = sessionFactory.openSession()) {    //пытаемся получить сессию, подключиться к базе
        user = session.get(User.class, id);
        if(user != null) {
            session.delete(user);
        }
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }

    }
}


@Override
public List<User> getAllUsers() {
    List<User> userList = new ArrayList<>();

    try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        userList = session.createQuery("from User order by name").getResultList();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
    return userList;
}

@Override
public void cleanUsersTable() {
    try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }

    }
}

}
