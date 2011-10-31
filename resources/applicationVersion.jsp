<%@ page import="com.shroggle.presentation.ApplicationVersionAction" %>
<%--
 Author: Igor Kanshin (grenader).

 Date: 27.06.2008
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ApplicationVersionAction action = (ApplicationVersionAction) request.getAttribute("actionBean"); %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<html>
    <head><title>Web-Deva App. <%= action.getApplicationVersion() %></title>
    <cache:no/>
    </head>
    <body><pre><%= action.getApplicationVersion() %></pre></body>
</html>