package services;


import connection.ConnectionPool;
import dao.UserDAO;
import dao.factory.impl.FactoryDAOImpl;
import entities.users.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import validators.UserValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private static UserService userServiceInstance;
    private UserDAO userDAO = FactoryDAOImpl.getFactoryDAOInstance().getUserDAO();
    private static final Logger logger = Logger.getLogger(UserService.class);

    private UserService() {
    }

    public static UserService getUserServiceInstance() {
        if (userServiceInstance == null) {
            synchronized (UserService.class) {
                if (userServiceInstance == null)
                    userServiceInstance = new UserService();
            }
        }
        return userServiceInstance;
    }

    public synchronized boolean registerUser(User user) {
        if (user == null)
            return false;
        boolean result = true;
        if (UserValidator.getUserValidatorInstance().validateRegister(user)) {
            if (userDAO.getUserByLogin(user.getLogin()) != null) {
                return false;
            }
            try (Connection connection = ConnectionPool.getConnector().getConnection()) {
                try {
                    connection.setAutoCommit(false);
                    if (userDAO.addHuman(user, connection) && userDAO.addUser(user, connection)
                            && userDAO.addMachineHasUser(user, connection)) {
                        logger.log(Level.INFO, "Added new user - " + user.getLogin());
                        connection.commit();
                    } else {
                        connection.rollback();
                        result = false;
                    }
                } catch (SQLException e) {
                    logger.log(Level.ERROR, "Exception", e);
                    result = false;
                    connection.rollback();
                    e.printStackTrace();
                } finally {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception", e);
                result = false;
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized User getUser(String login) {
        if (login == null)
            return null;
        return userDAO.getUserByLogin(login);
    }

    public synchronized boolean updateBalance(BigDecimal balance, String login) {
        if(balance == null || login == null)
            return false;
        return userDAO.updateBalance(balance, login);
    }
}
