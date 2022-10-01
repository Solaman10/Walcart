package org.servlet;

import org.details.model.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "QuantityIncDecServlet" , urlPatterns = "/quantity-inc-dec")
public class QuantityIncDecServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter())
        {
//            out.println("Inside the QuantityIncDecServlet");
            String action = request.getParameter("action");
            int id = Integer.parseInt(request.getParameter("id"));

            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");

            if(action!=null && id>=1)
            {
                if(action.equals("inc"))
                {
                    for(Cart c:cart_list)
                    {
                        if(c.getId()==id)
                        {
                            int quantity = c.getQuantity();
                            quantity++;
                            c.setQuantity(quantity);
                            response.sendRedirect("cart.jsp");
                        }
                    }
                }
                else if(action.equals("dec"))
                {
                    for(Cart c:cart_list)
                    {
                        if(c.getId()==id && c.getQuantity()>1)
                        {
                            int quantity = c.getQuantity();
                            quantity--;
                            c.setQuantity(quantity);
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
