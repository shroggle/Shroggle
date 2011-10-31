<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsService" %>
<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsTab" %>
<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configurePageSettings"/>
<%
    final ConfigurePageSettingsService service = (ConfigurePageSettingsService) request.getAttribute("pageSettingsService");
%>

<div class="windowMainContent">
    <input type="hidden" id="siteId" value="<%= service.getSite().getSiteId() %>"/>
    <div class="twoColumnsWindow_columnWrapper">
        <div id="twoColumnsWindow_leftColumn" class="twoColumnsWindow_leftColumn">
            <div class="leftmenu_header">
                <international:get name="pageSettingsHeader"/></div>
            <div class="leftmenu_body">
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.PAGE_NAME ? "c1current" : "c1" %>"
                     id="pageNameTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.PAGE_NAME %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.PAGE_NAME %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="pageNameTab"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.LAYOUT ? "c1current" : "c1" %>"
                     id="layoutTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.LAYOUT %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.LAYOUT %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="layout"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.ACCESSIBILITY ? "c1current" : "c1" %>"
                     id="accessibilityTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.ACCESSIBILITY %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.ACCESSIBILITY %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="accessibility"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.BACKGROUND ? "c1current" : "c1" %>"
                     id="backgroundTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.BACKGROUND %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.BACKGROUND %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="background"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.HTML_CSS ? "c1current" : "c1" %>"
                     id="htmlCssTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.HTML_CSS %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.HTML_CSS %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="htmlCss"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.SEO_META_TAGS ? "c1current" : "c1" %>"
                     id="seoMetaTagsTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.SEO_META_TAGS %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.SEO_META_TAGS %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="seoMetaTagsTab"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.SEO_HTML ? "c1current" : "c1" %>"
                     id="seoHtmlTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.SEO_HTML %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.SEO_HTML %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="seoHtmlTab"/></span>
                </div>
                <% if (service.getSite().getType() == SiteType.BLUEPRINT) { %>
                <div class="<%= service.getTab() == ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS ? "c1current" : "c1" %>"
                     id="blueprintPermissionsTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS %>TabContent');
                     configurePageSettings.showSeparateTab(<%= service.getPageId() %>, '<%= ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="blueprintPermissionsTab"/></span>
                </div>
                <% } %>
            </div>
        </div>
    </div>
    <div class="twoColumnsWindow_columnWrapper">
        <div id="twoColumnsWindow_rightColumn" class="twoColumnsWindow_rightColumn">
            <div id="<%= ConfigurePageSettingsTab.PAGE_NAME %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.PAGE_NAME ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.PAGE_NAME) { %>
                <jsp:include page="/site/page/configurePageName.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigurePageSettingsTab.LAYOUT %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.LAYOUT ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.LAYOUT) { %>
                <jsp:include page="/site/page/configurePageLayout.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigurePageSettingsTab.ACCESSIBILITY %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.ACCESSIBILITY ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.ACCESSIBILITY) { %>
                <jsp:include page="/site/accessibilityForRender/elementAccessibility.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigurePageSettingsTab.BACKGROUND %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.BACKGROUND ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.BACKGROUND) { %>
                <jsp:include page="/borderBackground/configureBackground.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigurePageSettingsTab.HTML_CSS %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.HTML_CSS ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.HTML_CSS) { %>
                <jsp:include page="/site/page/configurePageHtmlAndCss.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigurePageSettingsTab.SEO_META_TAGS %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.SEO_META_TAGS ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.SEO_META_TAGS) { %>
                <jsp:include page="/site/page/configurePageSEOMetaTags.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigurePageSettingsTab.SEO_HTML %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.SEO_HTML ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.SEO_HTML) { %>
                <jsp:include page="/site/page/configurePageSEOHtml.jsp" flush="true"/>
                <% } %>
            </div>
            <% if (service.getSite().getType() == SiteType.BLUEPRINT) { %>
            <div id="<%= ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS) { %>
                <jsp:include page="/site/page/configureBlueprintPagePermissions.jsp" flush="true"/>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
