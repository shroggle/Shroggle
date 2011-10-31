<%@ page import="com.shroggle.presentation.site.ConfigureAdminLoginService" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureAdminLogin"/>
<% final ConfigureAdminLoginService service = (ConfigureAdminLoginService) request.getAttribute("adminLoginService"); %>
<div class="itemSettingsWindowDiv">
    <input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
    <input type="hidden" value="<%= service.getAdminLogin().isShowDescription() %>" id="showconfigureAdminLoginHeader"/>
    <input type="hidden" value="<international:get name="headerShow"/>" id="itemDescriptionDisplayHeader"/>

    <div id="configureAdminLoginHeader"
         style="display:none"><%= StringUtil.getEmptyOrString(service.getAdminLogin().getDescription()) %>
    </div>
    <input type="hidden" value="<%= service.getAdminLogin().getId() %>" id="selectedAdminLoginId"/>

    <h1><international:get name="title"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="adminLoginService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="adminLoginErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="adminLoginReadOnlyMessage">
        <international:get name="readOnlyAccess"/>
    </div>

    <div class="bottomMargin10px">
        <label for="adminLoginName">
            <international:get name="adminAccessLoginItemName"/>
        </label>
        <input type="text" id="adminLoginName" value="<%= service.getAdminLogin().getName() %>"
               maxlength="255"/>

        <span class="marginLeft50px">
            <label for="adminLoginHeader"
                   onmouseover="bindTooltip({element: this, contentId: 'configureAdminLoginHeader'});">
                <international:get name="header"/>
            </label>


            <a id="adminLoginHeader"
               href="javascript:showConfigureItemDescription({id: 'configureAdminLogin'});"
               onmouseover="bindTooltip({element: this, contentId: 'configureAdminLoginHeader'});">
                <international:get name="headerEdit"/>
            </a>
        </span>
    </div>


    <div class="topMargin30px">
        <div class="inform_mark">
            <international:get name="instructions"/>
        </div>
        <label for="configureAdminLoginText">
            <international:get name="textLabel"/>:
        </label>
        <input type="text" id="configureAdminLoginText" maxlength="255"
               value="<%= service.getAdminLogin().getText() %>">
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureAdminLoginButtons">
        <input type="button" value="<international:get name="apply"/>" id="windowApply"
               onclick="configureAdminLogin.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="<international:get name="save"/>" id="windowSave"
               onclick="configureAdminLogin.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" id="windowCancel" value="<international:get name="cancel"/>"
               onclick="closeConfigureWidgetDivWithConfirm();"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

