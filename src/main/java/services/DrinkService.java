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
import entities.drinks.tea.Tea;
import entities.ingredients.Ingredient;
import entities.items.impl.Stick;

import java.util.ArrayList;
import java.util.List;

public class DrinkService {

    private static DrinkService drinkServiceInstance;
    private static IngredientDAO ingredientDAO = FactoryDAOImpl.getFactoryDAOInstance().getIngredientDAO();
    private static ItemDAO itemDAO = FactoryDAOImpl.getFactoryDAOInstance().getItemDAO();


    public static DrinkService getDrinkServiceInstance() {
        if (drinkServiceInstance == null) {
            synchronized (DrinkService.class) {
                if (drinkServiceInstance == null)
                    drinkServiceInstance = new DrinkService();
            }
        }
        return drinkServiceInstance;
    }

    public synchronized boolean makeDrink(Drink drink) {
        boolean result = true;

        String cup = CupService.getCupServiceInstance().chooseCup4Drink(drink);
        if (cup == null)
            return false;

        Ingredient[] ingredients = ingredientDAO.getAllIngredients();
        if (drink.isCoffee()) {
            Coffee coffee = (Coffee) drink;
            for (Ingredient ingredient : ingredients) {
                switch (ingredient.getName()) {
                    case "water":
                        ingredient.setQuantity(ingredient.getQuantity() - coffee.getWater());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                    case "milk":
                        ingredient.setQuantity(ingredient.getQuantity() - coffee.getMilk());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                    case "coffee":
                        ingredient.setQuantity(ingredient.getQuantity() - coffee.getCoffee());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                    case "sugar":
                        ingredient.setQuantity(ingredient.getQuantity() - coffee.getSugar());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                }
                if (!result)
                    return result;
            }
        }
        if (drink.isTea()) {
            Tea tea = (Tea) drink;
            for (Ingredient ingredient : ingredients) {
                switch (ingredient.getName()) {
                    case "water":
                        ingredient.setQuantity(ingredient.getQuantity() - tea.getWater());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                    case "black_tea":
                        ingredient.setQuantity(ingredient.getQuantity() - tea.getBlackTea());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                    case "green_tea":
                        ingredient.setQuantity(ingredient.getQuantity() - tea.getGreenTea());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                    case "sugar":
                        ingredient.setQuantity(ingredient.getQuantity() - drink.getSugar());
                        result = ingredient.getQuantity() < 0 ? false : result;
                        break;
                }
                if (!result)
                    return result;
            }
        }

        itemDAO.updateItem(cup, CupService.getCupServiceInstance().getCupCount(cup) - 1);
        int sticksCount = StickService.getStickServiceInstance().getSticksCount();
        if (sticksCount > 0)
            itemDAO.updateItem(Stick.DB_NAME, sticksCount - 1);
        for (Ingredient ingredient : ingredients) {
            ingredientDAO.updateIngredient(ingredient.getName(), ingredient.getQuantity());
        }
        return result;
    }

    public synchronized Drink[] getAvailableDrinks() {
        Drink[] drinks = {new Espresso(), new Americano(), new Cappuccino(), new Latte(), new BlackTea(), new GreenTea()};
        List<Drink> result = new ArrayList<>();
        Ingredient[] ingredients = ingredientDAO.getAllIngredients();
        for (Drink drink : drinks) {
            boolean available = true;
                for(Ingredient drinkIngredient : drink.getIngredients()){
                    for(Ingredient ingredient : ingredients){
                        if(drinkIngredient.getName().equals(ingredient.getName()))
                            available = drinkIngredient.getQuantity() > ingredient.getQuantity() ? false : available;
                    }
                }
            if (available)
                result.add(drink);
        }
        return result.toArray(new Drink[result.size()]);
    }

    public synchronized Drink[] getRequestedDrinks(String drinkTypeFilter, String milkFilter) {
        Drink[] availableDrinks = getAvailableDrinks();
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
        return resultDrinks.toArray(new Drink[resultDrinks.size()]);
    }
}
