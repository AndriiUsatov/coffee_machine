package controllers.servlets;


import controllers.Dispatcher;
import entities.users.buider.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends Dispatcher {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setCharacterEncoding(req, resp);
        setLocale(req);
        if (req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
            if (serviceFactory.getUserService().registerUser(
                    new UserBuilder().
                            setLogin(req.getParameter("login")).
                            setPassword(req.getParameter("password")).
                            setFirstName(req.getParameter("firstName")).
                            setLastName(req.getParameter("lastName")).
                            setMiddleName(req.getParameter("middleName")).
                            build()))
                forward("/index", req, resp);
            else
                forward("/pages/registration.jsp", req, resp);
        } else
            forward("/pages/registration.jsp", req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
