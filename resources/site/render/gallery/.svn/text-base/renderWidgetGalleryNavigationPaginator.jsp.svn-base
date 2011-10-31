<%@ page import="com.shroggle.presentation.gallery.PaginationCell" %>
<%@ page import="com.shroggle.entity.Gallery" %>
<%@ page import="com.shroggle.entity.GalleryNavigationPaginatorType" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderWidgetGalleryNavigationPaginator"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final boolean hidePaginator = (Boolean) request.getAttribute("hidePaginator");
    PaginationCell[] paginatorCells = (PaginationCell[]) request.getAttribute("paginationCells");
    paginatorCells = paginatorCells != null ? paginatorCells : new PaginationCell[0];
    final Widget paginatorWidget = (Widget) request.getAttribute("widgetGallery");
    final int paginatorWidgetId = paginatorWidget != null ? paginatorWidget.getWidgetId() : -1;
    final Gallery paginatorGallery = (Gallery) request.getAttribute("gallery");
    final int paginatorGalleryId = paginatorGallery != null ? paginatorGallery.getId() : -1;
    final String paginatorShowOption = request.getAttribute("siteShowOption").toString();
    if (!hidePaginator) {
        PaginationCell firstCell = paginatorCells[0];
        PaginationCell lastCell = paginatorCells[paginatorCells.length - 1];
%>
<div>
    <div style="float:left;position:relative;left:50%">
        <div style="float:left;position:relative;left:-50%" id="<%= request.getAttribute("paginatorId") %>Holder">
            <div style="text-align:center;vertical-align:middle;padding:4px;float:left;"
                 id="<%= request.getAttribute("paginatorId") %>">

                <%---------------------------------------------`previous` link------------------------------------------------%>
                <% if (request.getAttribute("navigationType") != GalleryNavigationPaginatorType.PICK_LIST_WITH_NUMBERS) { %>
                    <div onclick='<%= firstCell.isSelected() || firstCell.getPageNumber() == -1 ? "" : "showGalleryNavigation(" + paginatorWidgetId + "," + paginatorGalleryId + "," + firstCell.getPageNumber() + ",\"" + paginatorShowOption + "\");" %>'
                         style="float:left;text-align:center;<%= firstCell.getStyle() %>;<% if (firstCell.getPageNumber() == -1) { %>visibility:hidden;<% } %>"
                         class="<%= firstCell.getCssClassName() %> paginationCells paginationPreviousCell"><%= firstCell.getValue() %>
                    </div>
                <% } %>
                <%---------------------------------------------`previous` link------------------------------------------------%>

                <div style="float:left;text-align:center;">
                    <% if (request.getAttribute("navigationType") == GalleryNavigationPaginatorType.PICK_LIST_WITH_NUMBERS) { %>
                        <international:get name="goToPage"/>&nbsp;<%@include file="paginatorSelect.jsp" %>
                    <% } else { %>
                        <%@include file="paginatorLinks.jsp" %>
                    <% } %>
                </div>

                <%-----------------------------------------------`next` link--------------------------------------------------%>
                <% if (request.getAttribute("navigationType") != GalleryNavigationPaginatorType.PICK_LIST_WITH_NUMBERS) { %>
                    <div onclick='<%= lastCell.isSelected() || lastCell.getPageNumber() == -1 ? "" : "showGalleryNavigation(" + paginatorWidgetId + "," + paginatorGalleryId + "," + lastCell.getPageNumber() + ",\"" + paginatorShowOption + "\");" %>'
                        style="float:left;text-align:center;<%= lastCell.getStyle() %>;<% if (lastCell.getPageNumber() == -1) { %>visibility:hidden;<% } %>"
                        class="<%= lastCell.getCssClassName() %> paginationCells paginationNextCell"><%= lastCell.getValue() %>
                    </div>
                <% } %>
                <%-----------------------------------------------`next` link--------------------------------------------------%>
            </div>
            <div style="clear:both;"></div>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<% } %>
