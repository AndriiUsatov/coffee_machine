package services;

import dao.item_dao.ItemDAO;
import dao.item_dao.impl.ItemDAOImpl;
import mvc.models.entities.items.Item;
import mvc.models.entities.users.User;

public class ItemService {
    private static ItemService itemServiceInstance;
    private ItemDAO itemDAO = ItemDAOImpl.getItemDAOInstance();

    private ItemService(){}

    public static ItemService getItemServiceInstance(){
        if(itemServiceInstance == null){
            synchronized (ItemService.class){
                if(itemServiceInstance == null)
                    itemServiceInstance = new ItemService();
            }
        }
        return itemServiceInstance;
    }

    public Item[] getItems(){
        return itemDAO.getAllItems();
    }

    public void updateItem(String itemName,int count, User admin){
        int updateCount = count - itemDAO.getItemByName(itemName).getCount();
        if(itemDAO.updateItem(itemName,count)){
            itemDAO.noteItemUpdate(itemName,updateCount,admin);
        }
    }

    public Item getItemByName(String itemName){
        return itemDAO.getItemByName(itemName);
    }
}
