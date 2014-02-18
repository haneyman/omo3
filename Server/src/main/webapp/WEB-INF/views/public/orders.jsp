<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="topNavBar.jsp" %>

<div class="container">
    <div class="" style="padding-left:20px;background-color: #7aba7b">
        <h3>Today's Orders</h3>
    </div>
    <table class="table">
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
                    <table class="table table-striped" >
                        <c:forEach items="${order.menuItems}" var="menuItem">
                            <tr>
                                <td>${menuItem.internalNotes} - ${menuItem.name}</td>
                                <td>
                                    $ <fmt:formatNumber value="${menuItem.price}" type="currency" currencySymbol="" />
                                </td>
                            </tr>
                        </c:forEach>
                        <tr><td style="text-align: right;"><b>Total:</b></td>
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