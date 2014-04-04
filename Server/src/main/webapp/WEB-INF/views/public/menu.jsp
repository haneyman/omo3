<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="topNavBar.jsp" %>
<script>
    <c:if test="${!canOrder}">
        $( document ).ready(function() {
            $('input[type=checkbox]').hide();
        });
    </c:if>
</script>
<div class="container" style="margin-bottom: 200px">
    <div class="" style="padding-left:20px;background-color: #7aba7b;margin-bottom: 0px;">
        <h3>${menu.name}</h3>
    </div>
    <div style="float: right;width:50%">${offered}</div>
    <div style="float:left;font-style: italic;width:50%">
        <c:if test="${canOrder}">
            Select your food and press the "Place Order" button at the bottom.
        </c:if>
        <c:if test="${!canOrder}">
            <span style="color:red;">Sorry, you can <b>not</b> order from this menu right now.</span>
        </c:if>
    </div>
    <div style="clear: both;margin-left: 50px;"></div>
        <form role="form" id="order" action="/omo/orders/submitOrder" method="POST" enctype="application/x-www-form-urlencoded">
        <c:if test="${canOrder}">
        </c:if>
        ${menuHTML}
        <div style="clear: both;"></div>
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
<script>
    $( document ).ready(function() {
        $('.returnView').val('/menus/showMenu/${menu.id}');//sets the form field in login and register to which page to return to
        <c:if test="${canOrder}">
            <c:if test="${sessionScope.applicationUser == null}">
                $('#dialogLogin').modal('show');
            </c:if>
        </c:if>
    });
</script>
