<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="registrationFinished"/>
<html>
<head>
    <title><international:get name="registrationFinished"/></title>
    <jsp:include page="/includeHeadPresentationResources.jsp">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">
</head>
<body>
<div id="wrapper">
    <div id="container">
    <%@ include file="/includeHeadPresentation.jsp" %>
    <div id="mainContentCentered" class="clearbothNd">
        <div class="regfin">
            <h2>
                <international:get name="thankYou1"/>
            </h2>
        </div>
        <p class="regfin">
            <international:get name="thankYou2"/>
        </p>

        <p class="regfin">
            <span><strong><international:get name="thankYou3"/></strong></span>&nbsp;<international:get
                name="thankYou4"/>
        </p>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
    </div>
</body>
</html>