<%@ page import="com.shroggle.presentation.gallery.PaginationCell" %>
<%@ page import="com.shroggle.entity.WidgetItem" %>
<%@ page import="com.shroggle.entity.DraftGallery" %>
<%--
  @author Balakirev Anatoliy
  Date: 02.08.2009
--%>

<%
    PaginationCell[] paginatorSelectCells = (PaginationCell[]) request.getAttribute("paginationCells");
    paginatorSelectCells = paginatorSelectCells != null ? paginatorSelectCells : new PaginationCell[0];
    final WidgetItem paginatorSelectWidget = (WidgetItem) request.getAttribute("widgetGallery");
    final int paginatorSelectWidgetId = paginatorSelectWidget != null ? paginatorSelectWidget.getWidgetId() : -1;
    final DraftGallery paginatorSelectGallery = (DraftGallery) request.getAttribute("gallery");
    final int paginatorSelectGalleryId = paginatorSelectGallery != null ? paginatorSelectGallery.getId() : -1;
    final String paginatorSelectShowOption = request.getAttribute("siteShowOption").toString();
%>
<select onchange="galleryNavigationSelectChanged(this);" class="paginationNumberCell">
    <% for (int i = 1; i < paginatorSelectCells.length - 1; i++) {
        PaginationCell cell = paginatorSelectCells[i];
    %>
    <option value="<%= paginatorSelectWidgetId %>;<%= paginatorSelectGalleryId %>;<%= cell.getPageNumber() %>;<%= paginatorSelectShowOption %>"
            <% if (cell.isSelected()) { %> selected <% } %>>
        <%= cell.getValue() %>
    </option>
    <% } %>
</select>