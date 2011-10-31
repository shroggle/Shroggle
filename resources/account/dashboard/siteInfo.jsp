<%@ page import="com.shroggle.entity.AccessibleElementType" %>
<%@ page import="com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo" %>
<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page import="com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfoForCreatedSite" %>
<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="siteInfo"/>
<% DashboardSiteInfo siteInfo = (DashboardSiteInfo) request.getAttribute("dashboardSiteInfo"); %>

<div class="mainheader">
    <% if (siteInfo.isSiteCreated()) { %>
    <div class="site_name">
        <a href="/site/siteEditPage.action?siteId=<%= siteInfo.getSiteId() %>" id="siteName" target="_blank"
           style="<%= siteInfo.isActive() ? "" : "color:#777777;" %>"><%= siteInfo.getName() %>
        </a>
    </div>
    <div class="site_info">
        <span>
            <international:get name="lastUpdated"/>
        </span>
        <%= siteInfo.getLastUpdatedDate() %>
        <% if (!siteInfo.isBlueprint()) { %><%--According to http://jira.web-deva.com/browse/SW-5991--%>
        &nbsp;|&nbsp;
        <span>
            <international:get name="url"/>
        </span>
        <span id="activeSiteUrl" style="display:<%= siteInfo.isActive() ? "inline" : "none" %>;">
        <a href="<%= siteInfo.getUrl() %>" target="_blank"><%= siteInfo.getLimitedUrl() %>
        </a>
        </span>
        <span id="disabledSiteUrl" style="display:<%= !siteInfo.isActive() ? "inline" : "none" %>;">
        <%= siteInfo.getLimitedUrl() %>
        </span>
        &nbsp;|&nbsp;
        <span>
            <international:get name="status"/>
        </span>
        <span class="siteStatus_live" id="liveStatus" style="display:<%= siteInfo.isActive() ? "inline" : "none" %>;">
            <international:get name="live"/>
        </span>
        <span class="siteStatus_offline" id="offlineStatus"
              style="display:<%= !siteInfo.isActive() ? "inline" : "none" %>;">
            <international:get name="offLine"/>
        </span>
        <% } %>
    </div>
    <% } else { %>
    <%------------------------------------------------Not created Site------------------------------------------------%>
    <div class="site_name">
        <international:get name="siteHasNotBeenCreatedYet"/>
    </div>
    <div class="site_info">
        <span>
            <stripes:link beanclass="com.shroggle.presentation.site.CreateSiteAction">
                <stripes:param name="createChildSite" value="true"/>
                <stripes:param name="parentSiteId" value="<%= siteInfo.getParentSiteId() %>"/>
                <stripes:param name="settingsId" value="<%= siteInfo.getChildSiteSettingsId() %>"/>
                <span class="grey_14_u"><international:get name="createAMemberWebsite"/></span>
            </stripes:link>
            &nbsp;&nbsp;<%= siteInfo.getCreatedDateAsString() %>&nbsp;&nbsp;
            <a href="javascript:showNetworkInfoWindow(<%= siteInfo.getChildSiteSettingsId() %>);"><international:get
                    name="moreInfo"/></a>
            &nbsp;&nbsp;
            <a href="javascript:deleteChildSiteSettings(<%= siteInfo.getChildSiteSettingsId() %>);"><international:get
                    name="deleteInLowerCase"/></a>
        </span>
    </div>
    <%------------------------------------------------Not created Site------------------------------------------------%>
    <% } %>
