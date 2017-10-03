package mvc.controllers.servlets;


import mvc.controllers.Dispatcher;
import mvc.models.entities.users.buider.UserBuilder;
import services.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RegistrationServlet extends Dispatcher {
    Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setLocale(req);
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.DEBUG, "Exception", e);
            e.printStackTrace();
        }

        if (req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String middleName = req.getParameter("middleName");

            if (UserService.getUserServiceInstance().registerUser(
                    new UserBuilder().
                            setLogin(login).
                            setPassword(password).
                            setFirstName(firstName).
                            setLastName(lastName).
                            setMiddleName(middleName).
                            build()))
                forward("/pages/index.jsp", req, resp);
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
