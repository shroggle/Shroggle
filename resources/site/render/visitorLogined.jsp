<%@ page import="com.shroggle.presentation.site.ShowVisitorLoginService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="visitorsLogined"/>

<% ShowVisitorLoginService service = (ShowVisitorLoginService) request.getAttribute("service"); %>
<div id="loginBlock<%=service.getWidgetId()%>">
    <div class="loginForm">
        <international:get name="welcome"/><%=service.getVisitor().getEmail()%><br>
        <a href="#" onclick="logout(<%=service.getWidgetId()%>)"><international:get name="logout"/></a>
    </div>
</div>
