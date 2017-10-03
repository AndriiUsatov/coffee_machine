package services;


import dao.user_dao.impl.UserDAOImpl;
import mvc.models.entities.users.User;
import mvc.models.validators.UserValidator;

import java.math.BigDecimal;

public class UserService {
    private static UserService userServiceInstance;
    private UserDAOImpl userDAO = UserDAOImpl.getUserDAOInstance();

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
        if (UserValidator.getUserValidatorInstance().validateRegister(user))
            return userDAO.addUser(user);
        return false;
    }

    public synchronized User getUser(String login) {
        return userDAO.getUserByLogin(login);
    }

    public synchronized boolean updateBalance(BigDecimal balance, String login) {
        return userDAO.updateBalance(balance, login);
    }
}
