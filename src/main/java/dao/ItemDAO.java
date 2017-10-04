package dao;

import entities.Fill;
import entities.items.Item;
import entities.users.User;

public interface ItemDAO {

    Item[] getAllItems();

    boolean updateItem(String itemName, int count);

    Item getItemByName(String name);

    void noteItemUpdate(String itemName, int updateCount, User admin);

    Fill[] getItemFills();

}
