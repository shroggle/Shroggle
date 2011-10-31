<%@ page import="com.shroggle.entity.DraftForm" %>
<%@ page import="com.shroggle.entity.DraftFormItem" %>
<%@ page import="com.shroggle.entity.DraftItem" %>
<%@ page import="com.shroggle.presentation.form.filter.ConfigureFormFilterService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="configureFormFilter"/>
<% final ConfigureFormFilterService service = (ConfigureFormFilterService) request.getAttribute("service"); %>
<div class="windowOneColumn">
    <input type="hidden" id="formItemNotSelected" value="<international:get name="formItemNotSelected"/>"/>
    <input type="hidden" id="formFilterWithoutName" value="<international:get name="formFilterWithoutName"/>"/>
    <input type="hidden" id="deleteFilterConfirm" value="<international:get name="deleteFilterConfirm"/>"/>
    <input type="hidden" id="deleteFormFilterRuleConfirm"
           value="<international:get name="deleteFormFilterRuleConfirm"/>"/>
    <input type="hidden" id="formNotSelected" value="<international:get name="formNotSelected"/>"/>
    <input type="hidden" id="fromDateIsAfterTillDateException" value="<international:get name="fromDateIsAfterTillDateException"/>"/>
    <input type="hidden" id="modifyFormFilter" value="<international:get name="modifyFormFilter"/>">

    <h1><international:get name="header"/></h1>

    <div class="emptyError" id="errors"></div>
    <!-- Special element that used for store settings and use their in js -->
    <input type="hidden" id="configureFormFilterSettings">

    <div style="margin-top: 5px;">
        <label for="filterName" style="font-weight:bold;"><international:get name="filterName"/></label>
        <input id="filterName" onchange="this.customTextEntered = true;"
               <% if (service.getFormFilterLogic() != null) { %>value="<%= service.getFormFilterLogic().getFormFilter().getName() %>"
                <% } else if (service.getDefaultFilterName() != null) { %>value="<%= service.getDefaultFilterName()%>" onclick="filterNameInputClick(this);"<% } %>/>
        <% if (service.getFormFilterLogic() != null) { %><img src="/images/cross-circle.png" alt="Delete"
                                                              style="cursor:pointer;margin-left:5px;"
                                                              onclick="deleteFormFilter(<%= service.getFormFilterLogic().getFormFilter().getFormFilterId() %>);"><% } %>
    </div>

    <hr>

    <label for="formSelect"><international:get name="selectForm"/></label>
    <select id="formSelect" onchange="updateFilterDefaultName($('#filterName')[0], this.options[this.selectedIndex].value);">
        <option><international:get name="selectFormDefaultOption"/></option>
        <% for (DraftItem form : service.getForms()) { %>
        <option <% if (service.getSelectedForm() != null && form.getFormId() == service.getSelectedForm().getFormId()) { %>selected="selected"<% } %>
                value="<%= form.getFormId() %>"><%= form.getName() %>
        </option>
        <% } %>
    </select>

    <div class="inform_mark" style="margin-left:0;margin-top:10px">
        <international:get name="explan1"/>&nbsp;<a href="javascript:filterMoreInfo();"><international:get
            name="moreInfo"/></a>

        <div id="filterMoreInfo" style="display:none">
            <div class="windowOneColumn">
                <international:get name="filterMoreInfoText"/>
                <div align="right">
                    <input type="button" value="Close" onmouseout="this.className='but_w73';"
                           onmouseover="this.className='but_w73_Over';" class="but_w73"
                           onclick="closeConfigureWidgetDiv();">
                </div>
            </div>
        </div>
    </div>

    <hr>

    <label for="formItemSelect" style="font-weight:bold;"><international:get name="selectFormItem"/></label>
    <select id="formItemSelect">
        <option value="-1"><international:get name="selectFormItemDefaultOption"/></option>
        <% for (DraftFormItem formItem : service.getFormItems()) { %>
        <option value="<%= formItem.getFormItemId() %>" itemType="<%= formItem.getFormItemName().getType() %>">
            <%= formItem.getItemName() %>
        </option>
        <% } %>
    </select>
    <input type="button" style="margin-left:10px" value="Add" onmouseout="this.className='but_w73';"
           onmouseover="this.className='but_w73_Over';" class="but_w73"
           onclick="addFilterRule();"/>

    <div class="inform_mark" style="margin-left:0;margin-top:10px">
        <international:get name="explan2"/>
    </div>


    <div id="editFilterHeader"
         <% if (service.getFormFilterLogic() == null || service.getFormFilterLogic().getRules().isEmpty()) { %>style="display:none;"<% } %>>
        <hr/>
        <h2><international:get name="editFilter"/></h2>
    </div>

    <div id="editFilterBlock" class="editFilterBlock">
        <table class="filterRuleTable" id="formFilterRules">
        </table>
    </div>

    <div id="registrationButtons" class="buttons_box" style="width:100%;margin:20px 0 0">
        <input type="button" value="Save" onmouseout="this.className='but_w73';" id="windowSave"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="saveFormFilter(<%= service.getFormFilterLogic() != null ? service.getFormFilterLogic().getId() : null %>);">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';" id="windowCancel"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>
