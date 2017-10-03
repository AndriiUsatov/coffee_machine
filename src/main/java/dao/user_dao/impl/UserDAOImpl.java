package dao.user_dao.impl;

import mvc.models.connector.DBConnector;
import dao.user_dao.UserDAO;
import mvc.models.entities.machines.CoffeeMachine;
import mvc.models.entities.users.buider.UserBuilder;
import services.PasswordService;
import mvc.models.entities.users.Role;
import mvc.models.entities.users.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Properties;

public class UserDAOImpl implements UserDAO {
    private static FileInputStream propertiesFile;
    private static Properties properties = new Properties();
    private static UserDAOImpl userDAOInstance;
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    private UserDAOImpl() {
        try {
            propertiesFile = new FileInputStream(new File(getClass().getResource("/queries.properties").toURI()));
            properties.load(propertiesFile);
        } catch (IOException | URISyntaxException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    public static UserDAOImpl getUserDAOInstance() {
        if (userDAOInstance == null) {
            synchronized (UserDAOImpl.class) {
                if (userDAOInstance == null) {
                    userDAOInstance = new UserDAOImpl();
                }
            }
        }
        return userDAOInstance;
    }

//    public synchronized boolean addUser(User user) {
//        boolean result = false;
//        try (Connection connection = DBConnector.getConnector().getConnection();
//             Statement statement = connection.createStatement();
//             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("add_user"))) {
//
//            ResultSet resultSet = statement.executeQuery(properties.getProperty("get_user") + "'" + user.getLogin() + "'");
//            if (result = !resultSet.next()) {
//                preparedStatement.setString(1, user.getLogin());
//                preparedStatement.setString(2, PasswordService.getPasswordServiceInstance().encrypt(user.getPassword()));
//                preparedStatement.setString(3, user.getFirstName());
//                preparedStatement.setString(4, user.getLastName());
//                preparedStatement.setString(5, user.getMiddleName());
//                preparedStatement.execute();
//            }
//            logger.log(Level.INFO, "Added new user - " + user.getLogin());
//        } catch (SQLException e) {
//            logger.log(Level.DEBUG, "Exception", e);
//            e.printStackTrace();
//        }
//        return result;
//    }

    public synchronized boolean addUser(User user) {
        boolean result = true;
        long humanID = -1;
        long userID = -1;
        if (getUserByLogin(user.getLogin()) != null)
            return false;

        try (Connection connection = DBConnector.getConnector().getConnection();
             Statement statement = connection.createStatement()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.add_human"))) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getMiddleName());
                preparedStatement.execute();
                ResultSet resultSet = statement.executeQuery(properties.getProperty("coffee_machine.last_insert_id"));
                resultSet.next();
                humanID = resultSet.getLong(1);
            } catch (SQLException e) {
                logger.log(Level.DEBUG, "Exception", e);
                connection.rollback();
                result = false;
                e.printStackTrace();
            }
            if (result) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.add_user"))) {
                    if (humanID == -1)
                        throw new SQLException("Human ID invalid number: " + humanID);
                    preparedStatement.setString(1, user.getLogin());
                    preparedStatement.setString(2, PasswordService.getPasswordServiceInstance().encrypt(user.getPassword()));
                    preparedStatement.setLong(3, humanID);
                    preparedStatement.execute();
                    ResultSet resultSet = statement.executeQuery(properties.getProperty("coffee_machine.last_insert_id"));
                    resultSet.next();
                    userID = resultSet.getLong(1);
                } catch (SQLException e) {
                    logger.log(Level.DEBUG, "Exception", e);
                    connection.rollback();
                    result = false;
                    e.printStackTrace();
                }
            }
            if (result) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.add_machine_has_user"))) {
                    if (userID == -1)
                        throw new SQLException("User ID invalid number: " + userID);
                    preparedStatement.setLong(1, userID);
                    preparedStatement.setInt(2, CoffeeMachine.getMachineId());
                    preparedStatement.execute();
                    connection.commit();
                } catch (SQLException e) {
                    logger.log(Level.DEBUG, "Exception, e");
                    connection.rollback();
                    result = false;
                    e.printStackTrace();
                } finally {
                    connection.setAutoCommit(true);
                }
            }
            if (result)
                logger.log(Level.INFO, "Added new user - " + user.getLogin());
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized User getUserByLogin(String login) {
        User user = null;
        try (Connection connection = DBConnector.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.get_user"))) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userLogin = resultSet.getString("login");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                BigDecimal balance = new BigDecimal(resultSet.getDouble("balance"));
                Role role = resultSet.getString("role").equals("customer") ? Role.CUSTOMER : Role.ADMIN;
                Long id = resultSet.getLong("id");
                user = new UserBuilder().
                        setId(id).
                        setLogin(userLogin).
                        setPassword(password).
                        setFirstName(firstName).
                        setLastName(lastName).
                        setMiddleName(middleName).
                        setBalance(balance).
                        setRole(role).
                        build();
            }
        } catch (SQLException e) {
            logger.log(Level.DEBUG, "Exception", e);
            e.printStackTrace();
        }
        return user;
    }

    public synchronized boolean updateBalance(BigDecimal balance, String login) {
        try (Connection connection = DBConnector.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.set_balance"))) {
            preparedStatement.setDouble(1, balance.doubleValue());
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, "Exception", e);
            e.printStackTrace();
        }
        return false;
    }
}
