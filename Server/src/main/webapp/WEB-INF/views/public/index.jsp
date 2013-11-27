<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<head>
        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
        <meta content="IE=8" http-equiv="X-UA-Compatible" />
        <meta charset="utf-8" />
        <title>Order Lunch</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />

        <link rel="stylesheet" href="/omo/resources/assets/css/bootstrap.css" />
        <link rel="stylesheet" href="/omo/resources/assets/css/bootstrap-responsive.css" />
        <link rel="stylesheet" href="/omo/resources/assets/css/docs.css" />
        <link rel="stylesheet" href="/omo/resources/assets/js/google-code-prettify/prettify.css" />
        <link href="/omo/resources/assets/ico/apple-touch-icon-144-precomposed.png" sizes="144x144" rel="apple-touch-icon-precomposed" />
        <link href="/omo/resources/assets/ico/apple-touch-icon-114-precomposed.png" sizes="114x114" rel="apple-touch-icon-precomposed" />
        <link href="/omo/resources/assets/ico/apple-touch-icon-72-precomposed.png" sizes="72x72" rel="apple-touch-icon-precomposed" />
        <link href="/omo/resources/assets/ico/apple-touch-icon-57-precomposed.png" rel="apple-touch-icon-precomposed" />
        <link href="/omo/resources/assets/ico/favicon.png" rel="shortcut icon" />
        <link href="/omo/resources/assets/css/digitss.css" rel="stylesheet" />

    <title>Welcome to HTML {0}</title>
</head>
--%>
<%--<body data-target=".bs-docs-sidebar" data-spy="scroll">
    <div id="wrapper">--%>
<%--        <div version="2.0" id="header"><meta charset="utf-8" />
            <title>El Cafecito Order</title>
            <meta content="width=device-width, initial-scale=1.0" name="viewport" />
            <meta content="" name="description" /><meta content="" name="author" />
            <link rel="stylesheet" href="assets/css/bootstrap.css" />
            <link rel="stylesheet" href="assets/css/bootstrap-responsive.css" />
            <link rel="stylesheet" href="assets/css/docs.css" />
            <link rel="stylesheet" href="assets/js/google-code-prettify/prettify.css" />
            <link href="assets/ico/apple-touch-icon-144-precomposed.png" sizes="144x144" rel="apple-touch-icon-precomposed" />
            <link href="assets/ico/apple-touch-icon-114-precomposed.png" sizes="114x114" rel="apple-touch-icon-precomposed" />
            <link href="assets/ico/apple-touch-icon-72-precomposed.png" sizes="72x72" rel="apple-touch-icon-precomposed" />
            <link href="assets/ico/apple-touch-icon-57-precomposed.png" rel="apple-touch-icon-precomposed" />
            <link href="assets/ico/favicon.png" rel="shortcut icon" />
        </div>--%>
<%--
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div style="" class="container">
                    <a href="./" class="brand">*OMO*</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav">
                            <li class="active"><a href="./">Home</a></li>
                            <li class=""><a href="./getting-started.html">Order</a></li>
                            <li class=""><a href="./scaffolding.html">About</a></li>
                            <li class=""><a href="./base-css.html">Login</a></li>
                        </ul>
                    </div>
                    <div style="float:right; "  class="nav-collapse collapse">
                        <c:choose>
                            <c:when test="${sessionScope.applicationUser != null}">
                                <ul class="nav">
                                    <li class="active"><a href="#">${sessionScope.applicationUser.email}</a></li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <ul class="nav">
                                    <li class="active"><a href="#dialogLogin"  data-toggle="modal">Login</a></li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
--%>
        <div id="main"><!-- Subhead ================================================== -->
            <header class="jumbotron subhead" id="overview">
                <div class="container">
                    <h1>Order Lunch!</h1>
                    <p class="lead">Pick your reseller, order from the menu, eat.</p>
                </div>
            </header>
        </div>

        <div class="container">
<%--
            <a href="#dialogLogin" role="button" class="btn" data-toggle="modal">Login</a>
            <a href="#dialogRegister" role="button" class="btn" data-toggle="modal">Register</a>
--%>

            <!-- Download  ================================================== -->
            <section id="step1">
                <div class="row-fluid">
                    <div class="span20">
                        <%--<p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;Pick Reseller&nbsp;&nbsp;&nbsp;</a></p>--%>
                        <div class="btn-group">
                            <a class="btn btn-large btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                                <span id="textReseller">Who's got da food?</span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#" onclick="pickReseller('El Cafecito')">El Cafecito at 1400 Treat Blvd.</a></li>
                                <li><a href="#" onclick="pickReseller('Some Other Place')">Some Other Place</a></li>
                            </ul>
                        </div>
                            <h3>Pick the business that will have your lunch.</h3>
                    </div>
                </div>
            </section>

            <section id="step2">
                <div class="row-fluid">
                    <div class="span20">
                        <p><a class="btn btn-large btn-primary" href="menuBentelinos.html" >Order From Menu</a></p>
                        <h3>Today's menu will be displayed, pick what you want to eat</h3>
                    </div>
                </div>
            </section>

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

<script type="text/javascript">
/*
    $( document ).ready(function() {
        //console.log( "ready!" );
        //$( "#dialog" ).dialog();
    });
*/

     function pickReseller(reseller) {
         $('#textReseller').text(reseller);
         //
        //get menus available
        $.ajax({
            url: "test.html",
            context: document.body
        }).done(function() {

        $( this ).addClass( "done" );
        });
     }
</script>