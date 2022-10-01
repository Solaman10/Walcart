package org.servlet;

import org.details.connection.DbCon;
import org.details.dao.*;
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

@WebServlet(name = "OrderNowServlet",urlPatterns = "/order-now")
public class OrderNowServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try(PrintWriter out = response.getWriter())
        {
//            out.println("from order now servlet");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            User auth = (User) request.getSession().getAttribute("auth");
            if(auth!=null)
            {
                String productId = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));

                if(productQuantity<=0)
                {
                    productQuantity = 1;
                }

                Order orderModel = new Order();
                orderModel.setId(Integer.parseInt(productId));
                orderModel.setuId(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));

                OrderDao orderDao = new OrderDao(DbCon.getConnection());
                boolean result = orderDao.insertOrder(orderModel);

                if(result)
                {
                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if(cart_list != null)
                    {
                        for(Cart c:cart_list)
                        {
                            if(c.getId() == Integer.parseInt(productId))
                            {
                                cart_list.remove(cart_list.indexOf(c));
                                break;
                            }
                        }
                    }

                    response.sendRedirect("orders.jsp");
                }
                else
                {
                    out.println("order failed");
                }
            }
            else
            {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {

        doGet(request,response);
    }

}
