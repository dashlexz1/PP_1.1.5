package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UserDaoJDBCImpl extends Util implements UserDao {
    private Connection connection;

    {
        try {
            connection = connection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
CREATE TABLE IF NOT EXISTS users (
  ID INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(100),
  LASTNAME VARCHAR(100),
  AGE INT,
  PRIMARY KEY (ID)
);
""";
        try (Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("таблица создана");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try(Statement stat = connection.createStatement()){
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try(PreparedStatement prepst =connection.prepareStatement(sql)){
            prepst.setString(1, name);
            prepst.setString(2, lastName);
            prepst.setByte(3, age);
            prepst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE ID=?";
        try (PreparedStatement prepst =connection.prepareStatement(sql)){
            prepst.setLong(1, id);
            prepst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";

        try(Statement stat = connection.createStatement()) {
            ResultSet result = stat.executeQuery(sql);

            while(result.next()){
                User user = new User();
                user.setId(result.getLong("ID"));
                user.setName(result.getString("NAME"));
                user.setLastName(result.getString("LASTNAME"));
                user.setAge(result.getByte("AGE"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try(Statement stat = connection.createStatement()) {
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
