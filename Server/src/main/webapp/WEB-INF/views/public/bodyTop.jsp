<!-- start bodyTop.jsp -->
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div style="" class="container">
                <a href="/omo" class="brand">*OMO*</a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li class="active"><a href="/omo/">Home</a></li>
                        <li class=""><a href="./getting-started.html">My Orders</a></li>
                        <li class=""><a href="./scaffolding.html">About</a></li>
                        <%--<li class=""><a href="./base-css.html">Login</a></li>--%>
                    </ul>
                </div>
                <div style="float:right; "  class="nav-collapse collapse">
                    <c:choose>
                        <c:when test="${sessionScope.applicationUser != null}">
                            <ul class="nav">
                                <li class="active"><a href="#">${sessionScope.applicationUser.email}</a></li>
                                <li class=""><a href="./logout">Log Out</a></li>
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
    </div>
<!-- end bodyTop.jsp -->
