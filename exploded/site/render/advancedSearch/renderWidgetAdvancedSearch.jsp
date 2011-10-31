<%@ page import="com.shroggle.entity.DraftAdvancedSearch" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="com.shroggle.entity.AdvancedSearchOrientationType" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%
    final Widget widget = (Widget) request.getAttribute("widget");
    final DraftAdvancedSearch advancedSearch = (DraftAdvancedSearch) request.getAttribute("advancedSearch");
    final int galleryId = advancedSearch.getGalleryId();
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
%>

<input type="hidden" id="advancedSearchId<%= widget.getWidgetId() %>" value="<%= advancedSearch.getId() %>">
<input type="hidden" id="advancedSearchGalleryId<%= widget.getWidgetId() %>" value="<%= galleryId %>">
<input type="hidden" id="advancedSearchSiteSearchOption<%= widget.getWidgetId() %>" value="<%= siteShowOption %>">

<table id="advancedSearchTable<%= widget.getWidgetId() %>" class="advancedSearchTable">
    <% if (advancedSearch.getAdvancedSearchOrientationType() == AdvancedSearchOrientationType.ABOVE) { %>
    <tr class="advSearchBottomBorder">
        <td>
            <jsp:include page="searchBlock.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include page="galleryBlock.jsp"/>
        </td>
    </tr>
    <% } else if (advancedSearch.getAdvancedSearchOrientationType() == AdvancedSearchOrientationType.LEFT) { %>
    <tr>
        <td class="searchBlockTd_LEFT advSearchRightBorder">
            <jsp:include page="searchBlock.jsp"/>
        </td>
        <td class="galleryBlockTd_LEFT">
            <jsp:include page="galleryBlock.jsp"/>
        </td>
    </tr>
    <% } %>
</table>