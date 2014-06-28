<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- index.jsp -->
<!--[if lt IE 9]>
<style>
/*    .jumbotron { background-repeat: no-repeat; background-size: 100% 100%; filter: clear;
        background: image('/omo/resources/assets/img/blurry1.jpg') no-repeat center center;
        background-repeat: no-repeat;
        z-index: 1000;
    }*/
    /*.bg { url('/omo/resources/assets/img/blurry1.jpg') no-repeat center center;}*/


</style>
<![endif]-->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div style="" class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/omo">Menu<span style="font-style: italic">Breeze</span></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class=""><a href="/omo/about">About</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<%--
<div class="wide "  style="">
        <h1>Menu <i>Breeze</i></h1>
        <p>Order your food, pick up your food, EAT.</p>
        <p><a class="btn btn-primary btn-lg " role="button" onclick="window.location.href='public/start'">Let's Do This &raquo;</a></p>
</div>
--%>


<div id="main"><!-- Subhead ================================================== -->
    <div class="jumbotron " style="background-image: url('/omo/resources/assets/img/blurry1.jpg');  background-size: 100%;">
        <div class="container ">
            <h1>Menu <i>Breeze</i></h1>
            <p>Order your food, pick up your food, EAT.</p>
            <p><a class="btn btn-primary btn-lg " role="button" onclick="window.location.href='public/start'">Let's Do This &raquo;</a></p>
        </div>
    </div>
</div>

<%-- unavailable thang -->
<%--
<div id="main"><!-- Subhead ================================================== -->
    <div class="jumbotron">
        <div class="container">
            <h1>Menu Breeze</h1>
            <p>Sorry we are unavailable today :(</p>
        </div>
    </div>
</div>--%>
<div style="float: right;font-size: 9px;margin-right:10px;">V 0.26 - 6/28/14 MPH</div>
<!-- end of /index.jsp-->
<script type="text/javascript">
    $( document ).ready(function() {
    });
</script>