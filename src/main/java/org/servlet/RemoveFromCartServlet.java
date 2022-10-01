package org.servlet;

import org.details.model.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "RemoveFromCartServlet",urlPatterns = "/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter())
        {
            String id = request.getParameter("id");
//            out.println("Product id: "+id);
            if(id!=null)
            {
                ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                if(cart_list != null)
                {
                    for(Cart c:cart_list)
                    {
                        if(c.getId() == Integer.parseInt(id))
                        {
                            cart_list.remove(cart_list.indexOf(c));
                            break;
                        }
                    }
                    response.sendRedirect("cart.jsp");
                }
            }
            else
            {
                response.sendRedirect("cart.jsp");
            }
        }
    }
}
