<body data-target=".bs-docs-sidebar" data-spy="scroll" xmlns:c="http://java.sun.com/jsp/jstl/core">
<div id="wrapper">
    <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div style="" class="container">
            <a href="./" class="brand">*OMO*</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="./">Home</a></li>
                    <li class=""><a href="./getting-started.html">Order</a></li>
                    <li class=""><a href="./scaffolding.html">About</a></li>
                </ul>
            </div>
            ${sessionScope.applicationUser.nameFirst}
            <div style="float:right; "  class="nav-collapse collapse">
                <c:if test="${sessionScope.applicationUser != null  && sessionScope.applicationUser.email != null}">
                    <ul class="nav">
                        <li class="active"><a href="#">${sessionScope.applicationUser.email}</a></li>
                    </ul>
                </c:if>
                <c:if test="${1!=2}">
                    <ul class="nav">
                        <li class="active"><a href="#dialogLogin"  data-toggle="modal">Login</a></li>
                        <li class="active"><a href="#dialogRegister"  data-toggle="modal">Register</a></li>
                    </ul>
                </c:if>
            </div>
        </div>
    </div>
</div>
