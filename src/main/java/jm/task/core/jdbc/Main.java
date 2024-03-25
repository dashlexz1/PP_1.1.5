package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao1 = new UserDaoHibernateImpl();

        userDao1.createUsersTable();

        userDao1.saveUser("Name1", "LastName1", (byte) 20);
        userDao1.saveUser("Name2", "LastName2", (byte) 25);
        userDao1.saveUser("Name3", "LastName3", (byte) 31);
        userDao1.saveUser("Name4", "LastName4", (byte) 38);

        userDao1.removeUserById(1);
        userDao1.getAllUsers();
        userDao1.cleanUsersTable();
        userDao1.dropUsersTable();
    }// реализуйте алгоритм здесь
}
