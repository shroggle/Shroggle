<%@ page import="com.shroggle.presentation.gallery.PaginationCell" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="com.shroggle.entity.Gallery" %>
<%--
  @author Balakirev Anatoliy
--%>
<%
    PaginationCell[] paginatorLinksCells = (PaginationCell[]) request.getAttribute("paginationCells");
    paginatorLinksCells = paginatorLinksCells != null ? paginatorLinksCells : new PaginationCell[0];
    final Widget paginatorLinksWidget = (Widget) request.getAttribute("widgetGallery");
    final int paginatorLinksWidgetId = paginatorLinksWidget != null ? paginatorLinksWidget.getWidgetId() : -1;
    final Gallery paginatorLinksGallery = (Gallery) request.getAttribute("gallery");
    final int paginatorLinksGalleryId = paginatorLinksGallery != null ? paginatorLinksGallery.getId() : -1;
%>
<% for (int i = 1; i < paginatorLinksCells.length - 1; i++) {
    PaginationCell cell = paginatorLinksCells[i]; %>
<div onclick='<%= cell.isSelected() || cell.getPageNumber() == -1 ? "" : "showGalleryNavigation(" + paginatorLinksWidgetId + "," + paginatorLinksGalleryId + "," + cell.getPageNumber() + ",\"" + request.getAttribute("siteShowOption").toString() + "\");" %>'
     style="float:left;text-align:center;<%= cell.getStyle() %>"
     class="<%= cell.getCssClassName() %> paginationCells paginationNumberCell"><%= cell.getValue() %></div>
<% } %>
