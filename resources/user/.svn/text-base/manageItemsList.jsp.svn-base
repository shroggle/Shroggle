<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.logic.SiteOnItemManager" %>
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.logic.user.items.UserItemsInfo" %>
<%@ page import="com.shroggle.logic.user.items.UserItemsSortType" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageItems"/>
<%  final Paginator<UserItemsInfo> paginator = (Paginator<UserItemsInfo>) request.getAttribute("paginator");
    List<UserItemsInfo> items = paginator.getItems();
    ItemType itemType = (ItemType) request.getAttribute("itemType");
    final UserItemsSortType sortType = (UserItemsSortType) request.getAttribute("userItemsSortType"); %>
<input type="hidden" id="userItemsItemType" value="<%= itemType %>"/>
<table class="tbl_blog" id="userItemsTable" style="width:100%;">
    <thead class="sortable">
    <tr>
        <td style="width:15px;">&nbsp;</td>
        <td onclick="manageItems.sortUserItems();">
            <international:get name="itemType"/>
            <% request.setAttribute("show", (sortType == UserItemsSortType.ITEM_TYPE));
                request.setAttribute("sortFieldType", UserItemsSortType.ITEM_TYPE.toString()); %>
            <jsp:include page="../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageItems.sortUserItems();">
            <international:get name="itemName"/>
            <% request.setAttribute("show", (sortType == UserItemsSortType.NAME));
                request.setAttribute("sortFieldType", UserItemsSortType.NAME.toString()); %>
            <jsp:include page="../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageItems.sortUserItems();">
            <international:get name="created"/>
            <% request.setAttribute("show", (sortType == UserItemsSortType.DATE_CREATED));
                request.setAttribute("sortFieldType", UserItemsSortType.DATE_CREATED.toString()); %>
            <jsp:include page="../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageItems.sortUserItems();">
            <international:get name="updated"/>
            <% request.setAttribute("show", (sortType == UserItemsSortType.DATE_UPDATED));
                request.setAttribute("sortFieldType", UserItemsSortType.DATE_UPDATED.toString()); %>
            <jsp:include page="../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="manageItems.sortUserItems();" style="width:110px;">
            <international:get name="postsRecords"/>
            <% request.setAttribute("show", (sortType == UserItemsSortType.RECORDS_NUMBER));
                request.setAttribute("sortFieldType", UserItemsSortType.RECORDS_NUMBER.toString()); %>
            <jsp:include page="../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td><international:get name="settings"/></td>
        <td onclick="manageItems.sortUserItems();">
            <international:get name="sites"/>
            <% request.setAttribute("show", (sortType == UserItemsSortType.SITE));
                request.setAttribute("sortFieldType", UserItemsSortType.SITE.toString()); %>
            <jsp:include page="../site/sortTable/sortArrows.jsp" flush="true"/>
        </td>
    </tr>
    </thead>
    <tbody id="userItemsList">
    <% if (items.isEmpty()) { %>
    <tr>
        <td <% if (itemType == ItemType.ALL_FORMS) {%>colspan="8" <% } else { %>colspan="7"<% } %>>
            <international:get name="noItemsFound"/></td>
    </tr>
    <% } else { %>
    <% boolean odd = false; %>
    <% for (final UserItemsInfo item : items) { %>
    <tr <%= odd ? "class=\"odd\"" : "" %>>
        <td style="width:15px;">
            <input type="checkbox" value="<%= item.getId() %>,<%= item.getType() %>" name="itemSelectOrDeselect"
                   class="itemSelectOrDeselect">
        </td>
        <td>
            <international:get name="<%= item.getType().toString() %>"/>
        </td>
        <td>
            <a id="itemName<%= item.getId() %>"
               href="javascript:manageItems.showItemContent({onAfterClose: manageItems.updateUserItems, itemId: <%= item.getId() %>, itemType: '<%= item.getType() %>', itemName: '<%= item.getMaskedName() %>'})"><%= item.getName() %>
            </a>
        </td>
        <td><%= item.getItemCreatedDate() == null ? "" : DateUtil.toCommonDateStr(item.getItemCreatedDate()) %>
        </td>
        <td id="recordUpdated<%= item.getId() %>"><%= item.getItemUpdatedDate() == null ? (item.getItemCreatedDate() == null ? "" : DateUtil.toCommonDateStr(item.getItemCreatedDate())) : DateUtil.toCommonDateStr(item.getItemUpdatedDate()) %>
        </td>
        <td id="recordCount<%= item.getId() %>" style="text-align:center;width:110px;"><%= item.getRecordCount() %>
        </td>
        <td style="text-align:center">
            <% if (item.getType() != ItemType.LOGIN && item.getType() != ItemType.GALLERY_DATA) { %>
            <input type="image" src="/images/editSettings.png" value="Edit Settings"
                   onclick="manageItems.showItemSettings({onAfterClose: manageItems.updateUserItems, itemId: <%= item.getId() %>, itemType: '<%= item.getType() %>'}); return false;">
            <% } %>
        </td>
        <td>
            <% for (final SiteOnItemManager siteOnItemManager : item.getRights()) { %>
            <% final SiteManager siteManager = siteOnItemManager.getSite(); %>
            <% if (siteManager.isBlueprint()) { %>
            <international:get name="blueprint"/>:&nbsp;<%= HtmlUtil.limitName(siteManager.getName(), 40) %>
            <% } else { %>
            <a target="_blank"
               href="<%= siteManager.getPublicUrl() %>"><%= HtmlUtil.limitName(siteManager.getName(), 40) %>
            </a>
            <% } %>
            <% if (siteOnItemManager.getType() == null) { %>
            &ndash;&nbsp;<international:get name="owner"/>
            <% } else { %>
            &ndash;&nbsp;<international:get name="<%= siteOnItemManager.getType().toString() %>"/>
            <% if (siteOnItemManager.isDeletable()) { %>
            <a href="javascript:itemsDeletion.deleteItemAccess(<%= siteManager.getId() %>, <%= item.getId() %>, '<%= item.getType() %>')"><img
                    src="/images/cross-circle.png" border="0" alt="Delete"></a>
            <% } %>
            <% } %>
            <br>
            <% } %>
        </td>
    </tr>
    <% odd = !odd; %>
    <% } %>
    <% } %>
    </tbody>
</table>

<input type="hidden" id="paginatorDivToUpdate" value="userItemsDiv"/>
<% request.setAttribute("updatePaginatorItemsFunction", "manageItems.updateUserItems"); %>
<jsp:include page="/paginator/paginator.jsp"/>