<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@include file="bodyTop.jsp" %>
        <div id="main"><!-- Subhead ================================================== -->
            <header class="jumbotron subhead" id="overview">
                <div class="container">
                    <h1>Order Lunch!</h1>
                    <p class="lead">Pick your reseller, pick the menu, order, eat!</p>
                </div>
            </header>
        </div>

        <div class="container">
<%--
            <a href="#dialogLogin" role="button" class="btn" data-toggle="modal">Login</a>
            <a href="#dialogRegister" role="button" class="btn" data-toggle="modal">Register</a>
--%>
            <!-- Download  ================================================== -->
            <form action="/omo/menus/viewMenu" method="POST" enctype="application/x-www-form-urlencoded">
                <section id="step1">
                    <div class="row-fluid">
                        <div class="span20">
                            <%--<p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;Pick Reseller&nbsp;&nbsp;&nbsp;</a></p>--%>
                            <div class="btn-group">
                                <a class="btn btn-large btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                                    <span id="textReseller">Who's got the food?</span>
                                    <span class="caret"></span>
                                </a>
                                <input type="hidden" name="reseller" id="reseller">
                                <ul class="dropdown-menu">
                                    <li><a href="#" onclick="pickReseller('El Cafecito')">El Cafecito at 1400 Treat Blvd.</a></li>
                                    <li><a href="#" onclick="pickReseller('Some Other Place')">Some Other Place</a></li>
                                </ul>
                            </div>
                                <h3>1) Pick the business that will have your lunch.</h3>
                        </div>
                    </div>
                </section>

                <section id="step2">
                    <div class="row-fluid">
                        <div class="span20">
                            <%--<p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;Pick Reseller&nbsp;&nbsp;&nbsp;</a></p>--%>
                            <div class="btn-group">
                                <a class="btn btn-large btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                                    <span id="textRestaurant">Which food?</span>
                                    <span class="caret"></span>
                                </a>
                                <input type="hidden" name="restaurant" id="restaurant">
                                <ul class="dropdown-menu">
                                    <li><a href="#" onclick="pickRestaurant('Bentolinos')">Bentolinos</a></li>
                                    <li><a href="#" onclick="pickRestaurant('Some Other Restaurant')">Some Other Restaurant</a></li>
                                </ul>
                            </div>
                                <h3>2) Pick the restaurant menu.</h3>
                        </div>
                    </div>
                </section>

                <section id="step3">
                    <div class="row-fluid">
                        <div class="span20">
                            <button type="submit" class="btn btn-large btn-primary">Order From Menu</button>
                            <h3>3) Show me the Menu.</h3>
                        </div>
                    </div>
                </section>
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

     function pickReseller(reseller) {
         $('#textReseller').text(reseller);
         $('#reseller').val(reseller);
         //
        //get menus available
        $.ajax({
            url: "test.html",
            context: document.body
        }).done(function() {

        $( this ).addClass( "done" );
        });
     }

     function pickRestaurant(restaurant) {
         $('#textRestaurant').text(restaurant);
         $('#restaurant').val(restaurant);
     }
</script>