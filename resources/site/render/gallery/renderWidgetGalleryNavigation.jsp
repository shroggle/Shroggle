<%@ page import="com.shroggle.entity.WidgetItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final Widget widgetGalleryNavigation = (Widget) request.getAttribute("widgetGallery");
%>
<div id="galleryNavigation<%= widgetGalleryNavigation.getWidgetId() %>">
    <%@include file="renderWidgetGalleryNavigationInternal.jsp" %>
</div>