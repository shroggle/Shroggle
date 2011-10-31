<%@ page import="com.shroggle.presentation.site.RegistrationConfirmationAction" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="registrationConfirmation"/>
<%
    final RegistrationConfirmationAction actionBean = (RegistrationConfirmationAction) request.getAttribute("actionBean");
%>
<html>
<head>
    <title><international:get name="registrationConfirmation"/></title>
    <style type="text/css">
        a {
            color: gray;
            text-decoration: none
        }
    </style>
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
        <stripes:form beanclass="com.shroggle.presentation.site.RegistrationConfirmationAction">
            <p>
                <stripes:link beanclass="com.shroggle.presentation.StartAction">
                    <international:get name="home"/>
                </stripes:link>
            </p>
            <br>

            <div style="width:530px; text-align: left; padding:30px; font-size:14px; margin: 0 auto;">
                <international:get name="thankYou"/>
                <a href="mailto:<%= actionBean.getInfoEMail() %>"><international:get
                        name="contactUs"/></a><international:get name="directly"/>
                <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/FAQ"><international:get
                        name="Tutorial_FAQ"/></a><international:get name="sections"/>
                <a href="http://www.info.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Forum"><international:get
                        name="forums"/></a><international:get name="toChat"/>
                <br> <br> <br>

                <div style="text-align:center">
                    <input type="button" class="but_w170" onmouseover="this.className='but_w170_Over';"
                           onmouseout="this.className='but_w170';" value="Create Site Now"
                           onclick="{window.location = '../site/createSite.action';}"/>
                </div>
            </div>
        </stripes:form>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
    </div>
</body>
</html>