package services;

import dao.item_dao.impl.ItemDAOImpl;
import mvc.models.entities.drinks.Drink;
import mvc.models.entities.items.cups.BigCup;
import mvc.models.entities.items.cups.LittleCup;
import mvc.models.entities.items.cups.MiddleCup;

public class CupService {
    private static CupService cupServiceInstance;

    private CupService(){}

    public static CupService getCupServiceInstance(){
        if(cupServiceInstance == null){
            synchronized (CupService.class){
                if(cupServiceInstance == null)
                    cupServiceInstance = new CupService();
            }
        }
        return cupServiceInstance;
    }

    public synchronized String chooseCup4Drink(Drink drink){
        String cup = null;
        if(LittleCup.SIZE >= drink.getTotalSize()
                && ItemDAOImpl.getItemDAOInstance().getItemByName(LittleCup.DB_NAME).getCount() > 0){
            cup = LittleCup.DB_NAME;
        }else if(MiddleCup.SIZE >= drink.getTotalSize()
                && ItemDAOImpl.getItemDAOInstance().getItemByName(MiddleCup.DB_NAME).getCount() > 0){
            cup = MiddleCup.DB_NAME;
        }else if(BigCup.SIZE >= drink.getTotalSize()
                && ItemDAOImpl.getItemDAOInstance().getItemByName(BigCup.DB_NAME).getCount() > 0){
            cup = BigCup.DB_NAME;
        }
        return cup;
    }

    public synchronized int getCupCount(String cupName){
        return ItemDAOImpl.getItemDAOInstance().getItemByName(cupName).getCount();
    }
}
