package entities.drinks.coffee.impl;

import entities.drinks.coffee.Coffee;
import entities.ingredients.Ingredient;

import java.math.BigDecimal;

public class Latte extends Coffee {
    private static final int WATER = 80;
    private static final int COFFEE = 12;
    private static final int MILK = 100;
    private static final BigDecimal PRICE = new BigDecimal(20);
    private int sugar;

    public Latte() {
        sugar = REGULAR_SUGAR_COUNT;
    }

    public Latte(int sugar) {
        this.sugar = sugar * GRAM_OF_SUGAR_IN_SPOON;
    }

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
    @Override
    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

}
