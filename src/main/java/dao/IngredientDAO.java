package dao;

import entities.Fill;
import entities.ingredients.Ingredient;
import entities.users.User;

public interface IngredientDAO {

    Ingredient[] getAllIngredients();

    boolean updateIngredient(String ingredientName, int quantity);

    Ingredient getIngredientByName(String ingredientName);

    void noteIngredientUpdate(String ingredientName, int updateQuantity, User admin);

    Fill[] getIngredientFills();
}
