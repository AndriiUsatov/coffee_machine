package entities.drinks.coffee;


import entities.drinks.Drink;
import entities.ingredients.Ingredient;

public abstract class Coffee implements Drink{
    public abstract int getCoffee();
    public abstract int getMilk();

    @Override
    public boolean isCoffee() {
        return true;
    }

    @Override
    public boolean isTea() {
        return false;
    }

    @Override
    public int getTotalSize() {
        int result = 0;
        for(Ingredient ingredient : getIngredients()){
            result += ingredient.getQuantity();
        }
        return result;
    }
}
