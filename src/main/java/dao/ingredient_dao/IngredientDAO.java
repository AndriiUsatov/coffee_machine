package dao.ingredient_dao;

import mvc.models.entities.Fill;
import mvc.models.entities.ingredients.Ingredient;
import mvc.models.entities.users.User;

public interface IngredientDAO {

    Ingredient[] getAllIngredients();

    boolean updateIngredient(String ingredientName, int quantity);

    Ingredient getIngredientByName(String ingredientName);

    void noteIngredientUpdate(String ingredientName, int updateQuantity, User admin);

    Fill[] getIngredientFills();
}
