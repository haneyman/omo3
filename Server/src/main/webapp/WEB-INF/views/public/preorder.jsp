<!-- Subhead ================================================== -->
<header class="jumbotron subhead" id="overview">
    <div class="container">
        <h1>Order Lunch</h1>
        <p class="lead">Pick your reseller, order from the menu, eat.</p>
    </div>
</header>


<div class="container">
    <a href="#myModal" role="button" class="btn" data-toggle="modal">Launch demo modal</a>
    <button type='button' onclick="openDialog();"  >Launch bs dialog</button>
    <button type='button' onclick="openDialog2();"  >Launch jquery dialog</button>
    <%--<button type='button' onclick="alert('ffkfk');">Launch 3</button>--%>
    <!-- Download  ================================================== -->
    <section id="step1">
        <div class="row-fluid">
            <div class="span20">
                <p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;Pick Reseller&nbsp;&nbsp;&nbsp;</a></p>
                <h3>Pick the business that will deliver lunch to you</h3>
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


    <section id="step3">
        <div class="row-fluid">
            <div class="span20">
                <p><a class="btn btn-large btn-primary" href="" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Eat&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></p>
                <h3>Pick up your food from your reseller and EAT.</h3>
            </div>
        </div>
    </section>


    <div id="dialog" title="Basic dialog">
        <p>ndow can be moved, resized and closed with the 'x' icon.</p>
    </div>
    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">Modal header</h3>
        </div>
        <div class="modal-body">
            <p>One fine body…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button class="btn btn-primary">Save changes</button>
        </div>
    </div>



</div>
<script src="/omo/resources/assets/js/jquery.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/jquery-ui.js"></script>
<script type="text/javascript">
    function openDialog() {
        alert('should be open');
        $('#myModal').dialog('open');
    }
    function openDialog2() {
//        alert('should be open');
        $('#dialog').dialog('open');
    }
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
        $( "#dialog" ).dialog();
    });
</script>