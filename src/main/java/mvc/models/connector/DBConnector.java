package mvc.models.connector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBConnector {
    private final static String URL = "jdbc:mysql://localhost:3306/coffeemachine?useUnicode=true&characterEncoding=utf8";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "152758";
    private static final String NEW_USER = "INSERT INTO coffeemachine.users(login, password, firstName, lastName, middleName) VALUES(?,?,?,?,?)";
    private static final String GET_INGREDIENTS = "SELECT * FROM coffeemachine.ingredients";
    private static final String GET_ITEMS = "SELECT * FROM coffeemachine.items";
    private static final String GET_USER = "SELECT * FROM coffeemachine.users WHERE login=";
    private static final String SET_BALANCE = "UPDATE coffeemachine.users SET money=";
    private static final String SET_INGREDIENT_QUANTITY = "UPDATE coffeemachine.ingredients SET quantity=";
    private static final String SET_ITEM_COUNT = "UPDATE coffeemachine.items SET quantity=";
    private static Driver driver;
    private static DBConnector connector;
    private InitialContext initialContext;
    private Context context;
    private DataSource dataSource;
    private static final Logger logger = Logger.getLogger(DBConnector.class);

    private DBConnector() {
        init();
    }

    private void init() {
        try {
            initialContext = new InitialContext();
            context = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/coffee_machine");
        } catch (NamingException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    public static DBConnector getConnector() {
        if (connector == null) {
            synchronized (DBConnector.class) {
                if (connector == null) {
                    connector = new DBConnector();
                }
            }
        }
        return connector;
    }

    public synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
//        DriverManager.registerDriver(driver);
//        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

//    public boolean addUser(User user) {
//        boolean exists = false;
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(GET_USER + "'" + user.getLogin() + "'");
//            exists = resultSet.next();
//            if (!exists) {
//                PreparedStatement preparedStatement = connection.prepareStatement(NEW_USER);
//                preparedStatement.setString(1, user.getLogin());
//                preparedStatement.setString(2, user.getPassword());
//                preparedStatement.setString(3, user.getFirstName());
//                preparedStatement.setString(4, user.getLastName());
//                preparedStatement.setString(5, user.getMiddleName());
//                preparedStatement.execute();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return !exists;
//    }

//    public boolean userExists(String login, String password) {
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(GET_USER + "'" + login + "'");
//            if (resultSet.next()) {
//                return password.equals(resultSet.getString("password"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    public boolean makeCoffee(Coffee coffee) {
//        boolean result = true;
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(GET_INGREDIENTS);
//            int countWater = 0;
//            int countCoffee = 0;
//            int countMilk = 0;
//            int countSugar = 0;
//            while (resultSet.next() && result) {
//                String ingrName = resultSet.getString(2);
//                switch (ingrName) {
//                    case "water":
//                        countWater = resultSet.getInt(3);
//                        if (countWater < coffee.getWater())
//                            result = false;
//                        break;
//                    case "coffee":
//                        countCoffee = resultSet.getInt(3);
//                        if (countCoffee < coffee.getCoffee())
//                            result = false;
//                        break;
//                    case "milk":
//                        countMilk = resultSet.getInt(3);
//                        if (countMilk < coffee.getMilk())
//                            result = false;
//                        break;
//                    case "sugar":
//                        countSugar = resultSet.getInt(3);
//                        if (countSugar < coffee.getSugar())
//                            result = false;
//                        break;
//                }
//            }
//            if (!result)
//                return result;
//            else {
//                statement.executeUpdate("UPDATE coffemachine.ingredients SET quantity=" + (countWater - coffee.getWater()) + " WHERE name='water'");
//                statement.executeUpdate("UPDATE coffemachine.ingredients SET quantity=" + (countCoffee - coffee.getCoffee()) + " WHERE name='coffee'");
//                statement.executeUpdate("UPDATE coffemachine.ingredients SET quantity=" + (countMilk - coffee.getMilk()) + " WHERE name='milk'");
//                if (coffee.getSugar() > 0)
//                    statement.executeUpdate("UPDATE coffemachine.ingredients SET quantity=" + (countSugar - coffee.getSugar()) + " WHERE name='sugar'");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

//    public User getUser(String login) {
//        User user = null;
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(GET_USER + "'" + login + "'");
//            resultSet.next();
//            String userLogin = resultSet.getString("login");
//            String password = resultSet.getString("password");
//            String firstName = resultSet.getString("firstName");
//            String lastName = resultSet.getString("lastName");
//            String middleName = resultSet.getString("middleName");
//            BigDecimal balance = new BigDecimal(resultSet.getDouble("money"));
//            Role role = resultSet.getString("role").equals("customer") ? Role.CUSTOMER : Role.ADMIN;
//            user = new User(userLogin, password, firstName, lastName, middleName, balance, role);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }

//    public boolean setBalance(BigDecimal balance, String login) {
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            statement.executeUpdate(SET_BALANCE + balance.doubleValue() + " WHERE login='" + login + "'");
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public Ingredient[] getIngredients() {
//        List<Ingredient> list = new ArrayList<>();
//        try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(GET_INGREDIENTS);
//            while (resultSet.next()) {
//                Ingredient ingredient = new Ingredient(resultSet.getString("name"), resultSet.getInt("quantity"), resultSet.getString("measure"));
//                list.add(ingredient);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list.toArray(new Ingredient[list.size()]);
//    }

//    public Item[] getItems() {
//        List<Item> list = new ArrayList<>();
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(GET_ITEMS);
//            while (resultSet.next()) {
//                switch (resultSet.getString("name")) {
//                    case "sticks":
//                        list.add(new Stick(resultSet.getInt("quantity")));
//                        break;
//                    case "little_cups":
//                        list.add(new LittleCup(resultSet.getInt("quantity")));
//                        break;
//                    case "middle_cups":
//                        list.add(new MiddleCup(resultSet.getInt("quantity")));
//                        break;
//                    case "big_cups":
//                        list.add(new BigCup(resultSet.getInt("quantity")));
//                        break;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list.toArray(new Item[list.size()]);
//    }

//    public void setIngredient(String ingredient, int quantity) {
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            statement.executeUpdate(SET_INGREDIENT_QUANTITY + "'" + quantity + "' WHERE name='" + ingredient + "'");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public void setItem(String item, int count) {
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            statement.executeUpdate(SET_ITEM_COUNT + "'" + count + "' WHERE name='" + item + "'");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public Coffee[] getAvailableCoffee() {
//        Coffee[] coffees = {new Espresso(), new Americano(), new Cappuccino(), new Latte()};
//        List<Coffee> result = new ArrayList<>();
//        Ingredient[] ingredients = IngredientDAOImpl.getIngredientDAOInstance().getAllIngredients();
//        for (Coffee coffee : coffees) {
//            boolean available = true;
//            for (Ingredient ingredient : ingredients) {
//                switch (ingredient.getName()) {
//                    case "water":
//                        available = coffee.getWater() > ingredient.getQuantity() ? false : available;
//                        break;
//                    case "milk":
//                        available = coffee.getMilk() > ingredient.getQuantity() ? false : available;
//                        break;
//                    case "coffee":
//                        available = coffee.getCoffee() > ingredient.getQuantity() ? false : available;
//                        break;
//                }
//            }
//            if (available)
//                result.add(coffee);
//        }
//        return result.toArray(new Coffee[result.size()]);
//    }
}
