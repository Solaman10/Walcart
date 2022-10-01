<%@ page import="org.servlet.*"%>
<%@ page import="org.details.model.User"%>

<%
User Auth = (User) request.getSession().getAttribute("auth");
if(Auth!=null)
{
request.setAttribute("auth",Auth);
}
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <a class="navbar-brand" href="index.jsp">Online Shopping-Cart</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="index.jsp">Home </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="cart.jsp">Cart <span class="badge badge-danger">${cart_list.size()}</span></a>
          </li>
          <%if(Auth!=null) {%>
          <li class="nav-item">
            <a class="nav-link" href="orders.jsp">Orders</a>
          </li>
          <li class="nav-item">
             <a class="nav-link" href="log-out">Logout</a>
          </li>
          <%}else{%>
          <li class="nav-item">
             <a class="nav-link" href="login.jsp">Login</a>
          </li>
          <%}%>
        </ul>
        </form>
      </div>
      </div>
    </nav>