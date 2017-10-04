package services;

import dao.factory.impl.FactoryDAOImpl;
import entities.items.impl.Stick;

public class StickService {

    private static StickService stickServiceInstance;

    private StickService() {
    }

    public static StickService getStickServiceInstance() {
        if (stickServiceInstance == null) {
            synchronized (StickService.class) {
                if (stickServiceInstance == null) {
                    stickServiceInstance = new StickService();
                }
            }
        }
        return stickServiceInstance;
    }

    public int getSticksCount() {
        return FactoryDAOImpl.getFactoryDAOInstance().getItemDAO().getItemByName(Stick.DB_NAME).getCount();
    }
}
