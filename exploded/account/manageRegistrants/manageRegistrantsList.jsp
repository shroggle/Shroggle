<%@ page import="com.shroggle.entity.DraftForm" %>
<%@ page import="com.shroggle.entity.Group" %>
<%@ page import="com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType" %>
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<%@ page import="com.shroggle.presentation.site.RegisteredVisitorInfo" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageRegistrants"/>
<% final Paginator paginator = (Paginator) request.getAttribute("paginator");
    final List<RegisteredVisitorInfo> registrantsList = paginator.getItems();

    final ManageRegistrantsSortType sortType = (ManageRegistrantsSortType) request.getAttribute("manageRegistrantsSortType");
    request.setAttribute("descending", request.getAttribute("manageRegistrantsDesc"));
    final int siteId = (Integer) request.getAttribute("manageRegistrantsSiteId");
    final List<Group> availableGroups = (List<Group>) request.getAttribute("availableGroups");%>
<input type="hidden" id="selectVisitors" value="<international:get name="selectVisitors"/>"/>

<div class="manageGroupsDiv">
    <div class="manageRegistransManageGroupsSubDiv">
        <international:get name="assignGroups"/>:
        <input type="button" value="<international:get name="addSelectedVisitorsToGroup"/>"
               onclick="groups.showManageGroupsWindow(<%= siteId %>);"
               class="but_w230"
               onmouseover="this.className = 'but_w230_Over';"
               onmouseout="this.className = 'but_w230';">
        <select id="removeFromGroup" onchange="groups.removeVisitorsFromGroup(this.value);" style="width:255px;">
            <option selected="selected" value="-1">
                <international:get name="removeSelectedVisitorsFromGroup"/>
            </option>
            <% for (Group group : availableGroups) { %>
            <option value="<%= group.getGroupId() %>">
                <%= group.getName() %>
            </option>
            <% } %>
        </select>
    </div>
</div>

<div style="clear:both;"></div>

<table class="tbl_blog" style="width:100%" id="manageRegistrantsTable">
    <thead style="cursor:default" class="sortable">
    <tr>
        <td>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="firstNameTd">
            <international:get name="firstNameTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.FIRST_NAME));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.FIRST_NAME.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="lastNameTd">
            <international:get name="lastNameTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.LAST_NAME));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.LAST_NAME.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="emailTd">
            <international:get name="emailTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.EMAIL));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.EMAIL.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="createdTd">
            <international:get name="createdTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.CREATED_DATE));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.CREATED_DATE.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="updatedTd">
            <international:get name="updatedTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.UPDATED_DATE));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.UPDATED_DATE.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="formNameTd">
            <international:get name="formNameTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.FORM_NAME));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.FORM_NAME.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();"
            id="statusTd">
            <international:get name="statusTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.STATUS));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.STATUS.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageRegistrants.sort();">
            <international:get name="groupsTd"/>
            <% request.setAttribute("show", (sortType == ManageRegistrantsSortType.GROUP));
                request.setAttribute("sortFieldType", ManageRegistrantsSortType.GROUP.toString()); %>
            <jsp:include page="../../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="deleteTd">
            <international:get name="deleteTd"/>
        </td>
    </tr>
    </thead>
    <tbody id="registrantsTableBody">
    <% if (registrantsList == null || registrantsList.isEmpty()) { %>
    <tr id="no_registranrs">
        <td colspan="10">
            <international:get name="emptyTable"/>
        </td>
    </tr>
    <% } else { %>
    <% boolean odd = false; %>
    <% for (final RegisteredVisitorInfo visitor : registrantsList) { %>
    <tr <%= odd ? "class=\"odd\"" : "" %> id="row<%=visitor.getVisitorId()%>">
        <input type="hidden" name="visitorId" value="<%=visitor.getVisitorId()%>"/>
        <td>
            <input type="checkbox" id="<%= visitor.getVisitorId() %>" name="addRemoveVisitorsToGroup"/>
        </td>
        <td>
            <a href="javascript:manageRegistrants.showEditVisitor(<%= visitor.getVisitorId() %>, <%= siteId %>)">
                <%= (StringUtil.isNullOrEmpty(visitor.getFirstName())) ? "&lt;not specified&gt;" : visitor.getFirstName()%>
            </a>
        </td>
        <td>
            <a href="javascript:manageRegistrants.showEditVisitor(<%= visitor.getVisitorId() %>, <%= siteId %>)">
                <%= (StringUtil.isNullOrEmpty(visitor.getLastName())) ? "&lt;not specified&gt;" : visitor.getLastName()%>
            </a>
        </td>
        <td>
            <%= visitor.getEmail() %>
        </td>
        <td>
            <%= visitor.getCreated() %>
        </td>
        <td>
            <%= visitor.getUpdated() %>
        </td>
        <td>
            <% for (int i = 0; i < visitor.getForms().size(); i++) { %>
            <% DraftForm form = visitor.getForms().get(i); %>
            <%= i != 0 ? ", " : "" %><a
                href="javascript:manageItems.showItemSettings({itemId:<%= form.getFormId() %>, itemType: '<%= form.getItemType() %>', onAfterClose:manageRegistrants.updateManageRegistransForms})">
            <span class="itemName<%= form.getFormId() %>"><%= form.getName() %></span>
        </a>
            <% } %>
        </td>
        <td>
            <span id="statusSpan<%= visitor.getVisitorId() %>"><%= visitor.getStatus() %></span>
        </td>
        <td>
            <%= visitor.getGroupsNames() %>
        </td>
        <td width="5%" style="text-align:center;">
            <input type="image" src="/images/cross-circle.png" value="Delete"
                   onclick="manageRegistrants.deleteVisitor(<%= visitor.getVisitorId() %>);">
        </td>
    </tr>
    <% odd = !odd; %>
    <% } %>
    <%}%>
    </tbody>
</table>

<div class="manageRegistrantsSelectDeselectDiv">
    <a href="javascript:manageRegistrants.selectAllVisitors();"><international:get name="selectAll"/></a>
    &nbsp;
    <a href="javascript:manageRegistrants.deselectAllVisitors();"><international:get name="deselectAll"/></a>
</div>

<input type="hidden" id="paginatorDivToUpdate" value="registrantsDiv"/>
<% request.setAttribute("updatePaginatorItemsFunction", "manageRegistrants.updateRegistrantsTable"); %>
<jsp:include page="/paginator/paginator.jsp"/>