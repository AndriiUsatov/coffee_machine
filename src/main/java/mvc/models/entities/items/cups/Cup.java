package mvc.models.entities.items.cups;

import mvc.models.entities.items.Item;


public abstract class Cup extends Item {

    public Cup(String name, int count) {
        super(name, count);
    }

    public abstract int getSize();

    @Override
    public String getName(){
        return super.getName() + "(" + getSize() + "ml)";
    }
}
