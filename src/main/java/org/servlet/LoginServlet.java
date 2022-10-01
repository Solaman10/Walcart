package org.servlet;

import org.details.connection.DbCon;
import org.details.dao.UserDao;
import org.details.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name="LoginServlet",urlPatterns = "/user-login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {
//            out.println("this is login servlet");
            String email = request.getParameter("login-email");
            String password = request.getParameter("login-password");
//            out.println("email: "+email+"  "+"password"+password);

            try {
                UserDao userDao = new UserDao(DbCon.getConnection());
                User user = userDao.userLogin(email,password);

                if(user!=null)
                {
                    request.getSession().setAttribute("auth",user);
                    response.sendRedirect("index.jsp");
                }
                else
                {
                    out.println("login failed");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}