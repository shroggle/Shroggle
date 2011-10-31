<%@ page import="com.shroggle.entity.ChildSiteSettings" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.presentation.childSites.ChildSiteRegistrationConfirmationAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="childSiteRegistrationConfirmationSecondState"/>
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
            <p>
                <stripes:link beanclass="com.shroggle.presentation.StartAction">
                    <international:get name="home"/>
                </stripes:link>
            </p>
            <br>

            <div style="width:820px; text-align: left; padding:30px;">
                <%= StringUtil.getEmptyOrString(settings != null ? settings.getWelcomeText() : "").replace("&lt;network site name&gt;", networkSiteLink) %>

                <br>
                <br>
                <br>
                <table style="width:100%;">
                    <tr>
                        <td style="vertical-align:top;text-align:center;">
                            <div style="width:100%;margin:0 auto">
                                <div style="padding-bottom:5px;">
                                    <input type="button" class="but_w130"
                                           onmouseover="this.className='but_w130_Over';"
                                           onmouseout="this.className='but_w130';"
                                           value="<international:get name="createSiteNow"/>"
                                           onclick="{window.location = '../site/createSite.action?createChildSite=true&settingsId=<%= settings != null ? settings.getChildSiteSettingsId() : -1 %>';}"/>
                                </div>
                                                <span class="inform_mark_shifted" style="margin:0;">
                                                    <international:get name="stepByStepText"/>
                                                </span>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </stripes:form>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
</body>
</html>