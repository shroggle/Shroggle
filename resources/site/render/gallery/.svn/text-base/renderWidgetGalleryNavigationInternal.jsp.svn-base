<%@ page import="com.shroggle.logic.gallery.PaginatorType" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.presentation.gallery.NavigationCell" %>
<%@ page import="com.shroggle.presentation.gallery.NavigationRow" %>
<%@ page import="com.shroggle.presentation.site.render.ShowPageVersionUrlGetter" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%
    List<NavigationRow> navigationRows = (List<NavigationRow>) request.getAttribute("navigationRows");
    navigationRows = navigationRows != null ? navigationRows : new ArrayList<NavigationRow>();
    PaginatorType paginatorType = (PaginatorType) request.getAttribute("paginatorType");
    paginatorType = paginatorType != null ? paginatorType : PaginatorType.NONE;
    final int navigationWidth = (Integer) request.getAttribute("navigationWidth");
    final int navigationHeight = (Integer) request.getAttribute("navigationHeight");
    final String verticalOverflow = ((Boolean) request.getAttribute("verticalScroll")) ? "overflow-y:auto" : "overflow-y:hidden";
    final String horizontalOverflow = ((Boolean) request.getAttribute("horizontalScroll")) ? "overflow-x:auto" : "overflow-x:hidden";
    final Boolean hideWatchedVideos = (Boolean) request.getAttribute("hideWatchedVideos");
    boolean setCellId = true;

    final Widget paginatorWidget1 = (Widget) request.getAttribute("widgetGallery");
    final int paginatorWidgetId1 = paginatorWidget1 != null ? paginatorWidget1.getWidgetId() : -1;
    final int pageId = paginatorWidget1 != null ? paginatorWidget1.getPage().getPageId() : -1;
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
    final Gallery paginatorGallery1 = (Gallery) request.getAttribute("gallery");
    final int paginatorGalleryId1 = paginatorGallery1 != null ? paginatorGallery1.getId() : -1;
%>

