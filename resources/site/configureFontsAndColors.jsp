<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ page import="com.shroggle.presentation.site.cssParameter.ConfigureFontsAndColorsService" %>
<%@ page import="com.shroggle.presentation.site.cssParameter.CssParameter" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureCssParameters"/>
<%
    final ConfigureFontsAndColorsService service = (ConfigureFontsAndColorsService) request.getAttribute("fontsColorsService");
    final ConfigureItemSettingsService itemSettingsService = request.getAttribute("itemSettingsService") != null ?
            (ConfigureItemSettingsService) request.getAttribute("itemSettingsService") : null;
    final boolean isWidgetComposit = service.getWidget() != null && service.getWidget().isWidgetComposit();
%>

<div <% if (itemSettingsService != null && itemSettingsService.getItemType().isExtendedSettingsWindow()) { %>
        class="extendedItemSettingsWindowDiv"
        <% } else { %>
        class="itemSettingsWindowDiv"
        <% } %>>

    <h1>
        <international:get name="ConfigureWidgetCSSParamaters">
        <% if (service.getWidget() != null && service.getWidget().getItemType() != null) { %>
            <international:param>
                <jsp:attribute name="value" >
                     <international:get name="<%= String.valueOf(service.getWidget().getItemType()) %>" part="addWidget" />
                </jsp:attribute>
            </international:param>
        <% } else { %>
            <international:param value="<%= '\b' %>"/>
        <% } %>
        </international:get>
    </h1>

    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="fontsColorsService"/>
    <% } %>

    <div class="windowTopLine">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="fontsAndColorsReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <br>

    <div class="cssParametersDiv">
        <table width="100%" style="margin: 10px;">
            <% for (CssParameter cssParameter : service.getCssParameters()) { %>
            <tr>
                <td width="50%"><%= cssParameter.getDescription() %>:
                </td>
                <td width="50%">
                    <input type="text" name="widgetCssParameter" maxlength="255" onchange="setWindowSettingsChanged();"
                           id="widgetCssParameter<%= cssParameter.getName() %><%= cssParameter.getSelector() %>"
                           value="<%= cssParameter.getValue() == null ? "" : cssParameter.getValue() %>" size="50">
                    <input type="hidden" name="widgetCssParameterName" value="<%= cssParameter.getName() %>">
                    <input type="hidden" name="widgetCssParameterSelector" value="<%= cssParameter.getSelector() %>">
                    <input type="hidden" name="widgetCssParameterDescription"
                           value="<%= cssParameter.getDescription() %>">
                </td>
            </tr>
            <% } %>
        </table>
    </div>
    <br clear="all">
</div>

<div class="itemSettingsButtonsDiv" id="configureFontsColorsButtons">
    <div <% if (isWidgetComposit) { %>class="itemSettingsButtonsDivInner"<% } %>>
        <% if (service.getWidgetId() != null && !isWidgetComposit) { %>
        <div align="right" class="forItemDiv" id="forItemDiv">
            <input type="checkbox" id="saveCssInCurrentPlace"
                   <% if (service.isSavedInCurrentPlace()) { %>checked<% } %>>
            <label for="saveCssInCurrentPlace"><international:get name="forItem"/></label>
        </div>
        <% } %>
        <div class="fr">
            <input type="button" id="windowApply" value="<international:get name="apply"/>"
                   onclick="configureFontsAndColors.save(<%= service.getWidgetId() %>, <%= service.getDraftItemId() %>, false);"
                   onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';"
                   class="but_w73">
            <input type="button" id="windowSave" value="<international:get name="save"/>"
                   onclick="configureFontsAndColors.save(<%= service.getWidgetId() %>, <%= service.getDraftItemId() %>, true);"
                   onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';"
                   class="but_w73">
            <input type="button" id="windowCancel" value="<international:get name="cancel"/>"
                   onclick="closeConfigureWidgetDivWithConfirm();"
                   onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';"
                   class="but_w73">
        </div>

        <div style="clear:both;"></div>
    </div>
</div>

