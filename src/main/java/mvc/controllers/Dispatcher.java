package mvc.controllers;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Dispatcher extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Dispatcher.class);
    public void forward(String to, HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(to);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.DEBUG,"Exception", e);
            e.printStackTrace();
        }
    }

    public void setLocale(HttpServletRequest request){
        if(request.getParameter("locale") != null)
            request.getSession().setAttribute("locale", request.getParameter("locale"));
        if(request.getSession().getAttribute("locale") == null)
            request.getSession().setAttribute("locale","ua");
    }
}
