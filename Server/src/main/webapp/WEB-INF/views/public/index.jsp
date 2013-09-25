
    <head>
        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
        <meta content="IE=8" http-equiv="X-UA-Compatible" />
        <meta charset="utf-8" />
        <title>Order Lunch</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />


        <style>
            .section {border-top: 1px solid red}

            label {
                display: block;
                padding-left: 15px;
                text-indent: -15px;
            }
            input {
                width: 13px;
                height: 13px;
                padding: 0;
                margin:0;
                vertical-align: bottom;
                position: relative;
                top: -1px;
                *overflow: hidden;
            }

            .divCheckbox {
                float:left;
                width:18px;
                margin-left:20px;
            }
            .divNamePrice {
                width: 170px;
                /*float:left;*/
            }

            .menuItemName{
                float:left;
            }

            .menuItemPrice{
                float:right;
            }

            .divMenuSection {
                border-style: solid;
                border-width: 1px;
                border-color: #195f91;
                margin-top: 10px;
                padding: 3px;
                background-color: #e9e7ff;
            }

        </style>

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

        <script src="/omo/resources/assets/js/jquery.js" ></script>
        <%--<script src="http://platform.twitter.com/widgets.js" type="text/javascript" ></script>--%>
        <script src="/omo/resources/assets/js/bootstrap-transition.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-alert.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-modal.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-dropdown.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-scrollspy.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-tab.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-tooltip.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-popover.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-button.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-collapse.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-carousel.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-typeahead.js" ></script>
        <script src="/omo/resources/assets/js/bootstrap-affix.js" ></script>
        <script src="/omo/resources/assets/js/holder/holder.js" ></script>
        <script src="/omo/resources/assets/js/google-code-prettify/prettify.js" ></script>
        <script src="/omo/resources/assets/js/application.js" ></script>
        <%--<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/jquery-ui.js"></script>--%>
        <script type="text/javascript">
            $('#myFormSubmit').click(function(e){
                e.preventDefault();
                alert($('#myField').val());
                /*
                 $.post('http://path/to/post',
                 $('#myForm').serialize(),
                 function(data, status, xhr){
                 // do something here with response;
                 });
                 */
                alert('ffkfk');
            });
            $( document ).ready(function() {
                //console.log( "ready!" );
                //$( "#dialog" ).dialog();
            });

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



    <title>Welcome to HTML {0}</title>
</head>
<body data-target=".bs-docs-sidebar" data-spy="scroll">
    <div id="wrapper">
        <div version="2.0" id="header"><meta charset="utf-8" />
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
        </div>
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
                    <div style="float:right;width:120px;" class="nav-collapse collapse">
                        <ul class="nav">
                            <li class="active"><a href="./order.html">ORDERS</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="main"><!-- Subhead ================================================== -->
            <header class="jumbotron subhead" id="overview">
                <div class="container">
                    <h1>Order Lunch</h1>
                    <p class="lead">Pick your reseller, order from the menu, eat.</p>
                </div>
            </header>


            <div class="container">
                <a href="#dialogLogin" role="button" class="btn" data-toggle="modal">Login</a>
                <a href="#dialogRegister" role="button" class="btn" data-toggle="modal">Register</a>

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

                <!-- Modal -->
                <div id="dialogLogin" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Close</button>
                        <h3 id="myModalLabel">Who are you?</h3>
                    </div>
                    <div class="modal-body">
                        <form>
                           <input type="text" name="email" placeholder="Email"><br/>
                           <input type="password" name="password" placeholder="Password"><br/>
                            <button class="btn btn-primary">Login</button>
                        </form>
                        <%--<p>Enter your email and password.</p>--%>
                    </div>
                    <div class="modal-footer">
                        <%--<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>--%>
                        Not Registered Yet? <button class="btn btn-primary">Register</button>
                    </div>
                </div>

                <div id="dialogRegister" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="registerHeader" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Close</button>
                        <h3 id="registerHeader">Who are you?</h3>
                    </div>
                    <div class="modal-body">
                        <form id="applicationUser" action="/omo/applicationusers/registerUser" method="POST" enctype="application/x-www-form-urlencoded">
                           <input type="text" name="email" placeholder="Email"><br/>
                           <input type="password" name="password" placeholder="Password"><br/>
                            <p>Some contact info so you can be reached if there is a question or issue</p>
                           <input type="text" name="nameFirst" placeholder="First Name">
                           <input type="text" name="nameLast" placeholder="Last Name"><br/>
                           <input type="text" name="phoneNumber" placeholder="Contact Phone Number"><br/>
                           <button class="btn btn-primary">Register Me!</button>
                        </form>
                        <%--<p>Enter your email and password.</p>--%>
                    </div>
<%--
                    <div class="modal-footer">
                        &lt;%&ndash;<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>&ndash;%&gt;
                        Not Registered Yet? <button class="btn btn-primary">Register</button>
                    </div>
--%>
                </div>
            </div>
        </div>
    </div>
    <footer class="footer">
        <div class="container">
            <p>This is the footer and something important should probably be said here.</p>
        </div>
    </footer>
</body>