package mvc.models.entities.drinks.coffee.impl;

import mvc.models.entities.drinks.coffee.Coffee;
import mvc.models.entities.ingredients.Ingredient;

import java.math.BigDecimal;


public class Espresso extends Coffee {
    private static final int WATER = 30;
    private static final int COFFEE = 9;
    private static final int MILK = 0;
    private static final BigDecimal PRICE = new BigDecimal(10);
    private int sugar;

    public Espresso(){ sugar = REGULAR_SUGAR_COUNT; }

    public Espresso(int sugar){ this.sugar = sugar * GRAM_OF_SUGAR_IN_SPOON; }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getWater() {
        return WATER;
    }

    @Override
    public int getCoffee() {
        return COFFEE;
    }

    @Override
    public int getMilk() {
        return MILK;
    }

    @Override
    public int getSugar() {
        return sugar;
    }

    @Override
    public BigDecimal getPrice() {
        return PRICE;
    }

    @Override
    public Ingredient[] getIngredients() {
        Ingredient[] result = {
                new Ingredient("water", WATER),
                new Ingredient("coffee", COFFEE),
                new Ingredient("milk", MILK),
                new Ingredient("sugar", sugar)};
        return result;
    }

}