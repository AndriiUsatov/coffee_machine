import dao.ingredient_dao.impl.IngredientDAOImpl;
import dao.item_dao.impl.ItemDAOImpl;
import dao.user_dao.UserDAO;
import dao.user_dao.impl.UserDAOImpl;
import mvc.models.connector.DBConnector;
import mvc.models.entities.Fill;
import mvc.models.entities.drinks.Drink;
import mvc.models.entities.drinks.tea.impl.BlackTea;
import mvc.models.entities.ingredients.Ingredient;
import mvc.models.entities.users.Role;
import mvc.models.entities.users.User;
import services.PasswordService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by andrii on 05.09.17.
 */
public class Main {
    private final static String URL = "jdbc:mysql://localhost:3306/coffee_machine?useUnicode=true&characterEncoding=utf8";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "152758";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        Class.forName("com.mysql.jdbc.Driver");
//        while (true) {
//            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//                 Statement statement = connection.createStatement()) {
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM coffee_machine.ingredient");
//                List<Ingredient> ingredients = new ArrayList<>();
//                while (resultSet.next()) {
//                    Ingredient ingredient = new Ingredient(resultSet.getString("name"), resultSet.getInt("quantity"), resultSet.getInt("id"));
//                    ingredient.setExpirationDate(resultSet.getDate("expiration_date"));
//                    ingredients.add(ingredient);
//                }
//                for (Ingredient ingredient : ingredients) {
//                    System.out.println(ingredient);
//                    if (ingredient.getExpirationDate().getTime() <= System.currentTimeMillis())
//                        statement.executeUpdate("UPDATE coffee_machine.ingredient SET quantity=0 WHERE name=\'" + ingredient.getName() + "\'");
//                }
//            }
//            Thread.currentThread().sleep(1000 * 60 * 60 * 24);
//        }

//        String result = null;
//        String s = new String(PasswordService.getPasswordServiceInstance().decrypt("ﾰ\";ￇyj\u0019ﾝ\u000E�ﾍR\uFFBFﾍﾈ\t"));
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//        byte[] hashedByteArray = digest.digest(s.getBytes("UTF-8"));
//        result = Base64.getEncoder().encodeToString(hashedByteArray);
//        System.out.println("Input string: " + s);
//        System.out.println("Encoded string: " + result);



//        Locale ua = new Locale("ua");
//
//        ResourceBundle bundle_ua = ResourceBundle.getBundle("i18n",ua);
//        ResourceBundle bundle_en = ResourceBundle.getBundle("i18n",Locale.ENGLISH);
//        ResourceBundle bundle_ru = ResourceBundle.getBundle("i18n");
//        System.out.println(bundle_ua.getString("string"));
//        System.out.println(bundle_en.getString("string"));
//        System.out.println(bundle_ru.getString("string"));


//        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
//        Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery("SELECT login, name, item_fills.quantity, date FROM coffee_machine.user INNER JOIN coffee_machine.item_fills INNER JOIN coffee_machine.item " +
//                    "ON user.id = item_fills.user_id AND item.id = item_fills.item_id" );
//            while (resultSet.next()){
//                System.out.println(new Fill(resultSet.getString("name"),resultSet.getInt("quantity"),resultSet.getString("login"),resultSet.getDate("date")));
//            }
//        }

        //SELECT user.id, login, password, balance, role, first_name, last_name, middle_name FROM coffee_machine.user INNER JOIN coffee_machine.human ON user.human_id = human.id AND login=?

//        User user1 = new User("l","p","f","l","m",new BigDecimal(10), Role.CUSTOMER,1L);
//        User user2 = new User("l","p","f","l","m",new BigDecimal(0), Role.CUSTOMER,1L);
//        Drink drink = new BlackTea();
//        System.out.println(user1.getBalance().compareTo(drink.getPrice()));
//        System.out.println(user1.getBalance().compareTo(drink.getPrice()));
//        UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
//        ItemDAOImpl.getItemDAOInstance();
//        IngredientDAOImpl.getIngredientDAOInstance();
//        String s = PasswordService.getPasswordServiceInstance().encrypt("admin");
//        System.out.println("-----|" + s + "|+++++");
//        String hhh = new String(new char[]{'g','f'});
//        System.out.println(s);
//        System.out.println(hhh);
//
//        for(byte b : s.getBytes())
//            System.out.print(b);
//        System.out.println();
//
//        byte[] bddd = new byte[s.toCharArray().length];
//        char[] chars = s.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            bddd[i] = (byte) chars[i];
//        }
//        String ser = PasswordService.getPasswordServiceInstance().decrypt();
//        System.out.println(ser);
//        Item[] items = {new Stick(500),new LittleCup(500), new MiddleCup(200), new BigCup(1000)};
//        for(Item item : items)
//            System.out.println(item);
//        BigDecimal bigDecimal = BigDecimal.valueOf(1232.2133123);
//        bigDecimal.setScale(1,BigDecimal.ROUND_DOWN);
//        System.out.println(bigDecimal);
//        DBConnector connector = DBConnector.getConnector();
//        Connection connection = connector.getConnection();
//        Statement statement = connection.createStatement();
//        ResultSet set = statement.executeQuery("SELECT * FROM  coffemachine.users");
//        while (set.next()){
//            System.out.println(set.getString(2));
//            System.out.println(set.getString(3));
//            System.out.println(set.getString(4));
//            System.out.println(set.getString(5));
//            System.out.println(set.getString(6));
//            System.out.println();
//        }
    }

}

