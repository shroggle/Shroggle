<%@ page import="com.shroggle.presentation.site.RegistrationConfirmationAction" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.entity.ChildSiteSettings" %>
<%@ page import="com.shroggle.presentation.childSites.ChildSiteRegistrationConfirmationAction" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.util.resource.provider.ResourceGetterType" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.logic.image.ImageManager" %>
<%@ page import="com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="childSiteRigistrationConfirmationFirstState"/>
<%
    final ChildSiteRegistrationConfirmationAction actionBean = (ChildSiteRegistrationConfirmationAction) request.getAttribute("actionBean");
    final ChildSiteSettings settings = actionBean.getChildSiteSettings();
    final Site networkSite = settings != null ? settings.getParentSite() : null;
    final String networkSiteName = networkSite != null ? settings.getParentSite().getTitle() : "";
    final String networkSiteLink = "<a href='" + new SiteManager(networkSite).getPublicUrl() + "' target='_blank'>" + networkSiteName + "</a>";
    request.setAttribute("logoUrl", new ChildSiteSettingsManager(settings).getHisNetworkLogoUrl());
%>
<html>
<head>
    <title><international:get name="registrationConfirmation"/></title>
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
            <div style="text-align: left; padding:30px;">
                <%= StringUtil.getEmptyOrString(settings != null ? settings.getWelcomeText() : "").replace("&lt;network site name&gt;", networkSiteLink) %>

                <br>
                <br>
                <br>

                <div style="text-align:center; font-weight: bold;color:green; font-size:larger;">
                    <international:get name="yourSiteHasBeenCreated"/>
                </div>

                <br>
                <br>

                <div style="text-align:center;">
                    <international:get name="weHaveUsedDefaultSettings"/>
                    <a href="<%= actionBean.getUrl() %>" target="_blank"><%= actionBean.getUrl() %>
                    </a>
                </div>
                <br>

                <div style="width:50%;margin: 0 auto;" align="center">
                    <%= actionBean.getTellFriendHtml() %>
                </div>
                <br>

                <div style="text-align:center;">
                    <international:get name="toEditYourSiteGoToYour"/>
                    &nbsp;
                    <input type="button" class="but_w130" onmouseover="this.className='but_w130_Over';"
                           onmouseout="this.className='but_w130';"
                           value="<international:get name="dashboard"/>"
                           onclick="{window.location = '/account/dashboard.action';}"/>
                </div>

            </div>
        </stripes:form>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
    </div>
</body>
</html>