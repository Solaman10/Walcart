package org.servlet;

import org.details.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "AddToCartServlet", urlPatterns = "/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try(PrintWriter out=response.getWriter())
        {
            ArrayList<Cart> cartList = new ArrayList<>();

            int id = Integer.parseInt(request.getParameter("id"));
            Cart cm = new Cart();
            cm.setId(id);
            cm.setQuantity(1);

            HttpSession session = request.getSession();
            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

            if(cart_list==null)
            {
                cartList.add(cm);
                session.setAttribute("cart-list",cartList);
//                out.println("session created");
                response.sendRedirect("index.jsp");
            }
            else
            {
//                out.println("cart_list is already exists");
                  cartList=cart_list;
                  boolean exist=false;
                  for(Cart c:cartList)
                  {
                      if(c.getId()==id)
                      {
                          exist=true;
                          out.println("<h3 style='color:crimson; text-align:center'>Item already exist in Cart.<a href='cart.jsp'>Go to Cart Page</a>");
                      }
                  }
                if(!exist)
                {
                    cartList.add(cm);
//                    out.println("product added");
                    response.sendRedirect("index.jsp");
                }
            }
        }
    }
}
