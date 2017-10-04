package validators;


import dao.impl.UserDAOImpl;
import entities.users.User;
import services.PasswordService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static UserValidator userValidatorInstance;

    private UserValidator() {
    }

    public static UserValidator getUserValidatorInstance() {
        if (userValidatorInstance == null) {
            synchronized (UserValidator.class) {
                if (userValidatorInstance == null)
                    userValidatorInstance = new UserValidator();
            }
        }
        return userValidatorInstance;
    }

    public synchronized boolean validateLoginIn(String login, String password) {
        if (!hasScript(login) && !hasScript(password)) {
            User user = UserDAOImpl.getUserDAOInstance().getUserByLogin(login);
            if (user != null) {
                return PasswordService.getPasswordServiceInstance().encrypt(password).equals(user.getPassword());
            }
        }
        return false;
    }

    public synchronized boolean validateRegister(User user) {
        return validateLogin(user.getLogin()) &&
                validatePassword(user.getPassword()) &&
                validateName(user.getFirstName()) &&
                validateName(user.getLastName()) &&
                validateName(user.getMiddleName());
    }

    private synchronized boolean validateName(String name) {
        if (hasScript(name))
            return false;
        boolean result = false;
        String nameRegex = new String("[a-zA-Zа-яА-ЯїЇіІєЄёЁ]{3,100}");
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        while (matcher.find())
            result = matcher.group().equals(name) ? true : result;
        return result;
    }

    private synchronized boolean validatePassword(String password) {
        if (hasScript(password))
            return false;
        boolean result = false;
        String passwordRegex = new String("[a-zA-Z0-9]{6,25}");
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        while (matcher.find())
            result = matcher.group().equals(password) ? true : result;
        return result;
    }

    private synchronized boolean validateLogin(String login) {
        if (hasScript(login))
            return false;
        boolean result = false;
        String loginRegex = new String("[a-zA-Z0-9_.]{6,25}");
        Pattern pattern = Pattern.compile(loginRegex);
        Matcher matcher = pattern.matcher(login);
        while (matcher.find())
            result = matcher.group().equals(login) ? true : result;
        return result;
    }

    private synchronized boolean hasScript(String line) {
        String scriptRegex = new String("<script");
        Pattern pattern = Pattern.compile(scriptRegex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }


}
