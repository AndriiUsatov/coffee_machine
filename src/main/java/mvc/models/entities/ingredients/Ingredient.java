package mvc.models.entities.ingredients;

import mvc.models.entities.machines.CoffeeMachine;

import java.sql.Date;

public class Ingredient {
    private static final Long MILLIS_IN_DAY = 1000L * 60 * 60 * 24;
    public static final Long WATER_EXPIRATION_DATE = MILLIS_IN_DAY * 7;
    public static final Long COFFEE_EXPIRATION_DATE = MILLIS_IN_DAY * 182;
    public static final Long MILK_EXPIRATION_DATE = MILLIS_IN_DAY * 3;
    public static final Long SUGAR_EXPIRATION_DATE = MILLIS_IN_DAY * 365 * 2;
    public static final Long TEA_EXPIRATION_DATE = MILLIS_IN_DAY * 365;
    private String name;
    private int quantity;
    private int id;
    private Date expirationDate;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Ingredient(String name, int quantity, int id) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCamelCaseName() {
        return (name.substring(0, 1).toUpperCase() +
                name.substring(1).toLowerCase()).replaceAll("_", " ");
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    public int getMaxQuantity() {
        switch (name) {
            case "milk":
                return CoffeeMachine.getMilkMaxQuantity();
            case "coffee":
                return CoffeeMachine.getCoffeeMaxQuantity();
            case "water":
                return CoffeeMachine.getWaterMaxQuantity();
            case "sugar":
                return CoffeeMachine.getSugarMaxQuantity();
            case "black_tea":
                return CoffeeMachine.getBlackTeaMaxQuantity();
            case "green_tea":
                return CoffeeMachine.getGreenTeaMaxQuantity();
        }
        return -1;
    }

    public int filledInPercentage() {
        return (int) (((double) quantity / getMaxQuantity()) * 100);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public Long getMaxExpirationDate(){
        switch (name){
            case "water":
                return WATER_EXPIRATION_DATE;
            case "coffee":
                return COFFEE_EXPIRATION_DATE;
            case "milk":
                return  MILK_EXPIRATION_DATE;
            case "sugar":
                return SUGAR_EXPIRATION_DATE;
            case "black_tea":
                return TEA_EXPIRATION_DATE;
            case "green_tea":
                return TEA_EXPIRATION_DATE;

        }
    return null;
    }
}
