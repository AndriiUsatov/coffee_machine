package services;

import dao.IngredientDAO;
import dao.factory.impl.FactoryDAOImpl;
import entities.Fill;
import entities.ingredients.Ingredient;
import entities.users.User;

public class IngredientService {

    private static IngredientService ingredientServiceInstance;
    private IngredientDAO ingredientDAO = FactoryDAOImpl.getFactoryDAOInstance().getIngredientDAO();

    private IngredientService() {
    }

    public static IngredientService getIngredientServiceInstance() {
        if (ingredientServiceInstance == null) {
            synchronized (IngredientService.class) {
                if (ingredientServiceInstance == null)
                    ingredientServiceInstance = new IngredientService();
            }
        }
        return ingredientServiceInstance;
    }

    public Ingredient[] getIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    public void updateIngredient(String ingredientName, int quantity, User admin) {
        int updateQuantity = quantity - ingredientDAO.getIngredientByName(ingredientName).getQuantity();
        if (ingredientDAO.updateIngredient(ingredientName, quantity))
            ingredientDAO.noteIngredientUpdate(ingredientName, updateQuantity, admin);

    }

    public Ingredient getIngredientByName(String sugar) {
        if (sugar == null)
            return null;
        return ingredientDAO.getIngredientByName(sugar);
    }

    public synchronized void checkExpirationDate() {
        Ingredient[] ingredients = ingredientDAO.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getExpirationDate().getTime() <= System.currentTimeMillis()) {
                ingredientDAO.updateIngredient(ingredient.getName(), 0);
            }
        }
    }

    public synchronized Fill[] getAllFills() {
        return ingredientDAO.getIngredientFills();
    }

}
