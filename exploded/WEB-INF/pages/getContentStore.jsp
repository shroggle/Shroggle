<%@ page import="com.shroggle.presentation.content.GetContentStoreAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final GetContentStoreAction action = (GetContentStoreAction) request.getAttribute("actionBean"); %>
<%= action.getContent() == null ? "" : action.getContent().getValue() %>