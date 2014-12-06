<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="topNavBar.jsp" %>
<script>
    $( document ).ready(function() {
        <c:if test="${order != null}">
            $('#dialogOrderConfirm').modal('show');
        </c:if>
    });
</script>

<div class="container">
    <div class="" style="padding-left:20px;background-color: #7aba7b">
        <h3>Your orders</h3>
    </div>
    <table class="table">
        <tr>
            <th>Date</th>
            <%--<th>Status</th>--%>
            <th>Items</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><fmt:formatDate value="${order.orderDate}" pattern="E MM/dd/yyyy "/>
                    <br/><fmt:formatDate value="${order.orderDate}" pattern="hh:mm aa"/>
                    <div style="margin-left: 40px;margin-top: 40px;">
                        <%--<button type="button" class="btn btn-info">Order this now!</button>--%>
                        <a href="reorder/${order.id}" class="btn btn-primary active" role="button">Order This Again!</a>
                    </div>
                </td>
                <%--<td>${order.status}</td>--%>
                <td>
                    <table class="table table-striped" >
                        <c:forEach items="${order.orderItems}" var="orderItem">
                            <tr>
                                <td>${orderItem.section.name} - ${orderItem.group.name}
                                    <%--<br/>--%>
                                    - <span style="font-weight: 800">${orderItem.menuItem.name}</span>
                                    <c:forEach items="${orderItem.menuItem.options}" var="optionGroup">
                                        <c:forEach items="${optionGroup.children}" var="option">
                                            <br/><span style="font-size: small">${optionGroup.description} - ${option.description}<span>
                                        </c:forEach>
                                    </c:forEach>
                                </td>
                                <td>
                                    $ <fmt:formatNumber value="${orderItem.total}" type="currency" currencySymbol="" />
                                </td>
                                <c:choose>
                                    <c:when test="${orderItem.note == ''}">
                                        <!-- no notes  -->
                                    </c:when>
                                    <c:otherwise>
                                        <table class="table " >
                                            <tr><td><b>Note:</b> ${orderItem.note}</td><td></td></tr>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        <tr><td style="text-align: right;"><b>Total:</b></td>
                            <td>
                                <b>$ <fmt:formatNumber value="${order.totalPretax}" type="currency" currencySymbol="" /></b>
                            </td>
                        </tr>
                    </table>

                </td>
            </tr>
        </c:forEach>
    </table>
<%--
    <div id="dialogOrderConfirm" class="modal  fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 id="myModalLabel2">Order Confirmation</h4>
                </div>
                <div class="modal-body" >
                    <b>We confirm your awesome meal:</b>
                    <div style="margin-left: 10px;">
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
                        Your yummy meal will be waiting for you to pick it up at:

                        <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#468847">El Cafecito at 1400 Treat by 12:00 pm.</span>
                        <br/><br/>Pay when you pick up.  If you have any questions call Sylvia at x32095.

                    </div>
                </div>
                <div class="modal-footer">
                    <span style="font-size: large;font-style: italic;float: left">Thanks for your business!!</span>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Got It!</button>
                </div>
            </div>
        </div>
    </div>
--%>
</div>
