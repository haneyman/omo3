<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="bodyTop.jsp" %>
<div id="main"><!-- Subhead ================================================== -->
    <header class="zjumbotron zsubhead" id="overview">
<%--
        <div class="container">
            <h3>Order Lunch!</h3>
            <p class="lead">Pick your reseller, pick the menu, order, eat!</p>
        </div>
--%>
    </header>
</div>

<div class="container">
        <form role="form" id="order" action="/omo/orders/submitOrder" method="POST" enctype="application/x-www-form-urlencoded">
            <div class="well well-sm" style="margin-top: 8px;width:390px;height: 25px;">
                <div class="form-group" style="float:left;">
                    <label for="notes" style="margin-top: 5px;margin-right: 3px;">Notes: </label>
                    <input style="zwidth:80%" type="text" class="form-control" id="notes" name="notes" placeholder="Hold the mayo">
                </div>
                <div  style="float:right;">
                    <button type="submit" class="btn btn-success">Place Order</button>
                </div>
            </div>
            ${menuHTML}
            <div style="height:30px;margin-top: 10px;margin-left:100px;">
                <button type="submit" class="btn">Order</button>
            </div>
        </form> <!-- order -->
</div> <!-- container -->
