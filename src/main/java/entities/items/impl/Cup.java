package entities.items.impl;

import entities.items.Item;

public abstract class Cup extends Item {

    public Cup(String name, int count) {
        super(name, count);
    }

    public abstract int getSize();

    @Override
    public String getName() {
        return super.getName() + "(" + getSize() + "ml)";
    }
}
