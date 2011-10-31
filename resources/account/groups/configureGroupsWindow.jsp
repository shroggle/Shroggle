<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.presentation.groups.GroupData" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGroupsWindow"/>
<%--
  @author: Balakirev Anatoliy
--%>
<%
    final List<GroupData> groupsData = (List<GroupData>) request.getAttribute("groupsData");
    final String siteName = (String) request.getAttribute("siteName");
    final Integer siteId = (Integer) request.getAttribute("siteId");
%>
<input type="hidden" id="removeGroupConfirm" value="<international:get name="removeGroupConfirm"/>">
<input type="hidden" id="groupNameCantBeEmpty" value="<international:get name="groupNameCantBeEmpty"/>">
<input type="hidden" id="duplicateGroupNames" value="<international:get name="duplicateGroupNames"/>">
<div class="windowOneColumn">
    <h1>
        <international:get name="manageVisitorGroups"/>
    </h1>
    <h2>
        <%= siteName %>
    </h2>

    <div class="emptyError" id="errors"></div>
    <table class="tbl_groups head" style="width:100%;">
        <thead style="cursor:default" class="sortable">
        <tr>
            <td class="firstTd">
                <international:get name="name"/>
            </td>
            <td class="secondTd" class="firstTd">
                <international:get name="visitorsCount"/>
            </td>
            <td class="thirdTd">
                <international:get name="delete"/>
            </td>
        </tr>
        </thead>
    </table>
    <div style="max-height:200px; overflow-y:scroll; overflow-x:hidden;">
        <table id="groupsTable" class="tbl_groups" style="width:100%">
            <tbody>
            <%----------------------------------------Default tr for copying in js----------------------------------------%>
            <tr id="defaultTrWithGroup" style="display:none;">
                <td style="width:30%;text-align:center;">
                    <input type="text" id="" value="" name="groupNameWithId" defaultTr="true" maxlength="250" style="width:250px;">
                </td>
                <td name="visitorsCount" style="width:30%;text-align:center;">
                </td>
                <td style="width:30%;text-align:center;vertical-align:middle;">
                    <input type="image" name="deleteGroupButton" src="/images/cross-circle.png"
                           value="<international:get name="delete"/>">
                </td>
            </tr>
            <%----------------------------------------Default tr for copying in js----------------------------------------%>
            <% boolean odd = false;
                for (GroupData groupData : groupsData) { %>
            <tr id="trWithGroup<%= groupData.getGroupId() %>" <% if (odd) { %>class="odd" <% }
                odd = !odd; %>>
                <td style="width:30%;text-align:center;">
                    <input type="text" id="<%= groupData.getGroupId() %>" value="<%= groupData.getName() %>"
                           name="groupNameWithId"
                           maxlength="250" style="width:250px;">
                </td>
                <td name="visitorsCount" style="width:30%;text-align:center;">
                    <%= groupData.getVisitorsCount() %>
                </td>
                <td style="width:30%;text-align:center;vertical-align:middle;">
                    <input type="image" src="/images/cross-circle.png" value="<international:get name="delete"/>"
                           onclick="groups.removeGroup(<%= groupData.getGroupId() %>);">
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <div align="right" style="margin:10px 0 20px 0;">
        <input type="button" onclick="groups.addGroup(<%= siteId %>);" value="<international:get name="add"/>"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>

    <div align="right">
        <input type="button" id="windowSave"
               onclick="groups.updateGroupNames();"
               value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';"
               class="but_w73">

        <input type="button" onclick="closeConfigureWidgetDivWithConfirm();" value="<international:get name="cancel"/>"
               id="windowCancel" onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>

</div>