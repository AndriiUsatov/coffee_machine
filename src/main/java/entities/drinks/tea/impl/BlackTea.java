package entities.drinks.tea.impl;

import entities.drinks.tea.Tea;
import entities.ingredients.Ingredient;


public class BlackTea extends Tea {
    private static final int BLACK_TEA = 10;
    private int sugar;

    public BlackTea() {
        sugar = REGULAR_SUGAR_COUNT;
    }

    public BlackTea(int sugar) {
        this.sugar = sugar * GRAM_OF_SUGAR_IN_SPOON;
    }

    @Override
    public int getSugar() {
        return sugar;
    }

    @Override
    public Ingredient[] getIngredients() {
        Ingredient[] result = {
                new Ingredient("water", getWater()),
                new Ingredient("black_tea", BLACK_TEA),
                new Ingredient("sugar", getSugar())};
        return result;
    }

    @Override
    public String getName() {
        return "Black tea";
    }

    @Override
    public int getBlackTea() {
        return BLACK_TEA;
    }

    @Override
    public void setSugar(int sugar) {
        this.sugar = sugar;
    }
}
