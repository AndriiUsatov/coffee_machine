package dao.factory;

import dao.IngredientDAO;
import dao.ItemDAO;
import dao.UserDAO;

public interface FactoryDAO {

    IngredientDAO getIngredientDAO();

    ItemDAO getItemDAO();

    UserDAO getUserDAO();
}
