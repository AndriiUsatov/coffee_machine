package services;

import dao.ItemDAO;
import dao.factory.impl.FactoryDAOImpl;
import entities.drinks.Drink;
import entities.items.impl.BigCup;
import entities.items.impl.LittleCup;
import entities.items.impl.MiddleCup;

public class CupService {
    private static CupService cupServiceInstance;
    private static ItemDAO itemDAO = FactoryDAOImpl.getFactoryDAOInstance().getItemDAO();

    private CupService() {
    }

    public static CupService getCupServiceInstance() {
        if (cupServiceInstance == null) {
            synchronized (CupService.class) {
                if (cupServiceInstance == null)
                    cupServiceInstance = new CupService();
            }
        }
        return cupServiceInstance;
    }

    public synchronized String chooseCup4Drink(Drink drink) {
        String cup = null;
        if (LittleCup.SIZE >= drink.getTotalSize()
                && itemDAO.getItemByName(LittleCup.DB_NAME).getCount() > 0) {
            cup = LittleCup.DB_NAME;
        } else if (MiddleCup.SIZE >= drink.getTotalSize()
                && itemDAO.getItemByName(MiddleCup.DB_NAME).getCount() > 0) {
            cup = MiddleCup.DB_NAME;
        } else if (BigCup.SIZE >= drink.getTotalSize()
                && itemDAO.getItemByName(BigCup.DB_NAME).getCount() > 0) {
            cup = BigCup.DB_NAME;
        }
        return cup;
    }

    public synchronized int getCupCount(String cupName) {
        if (cupName == null)
            return 0;
        return itemDAO.getItemByName(cupName).getCount();
    }
}
