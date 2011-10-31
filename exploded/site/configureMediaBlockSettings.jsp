<%@ page import="com.shroggle.presentation.site.ConfigureMediaBlockSettingsService" %>
<%@ page import="com.shroggle.presentation.site.ConfigureMediaBlockSettingsTab" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureMediaBlockSettings"/>
<%
    final ConfigureMediaBlockSettingsService service = (ConfigureMediaBlockSettingsService) request.getAttribute("mediaBlockSettingsService");
%>

<div class="windowMainContent">
    <div class="twoColumnsWindow_columnWrapper">
        <div id="twoColumnsWindow_leftColumn" class="twoColumnsWindow_leftColumn">
            <div class="leftmenu_header">
                <international:get name="mediaBlockVisualSettingsHeader"/></div>
            <div class="leftmenu_body">
                <div class="<%= service.getTab() == ConfigureMediaBlockSettingsTab.FONTS_COLORS ? "c1current" : "c1" %>"
                     id="fontsColorsTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureMediaBlockSettingsTab.FONTS_COLORS %>TabContent');
                     configureMediaBlockSettings.showSeparateTab(<%= service.getWidgetId() %>, '<%= ConfigureMediaBlockSettingsTab.FONTS_COLORS %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="fontsColors"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigureMediaBlockSettingsTab.BORDER ? "c1current" : "c1" %>"
                     id="borderTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureMediaBlockSettingsTab.BORDER %>TabContent');
                     configureMediaBlockSettings.showSeparateTab(<%= service.getWidgetId() %>, '<%= ConfigureMediaBlockSettingsTab.BORDER %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="border"/></span>
                </div>
                <div class="<%= service.getTab() == ConfigureMediaBlockSettingsTab.BACKGROUND ? "c1current" : "c1" %>"
                     id="backgroundTab"
                     onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureMediaBlockSettingsTab.BACKGROUND %>TabContent');
                     configureMediaBlockSettings.showSeparateTab(<%= service.getWidgetId() %>, '<%= ConfigureMediaBlockSettingsTab.BACKGROUND %>');">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="background"/></span>
                </div>
            </div>
        </div>
    </div>
    <div class="twoColumnsWindow_columnWrapper">
        <div id="twoColumnsWindow_rightColumn" class="twoColumnsWindow_rightColumn">
            <div id="<%= ConfigureMediaBlockSettingsTab.FONTS_COLORS %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigureMediaBlockSettingsTab.FONTS_COLORS ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigureMediaBlockSettingsTab.FONTS_COLORS) { %>
                <jsp:include page="configureFontsAndColors.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigureMediaBlockSettingsTab.BORDER %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigureMediaBlockSettingsTab.BORDER ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigureMediaBlockSettingsTab.BORDER) { %>
                <jsp:include page="/borderBackground/configureBorder.jsp" flush="true"/>
                <% } %>
            </div>
            <div id="<%= ConfigureMediaBlockSettingsTab.BACKGROUND %>TabContent" class="tabContent wholeItemSettingsWindowDiv"
                 style="<%= service.getTab() == ConfigureMediaBlockSettingsTab.BACKGROUND ? "" : "display:none;" %>">
                <% if (service.getTab() == ConfigureMediaBlockSettingsTab.BACKGROUND) { %>
                <jsp:include page="/borderBackground/configureBackground.jsp" flush="true"/>
                <% } %>
            </div>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
