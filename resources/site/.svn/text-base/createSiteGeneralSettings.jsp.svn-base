<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.Icon" %>
<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page import="com.shroggle.presentation.site.CreateSiteAction" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.resource.provider.ResourceGetterType" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="createSite"/>
<% final CreateSiteAction action = (CreateSiteAction) request.getAttribute("actionBean"); %>
<dl class="w_30">
    <div class="b_16">
        <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
            <international:get name="nameYourBlueprint"/>
        <% } else { %>
            <international:get name="nameYourSite"/>
        <% } %>
    </div>
    <br>
    <dt>
        <label for="name_site" style="font-size:13px;">
            <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
                <international:get name="blueprintTitleName"/> <span style="font-weight:bold;">*</span>
            <% } else { %>
                <international:get name="webSiteTitleName"/> <span style="font-weight:bold;">*</span>
            <% } %>
        </label>
    </dt>
    <dd><stripes:text class="title" name="title" id="name_site"/></dd>
    <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
    <dt><label for="description"><international:get name="siteDescription"/></label></dt>
    <dd><stripes:textarea id="description" name="description" class="siteDescription"/></dd>
    <% } %>
    <br clear="all">
    <!-- Web site address -->
    <% if (action.getSiteType() == SiteType.COMMON) { %>
        <hr>
        <div class="b_16"><international:get name="chooseAWebSiteAddress"/></div>
        <div><international:get name="domainExplan"/></div>
        <br>

        <% if (!action.isCreateChildSite() || action.isBrandedAllowShroggleDomain()) { %>
            <dt>
                <label for="siteUrlPrefix">
                    <%= ServiceLocator.getConfigStorage().get().getApplicationUrl() %>&nbsp;
                    <international:get name="subDomain"/> <span style="font-weight:bold;">*</span>
                </label>
            </dt>
            <dd class="b_14">
                http://www.&nbsp;<stripes:text name="subDomain" id="siteUrlPrefix"
                                               onchange="createSite.checkSiteUrlPrefix()"
                                               class="title"/>&nbsp;.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>
            </dd>
            <div class="inform_mark_shifted create_site_inform_mark"><international:get name="enterSubDomain"/></div>
            <div id="siteUrlPrefixCheckResult" class="siteUrlPrefixCheckResultDiv"></div>
        <% } %>

        <% if (action.getBrandedUrl() != null) { %>
            <dt>
                <label for="brandedUrl">
                    <international:get name="brandedUrl">
                        <international:param value="<%= action.getNetworkName() %>"/>
                    </international:get>
                    <span style="font-weight:bold;">*</span>
                </label>
            </dt>
            <dd class="b_14">
                <% final String s = "createSite.checkBrandedSubDomain(" + action.getChildSiteRegistrationId() + ")"; %>
                http://www.&nbsp;<stripes:text name="brandedSubDomain" id="brandedSubDomain"
                                               onchange="<%= s %>"
                                               class="title"/>&nbsp;.<%= action.getBrandedUrl() %>
            </dd>
            <div class="inform_mark_shifted create_site_inform_mark"><international:get name="enterBrandedSubDomain"/></div>
            <div id="brandedSubDomainCheckResult" class="siteUrlPrefixCheckResultDiv"></div>
        <% } %>

        <dt>
            <label for="customUrl" class="span-6_1"><international:get
                    name="yourOwnDomainName"/></label>
        </dt>

        <dd class="b_14">http://www.&nbsp;<stripes:text id="customUrl" name="customUrl" accesskey=""
                                                        onchange="createSite.checkSiteAlias()" class="title"/>
            <% if (action.getCustomUrl() != null && !action.getCustomUrl().isEmpty()) { %>
                <span id="validateAlias" style='margin-left:20px;'><input type="button" value="validate"
                                                                      onclick="createSite.checkSiteAlias()"
                                                                      onmouseout="this.className='but_w73_misc';"
                                                                      onmouseover="this.className='but_w73_Over_misc';"
                                                                      class="but_w73_misc"></span>
            <% } %>
        </dd>
        <div class="mark create_site_inform_mark"><a
                href="javascript:createSite.showHowToSetUpDomainNameForSite()"><international:get
                name="howToSetUpYourOwnDomainName"/></a></div>
        <div id="siteAliasCheckResult" class="siteUrlPrefixCheckResultDiv"></div>

        <br clear="all">
        <%--------------------------------------------------Your site icon------------------------------------------------%>
        <dt><label><international:get name="yourSiteIcon"/></label></dt>
        <dd class="b_14">
            <img id="siteIconImage"
                 src="<%= action.getIcon() != null ? ResourceGetterType.ICON.getUrl(action.getIcon().getIconId()) : "../images/favicon.gif"%>"
                 alt="<international:get name="yourSiteIcon"/>"
                 width="<%= action.getIcon() != null ? action.getIcon().getWidth() : Icon.getDefaultWidth() %>"
                 height="<%= action.getIcon() != null ? action.getIcon().getHeight() : Icon.getDefaultHeight() %>"
                 style="margin-right:15px;">
            <input type="button" value="<international:get name="browseAndUpload"/>" id="browseAndUploadIconButton"
                   class="but_w170_misc">
                    <span id="siteIconButtonContainer"
                          style="position:relative;top:0;left:-170px;cursor: pointer;"
                          onmouseout="$('#browseAndUploadIconButton')[0].className='but_w170_misc';"
                          onmouseover="$('#browseAndUploadIconButton')[0].className='but_w170_Over_misc';">
                        <span id="siteIconButtonPlaceHolder">

                        </span>
                    </span>
            <img id="removeIconButton" src="/images/cross-circle.png" alt="Delete" style='cursor:pointer;'
                 onclick="removeIcon(<%= Icon.getDefaultWidth() %>, <%= Icon.getDefaultHeight() %>, '../images/favicon.gif');">
            <input type="hidden" id="isIconExist" value="<%= action.getIcon() != null %>">
            <input type="hidden" id="siteIconId"
                   value="<%= action.getIcon() != null ? action.getIcon().getIconId() : "" %>">
        </dd>
        <%--------------------------------------------------Your site icon------------------------------------------------%>
    <% } %>

    <% if (action.getSiteType() == SiteType.BLUEPRINT) { %>
        <hr>
        <!-- Permission block -->
        <div class="b_16"><international:get name="blueprintPermission"/></div>
        <br/>
        <stripes:radio id="blueprintRightTypeCan" value="CAN_ADD_PAGES" style="float:left; width:auto;"
                       name="blueprintRightType"/>
        <label for="blueprintRightTypeCan" style="width:auto;"><international:get name="blueprintPermissionCan"/></label>
        <br clear="all"/>
        <stripes:radio id="blueprintRightTypeNotCan" value="CANNOT_ADD_PAGE" style="float:left; width:auto;"
                       name="blueprintRightType"/>
        <label for="blueprintRightTypeNotCan" style="width:auto;"><international:get
                name="blueprintPermissionNotCan"/></label>
        <br clear="all"/>
    <% } %>
</dl>