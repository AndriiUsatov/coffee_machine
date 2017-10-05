package dao;

import entities.Fill;
import entities.items.Item;
import entities.users.User;

/**
 * DAO for operations with Item objects
 */

public interface ItemDAO {

    /**
     * @return Returns array of Item objects from database
     */

    Item[] getAllItems();

    /**
     * @param itemName - The name of the item
     * @param count    - The parameter sets the quantity of the item
     * @return Return true if item updated successfully
     */

    boolean updateItem(String itemName, int count);

    /**@param name - The name of item
     * @return Returns the Item object from database
     */

    Item getItemByName(String name);

    /**@param itemName - The name of the item
     * @param  updateCount - The parameter sets the updated quantity of the item
     * @param admin - The user object that filled the item
     */

    void noteItemUpdate(String itemName, int updateCount, User admin);

    Fill[] getItemFills();

}
