package mvc.models.entities.machines;

public class CoffeeMachine {
    private static final int WATER_MAX_QUANTITY = 10000;
    private static final int MILK_MAX_QUANTITY = 4000;
    private static final int COFFEE_MAX_QUANTITY = 2000;
    private static final int SUGAR_MAX_QUANTITY = 3000;
    private static final int STICKS_MAX_COUNT = 1000;
    private static final int LITTLE_CUPS_MAX_COUNT = 500;
    private static final int MIDDLE_CUPS_MAX_COUNT = 500;
    private static final int BIG_CUPS_MAX_COUNT = 500;
    private static final int BLACK_TEA_MAX_QUANTITY = 2000;
    private static final int GREEN_TEA_MAX_QUANTITY = 2000;
    private static final int MACHINE_ID = 1;

    public static int getWaterMaxQuantity() {
        return WATER_MAX_QUANTITY;
    }

    public static int getMilkMaxQuantity() {
        return MILK_MAX_QUANTITY;
    }

    public static int getCoffeeMaxQuantity() {
        return COFFEE_MAX_QUANTITY;
    }

    public static int getSugarMaxQuantity() {
        return SUGAR_MAX_QUANTITY;
    }

    public static int getSticksMaxCount() {
        return STICKS_MAX_COUNT;
    }

    public static int getLittleCupsMaxCount() {
        return LITTLE_CUPS_MAX_COUNT;
    }

    public static int getMiddleCupsMaxCount() {
        return MIDDLE_CUPS_MAX_COUNT;
    }

    public static int getBigCupsMaxCount() {
        return BIG_CUPS_MAX_COUNT;
    }

    public static int getBlackTeaMaxQuantity() {
        return BLACK_TEA_MAX_QUANTITY;
    }

    public static int getGreenTeaMaxQuantity() {
        return GREEN_TEA_MAX_QUANTITY;
    }

    public static int getMachineId(){ return MACHINE_ID; }
}
