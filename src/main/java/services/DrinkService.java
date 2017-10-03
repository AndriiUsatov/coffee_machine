package services;


import dao.ingredient_dao.impl.IngredientDAOImpl;
import dao.item_dao.impl.ItemDAOImpl;
import mvc.models.entities.drinks.Drink;
import mvc.models.entities.drinks.coffee.*;
import mvc.models.entities.drinks.coffee.impl.Americano;
import mvc.models.entities.drinks.coffee.impl.Cappuccino;
import mvc.models.entities.drinks.coffee.impl.Espresso;
import mvc.models.entities.drinks.coffee.impl.Latte;
import mvc.models.entities.drinks.tea.impl.BlackTea;
import mvc.models.entities.drinks.tea.impl.GreenTea;
import mvc.models.entities.drinks.tea.Tea;
import mvc.models.entities.ingredients.Ingredient;
import mvc.models.entities.items.sticks.Stick;

import java.util.ArrayList;
import java.util.List;

public class DrinkService {

    private static DrinkService drinkService;

    public static DrinkService getDrinkService() {
        if (drinkService == null) {
            synchronized (DrinkService.class) {
                if (drinkService == null)
                    drinkService = new DrinkService();
            }
        }
        return drinkService;
    }

    public synchronized boolean makeDrink(Drink drink) {
        boolean result = true;

        String cup = CupService.getCupServiceInstance().chooseCup4Drink(drink);
        if (cup == null)
            return false;

        Ingredient[] ingredients = IngredientDAOImpl.getIngredientDAOInstance().getAllIngredients();
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

        ItemDAOImpl.getItemDAOInstance().updateItem(cup, CupService.getCupServiceInstance().getCupCount(cup) - 1);
        int sticksCount = StickService.getStickServiceInstance().getSticksCount();
        if (sticksCount > 0)
            ItemDAOImpl.getItemDAOInstance().updateItem(Stick.DB_NAME, sticksCount - 1);
        for (Ingredient ingredient : ingredients) {
            IngredientDAOImpl.getIngredientDAOInstance().updateIngredient(ingredient.getName(), ingredient.getQuantity());
        }
        return result;
    }

    public synchronized Drink[] getAvailableDrinks() {
        Drink[] drinks = {new Espresso(), new Americano(), new Cappuccino(), new Latte(), new BlackTea(), new GreenTea()};
        List<Drink> result = new ArrayList<>();
        Ingredient[] ingredients = IngredientDAOImpl.getIngredientDAOInstance().getAllIngredients();
        for (Drink drink : drinks) {
            boolean available = true;
            if (drink.isCoffee()) {
                Coffee coffee = (Coffee) drink;
                for (Ingredient ingredient : ingredients) {
                    switch (ingredient.getName()) {
                        case "water":
                            available = coffee.getWater() > ingredient.getQuantity() ? false : available;
                            break;
                        case "milk":
                            available = coffee.getMilk() > ingredient.getQuantity() ? false : available;
                            break;
                        case "coffee":
                            available = coffee.getCoffee() > ingredient.getQuantity() ? false : available;
                            break;
                    }
                }
            }
            if (drink.isTea()) {
                Tea tea = (Tea) drink;
                for (Ingredient ingredient : ingredients) {
                    switch (ingredient.getName()) {
                        case "water":
                            available = tea.getWater() > ingredient.getQuantity() ? false : available;
                            break;
                        case "black_tea":
                            available = tea.getBlackTea() > ingredient.getQuantity() ? false : available;
                            break;
                        case "green_tea":
                            available = tea.getGreenTea() > ingredient.getQuantity() ? false : available;
                            break;
                    }
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
