package dao.factory;

import dao.*;

public interface FactoryDAO {

    IngredientDAO getIngredientDAO();

    ItemDAO getItemDAO();

    UserDAO getUserDAO();

    FillDAO getFillDAO();

    HumanDAO getHumanDAO();
}
