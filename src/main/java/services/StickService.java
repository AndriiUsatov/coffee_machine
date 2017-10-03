package services;

import dao.item_dao.impl.ItemDAOImpl;
import mvc.models.entities.items.sticks.Stick;

public class StickService {

    private static StickService stickServiceInstance;

    private StickService(){}

    public static StickService getStickServiceInstance(){
        if(stickServiceInstance == null){
            synchronized (StickService.class){
                if(stickServiceInstance == null){
                    stickServiceInstance = new StickService();
                }
            }
        }
        return stickServiceInstance;
    }

    public int getSticksCount(){
        return ItemDAOImpl.getItemDAOInstance().getItemByName(Stick.DB_NAME).getCount();
    }
}
