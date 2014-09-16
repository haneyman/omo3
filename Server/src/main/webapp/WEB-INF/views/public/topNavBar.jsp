<!-- start topNavBar.jsp -->
<script type="text/javascript">
    $( document ).ready(function() {
        var pathname = window.location.pathname;
        if (pathname.indexOf("start") > -1)
            $("#start").addClass('active');
        if (pathname.indexOf("listMenuPublic") > -1)
            $("#menus").addClass('active');
        if (pathname.indexOf("myOrders") > -1)
            $("#myOrders").addClass('active');
        if (pathname.indexOf("about") > -1)
            $("#about").addClass('active');
        if (pathname.indexOf("orders/orders") > -1)
            $("#allOrders").addClass('active');

    });

    function showOrder() {
        $("#order").slideDown();
        //$("#myOrderButton").hide();
    }

</script>
<header class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div style="" class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#divMainNav">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/omo">Menu<span style="font-style: italic">Breeze</span></a>
        </div>
        <div class="collapse navbar-collapse" id="divMainNav">
            <ul id="main-nav" class="nav navbar-nav">
                <li id="start" class=""><a href="/omo/public/start">Order</a></li>
                <li id="menus" class=""><a href="/omo/menus/listMenuPublic">Menus</a></li>
                <c:if test="${sessionScope.applicationUser != null}">
                    <li id="myOrders" class=""><a href="/omo/orders/myOrders">My Orders</a></li>
                </c:if>
                <li id="about" class=""><a href="/omo/about">About</a></li>
                <c:if test="${sessionScope.applicationUser != null}">
                    <c:if test="${sessionScope.applicationUser.isAdmin }">
                        <li id="allOrders" class=""><a href="/omo/orders/orders/open">All Orders</a></li>
                    </c:if>
                </c:if>
                <li id="divider" class="divider"><a href="#">|</a></li>
                <c:if test="${sessionScope.order != null}">
                    <li id="myOrder" class=""><a href="#" onclick="showOrder();">My Order<span style="color: #4aa13c"> ${order.orderItems.size()} items </span></a></li>
                </c:if>
            </ul>
            <div style="float:right; "  class="">
                <c:choose>
                    <c:when test="${sessionScope.applicationUser != null}">
                        <ul class="nav navbar-nav">
                                <%--<li class=""><a href="#">${sessionScope.applicationUser.email}</a></li>--%>
                            <li class=""><a href="/omo/applicationusers/logout">Log Out</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="nav navbar-nav">
                            <li class=""><a href="#dialogLogin"  data-toggle="modal">Log In</a></li>
                            <li class=""><a href="#dialogRegister"  data-toggle="modal">Register</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div><!--/.nav-collapse -->

        <!-- THE ORDER -->
        <div id="order" class="panel panel-success" style="float:right; width: 30%; margin-right: 10%; margin-top: 20px;">
            <div class="panel-heading">
                Order
                <div style="float:right;" ><a href="#" onclick="hideOrder();">x</a></div>

            </div>
            <div class="panel-body">
                <table class="table">
                    <tr>
                        <th>Qty</th>
                        <th>Item</th>
                        <th>Price</th>
                        <th><a href="#"></a></th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>Phlegmy Head Cheese</td>
                        <td>$6.00</td>
                        <td><a href="#">Delete</a></td>
                    </tr>
                </table>
            </div>
            <div class="panel-footer">
                <button type="button" class="btn btn-success">Checkout</button>
            </div>
        </div>
        <!-- END THE ORDER -->
    </div>
</header>
