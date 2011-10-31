<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.presentation.site.ConfigureBlueprintItemPermissionsService" %>
<%@ page import="com.shroggle.logic.site.WidgetRightsManager" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="com.shroggle.entity.Item" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%
    final ConfigureBlueprintItemPermissionsService service =
            (ConfigureBlueprintItemPermissionsService) request.getAttribute("blueprintItemPermissionsService");
    final ConfigureItemSettingsService itemSettingsService =
            (ConfigureItemSettingsService) request.getAttribute("itemSettingsService");
    final WidgetRightsManager rightsManager = service.getWidgetLogic().getRights();
    final Widget widget = service.getWidgetLogic().getWidget();
    final Item draftItem = service.getWidgetLogic().getItemManager().getDraftItem();
%>
<div <% if (itemSettingsService.getItemType().isExtendedSettingsWindow()) { %>
        class="extendedItemSettingsWindowDiv"
        <% } else { %>
        class="itemSettingsWindowDiv"
        <% } %>>
    <h1>Blueprint Content Permissions.</h1>
    <widget:title customServiceName="blueprintItemPermissionsService"/>
    <h3 style="margin-bottom:5px;"> Option to include shared content in this content
        module</h3>
    <br>

    <div class="inform_mark" style="margin-left:5px;">Sites created with this blueprint may either include shared
        network content (in this content module) or not.
    </div>
    <br>

    <% if (rightsManager.isPageVersionLock()) { %>
        <span id="configureWidgetBlueprintRightPageLock">
            In `Blueprint Page Permissions` you have chosen to make the content of this page `Not editable`. Therefore the
            settings for content modules on this page are `pre set`. If you would like to manually select which items of this
            page`s content will be editable, please return to page permissions and unlock the page.
        </span>
    <br><br>
    <% } %>

    <% if (draftItem.getItemType().isShareable()) { %>
    <input type="radio" id="configureWidgetBlueprintRightNotShared"
           name="configureWidgetBlueprintRightShared" <%= widget.isBlueprintShareble() ? "" : "checked=\"checked\"" %>>
    <label for="configureWidgetBlueprintRightNotShared">
        Content Module is not shared. Content is unique to each site
    </label><br><br>

    <input type="radio" id="configureWidgetBlueprintRightIsShared"
           name="configureWidgetBlueprintRightShared" <%= !widget.isBlueprintShareble() ? "" : "checked=\"checked\"" %>>
    <label for="configureWidgetBlueprintRightIsShared">
        Content Module is shared among all network members with this module
    </label>
    <% } %>

    <div style="margin-top:15px;">
        <b>Please select those options that describe how you want the template user (child site administrator) to use
            this
            template content module.</b>

        <br><br>
        <input name="widgetBlueprintRight" type="radio"
               id="widgetBlueprintEditableRequired" <%= rightsManager.isRequired() && rightsManager.isEditable() ? "checked='checked'" : "" %> <%= rightsManager.isPageVersionLock() ? "disabled='disabled'" : ""%>>
        <label for="widgetBlueprintEditableRequired">Content Module is required in this location, but is
            editable</label>

        <br><br>
        <input name="widgetBlueprintRight" type="radio"
               id="widgetBlueprintNoEditableRequired" <%= rightsManager.isRequired() && !rightsManager.isEditable() ? "checked='checked'" : "" %> <%= rightsManager.isPageVersionLock() ? "disabled='disabled'" : ""%>>
        <label for="widgetBlueprintNoEditableRequired">
            Content Module is required in this location, and is not editable
        </label>

        <br><br>
        <input name="widgetBlueprintRight" type="radio"
               id="widgetBlueprintNoEditableNoRequired" <%= !rightsManager.isRequired() && !rightsManager.isEditable() ? "checked='checked'" : "" %> <%= rightsManager.isPageVersionLock() ? "disabled='disabled'" : ""%>>
        <label for="widgetBlueprintNoEditableNoRequired">
            Content Module is optional in this location, but is not editable
        </label>

        <br><br>
        <input name="widgetBlueprintRight" type="radio"
               id="widgetBlueprintEditableNoRequired" <%= !rightsManager.isRequired() && rightsManager.isEditable() ? "checked='checked'" : "" %> <%= rightsManager.isPageVersionLock() ? "disabled='disabled'" : ""%>>
        <label for="widgetBlueprintEditableNoRequired">
            Content Module is optional in this location, and is editable
        </label>
        <br><br>
        <input type="checkbox"
               id="widgetBlueprintEditableRushe" <%= !rightsManager.isEditableRuche() ? "checked='checked'" : "" %> <%= rightsManager.isPageVersionLock() ? "disabled='disabled'" : ""%>>
        <label for="widgetBlueprintEditableRushe">
            Font and colors, borders, backgrounds, CSS and HTML are not editable
        </label>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <% if (rightsManager.isPageVersionLock()) { %>
        <input type="button" onclick="closeConfigureWidgetDiv();" value="Close"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <% } %>

        <% if (!rightsManager.isPageVersionLock()) { %>
        <input type="button" onclick="configureBlueprintItemPermissions.save(false);" value="Apply"
               onmouseout="this.className='but_w73';" id="windowApply"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" onclick="configureBlueprintItemPermissions.save(true);" value="Save"
               onmouseout="this.className='but_w73';" id="windowSave"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" onclick="closeConfigureWidgetDivWithConfirm();" value="Cancel"
               onmouseout="this.className='but_w73';" id="windowCancel"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <% } %>
    </div>
</div>