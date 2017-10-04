package services;

import dao.ItemDAO;
import dao.factory.impl.FactoryDAOImpl;
import entities.Fill;
import entities.items.Item;
import entities.users.User;

public class ItemService {
    private static ItemService itemServiceInstance;
    private ItemDAO itemDAO = FactoryDAOImpl.getFactoryDAOInstance().getItemDAO();

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

    public synchronized Item[] getItems(){
        return itemDAO.getAllItems();
    }

    public synchronized void updateItem(String itemName,int count, User admin){
        int updateCount = count - itemDAO.getItemByName(itemName).getCount();
        if(itemDAO.updateItem(itemName,count)){
            itemDAO.noteItemUpdate(itemName,updateCount,admin);
        }
    }

    public synchronized Item getItemByName(String itemName){
        return itemDAO.getItemByName(itemName);
    }

    public synchronized Fill[] getAllFills(){
        return itemDAO.getItemFills();
    }
}
