<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    label {margin-right: 5px;font-weight: 800;}
    .col-sm-10 {margin-bottom: 5px;}
</style>
<script>
    function remind() {
        url = "/omo/applicationusers/remind/" + encodeURIComponent($('#email').val().replace(/\./g, '&#46;'));
//        alert(url);
        window.location.href=url;
    }
</script>
<%--</div>--%>
<!-- Modal -->
<div id="dialogLogin" class="modal  fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<h1 id="myModalLabel">Welcome to OMO</h1>--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
                <div style="Zfloat: left;">
                    <h3 id="myModalLabel2">Log In</h3>
                </div>
                    <div style="float: right;margin-top: -40px;">
                        Not Registered Yet? <button class="btn btn-primary" onclick="$('#dialogLogin').modal('hide');$('#dialogRegister').modal('show');">Register</button>
                    </div>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="login" action="/omo/applicationusers/login" method="POST" enctype="application/x-www-form-urlencoded">
                    <input class="returnView" id="returnView" name="returnView" type="hidden" value=""/>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-6">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                            <a href="#" onclick="remind();">Remind me</a>
                        </div>
                    </div>
                    <div>
                        <button class="btn btn-success" style="margin-left: 104px;">Login</button>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <span style="font-size: small;font-style: italic">Enter your login information OR press the "Register" button if you are NEW.</span>
            </div>
        </div>
    </div>
</div>

<div id="dialogRegister" class="modal  fade" tabindex="-1" role="dialog" aria-labelledby="registerHeader" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
                <h3 id="registerHeader">New User - Welcome!</h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="applicationUser" action="/omo/applicationusers/registerUser" method="POST" enctype="application/x-www-form-urlencoded">
                    <input  class="returnView" id="returnView" name="returnView" type="hidden" value=""/>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-8">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="nameFirst" class="col-sm-3 control-label">First Name</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="nameFirst" name="nameFirst" placeholder="First Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="nameLast" class="col-sm-3 control-label">Last Name</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="nameLast" name="nameLast" placeholder="Last Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber" class="col-sm-3 control-label">Phone Number</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Phone Number">
                        </div>
                    </div>
        <%--
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="remember">Remember me on this computer.
                                </label>
                            </div>
                        </div>
                    </div>
        --%>
                    <div class="form-group">
                        <div style="margin-left: 200px; margin-top: 10px;">
                        <%--<div class="col-sm-offset-2 col-sm-10">--%>
                            <button type="submit" class="btn btn-primary">Register Me!</button>
                        </div>
                    </div>

        <%--
                    <label for="email">Email: </label><br/>
                    <label for="password">Password: </label><input id="password" type="password" name="password" placeholder="Password"><br/>
                    <label for="nameFirst">First Name: </label> <input id="nameFirst" type="text" name="nameFirst" placeholder="First Name"><br/>
                    <label for="nameFirst">Last Name: </label> <input type="text" name="nameLast" placeholder="Last Name"><br/>
                    <label for="nameFirst">Phone Number: </label> <input type="text" name="phoneNumber" placeholder="Contact Phone Number"><br/>
                    <button class="btn btn-primary">Register Me!</button>
        --%>
                </form>
        <%--<p>Enter your email and password.</p>--%>
            </div>
        </div>
    </div>
    <%--
                        <div class="modal-footer">
                            &lt;%&ndash;<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>&ndash;%&gt;
                            Not Registered Yet? <button class="btn btn-primary">Register</button>
                        </div>
    --%>

</div>

<footer class="footer navbar navbar-fixed-bottom" >
    <div class="container">
        <div style="float:left;font-size: 11px;font-style: italic">Produced and Supported with loving care by <span style="text-align: center;color: #c12e2a"><a href="http://mtsconsulting.biz/">MTS Solutions</a></span></div>
        <div style="float:right;">We love feedback - <a href="mailto:feedback@markhaney.net">Bring It!</a></div>
    </div>
</footer>

