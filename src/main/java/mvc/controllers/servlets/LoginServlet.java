package mvc.controllers.servlets;

import mvc.controllers.Dispatcher;
import services.DrinkService;
import services.IngredientService;
import services.ItemService;
import services.UserService;
import mvc.models.validators.UserValidator;
import mvc.models.entities.drinks.Drink;
import mvc.models.entities.ingredients.Ingredient;
import mvc.models.entities.items.Item;
import mvc.models.entities.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends Dispatcher {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        if (req.getParameter("registrationButton") != null) {
            forward("/pages/registration.jsp", req, resp);
        } else if (req.getParameter("loginButton") != null) {
            if (UserValidator.getUserValidatorInstance().validateLoginIn(req.getParameter("login"), req.getParameter("password"))) {
                User user = UserService.getUserServiceInstance().getUser(req.getParameter("login"));
                req.getSession().setAttribute("currentUser", user);
                if (user.isCustomer()) {
                    customerForward(req, resp);
                }
                if (user.isAdmin()) {
                    adminForward(req, resp);
                }
            } else {
                forward("/pages/index.jsp", req, resp);
            }
        } else if ((req.getSession().getAttribute("currentUser")) != null) {
            User user = (User) req.getSession().getAttribute("currentUser");
            if (user.isAdmin()) {
                adminForward(req, resp);
            }
            if (user.isCustomer()) {
                customerForward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void customerForward(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("milkAvailability") != null)
            req.getSession().setAttribute("milkAvailability", req.getParameter("milkAvailability"));
        else if (req.getSession().getAttribute("milkAvailability") == null)
            req.getSession().setAttribute("milkAvailability", "allDrinks");
        if (req.getParameter("drinkType") != null)
            req.getSession().setAttribute("drinkType", req.getParameter("drinkType"));
        else if (req.getSession().getAttribute("drinkType") == null)
            req.getSession().setAttribute("drinkType", "allDrinks");
        Drink[] drinks = DrinkService.getDrinkService().getRequestedDrinks((String) req.getSession().getAttribute("drinkType"),
                (String) req.getSession().getAttribute("milkAvailability"));
        req.setAttribute("drinks", drinks);
        forward("/pages/menu.jsp", req, resp);
    }

    private void adminForward(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("login") != null) {
            User user = UserService.getUserServiceInstance().getUser(req.getParameter("login"));
            req.getSession().setAttribute("currentUser", user);
//            req.setAttribute("user_dao", user);
        }
        Ingredient[] ingredients = IngredientService.getIngredientServiceInstance().getIngredients();
        req.setAttribute("ingredients", ingredients);

        Item[] items = ItemService.getItemServiceInstance().getItems();
        req.setAttribute("items", items);
        forward("/pages/machine_settings.jsp", req, resp);
    }
}
