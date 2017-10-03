package dao.user_dao;

import mvc.models.entities.users.User;

import java.math.BigDecimal;

public interface UserDAO {

    boolean addUser(User user);

    User getUserByLogin(String login);

    boolean updateBalance(BigDecimal balance, String login);
}
