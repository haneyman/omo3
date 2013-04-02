<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="field" uri="http://www.springframework.org/tags/form" %>


<%--<link href="/comhub/resources/scripts/checkboxtree/jquery.checkboxtree.css" rel="stylesheet" type="text/css"/>--%>
<style type="text/css">
    label {
        display: block;
        padding-left: 15px;
        text-indent: -15px;
        float:left;
        margin:3px;
    }
    input {
        /*width: 200px;*/
        /*height: 13px;*/
        padding: 0px;;
        margin:5px;
        vertical-align: bottom;
        position: relative;
        top: -1px;
        *overflow: hidden;
    }

    .dialogs label {
        display: inline;
        float: none;
        padding: 0px;
    }
    .dialogs input {
        height: auto;
        padding: 0px;
        margin: 0px;
        border-style: none;
    }

    .divListContainer {
        float:left;
        width:302px;
        margin-left: 5px;
        margin-right:5px;
    }

    .tableList {
        border: 0px;
        background-color: white;
    }

    .ui-button-text-only .ui-button-text {
        padding-top: .1em;
        padding-left: 1em;
        padding-right: 1em;
        padding-bottom: .1em;
    }


</style>
<!-- end checkboxTree configuration -->
<%--<page:show id="showDistributionGroup" object="${distributiongroup}" path="/distributiongroups" >--%>

<div>
    <div id="divDebug">
        <div>
            <label>Name:</label>${menu.name}
        </div>
        <div style="clear:both">&nbsp;</div>
        <div>
            <label>Description:</label>${menu.description}
        </div>
        <div style="clear:both">&nbsp;</div>
        <hr/>
    </div>

    <!--  -->
    <div style="">
        <label>Locations:</label>
        <div class="divListContainer" style="">
            <table class="tableList" style="">
                <c:if test="${fn:length(menu.menuItems())==0}">
                    <tr><td>No Items?</td></tr>
                </c:if>
                <c:forEach items="${menu.menuItems()}" var="menuItem">
                    <tr>
                        <td>${location.workBuilding}</td>
                        <td>${location.workStreet} ${location.workStreet2}</td>
                        <td>${location.workCity}, ${location.workState}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div style="clear:both;height:15px;">&nbsp;</div>

