<%@ page import="com.shroggle.entity.UserOnSiteRight" %>
<%@ page import="com.shroggle.presentation.account.accessPermissions.AccessPermissionsModel" %>
<%@ page import="com.shroggle.presentation.account.accessPermissions.RemoveUserRightsUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="accessPermissions"/>
<% AccessPermissionsModel model = ((AccessPermissionsModel) request.getAttribute("model"));
    final RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse = model.getRemovedRightsResponse(); %>

<input type="hidden" id="usersNumber" value="<%= model.getUsersCount() %>">
<% if (removedRightsResponse != null && (!removedRightsResponse.getRemovedRights().isEmpty() || !removedRightsResponse.getRemovedUsers().isEmpty())) { %>
<div class="greenMessage" id="deletedUsers">
    <%---------------------------------------------Removed Rights Message---------------------------------------------%>
    <% if (!removedRightsResponse.getRemovedRights().isEmpty()) {
        for (RemoveUserRightsUtil.RemovedRights removedRight : removedRightsResponse.getRemovedRights()) {
            final String email = removedRight.getEmail();
            final String siteTitle = removedRight.getSiteTitle(); %>
    <international:get name="removedAccess">
        <international:param value="<%= email %>"/>
        <international:param value="<%= siteTitle %>"/>
    </international:get>
    <br>
    <% }
    } %>
    <%---------------------------------------------Removed Rights Message---------------------------------------------%>
    <%----------------------------------------------Removed Users Message---------------------------------------------%>
    <% if (!removedRightsResponse.getRemovedUsers().isEmpty()) {
        final String usersRemovedMessage = (removedRightsResponse.getRemovedUsers().size() == 1) ? "userWasRemoved" : "usersWereRemoved";%>
    <br>
    <international:get name="<%= usersRemovedMessage %>"/>
    <br>
    <%= removedRightsResponse.getRemovedUsersEmails() %>
    <br>
    <% } %>
    <%----------------------------------------------Removed Users Message---------------------------------------------%>
</div>
<% } %>
<table id="usersTable" class="tbl_blog">
    <thead>
    <tr>
        <td style="text-align:center;">
            <international:get name="firstName"/>
        </td>
        <td style="text-align:center;">
            <international:get name="lastName"/>
        </td>
        <td style="text-align:center;">
            <international:get name="userId"/>
        </td>
        <td style="text-align:center;">
            <international:get name="sites"/>
        </td>
        <td style="text-align:center;">
            <international:get name="accessType"/>
        </td>
        <td style="text-align:center;">
            <international:get name="status"/>
        </td>
        <td style="text-align:center;">
            <international:get name="delete"/>
        </td>
    </tr>
    </thead>
    <%
        int i = 0;
        for (AccessPermissionsModel.UserSites userSites : model.getUserSites()) {
    %>
    <tbody id="modifyUsersTable">
    <tr <% if (i % 2 == 0) { %>class="odd"<% } %>>
        <td>
            <a href="javascript:showShareYourSitesWindow(<%= userSites.getUserId() %>)">
                <%= userSites.getUserFirstName() %>
            </a>
        </td>
        <td>
            <a href="javascript:showShareYourSitesWindow(<%= userSites.getUserId() %>)">
                <%= userSites.getUserLastName() %>
            </a>
        </td>
        <td><%= userSites.getUserEmail() %>
        </td>
        <td>
            <table>
                <% for (String siteTitle : userSites.getSiteTitles()) { %>
                <tr>
                    <td style="border:none; padding:0;">
                        <% final boolean isNeedLimitation = HtmlUtil.isNeedLimitation(siteTitle, 40); %>
                        <div <% if (isNeedLimitation) { %>onmouseover="bindTooltip({element:this, contentElement:$(this).find('.content')});"<% } %>>
                            <%= HtmlUtil.limitName(siteTitle, 40) %>
                            <div style="display:none" class="content">
                                <%= siteTitle %>
                            </div>
                        </div>
                    </td>
                </tr>
                <% } %>
            </table>
        </td>
        <td>
            <table>
                <% for (UserOnSiteRight right : userSites.getRights()) { %>
                <tr>
                    <td style="border:none; padding:0;">
                        <a href="javascript:showShareYourSitesWindow(<%= userSites.getUserId() %>)"><international:get
                                name="<%= right.getSiteAccessType().toString() %>"/>
                        </a>
                    </td>
                </tr>
                <%}%>
            </table>

        </td>
        <td>
            <table>
                <% for (final UserOnSiteRight right : userSites.getRights()) { %>
                <tr>
                    <td style="border:none; padding:0;">
                        <% if (userSites.isUserActivated() && right.isActive()) { %>
                        <international:get name="active"/>
                        <% } else { %>
                        <international:get name="pending"/>
                        <% } %>
                    </td>
                </tr>
                <%}%>
            </table>
        </td>
        <td>
            <input type="checkbox" id="user<%= i %>" value="<%=userSites.getUserId()%>" name="checkbox">
        </td>
    </tr>
    </tbody>
    <%
            i++;
        }
    %>
</table>