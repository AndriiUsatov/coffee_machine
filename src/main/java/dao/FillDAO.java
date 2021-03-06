package dao;


import entities.Fill;
import entities.ingredients.Ingredient;
import entities.items.Item;
import entities.users.User;

import java.util.List;

public interface FillDAO {

    /**
     * @param ingredient     - The ingredient
     * @param updateQuantity - The parameter sets the updated quantity of the ingredient
     * @param admin          - The user object that filled the ingredient
     */

    void noteIngredientFill(Ingredient ingredient, int updateQuantity, User admin);

    /**
     * @param item        - The item
     * @param updateCount - The parameter sets the updated quantity of the item
     * @param admin       - The user object that filled the item
     */

    void noteItemFill(Item item, int updateCount, User admin);

    List<Fill> getIngredientFillsLimit(int skipCount);

    List<Fill> getItemFillsLimit(int skipCount);

    int getItemFillsLength();

    int getIngredientFillsLength();
}
