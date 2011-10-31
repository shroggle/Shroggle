<%@ page import="com.shroggle.presentation.site.ConfigureMediaBlockSettingsService" %>
<%@ page import="com.shroggle.presentation.site.ConfigureMediaBlockSettingsTab" %>
<%
    final ConfigureMediaBlockSettingsService service =
            (ConfigureMediaBlockSettingsService) request.getAttribute("mediaBlockSettingsService");
%>
<% if (service.getTab() == ConfigureMediaBlockSettingsTab.FONTS_COLORS) { %>
<jsp:include page="configureFontsAndColors.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureMediaBlockSettingsTab.BORDER) { %>
<jsp:include page="/borderBackground/configureBorder.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureMediaBlockSettingsTab.BACKGROUND) { %>
<jsp:include page="/borderBackground/configureBackground.jsp" flush="true"/>
<% } %>
