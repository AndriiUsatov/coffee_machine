package controllers.servlets;

import controllers.Dispatcher;
import entities.users.User;
import entities.CoffeeMachine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FillContainerServlet extends Dispatcher {
    private static CoffeeMachine coffeeMachine = CoffeeMachine.getCoffeeMachineInstance();
    private static Map<String, Integer> ingr = new HashMap<String, Integer>() {
        {
            put("water", coffeeMachine.getWaterMaxQuantity());
            put("milk", coffeeMachine.getMilkMaxQuantity());
            put("coffee", coffeeMachine.getCoffeeMaxQuantity());
            put("sugar", coffeeMachine.getSugarMaxQuantity());
            put("black_tea", coffeeMachine.getBlackTeaMaxQuantity());
            put("green_tea", coffeeMachine.getGreenTeaMaxQuantity());
        }
    };
    private static Map<String, Integer> items = new HashMap<String, Integer>() {
        {
            put("sticks", coffeeMachine.getSticksMaxCount());
            put("little_cups", coffeeMachine.getLittleCupsMaxCount());
            put("middle_cups", coffeeMachine.getMiddleCupsMaxCount());
            put("big_cups", coffeeMachine.getBigCupsMaxCount());
        }
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        User admin = (User) req.getSession().getAttribute("currentUser");
        if (!admin.isAdmin())
            forward("/pages/error.jsp", req, resp);
        String ingredient = null;
        String item = null;
        int quantity = 0;
        for (Map.Entry entry : ingr.entrySet()) {
            String key = (String) entry.getKey();
            if (req.getParameter(key) != null) {
                ingredient = key;
                quantity = (Integer) entry.getValue();
                break;
            }
        }
        for (Map.Entry entry : items.entrySet()) {
            String key = (String) entry.getKey();
            if (req.getParameter(key) != null) {
                item = key;
                quantity = (Integer) entry.getValue();
                break;
            }
        }

        if (ingredient != null) {
            if (serviceFactory.getIngredientService().getIngredientByName(ingredient).filledInPercentage() < 6)
                serviceFactory.getIngredientService().updateIngredient(ingredient, quantity, admin);
        }
        if (item != null) {
            if (serviceFactory.getItemService().getItemByName(item).filledInPercentage() < 6)
                serviceFactory.getItemService().updateItem(item, quantity, admin);
        }
        forward("/login", req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
