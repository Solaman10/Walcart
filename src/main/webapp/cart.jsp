<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="org.servlet.*"%>
<%@ page import="org.details.model.*"%>
<%@ page import="org.details.dao.*"%>
<%@ page import="org.details.connection.DbCon"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf",dcf);
User auth = (User) request.getSession().getAttribute("auth");
if(auth!=null)
{
request.setAttribute("auth",auth);
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
List<Cart> cartProducts = null;
if(cart_list!=null)
{
  ProductDao pDao = new ProductDao(DbCon.getConnection());
  cartProducts = pDao.getCartProducts(cart_list);
  double total = pDao.getTotalCartPrice(cart_list);
  request.setAttribute("total",total);
  request.setAttribute("cart_list",cart_list);
}
%>

<!DOCTYPE html>
<html>
<head>
<title> Cart </title>
<%@include file="includes/head.jsp"%>
</head>
<body>
    <%@include file="includes/navbar.jsp"%>

    <div class="container my-3">
    		<div class="d-flex py-3"><h3>Total Price: $ ${(total>0)?dcf.format(total):0} </h3> <a class="mx-3 btn btn-primary" href="cart-check-out">Check out</a></div>
    		<table class="table table-light">
                  <thead>
                    <tr>
                      <th scope="col">Name</th>
                      <th scope="col">Category</th>
                      <th scope="col">Price</th>
                      <th scope="col">Buy now</th>
                      <th scope="col">Cancel</th>
                    </tr>
                </thead>
    			<tbody>
    			<%
    			if(cart_list!=null)
    			{
    			    for(Cart c:cartProducts)
    			    {
    			    %>
    				<tr>
    					<td><%= c.getName()%></td>
    					<td><%= c.getCategory()%></td>
    					<td><%= dcf.format(c.getPrice())%></td>
    					<td>
    						<form action="order-now" method="post" class="form-inline">
    						<input type="hidden" name="id" value="<%= c.getId() %>" class="form-input">
    							<div class="form-group d-flex justify-content-between">
    							    <a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%= c.getId()%>"><i class="fas fa-minus-square"></i></a>
    								<input type="text" name="quantity" class="form-control"  value="<%= c.getQuantity()%>" readonly>
    								<a class="btn bnt-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%= c.getId()%>"><i class="fas fa-plus-square"></i></a>

    							</div>
    							<button type="submit" class="btn btn-dark btn-sm">Buy</button>
    						</form>
    					</td>
    					<td><a href="remove-from-cart?id=<%= c.getId()%>" class="btn btn-sm btn-danger">Remove</a></td>
    				</tr>
               <% }
    			 } %>
    			</tbody>
    		</table>
    	</div>


    <%@include file="includes/footer.jsp"%>
</body>
</html>