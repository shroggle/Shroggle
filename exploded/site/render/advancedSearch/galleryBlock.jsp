<%@ page import="com.shroggle.entity.Widget" %>
<%
    final Widget widget = (Widget) request.getAttribute("widget");
    final String galleryHtml = (String) request.getAttribute("galleryHtml");
%>
<div id="advancedSearchGallery<%= widget.getWidgetId() %>" class="advancedSearchGalleryBlock">
    <%= galleryHtml %>
</div>