</div>
<div class="dashboardContent">
    <div class="content1">
        <% if (siteInfo.isSiteCreated() && siteInfo.isEditable()) { %>
        <div class="content_left">
            <div class="content_left_header">
                <% if (siteInfo.isBlueprint()) { %>
                <international:get name="manageBlueprint"/>
                <% } else if (siteInfo.isNetworkSite()) { %>
                <international:get name="manageNetwork"/>
                <% } else if (siteInfo.isChildSite()) { %>
                <international:get name="manageChildSite"/>
                <% } else { %>
                <international:get name="manageSite"/>
                <% } %>
            </div>
            <div class="content_left_body">
                <ul>
                    <% if (siteInfo.isBlueprint()) { %>
                    <%-----------------------------------------Blueprint Links----------------------------------------%>
                    <li><a href="/site/siteEditPage.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                            name="edit"/></a></li>
                    <li><a href="/copyBlueprint.action?blueprintId=<%= siteInfo.getSiteId() %>"><international:get name="copy"/></a></li>
                    <li><a href="javascript:previewSite(<%= siteInfo.getFirstSitePageId() %>);"><international:get
                            name="preview"/></a></li>
                    <li>
                        <a href="/site/createSite.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                                name="blueprintSettings"/></a></li>
                    <li>
                        <a href="/site/createSite.action?siteId=<%= siteInfo.getSiteId() %>&showSEOTabOnPageLoad=true"><international:get
                                name="seoSettings"/></a></li>
                    <% if (siteInfo.hasAdminsRightOnSite()) { %>
                    <li><a href="javascript:deleteSite(<%= siteInfo.getSiteId() %>);"><international:get
                            name="delete"/></a></li>
                    <% } %>
                    <li>
                        <a href="javascript:configureAccessibility.show(<%= siteInfo.getSiteId() %>, '<%= AccessibleElementType.SITE %>')"><international:get
                                name="blueprintPermissions"/></a></li>
                    <li>
                        <a href="/site/setSiteTemplate.action?siteId=<%= siteInfo.getSiteId() %>&editingMode=true"><international:get
                                name="templatesLayouts"/></a></li>
                    <li>
                        <a href="/account/manageRegistrants.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                                name="viewEditAccountRegistrations"/></a></li>
                    <li>
                        <a href="javascript:requestContent.showRequestContent(<%= siteInfo.getSiteId() %>);"><international:get
                                name="requestContent"/></a></li>
                    <%------------------------------Publish, Activate and Offline buttons-----------------------------%>
                    <% if (siteInfo.isPublishedBlueprint()) { %>
                    <li>
                        <a href="javascript:publishBlueprint.showPublishWindowInActivationMode(<%= siteInfo.getSiteId() %>)"><international:get
                                name="activate"/></a></li>
                    <% } else if (siteInfo.isActivatedBlueprint()) { %>
                    <li>
                        <a href="javascript:deleteActivatedBlueprint(<%= siteInfo.getSiteId() %>)"><international:get
                                name="offline"/></a></li>
                    <% } else { %>
                    <li>
                        <a href="javascript:publishBlueprint.showPublishWindow(<%= siteInfo.getSiteId() %>, <%= siteInfo.hasBeenPublished() %>)"><international:get
                                name="publishBlueprint"/></a></li>
                    <% } %>
                    <%------------------------------Publish, Activate and Offline buttons-----------------------------%>
                    <%-----------------------------------------Blueprint Links----------------------------------------%>
                    <% } else { %>
                    <%---------------------------------------Common Site Links----------------------------------------%>
                    <li><a href="/site/siteEditPage.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                            name="edit"/></a></li>
                    <li><a href="javascript:previewSite(<%= siteInfo.getFirstSitePageId() %>);"><international:get
                            name="preview"/></a></li>
                    <li>
                        <a href="/site/createSite.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                                name="siteSettings"/></a></li>
                    <li>
                        <a href="/site/createSite.action?siteId=<%= siteInfo.getSiteId() %>&showSEOTabOnPageLoad=true"><international:get
                                name="seoSettings"/></a></li>
                    <% if (siteInfo.hasAdminsRightOnSite()) {%>
                    <li><a href="javascript:deleteSite(<%= siteInfo.getSiteId() %>);"><international:get
                            name="delete"/></a></li>
                    <% } %>
                    <li>
                        <a href="javascript:configureAccessibility.show(<%= siteInfo.getSiteId() %>, '<%= AccessibleElementType.SITE %>')"><international:get
                                name="permissions"/></a></li>
                    <li>
                        <a href="/site/setSiteTemplate.action?siteId=<%= siteInfo.getSiteId() %>&editingMode=true"><international:get
                                name="templatesLayouts"/></a></li>
                    <li>
                        <a href="/account/manageRegistrants.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                                name="viewEditAccountRegistrations"/></a></li>
                    <li><a href="/site/trafficPages.action?siteId=<%= siteInfo.getSiteId() %>"><international:get
                            name="trafficLogs"/></a></li>
                    <li>
                        <a href="javascript:requestContent.showRequestContent(<%= siteInfo.getSiteId() %>);"><international:get
                                name="requestContent"/></a></li>
                    <% if (siteInfo.canBeDeactivated()) { %>
                    <li id="deactivateSite"><a
                            href="javascript:deactivateSite(<%= siteInfo.getSiteId() %>);"><international:get
                            name="deactivate"/></a></li>
                    <% } %>
                    <% if (siteInfo.hasAdminsRightOnSite()) {%>
                    <li id="goLive" style="<%= !siteInfo.isActive() ? "" : "display:none" %>;">
                        <stripes:link beanclass="com.shroggle.presentation.site.payment.UpdatePaymentInfoAction"
                                      name="goLive">
                            <stripes:param name="selectedSiteId" value="<%= siteInfo.getSiteId() %>"/>
                            <international:get name="goLive"/>
                        </stripes:link>
                    </li>
                    <% } %>
                    <% if (siteInfo.isNetworkSite()) { %>
                    <%---------------------------------------Network Site Links---------------------------------------%>
                    <li>
                        <a href="/account/manageNetworkRegistrantsAction.action?parentSiteId=<%= siteInfo.getSiteId() %>"><international:get
                                name="manageNetworkRegistrants"/></a></li>
                    <li><a href="javascript:showIncomeSettingsWindow(<%= siteInfo.getSiteId() %>)"><international:get
                            name="incomeSettings"/></a></li>
                    <% } %>
                    <%--One site can be parent and child site at the same time and we must show "Opt out from network"
                    link only for sites, which is shown on the dashboard as a child site.--%>
                    <% if (siteInfo.isChildSite()) { %>
                    <li>
                        <a href="javascript:optOutFromNetwork(<%= siteInfo.getSiteId() %>, '<%= siteInfo.getHisNetworkName() %>');">
                            <international:get name="optOutFromNetwork"/>
                        </a>
                    </li>
                    <% } %>
                    <%---------------------------------------Network Site Links---------------------------------------%>
                    <%---------------------------------------Common Site Links----------------------------------------%>
                    <% } %>
                    <li><a href="javascript:keywordManager.show(<%= siteInfo.getSiteId() %>)"><international:get
                            name="keywordManager"/></a></li>
                </ul>
            </div>
        </div>
        <div class="content_right">
            <div class="content_right_header"><international:get name="editContents"/></div>
            <div class="content_right_body">
                <div class="dashboardWidgets <%= siteInfo.getSiteType().toString() %>">
                    <ul>
                        <% if (!siteInfo.getDashboardWidgets().isEmpty()) {
                            for (DashboardSiteInfoForCreatedSite.DashboardWidget dashboardWidget : siteInfo.getDashboardWidgets()) { %>
                        <li>
                            <a <%= dashboardWidget.isDraft() ? " class=\"grey\"" : "" %>
                                    href="javascript:showDashboardWidget(<%= dashboardWidget.getWidgetId() %>, <%= dashboardWidget.getItemId() %>, '<%= dashboardWidget.getItemType() %>')"><span
                                    style="font-weight:bold"><% if (dashboardWidget.getPageName() != null) { %><%= dashboardWidget.getPageName() %>&nbsp;/&nbsp;<% } %><international:get
                                    name="<%= dashboardWidget.getItemType().toString() %>"/></span>: <span
                                    id="itemName<%= dashboardWidget.getItemId() %>"><%= dashboardWidget.getItemName() %></span>
                            </a>
                        </li>
                        <% }
                        } else { %>
                        <international:get name="emptySiteContent"/>
                        <% } %>
                    </ul>
                </div>
            </div>
        </div>
        <% } else if (!siteInfo.isEditable()) { %>
        <div class="height250px">
            <table class="width100height100">
                <tr>
                    <td class="centerAligned">
                        <international:get name="youHaveNoAccessToEditContentOfThisSite"/>
                    </td>
                </tr>
            </table>
        </div>
        <% } else {// If site has not been create we don`t show anything here. Tolik %>
        <div class="height250px">
        </div>
        <% } %>
    </div>
</div>
