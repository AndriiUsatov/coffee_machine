package services.factory;


import services.*;

public interface ServiceFactory {

    CupService getCupService();

    DrinkService getDrinkService();

    IngredientService getIngredientService();

    ItemService getItemService();

    PasswordService getPasswordService();

    StickService getStickService();

    UserService getUserService();
}
