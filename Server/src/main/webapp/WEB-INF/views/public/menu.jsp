<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="topNavBar.jsp" %>

<div class="container">
        <form role="form" id="order" action="/omo/orders/submitOrder" method="POST" enctype="application/x-www-form-urlencoded">
            <div class="well well-sm" style="margin-top: 18px;margin-left: 40px;">
                <div class="form-group">
                    <label style="padding-top: 6px;" for="notes" class="col-sm-1 control-label">Notes</label>
                    <div class="col-sm-8" style="Zpadding-top: 7px;">
                        <input type="notes" class="form-control" id="notes" name="notes" placeholder="Hold the mayo">
                    </div>
                    <button type="submit" class="btn btn-success">Place Order</button>
                </div>
<%--
                <div class="form-group" style="width:60%">
                    <label for="notes" style="margin-top: 5px;float:left;">Notes: </label>
                    <input style="float:left;" type="text" class="form-control" id="notes" name="notes" placeholder="Hold the mayo">
                </div>
--%>
                <%--<div  style="Zfloat:left;">--%>
                <%--</div>--%>
            </div>
            ${menuHTML}
            <div style="height:30px;margin-top: 10px;margin-left:100px;">
                <button type="submit" class="btn">Order</button>
            </div>
        </form> <!-- order -->
</div> <!-- container -->
