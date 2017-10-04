package dao.impl;

import connector.ConnectionPool;
import dao.UserDAO;
import entities.CoffeeMachine;
import entities.users.buider.UserBuilder;
import services.PasswordService;
import entities.users.Role;
import entities.users.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;

public class UserDAOImpl implements UserDAO {
    private static Properties properties = new Properties();
    private static UserDAOImpl userDAOInstance;
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    private static CoffeeMachine coffeeMachine = CoffeeMachine.getCoffeeMachineInstance();

    private UserDAOImpl() {
        try {
            properties.load(getClass().getResourceAsStream("/queries.properties"));
        } catch (IOException e) {
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

    public synchronized boolean addHuman(User user, Connection connection) {
        boolean result = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.add_human"))) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getMiddleName());
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public synchronized boolean addUser(User user, Connection connection) {
        boolean result = true;
        long humanID = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.add_user"));
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(properties.getProperty("coffee_machine.last_insert_id"));
            resultSet.next();
            humanID = resultSet.getLong(1);
            if (humanID == -1)
                throw new SQLException("Human ID invalid number: " + humanID);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, PasswordService.getPasswordServiceInstance().encrypt(user.getPassword()));
            preparedStatement.setLong(3, humanID);
            preparedStatement.execute();
            ResultSet set = statement.executeQuery(properties.getProperty("coffee_machine.last_insert_id"));
            set.next();
            user.setId(set.getLong(1));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public synchronized boolean addMachineHasUser(User user, Connection connection){
        boolean result = true;
            try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.add_machine_has_user"))) {
                if (user.getId() == -1)
                    throw new SQLException("User ID invalid number: " + user.getId());
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setInt(2, coffeeMachine.getMachineId());
                preparedStatement.execute();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception, e");
                result = false;
                e.printStackTrace();
            }
        return result;
}

    public synchronized User getUserByLogin(String login) {
        User user = null;
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.get_user"))) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserBuilder().
                        setId(resultSet.getLong("id")).
                        setLogin(resultSet.getString("login")).
                        setPassword(resultSet.getString("password")).
                        setFirstName(resultSet.getString("first_name")).
                        setLastName(resultSet.getString("last_name")).
                        setMiddleName(resultSet.getString("middle_name")).
                        setBalance(new BigDecimal(resultSet.getDouble("balance"))).
                        setRole(resultSet.getString("role").equals("customer") ? Role.CUSTOMER : Role.ADMIN).
                        build();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return user;
    }

    public synchronized boolean updateBalance(BigDecimal balance, String login) {
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("coffee_machine.set_balance"))) {
            preparedStatement.setDouble(1, balance.doubleValue());
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return false;
    }
}
