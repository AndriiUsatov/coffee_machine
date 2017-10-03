package dao.item_dao;

import mvc.models.entities.Fill;
import mvc.models.entities.items.Item;
import mvc.models.entities.users.User;

public interface ItemDAO {

    Item[] getAllItems();

    boolean updateItem(String itemName, int count);

    Item getItemByName(String name);

    void noteItemUpdate(String itemName, int updateCount, User admin);

    Fill[] getItemFills();

}
