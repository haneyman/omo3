<!-- start topNavBar.jsp -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div style="" class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/omo">Menu<span style="font-style: italic">Breeze</span></a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/omo/public/start">Order</a></li>
                    <li class=""><a href="/omo/menus/listMenuPublic">Menus</a></li>
                    <c:if test="${sessionScope.applicationUser != null}">
                        <li class=""><a href="/omo/orders/myOrders">My Orders</a></li>
                    </c:if>
                    <li class=""><a href="/omo/about">About</a></li>
                        <c:if test="${sessionScope.applicationUser != null}">
                            <c:if test="${sessionScope.applicationUser.isAdmin }">
                                <li class=""><a href="/omo/orders/orders/open">All Orders</a></li>
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
<%--
            <div style="float:right; "  class="nav-collapse collapse">
                <c:choose>
                    <c:when test="${sessionScope.applicationUser != null}">
                        <ul class="nav">
                            <li class="active"><a href="#">${sessionScope.applicationUser.email}</a></li>
                            <li class=""><a href="/omo/applicationusers/logout">Log Out</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="nav">
                            <li class="active"><a href="#dialogLogin"  data-toggle="modal">Log In</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
--%>
        </div>
    </div>



<%--


            &lt;%&ndash;<a href="/omo" class="brand">*OMO*</a>&ndash;%&gt;
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="/omo/">Home</a></li>
                    <li class=""><a href="/omo/orders/myOrders">My Orders</a></li>
                    <li class=""><a href="./scaffolding.html">About</a></li>
                    &lt;%&ndash;<li class=""><a href="./base-css.html">Login</a></li>&ndash;%&gt;
                </ul>
            </div>
            <div style="float:right; "  class="nav-collapse collapse">
                <c:choose>
                    <c:when test="${sessionScope.applicationUser != null}">
                        <ul class="nav">
                            <li class="active"><a href="#">${sessionScope.applicationUser.email}</a></li>
                            <li class=""><a href="/omo/applicationusers/logout">Log Out</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="nav">
                            <li class="active"><a href="#dialogLogin"  data-toggle="modal">Log In</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
<!-- end topNavBar.jsp -->
--%>
