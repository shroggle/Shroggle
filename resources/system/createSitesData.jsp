<%@ page import="com.shroggle.presentation.system.CreateSitesDataAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<% final CreateSitesDataAction action = (CreateSitesDataAction) request.getAttribute("actionBean"); %>
"<%= action.getInfo() %>"