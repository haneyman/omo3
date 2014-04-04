<%@ page import="java.util.Calendar" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="topNavBar.jsp" %>

<div class="" style="padding-left:20px;background-color: #7aba7b;margin-bottom: 30px;">
    <h3>Featured Menus</h3>
</div>
<div class="container">
    <c:set var="curReseller" value=""/>
    <c:forEach items="${schedules}" var="schedule">
        <div>

            <c:if test="${curReseller != schedule.reseller.name}">
                <h4>${schedule.reseller.name} - ${schedule.reseller.address}</h4>
            </c:if>
            <div style="margin-left: 20px;">
                <c:if test="${curDayOfWeek != schedule.getDayOfWeekName()}">
                    <br/>
                    <span style="color:green;font-weight: 800;font-size: larger">${schedule.getDayOfWeekName()}</span>
                </c:if>
                 <div style="margin-left: 20px;">
                    <b><a target="_blank" href="/omo/menus/showMenuByRestaurant/${schedule.restaurant.id}">${schedule.restaurant.name}</a></b> - ${schedule.restaurant.description} at ${schedule.restaurant.address}
                 </div>
            </div>
        </div>
        <c:set var="curReseller" value="${schedule.reseller.name}"/>
        <c:set var="curDayOfWeek" value="${schedule.getDayOfWeekName()}"/>
    </c:forEach>
</div>
<script>
    $( document ).ready(function() {
        $('.returnView').val('/menus/listMenuPublic');//sets the form field in login and register to which page to return to
    });
</script>