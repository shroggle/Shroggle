<%@ page import="com.shroggle.presentation.advancedSearch.EditSearchOptionsService" %>
<%@ page import="com.shroggle.entity.FormItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="editSearchOptions"/>
<% final EditSearchOptionsService service = (EditSearchOptionsService) request.getAttribute("service"); %>
<input type="hidden" id="editSearchOptionsFormId" value="<%= service.getFormId() %>">
<input type="hidden" id="EditOptionsFieldNotSelectedException"
       value="<international:get name="EditOptionsFieldNotSelectedException"/>"/>

<div class="windowOneColumn">
    <h1><international:get name="header"/></h1>

    <div class="emptyError" id="editOptionsErrors"></div>

    <div class="inform_mark">
        <international:get name="searchModulesExplan"/>
        <a href="javascript:configureAdvancedSearch.showSearchModulesExplan()"><international:get name="moreInfo"/></a>

        <div style="display:none" id="searchModulesExplanDiv">
            <div class="windowOneColumn">
                <international:get name="searchModulesWindow"/>
                <div align="right">
                    <input type="button" class="but_w73" value="Close"
                           onmouseover="this.className='but_w73_Over';"
                           onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();"/>
                </div>
            </div>
        </div>
    </div>

    <hr/>

    <div class="default_settings_header">
        <international:get name="selectFieldHeader"/>
    </div>

    <div class="default_settings_header">
        <select id="formFieldsSelect">
            <option value="-1"><international:get name="formFieldsSelectDefaultOption"/></option>
            <% for (FormItem formItem : service.getSelectedFormItems()) { %>
            <option value="<%= formItem.getFormItemId() %>"><%= formItem.getItemName() %>
            </option>
            <% } %>
            <% if (!service.getSelectedFormItems().isEmpty()) { %>
            <option value="allFields"><international:get name="allFields"/></option>
            <% } %>
        </select>
        <input type="button" value="Add" onmouseout="this.className='but_w73_misc';"
               onclick="configureAdvancedSearch.addSearchOption();"
               onmouseover="this.className='but_w73_Over_misc';" class="but_w73_misc">

        <div id="addSearchOptionLoadingDiv" style="display:none;">
            <img alt="Loading text editor..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>

    <div class="inform_mark">
        <international:get name="searchFieldsExplan"/>
    </div>

    <hr/>

    <div class="default_settings_header">
        <international:get name="editSearchOptionsHeader"/>
    </div>

    <div class="default_settings_block" id="editSearchOptionsList" style="max-height:500px;overflow-y:auto">
        <jsp:include page="editSearchOptionsList.jsp"/>
    </div>

    <div id="advancedSearchButtons" class="buttons_box" style="width:100%; margin:20px 0 0;">
        <input type="button" value="Save" id="saveEditSearchOptions" onmouseout="this.className='but_w73';"
               onclick="configureAdvancedSearch.saveEditOptions();" id="windowSave"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="configureAdvancedSearch.cancelEditOptions();">
    </div>
</div>
