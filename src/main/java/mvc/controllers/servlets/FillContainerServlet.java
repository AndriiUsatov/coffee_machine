package mvc.controllers.servlets;

import mvc.controllers.Dispatcher;
import mvc.models.entities.users.User;
import services.IngredientService;
import services.ItemService;
import mvc.models.entities.machines.CoffeeMachine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FillContainerServlet extends Dispatcher {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        User admin = (User) req.getSession().getAttribute("currentUser");
        if(!admin.isAdmin())
            forward("/pages/error.jsp",req,resp);
        String ingredient = null;
        int quantity = 0;
        String item = null;
        int count = 0;
        if (req.getParameter("water") != null) {
            ingredient = "water";
            quantity = CoffeeMachine.getWaterMaxQuantity();
        } else if (req.getParameter("milk") != null) {
            ingredient = "milk";
            quantity = CoffeeMachine.getMilkMaxQuantity();
        } else if (req.getParameter("coffee") != null) {
            ingredient = "coffee";
            quantity = CoffeeMachine.getCoffeeMaxQuantity();
        } else if (req.getParameter("sugar") != null) {
            ingredient = "sugar";
            quantity = CoffeeMachine.getSugarMaxQuantity();
        } else if (req.getParameter("black_tea") != null) {
            ingredient = "black_tea";
            quantity = CoffeeMachine.getBlackTeaMaxQuantity();
        } else if (req.getParameter("green_tea") != null) {
            ingredient = "green_tea";
            quantity = CoffeeMachine.getGreenTeaMaxQuantity();
        } else if (req.getParameter("sticks") != null) {
            item = "sticks";
            count = CoffeeMachine.getSticksMaxCount();
        } else if (req.getParameter("little_cups") != null) {
            item = "little_cups";
            count = CoffeeMachine.getLittleCupsMaxCount();
        } else if (req.getParameter("middle_cups") != null) {
            item = "middle_cups";
            count = CoffeeMachine.getMiddleCupsMaxCount();
        } else if (req.getParameter("big_cups") != null) {
            item = "big_cups";
            count = CoffeeMachine.getBigCupsMaxCount();
        }

        if (ingredient != null) {
            if (IngredientService.getIngredientServiceInstance().getIngredientByName(ingredient).filledInPercentage() < 6)
                IngredientService.getIngredientServiceInstance().updateIngredient(ingredient, quantity, admin);
        }
        if (item != null) {
            if (ItemService.getItemServiceInstance().getItemByName(item).filledInPercentage() < 6)
                ItemService.getItemServiceInstance().updateItem(item, count, admin);
        }
        forward("/login", req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
