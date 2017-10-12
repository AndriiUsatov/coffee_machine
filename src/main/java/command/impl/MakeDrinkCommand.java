package command.impl;

import command.Command;
import entities.drinks.Drink;
import entities.drinks.coffee.impl.Americano;
import entities.drinks.coffee.impl.Cappuccino;
import entities.drinks.coffee.impl.Espresso;
import entities.drinks.coffee.impl.Latte;
import entities.drinks.tea.impl.BlackTea;
import entities.drinks.tea.impl.GreenTea;
import entities.users.Role;
import entities.users.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class MakeDrinkCommand implements Command {

    private static List<Drink> drinks = new ArrayList<Drink>() {
        {
            add(new Espresso());
            add(new Americano());
            add(new Cappuccino());
            add(new Latte());
            add(new BlackTea());
            add(new GreenTea());
        }
    };

    @Override
    public String execute(HttpServletRequest request) {
        int countOfSugar = request.getParameter("sugar") == null ? 0 : Integer.valueOf(request.getParameter("sugar"));
        if (!USER_VALIDATOR.validateSession(request, Role.CUSTOMER))
            return COMMAND_FACTORY.getCommand("/machine/login").execute(request);
        User user = (User) request.getSession().getAttribute("currentUser");
        Drink selectedDrink = null;
        for (Drink drink : drinks) {
            String drinkValue = drink.getName() + " " + drink.getPrice().toString() + " UAH";
            if (drinkValue.equals(request.getParameter("selectedDrink")))
                selectedDrink = drink;
        }
        if (selectedDrink == null)
            return COMMAND_FACTORY.getCommand("/machine/login").execute(request);
        selectedDrink.setSugar(countOfSugar * Drink.GRAM_OF_SUGAR_IN_SPOON);
        if (user.getBalance().compareTo(selectedDrink.getPrice()) >= 0 && SERVICE_FACTORY.getDrinkService().makeDrink(selectedDrink, user)) {
            request.getSession().setAttribute("drink", selectedDrink);
            USER_VALIDATOR.validateSession(request, Role.CUSTOMER);
            return "/pages/ready.jsp";
        } else if (user.getBalance().compareTo(selectedDrink.getPrice()) < 0) {
            return "/pages/not_enough_money.jsp";
        }
        return COMMAND_FACTORY.getCommand("/machine/login").execute(request);
    }

}
