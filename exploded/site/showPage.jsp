<%@ page import="com.shroggle.presentation.site.ShowPageVersionAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ShowPageVersionAction action = (ShowPageVersionAction) request.getAttribute("actionBean"); %>
<%= action.getPageVersionHtml() %>