<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="bodyTop.jsp" %>
<div style="margin:70px;" >
    <h4>Press "CONFIRM ORDER" to place your order.</h4>
    Order from ${order.menu.name}  on ${order.orderDate} <br/>
    There are ${order.menuItems.size()} items.
    <div style="margin-left:40px;margin-top: 20px;margin-bottom: 0px;border: 1px solid #c0bed2; padding:0px ">
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
        <table class="table " >
            <tr><td><b>Notes:</b> ${order.notes}</td><td></td></tr>
        </table>
    </div>
    <c:set var="url" value="${pageContext.request.contextPath}/menus/showMenu/${order.menu.id}"/>
    <form id="orderConfirmation" action="/omo/orders/confirmOrder" method="POST" enctype="application/x-www-form-urlencoded">
        <div style="margin-top: 20px;margin-left:200px;">
            <button type="button" class="btn-danger btn-large" onclick="window.location='${pageContext.request.contextPath}/menus/showMenu/${order.menu.id}'">CANCEL</button>
            <span>&nbsp;</span>
            <button type="submit" class="btn-success btn-large">CONFIRM ORDER</button>
        </div>
        <input type='hidden' name='orderId' value='${order.id}' />
    </form>
</div>