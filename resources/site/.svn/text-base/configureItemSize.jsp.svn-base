<%@ page import="com.shroggle.presentation.site.ConfigureItemSizeService" %>
<%@ page import="com.shroggle.entity.WidgetSizeType" %>
<%@ page import="com.shroggle.entity.WidgetOverflowType" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureItemSize"/>
<%
    final ConfigureItemSizeService service = (ConfigureItemSizeService) request.getAttribute("itemSizeService");
    final ConfigureItemSettingsService itemSettingsService = request.getAttribute("itemSettingsService") != null ?
            (ConfigureItemSettingsService) request.getAttribute("itemSettingsService") : null;
%>

<div <% if (itemSettingsService != null && itemSettingsService.getItemType().isExtendedSettingsWindow()) { %>
        class="extendedItemSettingsWindowDiv"
        <% } else { %>
        class="itemSettingsWindowDiv"
        <% } %>>

    <h1>
        <international:get name="subHeader">
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
    <widget:title customServiceName="itemSizeService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="itemSizeReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <div class="emptyError" id="itemSizeErrors"></div>

    <input id="widgetSizeFloatable" type="checkbox" <% if (service.getItemSize().isFloatable()) {%>
           checked="checked"<% } %> onclick="widgetFloatableCheckboxClick(this);"
           onchange="setWindowSettingsChanged();"/>
    <label for="widgetSizeFloatable"><international:get name="floatable"/></label>
    <br/><br/>

    <input id="createClearDiv" type="checkbox" <% if (service.getItemSize().isCreateClearDiv()) {%>
           checked="checked"<% } %>  <% if (!service.getItemSize().isFloatable()) {%> disabled="disabled" <%}%>
           onchange="setWindowSettingsChanged();"/>
    <label for="createClearDiv"><international:get name="createClearDiv"/></label>
    <br/><br/>

    <div class="widgetSizeTableDiv">
        <table>
            <tr>
                <td class="widgetSizeTableFirstColumn">
                    <label for="widgetSizeWidth"><international:get name="widgetSizeWidth"/></label>
                </td>
                <td>
                    <input id="widgetSizeWidth" class="widgetSizeInput"
                           onkeyup="acceptOnlyDigits(this);"
                           onchange="setWindowSettingsChanged();"
                           value="<%= service.getItemSize().getWidth() %>"/>
                    <select id="widgetSizeWidthType" class="widgetSizeTypeSelect"
                            onchange="widgetSizeChanged('width');setWindowSettingsChanged();">
                        <option value="<%= WidgetSizeType.PERCENT %>"
                                <% if (service.getItemSize().getWidthSizeType().equals(WidgetSizeType.PERCENT)){ %>selected="selected"<% } %>>
                            %
                        </option>
                        <option value="<%= WidgetSizeType.PX %>"
                                <% if (service.getItemSize().getWidthSizeType().equals(WidgetSizeType.PX)){ %>selected="selected"<% } %>>
                            px
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="widgetSizeTableFirstColumn">
                    <label for="widgetSizeOverflow-x"><international:get name="widgetSizeOverflow-x"/></label>
                </td>
                <td>
                    <select id="widgetSizeOverflow-x" class="widgetSizeOverflowType"
                            onchange="setWindowSettingsChanged();">
                        <option value="<%= WidgetOverflowType.VISIBLE %>"
                                <% if (service.getItemSize().getOverflow_x().equals(WidgetOverflowType.VISIBLE)){ %>selected="selected"<% } %>>
                            none
                        </option>
                        <option value="<%= WidgetOverflowType.HIDDEN %>"
                                <% if (service.getItemSize().getOverflow_x().equals(WidgetOverflowType.HIDDEN)){ %>selected="selected"<% } %>>
                            <international:get name="hidden_overflow"/></option>
                        <option value="<%= WidgetOverflowType.SCROLL %>"
                                <% if (service.getItemSize().getOverflow_x().equals(WidgetOverflowType.SCROLL)){ %>selected="selected"<% } %>>
                            <international:get name="scroll_overflow"/></option>
                    </select>
                </td>
            </tr>
        </table>

        <div class="widgetSizeHorizontalRule"></div>

        <table>
            <tr>
                <td class="widgetSizeTableFirstColumn">
                    <label for="widgetSizeHeight"><international:get name="widgetSizeHeight"/></label>
                </td>
                <td>
                    <input id="widgetSizeHeight" class="widgetSizeInput"
                           onkeyup="acceptOnlyDigits(this);" onchange="setWindowSettingsChanged();"
                           value="<%= service.getItemSize().getHeight() != null ? service.getItemSize().getHeight() : "" %>"/>
                    <select id="widgetSizeHeightType" class="widgetSizeTypeSelect"
                            onchange="widgetSizeChanged('height');setWindowSettingsChanged();">
                        <option value="<%= WidgetSizeType.PERCENT %>"
                                <% if (service.getItemSize().getHeightSizeType().equals(WidgetSizeType.PERCENT)){ %>selected="selected"<% } %>>
                            %
                        </option>
                        <option value="<%= WidgetSizeType.PX %>"
                                <% if (service.getItemSize().getHeightSizeType().equals(WidgetSizeType.PX)){ %>selected="selected"<% } %>>
                            px
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="widgetSizeTableFirstColumn">
                    <label for="widgetSizeOverflow-y"><international:get name="widgetSizeOverflow-y"/></label>
                </td>
                <td>
                    <select id="widgetSizeOverflow-y" class="widgetSizeOverflowType" onchange="setWindowSettingsChanged();">
                        <option value="<%= WidgetOverflowType.VISIBLE %>"
                                <% if (service.getItemSize().getOverflow_y().equals(WidgetOverflowType.VISIBLE)){ %>selected="selected"<% } %>>
                            none
                        </option>
                        <option value="<%= WidgetOverflowType.HIDDEN %>"
                                <% if (service.getItemSize().getOverflow_y().equals(WidgetOverflowType.HIDDEN)){ %>selected="selected"<% } %>>
                            <international:get name="hidden_overflow"/></option>
                        <option value="<%= WidgetOverflowType.SCROLL %>"
                                <% if (service.getItemSize().getOverflow_y().equals(WidgetOverflowType.SCROLL)){ %>selected="selected"<% } %>>
                            <international:get name="scroll_overflow"/></option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
</div>

<div class="itemSettingsButtonsDiv" id="configureItemSizeButtons">
    <% if (service.getWidgetId() != null) { %>
    <div align="right" class="forItemDiv" id="forItemDiv">
        <input type="checkbox" id="saveItemSizeInCurrentPlace"
               <% if (service.isSavedInCurrentPlace()) { %>checked<% } %>>
        <label for="saveItemSizeInCurrentPlace"><international:get name="forItem"/></label>
    </div>
    <% } %>
    <div class="fr">
        <input type="button" value="<international:get name="applyButton"/>" id="windowApply"
               onClick="configureItemSize.save(<%= service.getWidgetId() %>, <%= service.getDraftItemId() %>, false);"
               onmouseout="this.className = 'but_w73';"
               onmouseover="this.className = 'but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="saveButton"/>" id="windowSave"
               onClick="configureItemSize.save(<%= service.getWidgetId() %>, <%= service.getDraftItemId() %>, true);"
               onmouseout="this.className = 'but_w73';"
               onmouseover="this.className = 'but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="cancelButton"/>" id="windowCancel"
               onClick="closeConfigureWidgetDiv();"
               onmouseout="this.className = 'but_w73';"
               onmouseover="this.className = 'but_w73_Over';"
               class="but_w73">
    </div>
</div>
