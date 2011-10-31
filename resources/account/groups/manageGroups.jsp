<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.presentation.manageRegistrants.ShowManageGroupsWindowService" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageGroups"/>
<%--
  @author: Balakirev Anatoliy
--%>
<% final ShowManageGroupsWindowService service = (ShowManageGroupsWindowService) request.getAttribute("service"); %>

<div class="windowOneColumn">
    <h1>
        <international:get name="addVisitorsToGroups"/>
    </h1>

    <div class="emptyError" id="errors"></div>
    <div style="margin-bottom:15px;">
        <international:get name="placeSelectedVisitorsIntoFollowingGroups"/>:
        <div
             style="width:400px; height:100px;border:1px solid gray;overflow-y:auto;overflow-x:hidden;margin-top:10px;padding:5px;">
            <% request.setAttribute("groups", service.getGroups());%>
            <jsp:include page="availableGroups.jsp" flush="true"/>
        </div>
    </div>


    <div align="right">
        <input type="button" id="windowSave"
               onclick="groups.addVisitorsToGroups('<%= service.getCheckedVisitorsId() %>');"
               value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';"
               class="but_w73">

        <input type="button" id="windowCancel" onclick="closeConfigureWidgetDivWithConfirm();" value="<international:get name="cancel"/>"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>

</div>