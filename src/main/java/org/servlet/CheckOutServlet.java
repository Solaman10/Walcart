package org.servlet;

import org.details.connection.DbCon;
import org.details.dao.OrderDao;
import org.details.model.Cart;
import org.details.model.Order;
import org.details.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "CheckOutServlet",urlPatterns = "/cart-check-out")
public class CheckOutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try(PrintWriter out = response.getWriter()) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
            User auth = (User) request.getSession().getAttribute("auth");

            if(cart_list!=null && auth!=null)
            {
                for(Cart c:cart_list)
                {
                    Order order = new Order();
                    order.setId(c.getId());
                    order.setuId(auth.getId());
                    order.setQuantity(c.getQuantity());
                    order.setDate(formatter.format(date));

                    OrderDao oDao = new OrderDao(DbCon.getConnection());
                    boolean result = oDao.insertOrder(order);
                    if(!result)
                        break;
                }
                cart_list.clear();
                response.sendRedirect("orders.jsp");
            }
            else if(auth==null)
            {
                response.sendRedirect("login.jsp");
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
