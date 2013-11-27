</div>
<!-- Modal -->
<div id="dialogLogin" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Close</button>
        <h3 id="myModalLabel">Who are you?</h3>
    </div>
    <div class="modal-body">
        <form id="login" action="/omo/applicationusers/login" method="POST" enctype="application/x-www-form-urlencoded">
            <div style="width:30px;">Email</div><input type="text" name="email" placeholder="Email"><br/>
            <div style="width:30px;">Password</div><input type="password" name="password" placeholder="Password"><br/>
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
            <label for="email">Email: </label><input id="email" type="text" name="email" placeholder="Email"><br/>
            <label for="password">Password: </label><input id="password" type="password" name="password" placeholder="Password"><br/>
            <p>Some contact info so you can be reached if there is a question or issue</p>
            <label for="nameFirst">First Name: </label> <input id="nameFirst" type="text" name="nameFirst" placeholder="First Name"><br/>
            <label for="nameFirst">Last Name: </label> <input type="text" name="nameLast" placeholder="Last Name"><br/>
            <label for="nameFirst">Phone Number: </label> <input type="text" name="phoneNumber" placeholder="Contact Phone Number"><br/>
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
<footer class="footer" >
    <div class="container">
        <p>This is the footer and something important should probably be said here.</p>
    </div>
</footer>



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
/*
        $('#myFormSubmit').click(function(e){
            e.preventDefault();
            alert($('#myField').val());
            */
/*
             $.post('http://path/to/post',
             $('#myForm').serialize(),
             function(data, status, xhr){
             // do something here with response;
             });
             *//*

            alert('ffkfk');
        });
*/
/*
        $( document ).ready(function() {
            //console.log( "ready!" );
            //$( "#dialog" ).dialog();
        });
*/

/*
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
*/
    </script>


</body>