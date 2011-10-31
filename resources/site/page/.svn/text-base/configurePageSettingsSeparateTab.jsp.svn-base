<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsService" %>
<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsTab" %>
<%
    final ConfigurePageSettingsService service =
            (ConfigurePageSettingsService) request.getAttribute("pageSettingsService");
%>
<% if (service.getTab() == ConfigurePageSettingsTab.PAGE_NAME) { %>
<jsp:include page="/site/page/configurePageName.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.LAYOUT) { %>
<jsp:include page="/site/page/configurePageLayout.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.ACCESSIBILITY) { %>
<jsp:include page="/site/accessibilityForRender/elementAccessibility.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.BACKGROUND) { %>
<jsp:include page="/borderBackground/configureBackground.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.HTML_CSS) { %>
<jsp:include page="/site/page/configurePageHtmlAndCss.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.SEO_META_TAGS) { %>
<jsp:include page="/site/page/configurePageSEOMetaTags.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.SEO_HTML) { %>
<jsp:include page="/site/page/configurePageSEOHtml.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS) { %>
<jsp:include page="/site/page/configureBlueprintPagePermissions.jsp" flush="true"/>
<% } %>
