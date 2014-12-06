<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="topNavBar.jsp" %>

<script>
    function printThis() {
        $('#moneyFilter').hide();
        window.print();
        $('#moneyFilter').show();
    }
</script>
<style>
    .container tr {
        line-height: 5px;
        min-height: 5px;
        height: 5px;
    }

    .table > TBODY > TR > TD { padding: 0px;}
</style>
<div class="container" style="margin-top: 25px;">
    <c:set var="open"  value=""/>
    <c:set var="today"  value=""/>
    <c:set var="all"  value=""/>
    <c:choose>
        <c:when test='${filter.equals("open")}'>
            <c:set var="open"  value="active"/>
        </c:when>
        <c:when test='${filter.equals("today")}'>
            <c:set var="today"  value="active"/>
        </c:when>
        <c:when test='${filter.equals("all")}'>
            <c:set var="all"  value="active"/>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>


    <ul class="nav nav-pills hidden-print">
        <li class="${open} "><a href="/omo/orders/orders/open">All Orders</a></li>
        <li class="${today} "><a href="/omo/orders/orders/today">Today's Orders</a></li>
        <%--<li class="${all} "><a href="/omo/orders/orders/all">All + Init</a></li>--%>
    </ul>
    <div class="hidden-print" style="float:right; margin-top: -40px;" >
        <button type="button" class="btn btn-info" onclick="printThis();">Print</button>
    </div>
    <div class="" style="padding-left:20px;background-color: #7aba7b;">
        <h3>Today's Orders</h3>
    </div>
    <table class="table " >
        <tr>
            <th>Who</th>
            <th>Date</th>
            <%--<th>Status</th>--%>
            <th>Items
               <div class="" id="moneyFilter" style="margin-top: 0px;margin-right: 50px;float:right;">
                <input type="checkbox" checked="true" onclick="$('.money').toggle();">Prices</input>
               </div>
            </th>
        </tr>
        <c:set var="isBreak" value="false"/>

        <c:forEach items="${orders}" var="order">
            <c:if test="${!order.isToday()}">
                <c:if test="${not isBreak}">
                    </table>
                        <div class="" style="padding-left:20px;background-color: #7aba7b">
                            <h3>Previous Orders</h3>
                        </div>
                    <table class="table">
                </c:if>
                <c:set var="isBreak" value="true"/>
            </c:if>
            <tr><td colspan="3"></td></tr>
            <tr style="background-color: #effff7;margin: 1em;">
                <td>${order.user.nameFirst} ${order.user.nameLast} <br/> ${order.user.email} <br/> ${order.user.phoneNumber} </td>
                <%--<td>${order.applicationUser}  </td>--%>
                <td><fmt:formatDate value="${order.orderDate}" pattern="E MM/dd/yyyy "/>
                    <br/><fmt:formatDate value="${order.orderDate}" pattern="hh:mm aa"/></td>
                <%--<td>${order.status}</td>--%>
                <td>
                    <table class="table " >
                        <c:forEach items="${order.orderItems}" var="orderItem">
                            <tr  style="background-color: #effff7;">
                                <td>${orderItem.section.name} - ${orderItem.group.name}
                                    - <span style="font-weight: 800">${orderItem.menuItem.name}</span>
                                    <c:forEach items="${orderItem.menuItem.options}" var="optionGroup">
                                        <c:forEach items="${optionGroup.children}" var="option">
                                            <br/><span style="font-size: small">+ ${optionGroup.description} - ${option.description}<span>
                                        <div style="float: right;">$ <fmt:formatNumber value="${option.price}" type="currency" currencySymbol="" /></div>
                                        </c:forEach>
                                    </c:forEach>
                                </td>
                                <td>
                                    $ <fmt:formatNumber value="${orderItem.price}" type="currency" currencySymbol="" />
                                </td>
                                <c:choose>
                                    <c:when test="${orderItem.note == ''}">
                                        <!-- no notes  -->
                                    </c:when>
                                    <c:otherwise>
                                        <table class=" " >
                                            <tr  style="background-color: #effff7;"><td><b>Note:</b> ${orderItem.note}</td><td></td></tr>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        <tr   style="background-color: #effff7;">
                            <td colspan="3" style="border-top-width: 0px;" >
                                <div style="margin-left: 10px;text-align: right;margin-right: 4px;border-top-width: 0px;"><b>Order Total:</b>
                                    <b>$ <fmt:formatNumber value="${order.totalPretax}" type="currency" currencySymbol="" /></b>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>