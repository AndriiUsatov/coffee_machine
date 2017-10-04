package entities;

import java.io.IOException;
import java.util.Properties;

public class CoffeeMachine {

//    public static final int WATER_MAX_QUANTITY = 10000;
//    public static final int MILK_MAX_QUANTITY = 4000;
//    public static final int COFFEE_MAX_QUANTITY = 2000;
//    public static final int SUGAR_MAX_QUANTITY = 3000;
//    public static final int STICKS_MAX_COUNT = 1000;
//    public static final int LITTLE_CUPS_MAX_COUNT = 500;
//    public static final int MIDDLE_CUPS_MAX_COUNT = 500;
//    public static final int BIG_CUPS_MAX_COUNT = 500;
//    public static final int BLACK_TEA_MAX_QUANTITY = 2000;
//    public static final int GREEN_TEA_MAX_QUANTITY = 2000;
//    public static final int MACHINE_ID = 1;
    private static Properties properties = new Properties();
    private static CoffeeMachine coffeeMachineInstance;

    private CoffeeMachine(){
        try {
            properties.load(getClass().getResourceAsStream("/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CoffeeMachine getCoffeeMachineInstance(){
        if(coffeeMachineInstance == null){
            synchronized (CoffeeMachine.class){
                if(coffeeMachineInstance == null)
                    coffeeMachineInstance = new CoffeeMachine();
            }
        }
        return coffeeMachineInstance;
    }

    public int getWaterMaxQuantity() {
        return Integer.parseInt(properties.getProperty("water_max_quantity"));
    }

    public int getMilkMaxQuantity() {
        return Integer.parseInt(properties.getProperty("milk_max_quantity"));
    }

    public int getCoffeeMaxQuantity() {
        return Integer.parseInt(properties.getProperty("coffee_max_quantity"));
    }

    public int getSugarMaxQuantity() {
        return Integer.parseInt(properties.getProperty("sugar_max_quantity"));
    }

    public int getSticksMaxCount() {
        return Integer.parseInt(properties.getProperty("sticks_max_count"));
    }

    public int getLittleCupsMaxCount() {
        return Integer.parseInt(properties.getProperty("little_cups_max_count"));
    }

    public int getMiddleCupsMaxCount() {
        return Integer.parseInt(properties.getProperty("middle_cups_max_count"));
    }

    public int getBigCupsMaxCount() {
        return Integer.parseInt(properties.getProperty("big_cups_max_count"));
    }

    public int getBlackTeaMaxQuantity() {
        return Integer.parseInt(properties.getProperty("black_tea_max_quantity"));
    }

    public int getGreenTeaMaxQuantity() {
        return Integer.parseInt(properties.getProperty("green_tea_max_quantity"));
    }

    public int getMachineId() {
        return Integer.parseInt(properties.getProperty("machine_id"));
    }
}
