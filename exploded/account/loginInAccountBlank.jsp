<%@ page import="com.shroggle.presentation.site.ShowLoginAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<international:part part="head"/>
<% final ShowLoginAction action = (ShowLoginAction) request.getAttribute("actionBean"); %>
<html>
<head>
    <title> Login - Web-Deva</title>
    <cache:no/>

    <script type="text/javascript">
        var internationalLoginInAccountErrorTexts = new Object();
        internationalLoginInAccountErrorTexts.inputAnEmail = "<international:get name="inputAnEmail"/>";
        internationalLoginInAccountErrorTexts.wrongEmailAddress = "<international:get name="wrongEmailAddress"/>";
        internationalLoginInAccountErrorTexts.wrongEmailAddressConfirm = "<international:get name="wrongEmailAddressConfirm"/>";
        internationalLoginInAccountErrorTexts.availableEmaile = "<international:get name="availableEmaile"/>";
        internationalLoginInAccountErrorTexts.loading = "<international:get name="loading"/>";
        internationalLoginInAccountErrorTexts.unknownLoginOrPassword = "<international:get name="unknownLoginOrPassword"/>";
        internationalLoginInAccountErrorTexts.accountNotActive = "<international:get name="accountNotActive"/>";
        internationalLoginInAccountErrorTexts.sessionHasExpired = "<international:get name="sessionHasExpired"/>";
        internationalLoginInAccountErrorTexts.waitCheckInputEmail = "<international:get name="waitCheckInputEmail"/>";
    </script>
    <script type="text/javascript" src="/dwr/engine.js"></script>
    <resources:natural name="login.css"/>
    <resources:natural name="login.js"/>
</head>

<body onload="showLoginInAccount('actionException','<%= action.getEnterUrl() %>');">
</body>

</html>