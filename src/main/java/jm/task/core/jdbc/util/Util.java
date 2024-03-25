package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mysq";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Dashlexz1!";
    private static SessionFactory sessionFactory;

    protected Util() {
    }

    public static Connection connection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Logger logger = Logger.getLogger(Util.class.getName());
            logger.info("cоединение есть");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration();

                    // Hibernate settings equivalent to hibernate.cfg.xml's properties
                    Properties settings = new Properties();
                    settings.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/mysq?useSSL=false");
                    settings.put(AvailableSettings.USER, USERNAME);
                    settings.put(AvailableSettings.PASS, PASSWORD);
                    settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                    settings.put(AvailableSettings.SHOW_SQL, "true");

                    settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                    settings.put(AvailableSettings.HBM2DDL_AUTO, "");

                    configuration.setProperties(settings);

                    configuration.addAnnotatedClass(User.class);

                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sessionFactory;
        }
    }
