<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="topNavBar.jsp" %>

<script>
    function printThis() {
        window.print();
    }
</script>

<div class="container" style="margin-top: 25px;">
    <c:set var="allActive"  value=""/>
    <c:set var="todayActive"  value=""/>
    <c:choose>
        <c:when test='${filter.equals("all")}'>
            <c:set var="allActive"  value="active"/>
        </c:when>
        <c:when test='${filter.equals("today")}'>
            <c:set var="todayActive"  value="active"/>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>


    <ul class="nav nav-pills hidden-print">
        <li class="${allActive} "><a href="/omo/orders/orders/all">All Orders</a></li>
        <li class="${todayActive} "><a href="/omo/orders/orders/today">Today's Orders</a></li>
        <li><a href="#">????</a></li>
        <button style="margin-top: 2px;margin-left: 10px;" type="button" class="btn btn-info " data-toggle="button" onclick="$('.money').toggle();">Hide $$</button>
    </ul>
    <div class="hidden-print" style="float:right; margin-top: -40px;" >
        <button type="button" class="btn btn-info" onclick="printThis();">Print</button>
    </div>
    <div class="" style="padding-left:20px;background-color: #7aba7b;">
        <h3>Today's Orders</h3>
    </div>
    <table class="table table-striped">
        <tr>
            <th>Who</th>
            <th>Date</th>
            <th>Status</th>
            <th>Items</th>
        </tr>
        <c:set var="break" value="False"/>
        <c:forEach items="${orders}" var="order">
            <c:if test="${!order.isToday()}">
                <c:if test='${break == "False"}'>
                    </table>
                        <div class="" style="padding-left:20px;background-color: #7aba7b">
                            <h3>Previous Orders</h3>
                        </div>
                    <table class="table">
                </c:if>
                <c:set var="break" value="True"/>
            </c:if>

            <tr>
                <td>${order.user.nameFirst} ${order.user.nameLast} <br/> ${order.user.email} <br/> ${order.user.phoneNumber} </td>
                <%--<td>${order.applicationUser}  </td>--%>
                <td><fmt:formatDate value="${order.orderDate}" pattern="E MM/dd/yyyy "/>
                    <br/><fmt:formatDate value="${order.orderDate}" pattern="hh:mm aa"/></td>
                <td>${order.status}</td>
                <td>
                    <table class="table " >
                        <c:forEach items="${order.menuItems}" var="menuItem">
                            <tr>
                                <td>${menuItem.internalNotes} - ${menuItem.name}</td>
                                <td class="money">
                                    $ <fmt:formatNumber value="${menuItem.price}" type="currency" currencySymbol="" />
                                </td>
                            </tr>
                        </c:forEach>
                        <tr class="money">
                            <td style="text-align: right;">
                                <b>Total:</b>
                            </td>
                            <td>
                                <b>$ <fmt:formatNumber value="${order.totalPretax}" type="currency" currencySymbol="" /></b>
                            </td>
                        </tr>
                    </table>
                    <c:choose>
                        <c:when test="${order.notes == ''}">
                            <!-- no notes  -->
                        </c:when>
                        <c:otherwise>
                            <table class="table " >
                                <tr><td><b>Notes:</b> ${order.notes}</td><td></td></tr>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>