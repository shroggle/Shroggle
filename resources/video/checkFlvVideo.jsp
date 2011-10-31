<%@ page import="com.shroggle.presentation.video.CheckFlvVideoAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final CheckFlvVideoAction action = (CheckFlvVideoAction) request.getAttribute("actionBean"); %>
"<%= action.getResult() %>"