<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page import="com.shroggle.presentation.site.page.ConfigurePageLayoutService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ page import="com.shroggle.entity.Layout" %>
<%@ page import="com.shroggle.entity.Theme" %>
<international:part part="configurePageLayout"/>
<% final ConfigurePageLayoutService service = (ConfigurePageLayoutService) request.getAttribute("pageLayoutService"); %>
<div class="itemSettingsWindowDiv">
    <input type="hidden" id="layoutNotSelected" value="<international:get name="layoutNotSelected"/>"/>
    <h1><international:get name="SELECT_YOUR_LAYOUT"/></h1>
    
    <page:title>
        <jsp:attribute name="customServiceName">pageLayoutService</jsp:attribute>
    </page:title>

    <div class="windowTopLine">&nbsp;</div>

    <div style="overflow-x: auto; overflow-y: hidden;  padding: 10px; border:1px solid #C5C2C2; width:100%; margin-top: 10px;">
        <table border="0" cellpadding="0" class="layout_tbl">
            <tr>
                <% int ii = 0; %>
                <% for (final Layout layout : service.getLayouts()) { %>
                    <% final String thumbnail = service.getLayoutThumbnails().get(layout); %>
                    <td width="220">
                        <label for="layout<%= ii %>"><img src="<%= thumbnail %>" alt="<%= layout.getName() %>" width="220" height=135></label> <br clear="all">
                        <input type="radio" name="layout" id="layout<%= ii %>" <%= layout.getFile().equals(service.getSelectLayoutFile()) ? "checked=\"checked\"" : "" %> value="<%= layout.getFile() %>">
                        <label for="layout<%= ii %>" class="inl"><%= layout.getName() %></label>
                    </td>
                    <% ii++; %>
                <% } %>
            </tr>
        </table>
    </div><br>
    <h2><international:get name="SELECT_YOUR_COLOR_SCHEME"/></h2>    
    <div style="padding:10px 0;">
        <% for (final Theme theme : service.getThemes()) { %>
            <label for="themeCssFile_<%= theme.getName() %>"> <div style="float: left; background-image: url(<%= service.getThemeColorTiles().get(theme) %>); width: 40px; height: 40px; text-align: center; vertical-align: middle; margin-top: 5px; margin: 0 5px;">
                <input type="radio" name="themeCssFile" id="themeCssFile_<%= theme.getName() %>" value="<%= theme.getFile() %>" <%= theme.getFile().equals(service.getSelectThemeFile()) ? "checked" : "" %>>
            </div></label>
        <% } %>
        <br clear="all">
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" onclick="configurePageLayout.save(<%= service.getPageId() %>, false);" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowApply">
        <input type="button" value="Save" onclick="configurePageLayout.save(<%= service.getPageId() %>, true);" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowSave">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>