package dao.factory.impl;

import dao.IngredientDAO;
import dao.ItemDAO;
import dao.UserDAO;
import dao.factory.FactoryDAO;
import dao.impl.IngredientDAOImpl;
import dao.impl.ItemDAOImpl;
import dao.impl.UserDAOImpl;

public class FactoryDAOImpl implements FactoryDAO {

    private static FactoryDAO factoryDAOInstance;

    private FactoryDAOImpl(){}

    public static FactoryDAO getFactoryDAOInstance(){
        if(factoryDAOInstance == null){
            synchronized (FactoryDAOImpl.class){
                if(factoryDAOInstance == null)
                    factoryDAOInstance = new FactoryDAOImpl();
            }
        }
        return factoryDAOInstance;
    }

    @Override
    public IngredientDAO getIngredientDAO() {
        return IngredientDAOImpl.getIngredientDAOInstance();
    }

    @Override
    public ItemDAO getItemDAO() {
        return ItemDAOImpl.getItemDAOInstance();
    }

    @Override
    public UserDAO getUserDAO() {
        return UserDAOImpl.getUserDAOInstance();
    }
}
