package dao;

import entities.Fill;
import entities.ingredients.Ingredient;
import entities.users.User;

/**
 * DAO for operations with Ingredient objects
 */

public interface IngredientDAO {

    /**
     * @return Returns array of Ingredient objects from database
     */

    Ingredient[] getAllIngredients();

    /**@param ingredientName - The name of the ingredient
     * @param quantity - The parameter sets the quantity of the ingredient
     * @return Return true if ingredient updated successfully
     */

    boolean updateIngredient(String ingredientName, int quantity);

    /**@param ingredientName - The name of ingredient
     * @return Returns the Ingredient object from database
     */

    Ingredient getIngredientByName(String ingredientName);

    /**@param ingredientName - The name of the ingredient
     * @param  updateQuantity - The parameter sets the updated quantity of the ingredient
     * @param admin - The user object that filled the ingredient
     */

    void noteIngredientUpdate(String ingredientName, int updateQuantity, User admin);

    Fill[] getIngredientFills();
}
