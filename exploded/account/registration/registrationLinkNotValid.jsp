<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="registrationLinkNotValid"/>
<html>
<head>
    <title><international:get name="registerLinkNotValid"/></title>
    <style type="text/css">
        a {
            color: gray;
            text-decoration: none
        }
    </style>
    <jsp:include page="/includeHeadPresentationResources.jsp" flush="true">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">
</head>
<body>
<div id="wrapper">
    <div id="container">
    <%@ include file="/includeHeadPresentation.jsp" %>
    <div id="mainContentCentered" class="clearbothNd">
        <stripes:form beanclass="com.shroggle.presentation.site.RegistrationConfirmationAction">
            <p>
                <stripes:link beanclass="com.shroggle.presentation.StartAction">
                    <international:get name="home"/>
                </stripes:link>
            </p>
            <br>
            <br><br><br>

            <p style="width:420px; text-align: center;font-weight:bold;color:red;">
                <international:get name="yourRegistrationLinkIsntValid"/>
            </p>
            <br><br><br>
        </stripes:form>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
        </div>
</div>
</body>
</html>