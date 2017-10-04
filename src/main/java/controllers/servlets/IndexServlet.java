package controllers.servlets;


import controllers.Dispatcher;
import controllers.IngredientController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends Dispatcher {
    private static boolean controllerRunning = false;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        req.getSession().removeAttribute("currentUser");
        if(!controllerRunning){
            synchronized (this.getClass()){
                if(!controllerRunning) {
                    controllerRunning = true;
                    new Thread(new IngredientController()).start();
                }
            }
        }
        setLocale(req);
        forward("/pages/index.jsp",req,resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}