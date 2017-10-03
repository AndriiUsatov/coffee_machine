package mvc.models.entities.items.sticks;


import mvc.models.entities.items.Item;
import mvc.models.entities.machines.CoffeeMachine;

public class Stick extends Item {
    public static final String DB_NAME = "sticks";

    public Stick(int count) {
        super("Stick", count);
    }

    @Override
    public String getCanonicalName() {
        return "sticks";
    }

    @Override
    public int getMaxCount() {
        return CoffeeMachine.getSticksMaxCount();
    }

    @Override
    public String getDBName() {
        return DB_NAME;
    }
}
