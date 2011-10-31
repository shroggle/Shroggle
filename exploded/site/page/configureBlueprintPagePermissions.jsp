<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ page import="com.shroggle.presentation.site.page.ConfigureBlueprintPagePermissionsService" %>
<international:part part="configureBlueprintPagePermissions"/>
<% final ConfigureBlueprintPagePermissionsService service =
        (ConfigureBlueprintPagePermissionsService) request.getAttribute("blueprintPagePermissionsService"); %>
<div class="itemSettingsWindowDiv">
    <input type="hidden" id="pageToEditId" value="<%= service.getPageId() %>"/>

    <h1><international:get name="header"/></h1>

    <page:title>
        <jsp:attribute name="customServiceName">blueprintPagePermissionsService</jsp:attribute>
    </page:title>

    <div class="windowTopLine">&nbsp;</div>

    <div class="inform_mark" style="margin: 15px 0 10px 0">
        <international:get name="explan1"/>
    </div>

    <input type="checkbox" id="pageIsRequired" <% if (service.isRequired()) { %>checked="checked"<% } %>>
    <label for="pageIsRequired"><international:get name="required"/></label>

    <br><br>
    <input type="checkbox" id="pageUrlAndNameNotEditable" <% if (service.isNotEditable()) { %>checked="checked"<% } %>>
    <label for="pageUrlAndNameNotEditable"><international:get name="notEditable"/></label>

    <br><br>
    <input type="checkbox" id="pageIsLocked" <% if (service.isLocked()) { %>checked="checked"<% } %>>
    <label for="pageIsLocked"><international:get name="locked"/></label>

    <div class="inform_mark" style="margin: 15px 0 10px 0">
        <international:get name="explan2"/>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" onclick="configureBlueprintPagePermissions.save(false);" value="Apply"
               onmouseout="this.className='but_w73';" id="windowApply"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" onclick="configureBlueprintPagePermissions.save(true);" value="Save"
               onmouseout="this.className='but_w73';" id="windowSave"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" onclick="closeConfigureWidgetDivWithConfirm();" value="Cancel"
               onmouseout="this.className='but_w73';" id="windowCancel"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>