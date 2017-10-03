package mvc.controllers.servlets;


import mvc.controllers.Dispatcher;
import mvc.models.entities.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PersonalAccountServlet extends Dispatcher {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        if (req.getParameter("gotoPA") != null || req.getParameter("locale" ) != null) {
            User user = (User) req.getSession().getAttribute("currentUser");
            if(!user.isCustomer())
                forward("/pages/error.jsp",req,resp);
            forward("/pages/user_account.jsp", req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
