<%@ page import="com.shroggle.entity.Group" %>
<%@ page import="com.shroggle.logic.groups.GroupsTime" %>
<%@ page import="com.shroggle.logic.groups.GroupsTimeManager" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.util.TimeInterval" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="availableGroups"/>
<%--
  @author: Balakirev Anatoliy
--%>
<% final GroupsTimeManager groupsTimeManager = new GroupsTimeManager((List<GroupsTime>) request.getAttribute("savedGroupsWithTime")); %>
<table id="placeInviteeIntoTheFollowingGroups">
    <% for (Group group : (List<Group>) request.getAttribute("groups")) {
        final boolean groupChecked = groupsTimeManager.containsGroup(group.getGroupId()); %>
    <tr style="margin-bottom:5px;">
        <td>
            <div style="width:100px; overflow:hidden;">
                <input type="checkbox" id="groupId<%= group.getGroupId() %>"
                    <% if (groupChecked) { %> checked<% } %>
                        onchange="disableTimeIntervalCheckbox(<%= group.getGroupId() %>);">
                <label for="groupId<%= group.getGroupId() %>">
                    &nbsp;<%= group.getName() %>
                </label>
            </div>
        </td>
        <% TimeInterval timeInterval = groupsTimeManager.getSavedTimeInterval(group.getGroupId());
            TimeInterval savedInterval = timeInterval != null ? timeInterval : TimeInterval.ONE_MONTH; %>
        <td style="margin-left:15px;">
            <input type="checkbox" id="limitedTimeForGroupCheckbox<%= group.getGroupId() %>"
                <% if(!groupChecked) { %>disabled<% } %>
                <% if (timeInterval != null) { %> checked<% } %>
                   onchange="disableTimeIntervalSelect(<%= group.getGroupId() %>);">
            <label for="limitedTimeForGroupCheckbox<%= group.getGroupId() %>">
                &nbsp;<international:get name="forLimitedTimeEndsIn"/>:&nbsp;
            </label>
            <select id="limitedTimeForGroupSelect<%= group.getGroupId() %>" <% if (timeInterval == null) { %> disabled<% } %>>
                <% for (TimeInterval limitedTime : TimeInterval.getLimitedTimeForGroups()) { %>
                <option value="<%= limitedTime.toString() %>"
                        <% if (limitedTime == savedInterval) { %>selected<% } %> >
                    <international:get name="<%= limitedTime.toString() %>"/>
                </option>
                <% } %>
            </select>
        </td>
    </tr>
    <% } %>
</table>