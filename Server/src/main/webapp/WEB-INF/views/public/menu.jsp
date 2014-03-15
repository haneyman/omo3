<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="topNavBar.jsp" %>

<div class="container" style="margin-bottom: 200px">
    <div class="" style="padding-left:20px;background-color: #7aba7b;margin-bottom: 0px;">
        <h3>${menu.name}</h3>
    </div>
    <div style="float: right;">${offered}</div>
    <div style="float:left;font-style: italic;">
        <c:if test="${canOrder}">
            Select your food and press the "Place Order" button at the bottom.
        </c:if>
        <c:if test="${!canOrder}">
            These are items you can order when this menu is featured.
        </c:if>
    </div>
    <div style="clear: both;margin-left: 50px;"/>
        <form role="form" id="order" action="/omo/orders/submitOrder" method="POST" enctype="application/x-www-form-urlencoded">
        <c:if test="${canOrder}">
        </c:if>
        ${menuHTML}
        <div style="clear: both;"
        <c:if test="${canOrder}">
            <div class="" style="margin-top: 18px;margin-left: 40px;">
                <div class="form-group">
                    <label style="padding-top: 6px;" for="notes" class="col-sm-1 control-label">Notes</label>
                    <%--<div class="col-sm-8" style="Zpadding-top: 7px;">--%>
                        <input type="notes" class="form-control" id="notes" name="notes" placeholder="Type your order notes here.">
                    <%--</div>--%>
                </div>
            </div>
            <div style="margin-left: 40%;">
                <div class="form-group">
                    <button type="submit" class="btn-lg btn-success">Place Order</button>
                </div>
            </div>
        </c:if>
    </form> <!-- order -->
</div> <!-- container -->
