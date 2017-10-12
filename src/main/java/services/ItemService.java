package services;

import dao.FillDAO;
import dao.ItemDAO;
import dao.factory.impl.FactoryDAOImpl;
import entities.Fill;
import entities.items.Item;
import entities.users.User;

import java.util.List;

public class ItemService {
    private static ItemService itemServiceInstance;
    private ItemDAO itemDAO = FactoryDAOImpl.getFactoryDAOInstance().getItemDAO();
    private FillDAO fillDAO = FactoryDAOImpl.getFactoryDAOInstance().getFillDAO();

    private ItemService() {
    }

    public static ItemService getItemServiceInstance() {
        if (itemServiceInstance == null) {
            synchronized (ItemService.class) {
                if (itemServiceInstance == null)
                    itemServiceInstance = new ItemService();
            }
        }
        return itemServiceInstance;
    }

    public synchronized List<Item> getItems() {
        return itemDAO.getAllItems();
    }

    public synchronized void updateItem(String itemName, int count, User admin) {
        int updateCount = count - itemDAO.getItemByName(itemName).getCount();
        if (itemDAO.updateItem(itemName, count)) {
            fillDAO.noteItemFill(itemDAO.getItemByName(itemName), updateCount, admin);
        }
    }

    public synchronized Item getItemByName(String itemName) {
        return itemDAO.getItemByName(itemName);
    }

    public synchronized List<Fill> getItemFills(int skipCount) {
        return fillDAO.getItemFillsLimit(skipCount);
    }

    public synchronized Integer getItemFillsCount() {
        return fillDAO.getItemFillsLength();
    }
}
