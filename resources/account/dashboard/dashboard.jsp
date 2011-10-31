<%--
    @author Balakirev Anatoliy
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.ChargeType" %>
<%@ page import="com.shroggle.logic.site.billingInfo.ChargeTypeManager" %>
<%@ page import="com.shroggle.presentation.account.dashboard.DashboardAction" %>
<%@ page import="com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page import="com.shroggle.presentation.account.dashboard.DashboardSiteType" %>
<international:part part="dashboard"/>
<% final DashboardAction action = (DashboardAction) request.getAttribute("actionBean"); %>
<% Locale.setDefault(new Locale("en")); %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><international:get name="dashboardTitle"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript" src="/tinymce/jquery.tinymce.js"></script>
    <link href="../../css/dashboard_style.css" rel="stylesheet" type="text/css">
</head>

<body onload="initDashboardAccordion('<%= action.getSelectedSiteType() %>');">
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>

        <div class="dashboardWrapper">
            <input type="hidden" id="offlineSiteConfirm" value="<international:get name="offlineSiteConfirm"/>"/>
            <input type="hidden" id="siteDelete" value="<international:get name="siteDelete"/>"/>
            <input type="hidden" id="deleteActivatedBlueprint" value="<international:get name="deleteActivatedBlueprint"/>"/>
            <input type="hidden" id="hasBeenPublished" value="<international:get name="hasBeenPublished"/>"/>
            <input type="hidden" id="dashboardPage" value="true"/>
            <div class="grey_24">
                <international:get name="dashboard"/>
            </div>
            <jsp:include page="/account/dashboardMenu.jsp"/>

            <div class="mainarea">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="t1" width="315">
                            <% final DashboardSiteInfo dashboardSiteInfo = action.getSelectedSiteInfo();
                                request.setAttribute("selectedSiteInfo", dashboardSiteInfo); %>
                            <div id="dashboardAccordion" class="dashboardAccordionsHolder<%= UsersManager.isLoginedUserAppAdmin() ? "Increased" : "" %>">
                                <div class="<%= action.getSelectedSiteType() == DashboardSiteType.COMMON ? "dashboardSelectedAccordion" : "dashboardNotSelectedAccordion" %> <% if (!action.isCommonSitesInfoEmpty()) { %>dashboardAccordionHeader<% } %>"
                                     id="<%= DashboardSiteType.COMMON %>">
                                    <international:get name="yourSites"/>
                                </div>
                                <% request.setAttribute("dashboardSiteInfo", action.getDashboardCommonSitesInfo()); %>
                                <jsp:include page="dashboardSitesList.jsp" flush="true"/>
                                <div class="dashboardHeader <%= action.getSelectedSiteType() == DashboardSiteType.NETWORK ? "dashboardSelectedAccordion" : "dashboardNotSelectedAccordion" %> <% if (!action.isNetworksInfoEmpty()) { %>dashboardAccordionHeader<% } %>"
                                     id="<%= DashboardSiteType.NETWORK %>">
                                    <international:get name="childSites"/></div>
                                <% request.setAttribute("dashboardSiteInfo", action.getDashboardNetworksInfo()); %>
                                <jsp:include page="dashboardSitesList.jsp" flush="true"/>
                                <div class="dashboardHeader <%= action.getSelectedSiteType() == DashboardSiteType.BLUEPRINT ? "dashboardSelectedAccordion" : "dashboardNotSelectedAccordion" %> <% if (!action.isBlueprintsInfoEmpty()) { %>dashboardAccordionHeader<% } %>"
                                     id="<%= DashboardSiteType.BLUEPRINT %>">
                                    <international:get name="blueprints"/></div>
                                <% request.setAttribute("dashboardSiteInfo", action.getDashboardBlueprintsInfo()); %>
                                <jsp:include page="dashboardSitesList.jsp" flush="true"/>
                                <%---------------------------Block for Web-Deva admins only---------------------------%>
                                <% if (UsersManager.isLoginedUserAppAdmin()) { %>
                                <div class="dashboardHeader <%= action.getSelectedSiteType() == DashboardSiteType.PUBLISHED_BLUEPRINT ? "dashboardSelectedAccordion" : "dashboardNotSelectedAccordion" %> <% if (!action.isPublishedBlueprintsInfoEmpty()) { %>dashboardAccordionHeader<% } %>"
                                     id="<%= DashboardSiteType.PUBLISHED_BLUEPRINT %>">
                                    <international:get name="publishedBlueprints"/></div>
                                <% request.setAttribute("dashboardSiteInfo", action.getPublishedBlueprints()); %>
                                <jsp:include page="dashboardSitesList.jsp" flush="true"/>
                                <div class="dashboardHeader <%= action.getSelectedSiteType() == DashboardSiteType.ACTIVATED_BLUEPRINT ? "dashboardSelectedAccordion" : "dashboardNotSelectedAccordion" %> <% if (!action.isActivatedBlueprintsInfoEmpty()) { %>dashboardAccordionHeader<% } %>"
                                     id="<%= DashboardSiteType.ACTIVATED_BLUEPRINT %>">
                                    <international:get name="activatedBlueprints"/></div>
                                <% request.setAttribute("dashboardSiteInfo", action.getActivatedBlueprints()); %>
                                <jsp:include page="dashboardSitesList.jsp" flush="true"/>
                                <%---------------------------Block for Web-Deva admins only---------------------------%>
                                <% } %>
                            </div>
                        </td>
                        <td class="t2">
                            <div id="siteInfo" style="float:left;">
                                <% if (dashboardSiteInfo != null) {
                                    request.setAttribute("dashboardSiteInfo", dashboardSiteInfo); %>
                                <jsp:include page="siteInfo.jsp" flush="true"/>
                                <% } else { %>
                                <a href="/site/createSite.action"><img border="0" src="../../images/dashboard/createAWebsite.png" alt=""></a>
                                <% } %>
                            </div>
                            <div style="clear:both;"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
<div id="optOutFromNetworkInfo" style="display:none;">
    <div class="windowOneColumn">
        <international:get name="optOutFromNetworkInfo">
            <international:param value="<%= new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() %>"/>
        </international:get>
        <br clear="all">
        <br>

        <p align="right">
            <input type="button" onclick="optOutFromNetworkFinish();"
                   class="but_w73"
                   onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   value="Close">
        </p>
    </div>
</div>
<input type="hidden" id="optOutFromNetworkConfirm" value="<international:get name="optOutFromNetworkConfirm"/>"/>
</body>
</html>