<div id="<%= request.getAttribute("galleryNavigationDivId") %>"
     style=' width:<%= navigationWidth %>px; height:<%= navigationHeight %>px; <%= horizontalOverflow %>; <%= verticalOverflow %>;'>

    <input type="hidden" id="galleryId<%= paginatorWidgetId1 %>"
           value="<%= paginatorGallery1.getId() %>">
    <input type="hidden" id="firstFilledFormId<%= paginatorWidgetId1 %>"
           value="<%= paginatorGallery1.getFirstFilledFormId() %>">
    <input type="hidden" id="siteShowOption<%= paginatorWidgetId1%>" value="<%= siteShowOption %>">


    <% if (hideWatchedVideos != null) { %>
    <div class="hideWatchedVideos">
        <a href="javascript:hideGalleryWatchedVideos(<%= paginatorWidgetId1 %>, <%= paginatorGalleryId1 %>, '<%= siteShowOption %>', false);" <%= hideWatchedVideos ? "" : "style=\"display: none;\"" %>
           class="showWatchedVideos">Show All</a>
        <a href="javascript:hideGalleryWatchedVideos(<%= paginatorWidgetId1 %>, <%= paginatorGalleryId1 %>, '<%= siteShowOption %>', true);" <%= !hideWatchedVideos ? "" : "style=\"display: none;\"" %>
           class="hideWatchedVideos">Hide Video Already Watched</a>
    </div>
    <% } %>

    <%-----------------------Paginator above-----------------------%>
    <% if (paginatorType == PaginatorType.ABOVE) { %>
    <%@include file="renderWidgetGalleryNavigationPaginator.jsp" %>
    <% } %>
    <%-----------------------Paginator above-----------------------%>
    <%---------------------------------------------------Thumbnails---------------------------------------------------%>
    <% for (int i = 0; i < navigationRows.size(); i++) { %>
    <div style="width:100%; text-align:center;">
        <div id="<%= request.getAttribute("galleryNavigationRowDivId") %><%= i %>"
             style="margin : 0 auto; width : <%= request.getAttribute("navigationRowWidth") %>px;">
            <% for (NavigationCell cell : navigationRows.get(i).getCells()) {
                boolean clickable = cell.getGalleryId() > 0 && cell.getFilledFormId() > 0 && cell.getUrl() != null && !cell.getUrl().getUserScript().isEmpty();
            %>
            <div <% if (setCellId) { setCellId = false; %>id="<%= request.getAttribute("galleryNavigationCellDivId") %>" <% } %>
                 style="float:left;">
                <div <% if (clickable) { %>onclick="<%= cell.getUrl().getUserScript() %>"<% } %>
                     <% if (clickable && !StringUtil.isNullOrEmpty(cell.getUrl().getAjaxDispatch())) { %>ajaxHistory="<%= cell.getUrl().getAjaxDispatch() %>"<% } %>
                     style="overflow:hidden;<% if (clickable) { %>cursor:pointer;<% } %><%= cell.getStyle() %>">
                    <% if (clickable) { %>
                    <a style="display:none;"
                       href="<%= cell.getUrl().getSearchEngineUrl() %>"><%= cell.getUrl().getKeywords() %></a>
                    <% } %>
                    <table style="width:100%;height:100%;" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <%-----------------------------Left Gallery Navigation Column-----------------------------%>
                            <% if (cell.getInnerCellsForLeftColumn().size() > 0) {%>
                            <td style="vertical-align:middle;">
                                <% request.setAttribute("navigationInnerCells", cell.getInnerCellsForLeftColumn()); %>
                                <jsp:include page="galleryNavigationInnerColumn.jsp" flush="true"/>
                            </td>
                            <% } %>
                            <%-----------------------------Left Gallery Navigation Column-----------------------------%>
                            <%----------------------------Center Gallery Navigation Column----------------------------%>
                            <% if (cell.getInnerCellsForCenterColumn().size() > 0) { %>
                            <td style="vertical-align:middle;">
                                <% request.setAttribute("navigationInnerCells", cell.getInnerCellsForCenterColumn()); %>
                                <jsp:include page="galleryNavigationInnerColumn.jsp" flush="true"/>
                            </td>
                            <% } %>
                            <%----------------------------Center Gallery Navigation Column----------------------------%>
                            <%----------------------------Right Gallery Navigation Column-----------------------------%>
                            <% if (cell.getInnerCellsForRightColumn().size() > 0) { %>
                            <td style="vertical-align:middle;">
                                <% request.setAttribute("navigationInnerCells", cell.getInnerCellsForRightColumn()); %>
                                <jsp:include page="galleryNavigationInnerColumn.jsp" flush="true"/>
                            </td>
                            <% } %>
                            <%----------------------------Right Gallery Navigation Column-----------------------------%>
                        </tr>
                    </table>
                </div>
            </div>
            <% } %>
        </div>
    </div>
    <div style="clear:both;"></div>
    <% } %>
    <%---------------------------------------------------Thumbnails---------------------------------------------------%>
    <%-----------------------Paginator below-----------------------%>
    <% if (paginatorType == PaginatorType.BELOW) { %>
    <%@include file="renderWidgetGalleryNavigationPaginator.jsp" %>
    <% } %>
    <%-----------------------Paginator below-----------------------%>
    <% final String paginatorOnloadValue = "createCorrectGallerySizeWithoutPaginator({" +
            "navigationId : '" + request.getAttribute("galleryNavigationDivId") + "'," +
            "rowId : '" + request.getAttribute("galleryNavigationRowDivId") + "'," +
            "cellId : '" + request.getAttribute("galleryNavigationCellDivId") + "'," +
            "paginatorId : '" + request.getAttribute("paginatorId") + "'," +
            "displayedRowsNumber : " + ((Gallery) request.getAttribute("gallery")).getRows() + "," +
            "displayedColumnsNumber : " + ((Gallery) request.getAttribute("gallery")).getColumns() + "," +
            "rowsNumber : " + request.getAttribute("galleryNavigationRowsNumber") + "," +
            "columnsNumber : " + request.getAttribute("galleryNavigationColumnsNumber") + "});"; %>
    <elementWithOnload:element onload="<%= paginatorOnloadValue %>"/>
</div>