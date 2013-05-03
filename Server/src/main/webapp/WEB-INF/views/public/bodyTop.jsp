<!-- From /public/bodyTop.jsp -->
<div id="header" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:spring="http://www.springframework.org/tags" version="2.0">
    <jsp:directive.page contentType="text/html;charset=UTF-8" />
    <jsp:output omit-xml-declaration="yes" />

    <!--<spring:url var="banner" value="/resources/images/banner-graphic.png" />-->
    <!--<spring:url var="home" value="/" />-->
    <!--<spring:message code="button_home" var="home_label" htmlEscape="false" />-->
    <!--<a href="${home}" name="${fn:escapeXml(home_label)}" title="${fn:escapeXml(home_label)}">-->
    <!--<img src="${banner}" />-->
    <!--</a>-->

    <meta charset="utf-8">
    <title>Bootstrap</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="assets/css/docs.css" rel="stylesheet">
    <link href="assets/js/google-code-prettify/prettify.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="assets/ico/favicon.png">

</div>

<!-- Navbar ================================================== -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container" style="">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="./index.html">Bootstrap</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active">
                        <a href="./index.html">Home</a>
                    </li>
                    <li class="">
                        <a href="./getting-started.html">Order</a>
                    </li>
                    <li class="">
                        <a href="./scaffolding.html">About</a>
                    </li>
                    <li class="">
                        <a href="./base-css.html">Other</a>
                    </li>
                </ul>
            </div>
            <div class="nav-collapse collapse" style="float:right;width:120px;">
                <ul class="nav">
                    <li class="active">
                        <a href="./order.html">Your ORDER</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- end From /public/bodyTop.jsp -->