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
        $('#orderPopup').hide();

    });

    function showOrder() {
        loadOrderPopup();
        $("#orderPopup").slideDown();
        $("#myOrderButton").hide();
    }
    function hideOrder() {
        $("#orderPopup").slideUp(function(){
            $("#myOrderButton").fadeIn();
        });

    }

    //this is where the html is generated for the popup order "My Order"
    // makes a ajax call to retrieve the current order in session then iterates through the subcomponents
    // analagous to the jsp iteration in orders.jsp
    //
    function loadOrderPopup() {
        var itemUrl = "/omo/orders/getOrderJSON";
        $.ajax({
            url: itemUrl,
            context: document.body,
            dataType: "json"
            //async: false
        }).done(function(order) {
            //alert('done: ' + data);
            var html = "<!-- begin genereated html from menu.jsp -->";
            html  += "<table class=\"table\">";
            html  += "   <tbody>";
            jQuery.each(order.orderItems, function(i, orderItem) {
                html +=  "      <tr>";
                html +=  "         <td>" + orderItem.quantity + "</td>";
                html +=  "         <td>" + orderItem.section.name + " - " + orderItem.group.name + " - " +orderItem.menuItem.name ;
                jQuery.each(orderItem.menuItem.options, function(j, optionGroup) {
                    html += "<ul  style='list-style-type: none; height:8px; font-size: 12px;'>";
                    jQuery.each(optionGroup.children, function(k, option) {
                        html += "<li><div style='width:200px;float:left;'>" + optionGroup.description + " - " + option.description + "</div><div style='float:right'>$" + option.price + "</div></li>";
                    })
                    html += "</ul>";
                });
                if (orderItem.note != "")
                    html +=  "<br/>Note: " + orderItem.note  ;
                html +=  "</td>";
                html +=  "         <td>$" + orderItem.total + "</td>";
                html +=  "         <td><a href='#' onclick = 'deleteOrderItem(" + orderItem.id + ")' >Delete</a></td>";
                html +=  "      </tr>" ;
            });
            html  += "   </tbody>";
            html  += "</table>";
            html  += "<div style='float:right;margin-right:60px;'> <b>  Total: $" + order.totalPretax + "</b></div>";
            $('#orderPopupBody').html(html);
        }).error(function() {
            alert('Dangit, there was an error getting your order, please refresh and try again.');
        });
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
                <c:if test="${sessionScope.order != null}">
                    <c:if test="${sessionScope.order.orderItems.size() > 0}">
                        <li id="divider" class="divider"><a href="#">|</a></li>
                        <li id="myOrder" class=""><a href="#" onclick="showOrder();">My Order<span style="color: #4aa13c"> ${order.orderItems.size()} items </span></a></li>
                    </c:if>
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
        <div id="orderPopup" class="panel panel-success" style="position: absolute;z-index: 900; width: 450px;
        margin-left: 30%; margin-top: 15px;">
            <div class="panel-heading">
                My Order for Today
                <%--<div style="float:right;" ><a href="#" onclick="hideOrder();">x</a></div>--%>
                <button type="button"  onclick="hideOrder();" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
            <div id="orderPopupBody" class="panel-body">
                <%--
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
                --%>
            </div>
            <div class="panel-footer">
                <%--<button type="button" class="btn btn-success">Checkout</button>--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-success"   onclick="hideOrder();" >&nbsp;&nbsp;  OK  &nbsp;&nbsp;</button>
                <div style="float:right;width:280px;margin-top: -3px;margin-left: 0px;"><i>These items will be ordered for you. <br/> Please pickup and pay at noon.  Enjoy!</i></div>
            </div>
        </div>
        <!-- END THE ORDER -->
    </div>
</header>
