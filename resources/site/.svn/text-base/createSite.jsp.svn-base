<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.CreateSiteAction" %>
<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.SiteAccessLevel" %>
<%@ page import="com.shroggle.logic.site.SitesManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="noBot" tagdir="/WEB-INF/tags/nobot" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="createSite"/>
<% final CreateSiteAction action = (CreateSiteAction) request.getAttribute("actionBean"); %>
<html>
<head>
    <title>
        <% if (action.getSiteId() != null) { %>
            <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
                <international:get name="editBlueprintTitle"/>
            <% } else { %>
                <international:get name="editSiteTitle"/>
            <% } %>
        <% } else { %>
            <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
                <international:get name="createBlueprintTitle"/>
            <% } else { %>
                <international:get name="createSiteTitle"/>
            <% } %>
        <% } %>
    </title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <% if (action.isShowSEOTabOnPageLoad()) { %>
        <script type="text/javascript">

            var customHtmlMaxIndex = <%= action.getSeoSettings().getHtmlCodeList().size() %>;

            $(document).ready(function () {
                createSite.showSEOSettingsTab();
            });

        </script>
    <% } %>
</head>

<body onload="addValidationErrors();showSiteIconUploader();">
    <input type="hidden" id="createSiteSiteId" value="<%= action.getSiteId() %>"/>

    <div id="siteUrlHelpWindow" style="display:none;">
        <div class="windowOneColumn">
            <div style="overflow:auto;height:400px; text-align:left;">
                <international:get name="domainNameText"/>
            </div>

            <div align="right" style="margin-top:10px"><input type="button" onclick="closeConfigureWidgetDiv();"
                                                              class="but_w73"
                                                              onmouseover="this.className='but_w73_Over';"
                                                              onmouseout="this.className='but_w73';"
                                                              value="Close"></div>
        </div>
    </div>
    <div class="wrapper">
        <div class="container">
            <%@ include file="/includeHeadApplication.jsp" %>
            <div class="content">
                <div class="middle_page">
                    <% if (action.getSiteId() == null) { %>
                    <b><international:get name="step1Of3"/></b>
                    <% } %>
                    <% if (action.getSiteId() != null) { %>
                    <div class="b_16"><%= HtmlUtil.limitName(action.getTitle()) %>
                    </div>
                    <% } %>
                    <h1>
                        <% if (action.getSiteId() != null) { %>
                        <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
                        <international:get name="editBlueprint"/>
                        <% } else { %>
                        <international:get name="editSite"/>
                        <% } %>
                        <% } else { %>
                        <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
                        <international:get name="createABlueprint"/>
                        <% } else { %>
                        <international:get name="createAWebSite"/>
                        <% } %>
                        <% } %>
                    </h1>
                    <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
                    <international:get name="itsFreeToSetUpYourBlueprint"/>
                    <% } else { %>
                    <international:get name="itsFreeToSetUpYourSite"/>
                    <% } %>
                    <stripes:form beanclass="com.shroggle.presentation.site.CreateSiteAction" method="post">
                        <stripes:hidden name="editingMode"/>
                        <stripes:hidden name="siteType"/>
                        <stripes:hidden name="iconId" id="iconId"
                                        value="<%= action.getIcon() != null ? action.getIcon().getIconId() : null %>"/>
                        <div class="middle_form">
                            <page:errors/><br>

                                <%---------------------------------------------Div with tabs------------------------------------------------------%>
                            <div class="tabsWrapper">
                                <div class="new_selected_tab long_tab" id="generalSettingsTab"
                                     onclick="createSite.showGeneralSettingsTab();">
                                    <international:get name="generalSettingsTab"/>
                                </div>
                                <div class="new_unselected_tab long_tab" id="SEOSettingsTab"
                                     onclick="createSite.showSEOSettingsTab();">
                                    <international:get name="SEOSettingsTab"/>
                                </div>
                                <br clear="all">
                            </div>
                            <%---------------------------------------------Div with tabs------------------------------------------------------%>

                            <%---------------------------------------------Div with content of tabs-------------------------------------------%>
                            <div class='tabbedArea'>
                                <div id="generalSettingsTabContentDiv">
                                    <jsp:include page="createSiteGeneralSettings.jsp" flush="true"/>
                                </div>
                                <div id="SEOSettingsTagContentDiv" style="display:none;">
                                    <jsp:include page="createSiteSEOSettings.jsp" flush="true"/>
                                </div>
                            </div>
                                <%---------------------------------------------Div with content of tabs-------------------------------------------%>

                            <%
                                if (action.isShowNoBotCodeConfirm()) { %>
                                <!-- Not bot code -->
                                <div class="b_16" style="margin-top:10px;"><international:get
                                        name="wordVerification"/> *
                                </div>
                                <br>

                                <div class="span-9">
                                    <label class="mark" style="margin-left:10px;"><international:get
                                            name="securityCode"/></label>
                                    <dl>
                                        <dt><label><noBot:image prefix="createSite" alt="Bot denied code"
                                                                className="bot_code"/></label></dt>
                                        <dd><input type="text" name="noBotCodeConfirm" id="noBotCodeConfirm" maxlength="255"
                                                   style="margin-left:20px;margin-top:7px"><span
                                                style="margin-left:2px;"></span>
                                        </dd>
                                    </dl>
                                </div>
                            <% } %>
                            <br clear="all">
                            <br>
                        </div>
                        <br clear="all">
                        <table style="width:100%">
                            <tr>
                                <td align="left" style="width:50%;height:70px;vertical-align:bottom;">
                                    <stripes:link beanclass="com.shroggle.presentation.account.dashboard.DashboardAction"
                                                  class="linkBack">
                                        <img src="../images/spacer.gif" alt="">
                                    </stripes:link>
                                </td>
                                <td style="width:50%;height:70px;vertical-align:bottom;text-align:right;">
                                    <% if (action.getSiteId() == null) { %>
                                        <stripes:submit name="persist" value="" onmouseout="this.className='butSubmit'"
                                                    onmouseover="this.className='butSubmitOver'" class="butSubmit"/>
                                    <% } else { %>
                                        <stripes:submit name="persist" class="but_w73"
                                                    onmouseover="this.className='but_w73_Over';"
                                                    onmouseout="this.className='but_w73';" value="Save"/>
                                    <% } %>
                                </td>
                            </tr>
                        </table>

                        <stripes:hidden name="siteId"/>
                        <% if (action.isCreateChildSite()) { %>
                            <stripes:hidden name="settingsId"/>
                            <stripes:hidden name="createChildSite"/>
                            <stripes:hidden name="parentSiteId"/>
                            <stripes:hidden name="childSiteRegistrationId"/>
                            <stripes:hidden name="createNewSettings"/>
                        <% } %>
                    </stripes:form>
                </div>
            </div>
            <%@ include file="../includeFooterApplication.jsp" %>
        </div>
    </div>
    </body>
</html>