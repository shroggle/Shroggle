<%@ page import="com.shroggle.presentation.gallery.NavigationInnerCell" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.presentation.gallery.NavigationCellType" %>
<%@ taglib prefix="img" tagdir="/WEB-INF/tags/imageWithLoadingProgress" %>
<%--
 @author Balakirev Anatoliy
--%>
<% List<NavigationInnerCell> navigationInnerCells = (List<NavigationInnerCell>) request.getAttribute("navigationInnerCells"); %>
<% for (NavigationInnerCell innerCell : navigationInnerCells) { %>
<div style="<%= innerCell.getAlign() %>" class="navigationLabels">

    <% if (innerCell.getNavigationCellType() == NavigationCellType.IMAGE) { %>
    <img:image src="<%= innerCell.getValue() %>" align="middle"
         width="<%= innerCell.getResizedWidthAsString() %>"
         height="<%= innerCell.getResizedHeightAsString() %>"
         alt="<%= innerCell.getAlt() %>"
         title="<%= innerCell.getAlt() %>"/>
    <% } else if (innerCell.getNavigationCellType() == NavigationCellType.GRAY_RECTANGLE) {%>
    <div align="middle"
         style="width:<%= innerCell.getResizedWidth() %>px;height:<%= innerCell.getResizedHeight() %>px;background : #F0F0F0;"></div>
    <% } else {%><%= innerCell.getValue() %><% } %>
</div>
<% } %>