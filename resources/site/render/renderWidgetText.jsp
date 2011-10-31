<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final String text = request.getAttribute("widgetText") != null ? (String) request.getAttribute("widgetText") : ""; %>
<%= StringUtil.isNullOrEmpty(text) || HtmlUtil.removeParagraphs(text).isEmpty()
        ? "&lt;no text entered&gt;" : text %>