<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="topNavBar.jsp" %>
<%--@elvariable id="canOrder" type="boolean"--%>
<%--@elvariable id="offered" type="string"--%>
<%--@elvariable id="menu" type="com.omo.domain.Menu"--%>
<script>
    //<![CDATA[
    <%--<c:if test="${!canOrder}">--%>
    $( document ).ready(function() {
        $('.returnView').val('/menus/showMenu/${menu.id}');//sets the form field in login and register to which page to return to
        <c:if test="${canOrder}">
           <c:if test="${sessionScope.applicationUser == null}">
                $('#divWarningLogin').show()
              //                $('#dialogLogin').modal('show');
           </c:if>
        </c:if>
        <c:if test="${!canOrder}">
            //jquery place to do what needs to be done for when they can't order
        </c:if>
    });

    function submitOrderItem(checkout) {
        if (checkout)
            $('#dialogOrderItemCheckout').value("checkout");
        //alert('submit ' + $('#inputCheckout').value());
        $('#formOrderItem').submit();
    };

    //ajax call to MenuController.getMenuItemForOrderInJSON which returns an OrderItem,
    //OrderItems contain a menuItem, menu, etc.
    function orderItem(item) {
        <c:if test="${sessionScope.applicationUser == null}">
            $("#dialogLogin").modal('show');
            return;
        </c:if>

        var itemUrl = "/omo/menus/getMenuItemForOrder/${menu.id}/" + item;
        $.ajax({
            url: itemUrl,
            context: document.body,
            dataType: "json",
            async: false
        }).done(function(orderItem) {   //data is a OrderItem object
            //alert('done: ' + data);
            $('#dialogOrderItemMenuId').val("${menu.id}");
            $('#dialogOrderItemItemUuid').val(orderItem.menuItem.uuid);
            $('#dialogOrderItemItemName').text(orderItem.menuItem.name);
            $('#dialogOrderItemItemDescription').text(orderItem.menuItem.description);
            $('#dialogOrderItemSections').html("");
            var html = "<!-- begin genereated html from menu.jsp -->";
            jQuery.each(orderItem.menuItem.options, function(i, optionGroup) {
                html +=     "<div class=\"panelOptions panel panel-default \" >";
                html +=     "   <div class=\"panel-heading\">" + optionGroup.description ;
                html +=     "   </div> <!-- end panel-heading -->";
                html +=     "   <div class=\"orderItemPanelBody panel-body\" >";
                html +=     "      <div class=\"form-group\" >";
                jQuery.each(optionGroup.children, function(j, option) {
                    html += "           <div class=\"optionsSection radio\">";
                    html += "              <label>";
                    html += "                 <div class=\"orderItemOptionDescription\" >";
                    html += "                    <input type=\"radio\" name=OPTION_" + optionGroup.uuid + " value=" + option.uuid + " >";
                    //html += "                    <input type=\"radio\" name=OPTION:" + option.uuid + " id=" + child.uuid  + " value=" + option.description + "|" + child.uuid + "|" + child.price + " checked>";
                    html += option.description;
                    html += '                 </div> <!-- end radio option -->';
                    html += "                 <div class=\"orderItemPrice\" > ";
                    if (option.price != "0")
                        html += "$" + option.price;
                    html += "                 </div> <!-- end price -->";
                    html += "              </label>";
                    html += "           </div> <!-- end optionsSection -->";
                });
                html += "      </div> <!-- end form-group --> ";
                html += "   </div> <!-- end panel-body --> ";
                html += "</div> <!-- end panelOptions -->";

            });
            $('#dialogOrderItemSections').append(html);
        }).error(function() {
            alert('Dangit, there was an error, please refresh and try again.');
        });





        $('#dialogOrderItem').modal('show');
        //alert('fin');
    }


    function hideOrderItem() {
        $("#dialogOrderItem").fadeOut();
    }

    function deleteOrderItem(id) {
        hideOrder();
        var itemUrl = "/omo/orders/deleteOrderItem/" + id;
        $.ajax({
            url: itemUrl,
            context: document.body,
            dataType: "json"
            //async: false
        }).done(function(order) {
            //alert('Item deleted');
            showOrder();
        }).error(function() {
            alert('Dangit, there was an error deleting that item, please refresh and try again or contact support.');
        });
    }


