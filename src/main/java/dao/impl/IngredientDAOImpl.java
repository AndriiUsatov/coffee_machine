package dao.impl;

import connection.ConnectionPool;
import dao.IngredientDAO;
import entities.Fill;
import entities.ingredients.Ingredient;
import entities.CoffeeMachine;
import entities.ingredients.builder.IngredientBuilder;
import entities.users.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class IngredientDAOImpl implements IngredientDAO {

    private static Properties queries = new Properties();
    private static final Logger logger = Logger.getLogger(IngredientDAOImpl.class);
    private static IngredientDAOImpl ingredientDAOInstance;
    private static CoffeeMachine coffeeMachine = CoffeeMachine.getCoffeeMachineInstance();

    private IngredientDAOImpl() {
        try {
            queries.load(getClass().getResourceAsStream("/queries.properties"));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    public static IngredientDAOImpl getIngredientDAOInstance() {
        if (ingredientDAOInstance == null) {
            synchronized (IngredientDAOImpl.class) {
                if (ingredientDAOInstance == null)
                    ingredientDAOInstance = new IngredientDAOImpl();
            }
        }
        return ingredientDAOInstance;
    }

    public synchronized Ingredient[] getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(queries.getProperty("coffee_machine.get_all_ingredients"));
            while (resultSet.next()) {
                ingredients.add(new IngredientBuilder()
                        .setName(resultSet.getString("name"))
                        .setQuantity(resultSet.getInt("quantity"))
                        .setId(resultSet.getInt("id"))
                        .setExpirationDate(resultSet.getDate("expiration_date"))
                        .build()
                );
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return ingredients.toArray(new Ingredient[ingredients.size()]);
    }

    public synchronized Ingredient getIngredientByName(String ingredientName) {
        Ingredient ingredient = null;
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queries.getProperty("coffee_machine.get_ingredient"))) {
            preparedStatement.setString(1, ingredientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ingredient = new IngredientBuilder()
                        .setName(resultSet.getString("name"))
                        .setQuantity(resultSet.getInt("quantity"))
                        .setId(resultSet.getInt("id"))
                        .setExpirationDate(resultSet.getDate("expiration_date"))
                        .build();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public void noteIngredientUpdate(String ingredientName, int updateQuantity, User admin) {
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queries.getProperty("coffee_machine.add_ingredient_fills"))) {
            Ingredient ingredient = getIngredientByName(ingredientName);
            preparedStatement.setInt(1, updateQuantity);
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, ingredient.getId());
            preparedStatement.setLong(4, admin.getId());
            preparedStatement.setInt(5, coffeeMachine.getMachineId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public Fill[] getIngredientFills() {
        List<Fill> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(queries.getProperty("coffee_machine.get_all_ingr_fills"));
            while (resultSet.next()) {
                list.add(new Fill(resultSet.getString("name"), resultSet.getInt("quantity"), resultSet.getString("login"), resultSet.getDate("date")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return list.toArray(new Fill[list.size()]);
    }

    public synchronized boolean updateIngredient(String ingredientName, int quantity) {
        boolean result = true;
        if (ingredientName == null || ingredientName.equals(""))
            return false;
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queries.getProperty("coffee_machine.update_ingredient"))) {
            Ingredient ingredient = getIngredientByName(ingredientName);
            preparedStatement.setLong(1, quantity);
            if (ingredient.getQuantity() < quantity)
                ingredient.setExpirationDate(new Date(System.currentTimeMillis() + ingredient.getMaxExpirationDate()));
            preparedStatement.setDate(2, ingredient.getExpirationDate());
            preparedStatement.setString(3, ingredientName);
            preparedStatement.executeUpdate();
            if (ingredient.getQuantity() < quantity) {
                logger.log(Level.INFO, "Added " + (quantity - ingredient.getQuantity()) +
                        " number of " + ingredientName.toUpperCase() + " into Coffee machine");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
            result = false;
        }
        return result;
    }

}
