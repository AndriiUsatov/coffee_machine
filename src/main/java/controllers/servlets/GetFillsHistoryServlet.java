package controllers.servlets;

import controllers.Dispatcher;
import entities.Fill;
import entities.users.User;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class GetFillsHistoryServlet extends Dispatcher {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setLocale(req);
        User admin = (User) req.getSession().getAttribute("currentUser");
        if (!admin.isAdmin())
            forward("/pages/error.jsp", req, resp);
        Fill[] fills = ArrayUtils.addAll(serviceFactory.getIngredientService().getAllFills(),
                serviceFactory.getItemService().getAllFills());
        Fill[] current = null;
        Arrays.sort(fills, new Comparator<Fill>() {
            @Override
            public int compare(Fill fill, Fill t1) {
                return fill.getDate().compareTo(t1.getDate());
            }
        });
        int currentPage = req.getParameter("currentPage") == null ? 1 : Integer.valueOf(req.getParameter("currentPage"));
        if (fills.length < 31)
            current = fills;
        else {
            current = new Fill[30];
            int currentCounter = 0;
            int from = currentPage == 1 ? 0 : (currentPage - 1) * 30;
            int to = currentPage * 30 > fills.length ? fills.length : currentPage * 30;
            for (int i = from; i < to; i++) {
                current[currentCounter++] = fills[i];
            }
        }
        req.setAttribute("activated", currentPage);
        req.setAttribute("fills", current);
        req.setAttribute("fillsLength", fills.length);

        forward("/pages/fillingHistory.jsp", req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
