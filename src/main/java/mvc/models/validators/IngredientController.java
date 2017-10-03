package mvc.models.validators;


import mvc.models.entities.ingredients.Ingredient;

import java.sql.*;

public class IngredientController implements Runnable {
    private final static String URL = "jdbc:mysql://localhost:3306/coffee_machine?useUnicode=true&characterEncoding=utf8";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "152758";

    @Override
    public void run() {
        System.out.println("Ingredient controller is running");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            while (true) {
                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM coffee_machine.ingredient");
                    while (resultSet.next()) {
                        Ingredient ingredient = new Ingredient(resultSet.getString("name"), resultSet.getInt("quantity"), resultSet.getInt("id"));
                        ingredient.setExpirationDate(resultSet.getDate("expiration_date"));
                        if (ingredient.getExpirationDate().getTime() <= System.currentTimeMillis())
                            statement.executeUpdate("UPDATE coffee_machine.ingredient SET quantity=0 WHERE name=\'" + ingredient.getName() + "\'");
                    }
                }
                Thread.currentThread().sleep(1000 * 60 * 60 * 24);
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
