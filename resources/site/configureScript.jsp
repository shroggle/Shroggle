<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.presentation.site.ConfigureScriptService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<% final ConfigureScriptService service = (ConfigureScriptService) request.getAttribute("scriptService"); %>
<% final DraftScript draftScript = service.getDraftScript(); %>
<div class="itemSettingsWindowDiv">
    <input type="hidden" value="Header / Description" id="itemDescriptionDefaultHeader"/>
    <input type="hidden" value="<%= draftScript.isShowDescription() %>" id="showconfigureScriptHeader"/>
    <input type="hidden" value="Display Header / Description" id="itemDescriptionDisplayHeader"/>
    <input type="hidden" value="<%= draftScript.getId() %>" id="selectedScriptItemId"/>

    <div id="configureScriptHeader"
         style="display:none"><%= StringUtil.getEmptyOrString(draftScript.getDescription()) %>
    </div>

    <h1>Edit Script Module: Module Settings</h1>

    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="scriptService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="scriptReadOnlyMessage">You have only read-only
        access to this module.</div>

    <br>

    <label for="configureScriptName" style="vertical-align:baseline;width:5%;">Script item - Name</label>
    <input class="title" style="vertical-align: baseline;width: 40%;" type="text" id="configureScriptName"
           value="<%= service.getDraftScript().getName() %>" maxlength="255"><br><br>

    <label for="scriptHeader"
           onmouseover="bindTooltip({element: this, contentId: 'configureScriptHeader'});">
        Script Header / Description
    </label>

    <a id="scriptHeader"
       href="javascript:showConfigureItemDescription({id: 'configureScript'});"
       onmouseover="bindTooltip({element: this, contentId: 'configureScriptHeader'});">Edit</a>

    <br><br>

    <label for="configureScriptText">Paste your script here:</label><br>
    <textarea id="configureScriptText" style="width: 100%;" rows="5"
              cols="5"><%= StringUtil.getEmptyOrString(draftScript.getText()) %></textarea>

    <br>
    <br>
    <br>

    <div align="right">

    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureScriptButtons">
        <input type="button" value="Apply" id="windowApply" onclick="configureScript.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onclick="configureScript.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" id="windowCancel" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

