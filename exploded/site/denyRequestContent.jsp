<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<international:part part="denyRequestContent"/>
<html>
<head>
    <title><international:get name="title"/></title>
    <jsp:include page="/includeHeadPresentationResources.jsp" flush="true">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">
</head>
<body>
<div id="wrapper">
    <div id="container">
    <%@ include file="/includeHeadPresentation.jsp" %>
    <div id="mainContentNdi" class="clearbothNd" style="text-align:center;">
        <h1><international:get name="header"/></h1><br>

        <h2><international:get name="message"/></h2><br>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
        </div>
</div>
</body>
</html>