package mvc.controllers.servlets;

import mvc.controllers.Dispatcher;
import services.DrinkService;
import services.UserService;
import mvc.models.entities.drinks.Drink;
import mvc.models.entities.drinks.coffee.impl.Americano;
import mvc.models.entities.drinks.coffee.impl.Cappuccino;
import mvc.models.entities.drinks.coffee.impl.Espresso;
import mvc.models.entities.drinks.coffee.impl.Latte;
import mvc.models.entities.drinks.tea.impl.BlackTea;
import mvc.models.entities.drinks.tea.impl.GreenTea;
import mvc.models.entities.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;


public class MakeDrinkServlet extends Dispatcher {

    private static final String[] drinks = {"espresso", "americano", "cappuccino", "latte", "black tea", "green tea"};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        int countOfSugar = req.getParameter("sugar") == null ? 0 : Integer.valueOf(req.getParameter("sugar"));
        User user = (User) req.getSession().getAttribute("currentUser");
        if (!user.isCustomer())
            forward("/pages/error.jsp", req, resp);

        Drink drink = null;
        for (String tmp : drinks) {
            if (req.getParameter(tmp) != null) {
                switch (tmp) {
                    case "espresso":
                        drink = new Espresso(countOfSugar);
                        break;
                    case "americano":
                        drink = new Americano(countOfSugar);
                        break;
                    case "cappuccino":
                        drink = new Cappuccino(countOfSugar);
                        break;
                    case "latte":
                        drink = new Latte(countOfSugar);
                        break;
                    case "black tea":
                        drink = new BlackTea(countOfSugar);
                        break;
                    case "green tea":
                        drink = new GreenTea(countOfSugar);
                        break;
                }
                break;
            }
        }
        if (drink == null) {
            forward("/pages/error.jsp", req, resp);
        }
        if (user.getBalance().compareTo(drink.getPrice()) >= 0 && DrinkService.getDrinkService().makeDrink(drink)) {
            BigDecimal balance = user.getBalance().subtract(drink.getPrice());
            user.setBalance(balance);
            UserService.getUserServiceInstance().updateBalance(balance, user.getLogin());
            req.setAttribute("drink", drink);
        } else {
            resp.setContentType("text/html");
            try {
                resp.getWriter().write("You haven't enough money.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        forward("/pages/ready.jsp", req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


}
