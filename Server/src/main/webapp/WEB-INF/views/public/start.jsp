<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="topNavBar.jsp" %>
<div id="main"><!-- Subhead ================================================== -->
    <header class="jumbotron subhead" id="overview">
        <div class="container">
            <h1>Order Lunch!</h1>
            <p class="lead">Pick your reseller, pick the menu, order, eat!</p>
        </div>
    </header>
</div>

<div class="container" style="margin-left: 100px;">
    <form action="/omo/menus/viewMenu" method="POST" enctype="application/x-www-form-urlencoded">
        <%--<section id="step1">--%>
            <div class="row">
                <div class="" style="margin-top: -40px;">
                    <%--<p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;Pick Reseller&nbsp;&nbsp;&nbsp;</a></p>--%>
                    <h3>1) Pick where to deliver your lunch.</h3>
                    <div class="btn-group">
                        <a style="width:200px;margin-left:50px;" class="btn btn-lg btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                            <span id="textReseller">Who's got the food?</span>
                            <span class="caret"></span>
                        </a>
                        <input type="hidden" name="reseller" id="reseller">
                        <ul class="dropdown-menu">
                            <c:forEach var="reseller" items="${resellers}">
                                <li><a href="#" onclick="pickReseller('${reseller.name}')">${reseller.name} - ${reseller.address}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        <%--</section>--%>

        <%--<section id="step2">--%>
            <div class="row">
                <div class="">
                    <%--<p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;Pick Reseller&nbsp;&nbsp;&nbsp;</a></p>--%>
                    <h3>2) Pick the restaurant menu.</h3>
                    <div id="divMenus" class="btn-group ">
                        <a style="width:200px;margin-left:50px;" id="dropDownMenus" class="btn btn-lg btn-primary dropdown-toggle disabled" data-toggle="dropdown" href="#">
                            <span id="textRestaurant">Which food?</span>
                            <span class="caret"></span>
                        </a>
                        <input type="hidden" name="menu" id="menu">
                        <ul id="listRestaurants" class="dropdown-menu">
                            <%--<li><a href="#" onclick="pickRestaurant('Some Other Restaurant')">Some Other Restaurant</a></li>--%>
                        </ul>
                    </div>
                </div>
            </div>
        <%--</section>--%>

        <%--<section id="step3">--%>
            <div class="row">
                <div class="">
                    <h3>3) Show me the Menu.</h3>
                    <button style="width:200px;margin-left:50px;" id="buttonSubmit" type="submit" class="btn btn-lg btn-primary disabled btn-success">Order From Menu</button>
                </div>
            </div>
        <%--</section>--%>
    </form>
    <%--

                <section id="step3">
                    <div class="row-fluid">
                        <div class="span20">
                            <p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Eat&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></p>
                            <h3>Pick up your food from your reseller and EAT.</h3>
                        </div>
                    </div>
                </section>

    --%>

</div> <!-- container -->
<%--</div> <!-- wrapper -->--%>

<script type="text/javascript">

     $( document ).ready(function() {
        <c:choose>
            <c:when test="${sessionScope.applicationUser != null}">
                //alert('logged in');
            </c:when>
            <c:otherwise>
                //alert('NOT logged in');
                //$('#dialogLogin').modal('show');
            </c:otherwise>
        </c:choose>
    });

    //picking a reseller, get valid menus for reseller for today
    function pickReseller(reseller) {
        //alert('pickReseller:' + reseller);
        $('#textReseller').text(reseller);
        $('#reseller').val(reseller);
        //
        //get menus available
//        $.getJSON("/omo/menus/byResellerToday/" + reseller,function(result){
//            $.each(result, function(i, field){
//                //$("div").append(field + " ");
//                //alert('menu' + field.name);
//            });
//        });
        $.ajax({
//           type: "POST",
           type: "GET",
            url: "/omo/menus/byResellerToday/" + reseller,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                //alert('data: ' + data.name);
//                var tbody = $("#divMenus > ul").html("");
//                var tbody = $("#divMenus > ul").html("");
                for (var i in data) {
                    var menu = data[i];
                    //var rowText = "<tr><td>" + customer.ID + "</td><td>" + customer.Name + "</td><td>" + customer.Company + "</td><td>" + customer.StartDate + "</td></tr>";
                    var rowText = '<li><a href="#" onclick="menuPicked(\'' + menu.name +  '\',\'' + menu.restaurant.name +  '\')">'
                            + menu.restaurant.name + ' - ' + menu.restaurant.description + '</a></li>';
                    //alert('success ' + rowText);
                    $('#listRestaurants').append(rowText);
                    //$(rowText).appendTo(tbody);

                }
                $('#dropDownMenus').removeClass('disabled')
            },
            failure: function() { alert("failure on jason call"); }
        });
    }

    //menu picked
    function menuPicked(menu, restaurant) {
        //cuz they SEE the restaurant but we send the controller the menu, that is what they are selecting
        //alert('menuPicked menu:' + menu + " rest:" + restaurant)
        $('#textRestaurant').text(restaurant);
        $('#menu').val(menu);
        $('#buttonSubmit').removeClass('disabled')
    }
</script>