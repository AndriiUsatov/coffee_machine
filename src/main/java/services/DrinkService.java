package services;


import dao.IngredientDAO;
import dao.ItemDAO;
import dao.factory.impl.FactoryDAOImpl;
import entities.drinks.Drink;
import entities.drinks.coffee.*;
import entities.drinks.coffee.impl.Americano;
import entities.drinks.coffee.impl.Cappuccino;
import entities.drinks.coffee.impl.Espresso;
import entities.drinks.coffee.impl.Latte;
import entities.drinks.tea.impl.BlackTea;
import entities.drinks.tea.impl.GreenTea;
import entities.ingredients.Ingredient;
import entities.items.impl.Stick;
import entities.users.User;
import services.factory.ServiceFactory;
import services.factory.impl.ServiceFactoryImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DrinkService {

    private static DrinkService drinkServiceInstance;
    private static IngredientDAO ingredientDAO = FactoryDAOImpl.getFactoryDAOInstance().getIngredientDAO();
    private static ItemDAO itemDAO = FactoryDAOImpl.getFactoryDAOInstance().getItemDAO();
    private static ServiceFactory serviceFactory = ServiceFactoryImpl.getServiceFactoryInstance();


    public static DrinkService getDrinkServiceInstance() {
        if (drinkServiceInstance == null) {
            synchronized (DrinkService.class) {
                if (drinkServiceInstance == null)
                    drinkServiceInstance = new DrinkService();
            }
        }
        return drinkServiceInstance;
    }

    public synchronized boolean makeDrink(Drink drink, User payer) {
        boolean result = true;

        String cup = CupService.getCupServiceInstance().chooseCup4Drink(drink);
        if (cup == null)
            return false;

        List<Ingredient> ingredients = ingredientDAO.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            for (Ingredient drinkIngredient : drink.getIngredients()) {
                if (ingredient.equals(drinkIngredient)) {
                    ingredient.setQuantity(ingredient.getQuantity() - drinkIngredient.getQuantity());
                    result = ingredient.getQuantity() < 0 ? false : result;
                }
            }
        }
        if (!result)
            return result;
        BigDecimal balance = serviceFactory.getUserService().getUser(payer.getLogin()).getBalance().subtract(drink.getPrice());
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            return false;
        }
        payer.setBalance(balance);
        serviceFactory.getUserService().updateBalance(balance, payer.getLogin());
        itemDAO.updateItem(cup, CupService.getCupServiceInstance().getCupCount(cup) - 1);
        int sticksCount = serviceFactory.getStickService().getSticksCount();
        if (sticksCount > 0 && drink.getSugar() > 0)
            itemDAO.updateItem(Stick.DB_NAME, sticksCount - 1);
        for (Ingredient ingredient : ingredients)
            ingredientDAO.updateIngredient(ingredient.getName(), ingredient.getQuantity());
        return result;
    }

    public synchronized List<Drink> getAvailableDrinks() {
        Drink[] drinks = {new Espresso(), new Americano(), new Cappuccino(), new Latte(), new BlackTea(), new GreenTea()};
        List<Drink> result = new ArrayList<>();
        List<Ingredient> ingredients = ingredientDAO.getAllIngredients();
        for (Drink drink : drinks) {
            boolean available = true;
            for (Ingredient drinkIngredient : drink.getIngredients()) {
                for (Ingredient ingredient : ingredients) {
                    if (drinkIngredient.getName().equals(ingredient.getName()))
                        available = drinkIngredient.getQuantity() > ingredient.getQuantity() ? false : available;
                }
            }
            if (available)
                result.add(drink);
        }
        return result;
    }

    public synchronized List<Drink> getRequestedDrinks(String drinkTypeFilter, String milkFilter) {
        List<Drink> availableDrinks = getAvailableDrinks();
        if (drinkTypeFilter == null || drinkTypeFilter.equals("allDrinks"))
            if (milkFilter == null || milkFilter.equals("allDrinks")) {
                return availableDrinks;
            }
        List<Drink> resultDrinks = new ArrayList<>();
        for (Drink drink : availableDrinks) {
            boolean allowed = true;
            if (drinkTypeFilter != null && !drinkTypeFilter.equals("allDrinks")) {
                switch (drinkTypeFilter) {
                    case "coffee":
                        allowed = drink.isTea() ? false : allowed;
                        break;
                    case "tea":
                        allowed = drink.isCoffee() ? false : allowed;
                        break;
                }
            }
            if (milkFilter != null && !milkFilter.equals("allDrinks")) {
                switch (milkFilter) {
                    case "withMilk":
                        allowed = drink.isTea() ? false : (((Coffee) drink).getMilk() == 0 ? false : allowed);
                        break;
                    case "withoutMilk":
                        allowed = drink.isTea() ? allowed : (((Coffee) drink).getMilk() > 0 ? false : allowed);
                        break;
                }
            }
            if (allowed)
                resultDrinks.add(drink);
        }
        return resultDrinks;
    }
}
