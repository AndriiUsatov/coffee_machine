package services;

import dao.ingredient_dao.IngredientDAO;
import dao.ingredient_dao.impl.IngredientDAOImpl;
import mvc.models.entities.ingredients.Ingredient;
import mvc.models.entities.users.User;

public class IngredientService {

    private static IngredientService ingredientServiceInstance;
    private IngredientDAO ingredientDAO = IngredientDAOImpl.getIngredientDAOInstance();

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

    public void updateIngredient(String ingredientName,int quantity, User admin){
        int updateQuantity = quantity - ingredientDAO.getIngredientByName(ingredientName).getQuantity();
        if(ingredientDAO.updateIngredient(ingredientName,quantity))
            ingredientDAO.noteIngredientUpdate(ingredientName, updateQuantity, admin);

    }

    public Ingredient getIngredientByName(String sugar) {
        return ingredientDAO.getIngredientByName(sugar);
    }
}
