package mvc.controllers.servlets;

import mvc.controllers.Dispatcher;
import services.UserService;
import mvc.models.validators.CardValidator;
import mvc.models.entities.card.Card;
import mvc.models.entities.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class ReplenishAccountServlet extends Dispatcher {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        setLocale(req);
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if(!currentUser.isCustomer())
            forward("/pages/error.jsp",req,resp);
        if(req.getParameter("submit") != null) {
            Card card = new Card((String) req.getAttribute("cardNumber"), (String) req.getAttribute("secureCode"));
            if (CardValidator.validateCard(card)) {
                User user = (User) req.getSession().getAttribute("currentUser");
                BigDecimal balance = user.getBalance().add(new BigDecimal(req.getParameter("amount")));
                UserService.getUserServiceInstance().updateBalance(balance, user.getLogin());
                req.getSession().setAttribute("currentUser",
                        UserService.getUserServiceInstance().getUser(user.getLogin()));
            }
        }
        forward("/pages/user_account.jsp", req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
