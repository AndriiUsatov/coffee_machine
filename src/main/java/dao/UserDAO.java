package dao;

import entities.users.User;

import java.math.BigDecimal;
import java.sql.Connection;

public interface UserDAO {

    boolean addUser(User user, Connection connection);

    boolean addHuman(User user, Connection connection);

    boolean addMachineHasUser(User user, Connection connection);

    User getUserByLogin(String login);

    boolean updateBalance(BigDecimal balance, String login);
}