</script>
<div class="menu" style="margin-bottom: 200px;">
    <div class="" style="padding-left:20px;background-color: #7aba7b;margin-bottom: 0px;padding:5px; ">
        <div style="float: right;margin-right: 35%;margin-top: 15px;">
            <%--<button id="myOrderButton" type="button" class="btn btn-primary " data-toggle="button" onclick='showOrder();'  >My Order</button>--%>
            <!--<a href="#" onclick="">My Order</a>-->
        </div>
        <h3>${menu.name} ${menu.description}</h3>
        <%--<h3>${menu.name} ${menu.description}</h3>--%>
    </div>
    <div class="container-fluid ">
        <div id="divWarningLogin" class="well well-sm" style=" background:#eff6b7;display:none;">
                Woa there! You need to log in before you can order <span style="font-size: 20px">&#8594;</span> <a href="#dialogLogin"  data-toggle="modal">Log Me In </a>
        </div>
        <!-- Begin Top Message Panels -->
        <div class="row-fluid">
            <div class="col-md-5 well well-sm" style="margin: 5px; font-style: italic;">
                <c:if test="${canOrder}">
                    Select your food, pick options, and press the "Add To Order" button.
                </c:if>
                <c:if test="${!canOrder}">
                    <span style="color:red;">Sorry, you can <b>not</b> order from this menu right now.</span>
                </c:if>
            </div>
            <div class="col-md-6 well well-sm" style="margin: 5px;" id="menuDescription" style="">
                ${offered}
            </div>
        </div>
        <!-- End Top Message Panels -->

        <!-- Begin Menu Sections -->
        <c:forEach var="section" items='${menu.menuItems}'>
            <div class="menuSection row-fluid" style="">
                <div class="menuSectionLabel col-md-12  zzcol-md-offset-1">
                    <h3>${section.name} ${section.description} </h3>
                </div>
                <div class="menuGroups row-fluid ">
                    <!-- Begin Menu Groups -->
                    <c:forEach var="group" items='${section.childMenuItems}'>
                        <!-- Begin Menu Group ${group.name} -->
                        <div class="menuGroup panel panel-default col-xs-12 col-sm-12 col-md-6 col-lg-4">
                            <div class="menuGroupTitleArea panel-heading ">
                                <span class="menuGroupTitle">${group.name}</span>
                                <br/>
                                <span class='menuGroupDescription'> <small>${group.description}</small></span>
                            </div>
                            <div class="menuGroupBody panel-body" >
                                <c:forEach var="item" items='${group.childMenuItems}'>
                                    <!-- Begin Menu Item ${item.name} -->
                                    <div class="menuItem col-md-12 row-fluid">
                                        <div class="col-md-12">
                                            <div class="menuItemName">
                                                <a href="#" class="orderItemLink"
                                                    <c:if test="${canOrder}">onclick='orderItem("${item.uuid}");return true;'</c:if>
                                                    <c:if test="${!canOrder}">onclick="alert('Sorry, you can NOT order from this menu right now.');"</c:if>
                                                >
                                                    ${item.name}
                                                    <c:if test="${!empty fn:trim(item.description)}">
                                                        - <span class="menuItemDescription">${item.description} </span>
                                                    </c:if>
                                                </a>
                                            </div>
                                            <div class="menuItemPrice">
                                                <a href="#" onclick='orderItem("${item.uuid}");return true;'>${item.price}</a>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- End Menu Item ${item.name} -->
                                </c:forEach>  <%-- item --%>
                            </div>
                        </div>
                        <!-- End Menu Group ${group.name} -->
                    </c:forEach> <%-- group --%>
                </div>
            </div> <!-- menuSection -->
        </c:forEach>
        <!-- Menu Section -->
    </div>
</div>


<div id="dialogOrderItem" class="modal  fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!--<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>-->
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <div>
                    <h3 id="myModalLabel2">Add Item To Order?</h3>
                    <div style="color: #214f1e; background-color: #f5f5f5; padding: 20px; font-weight: 500; font-size: large;">
                        <span id="dialogOrderItemItemName">Error</span> - <span id="dialogOrderItemItemDescription"></span>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="formOrderItem" action="/omo/orders/submitOrderItem" method="POST" enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="dialogOrderItemMenuId" id="dialogOrderItemMenuId" value="???">
                    <input type="hidden" name="dialogOrderItemItemUuid" id="dialogOrderItemItemUuid" value="???">
                    <input type="hidden" name="dialogOrderItemCheckout" id="dialogOrderItemCheckout" value="nahDontCheckout">
                    <input type="hidden" name="dialogOrderItemQuantity" id="dialogOrderItemQuantity" value="1">
                    <div  id="dialogOrderItemSections" class="" style="margin-left: 20px;">
                        <%-- Content will be inserted in here by javascript --%>
                    </div>
                    <label for="dialogOrderItemNote">Note</label>
                    <input type="text" class="form-control" name="dialogOrderItemNote"  id="dialogOrderItemNote" placeholder="Note">
                </form>
            </div>
            <div class="modal-footer">
                <div style="margin-top:10">
                    <button class="btn btn-success" style="margin-left: 4px;"  onclick='submitOrderItem(false);'>Add To Order</button>
                    <%--<button class="btn btn-success" style="margin-left: 4px;"  onclick='submitOrderItem(true);'>Add To Order AND Checkout</button>--%>
                </div>
            </div>
        </div>
    </div>
</div>



