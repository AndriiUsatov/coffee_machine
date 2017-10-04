package controllers.servlets;

import controllers.Dispatcher;
import entities.drinks.Drink;
import entities.drinks.coffee.impl.Americano;
import entities.drinks.coffee.impl.Cappuccino;
import entities.drinks.coffee.impl.Espresso;
import entities.drinks.coffee.impl.Latte;
import entities.drinks.tea.impl.BlackTea;
import entities.drinks.tea.impl.GreenTea;
import entities.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MakeDrinkServlet extends Dispatcher {

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        int countOfSugar = req.getParameter("sugar") == null ? 0 : Integer.valueOf(req.getParameter("sugar"));
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null || !user.isCustomer())
            forward("/pages/error.jsp", req, resp);
        Drink selectedDrink = null;
        for (Drink drink : drinks) {
            if (drink.getName().equals(req.getParameter("selectedDrink"))) {
                selectedDrink = drink;
            }
        }
        if (selectedDrink == null) {
            forward("/pages/error.jsp", req, resp);
        }
        selectedDrink.setSugar(countOfSugar);
        if (user.getBalance().compareTo(selectedDrink.getPrice()) >= 0 && serviceFactory.getDrinkService().makeDrink(selectedDrink)) {
            System.out.println(user.getBalance());
            System.out.println(selectedDrink.getPrice());
            BigDecimal balance = user.getBalance().subtract(selectedDrink.getPrice());
            user.setBalance(balance);
            serviceFactory.getUserService().updateBalance(balance, user.getLogin());
            req.setAttribute("drink", selectedDrink);
            forward("/pages/ready.jsp", req, resp);
        } else {
            if (user.getBalance().compareTo(selectedDrink.getPrice()) < 0) {
                resp.setContentType("text/html");
                try {
                    resp.getWriter().write("You haven't enough money.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                forward("/pages/error.jsp", req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


}
