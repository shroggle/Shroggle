<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.form.FormItemsManager" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.advancedSearch.AdvancedSearchHelper" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.logic.form.FormItemManager" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="editSearchOptions"/>
<% final List<DraftAdvancedSearchOption> searchOptions = (List<DraftAdvancedSearchOption>) request.getAttribute("searchOptions"); %>
<% final Integer siteId = (Integer) request.getAttribute("siteId"); %>
<% final Integer formId = (Integer) request.getAttribute("formId"); %>

<% new AdvancedSearchHelper().sortOptionsByPosition(searchOptions); %>

<input type="hidden" id="optionRemoveConfirm" value="<international:get name="optionRemoveConfirm"/>"/>
<input type="hidden" id="advSearchBothDateAreEmptyException"
       value="<international:get name="advSearchBothDateAreEmptyException"/>"/>
<input type="hidden" id="advSearchStartDateIsntValidException"
       value="<international:get name="advSearchStartDateIsntValidException"/>"/>
<input type="hidden" id="advSearchEndDateIsntValidException"
       value="<international:get name="advSearchEndDateIsntValidException"/>"/>
<table class="tbl_blog" style="width:99%">
<thead>
<tr>
    <td style="width:35px">
        <international:get name="order"/>
    </td>
    <td>
        <international:get name="fieldName"/>
    </td>
    <td style="width:15px"></td>
</tr>
</thead>
<tbody id="editSearchOptionsListTableBody">
<% for (int i = 0; i < searchOptions.size(); i++) { %>
<% final int uniqueSearchOptionsId = new Random().nextInt(); %>
<% final DraftAdvancedSearchOption searchOption = searchOptions.get(i);%>
<% final DraftFormItem formItem = ServiceLocator.getPersistance().getFormItemById(searchOption.getFormItemId()); %>
<tr class="optionTr">
<td>
    <input type="hidden" class="optionId" value="<%= searchOption.getAdvancedSearchOptionId() %>"/>
    <input type="hidden" class="formItemId" value="<%= formItem.getFormItemId() %>"/>
    <input type="hidden" class="formItemType" value="<%= formItem.getFormItemName().getType() %>"/>

    <img src="/images/up_arrow.gif" onclick="configureAdvancedSearch.moveSearchOptionUp(this);"
         class="advSearchUpImg"
         style="cursor:pointer;<% if (i == 0) { %>display:none;<% } %>" alt="Up"/>
    <img src="/images/down_arrow.gif" onclick="configureAdvancedSearch.moveSearchOptionDown(this);"
         class="advSearchDownImg"
         style="cursor:pointer;<% if (i == 0) { %>margin-left:18px;<% } %><% if (i == searchOptions.size() - 1 || searchOptions.size() == 1) { %>display:none;<% } %>"
         alt="Down"/>
</td>
<td>
    <label for="fieldLabel<%= uniqueSearchOptionsId %>"><b><international:get name="fieldLabel"/></b></label>
    <input type="text" id="fieldLabel<%= uniqueSearchOptionsId %>" maxlength="255" class="fieldLabel" value="<% if (StringUtil.isNullOrEmpty(searchOption.getFieldLabel())) { %><international:get
                    name="defaultFieldLabel"/><%= formItem.getItemName() %><% } else { %><%= searchOption.getFieldLabel() %><% } %>"/>
    <label><b><international:get
            name="fieldType"/></b>&nbsp;<%= FormItemManager.getItemFieldType(formItem.getFormItemName()) %>
    </label>

    <% if (formItem.getFormItemName().isSingleOptionPickList()) { %>
    <input type="hidden" class="optionType" value="PICK_LIST"/>

    <div class="default_settings_subblock">
        <international:get name="pickListHeader"/>
    </div>
    <div class="default_settings_subblock pickListOptionsList">
        <div style="float:left;width:305px;">
            <% final Map<Integer, List<String>> options = FormItemManager.getItemOptionListForPickList(formItem);%>
            <% for (String option : options.get(1)) { %>
            <% final int uniquePickListOptionId = new Random().nextInt(); %>
            <input type="checkbox" class="pickListOption" id="option<%= uniquePickListOptionId %>"
                   value="<%= option %>"
                   <% if (searchOption.getOptionCriteria().contains(option)) { %>checked="checked"<% } %>/><label
                for="option<%= uniquePickListOptionId %>"><%= option %>
        </label><br/>
            <% } %>
        </div>
        <div style="" class="pickListOptionsSelectionDiv">
            <span class="pickListOptionLink" onclick="configureAdvancedSearch.selectAllPickOptions(this);"
                    ><international:get name="pickListSelectAll"/></span>
            <span class="pickListOptionLink" onclick="configureAdvancedSearch.deselectAllPickOptions(this);"
                  style="margin-left:5px;"><international:get name="pickListDeselectAll"/></span>
        </div>
    </div>
    <div class="default_settings_subblock">
        <input name="pickListRadio<%= uniqueSearchOptionsId %>" type="radio"
               value="<%= OptionDisplayType.PICK_LIST_SELECT %>"
               <% if (searchOption.getDisplayType() == OptionDisplayType.PICK_LIST_SELECT || searchOption.getDisplayType() == null) { %>checked="checked"<% } %>
               id="pickListSelectOption<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"
               <% if (searchOption.getDisplayType() == null) { %>checked="checked"<% } %>/>
        <label for="pickListSelectOption<%= uniqueSearchOptionsId %>"><international:get
                name="pickListSelectOption"/></label>
    </div>
    <div class="default_settings_subblock">
        <input name="pickListRadio<%= uniqueSearchOptionsId %>" type="radio"
               value="<%= OptionDisplayType.PICK_LIST_MULTISELECT %>"
               <% if (searchOption.getDisplayType() == OptionDisplayType.PICK_LIST_MULTISELECT) { %>checked="checked"<% } %>
               id="pickListMultiselectOption<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"/>
        <label for="pickListMultiselectOption<%= uniqueSearchOptionsId %>"><international:get
                name="pickListMultiselectOption"/></label>
    </div>
    <% } else if (formItem.getFormItemName().isRange()) { %>
    <input type="hidden" class="optionType" value="RANGE"/>

    <div class="default_settings_subblock">
        <input name="rangeRadio<%= uniqueSearchOptionsId %>" type="radio"
               value="<%= OptionDisplayType.RANGE_AS_RANGE %>"
               <% if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_RANGE) { %>checked="checked"<% } %>
               id="rangesAsRange<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"
               onclick="$(this).parents('.optionTr').find('.rangesDefineDiv').show();getActiveWindow().resize();"/>
        <label for="rangesAsRange<%= uniqueSearchOptionsId %>"><international:get
                name="rangesAsRange"/></label>
    </div>
    <div class="default_settings_subblock">
        <input type="radio" name="rangeRadio<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"
               <% if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_RANGE_INPUTS) { %>checked="checked"<% } %>
               id="rangesAsInputs<%= uniqueSearchOptionsId %>" value="<%= OptionDisplayType.RANGE_AS_RANGE_INPUTS %>"
               onclick="$(this).parents('.optionTr').find('.rangesDefineDiv').hide();getActiveWindow().resize();"/>
        <label for="rangesAsInputs<%= uniqueSearchOptionsId %>"><international:get
                name="rangesAsInputs"/></label>
    </div>
    <div class="default_settings_subblock">
        <input name="rangeRadio<%= uniqueSearchOptionsId %>" type="radio"
               value="<%= OptionDisplayType.RANGE_AS_SEP_OPTION %>"
               <% if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_SEP_OPTION || searchOption.getDisplayType() == null) { %>checked="checked"<% } %>
               id="rangesAsSepOption<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"
               <% if (searchOption.getDisplayType() == null) { %>checked="checked"<% } %>
               onclick="$(this).parents('.optionTr').find('.rangesDefineDiv').hide();getActiveWindow().resize();"/>
        <label for="rangesAsSepOption<%= uniqueSearchOptionsId %>"><international:get
                name="rangesAsSepOption"/></label>
    </div>
    <div class="default_settings_subblock rangesDefineDiv"
         <% if (searchOption.getDisplayType() != OptionDisplayType.RANGE_AS_RANGE) { %>style="display:none;"<% } %>>
        <international:get name="rangesDefineHeader"/>
        <div class="default_settings_subblock rangesDiv">
            <% if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_RANGE) { %>
            <% for (String criteria : searchOption.getOptionCriteria()) { %>
            <div class='searchOptionParameter' value='<%= criteria %>'
                 itemname='<%= criteria %>'>
                <%= AdvancedSearchHelper.formatRange(formItem, criteria, true) %>
                <img src='/images/cross-circle.png' alt='' class='searchOptionParameterDeleteImg'
                     onclick='configureAdvancedSearch.removeOptionParameter(this);'/>
            </div>
            <% } %>
            <% } %>
        </div>
        <div class="default_settings_subblock">
            <% request.setAttribute("rangeInputsFormItem", formItem);%>
            <% request.setAttribute("rangeInputsFromCaption",
                    ServiceLocator.getInternationStorage().get("editSearchOptions", Locale.US).get("rangeStart")); %>
            <% request.setAttribute("rangeInputsTillCaption",
                    ServiceLocator.getInternationStorage().get("editSearchOptions", Locale.US).get("rangeEnd")); %>
            <jsp:include page="/advancedSearch/advancedSearchRangeInputs.jsp"/>

            <input type="button" value="Add More Range Options" id="addRangeButton"
                   onmouseout="this.className='but_w230';" onclick="configureAdvancedSearch.addRangeClick(this);"
                   onmouseover="this.className='but_w230_Over';" class="but_w230"/>

            <div class="addRangeError" style="color:red;display:inline;opacity:0;"></div>
        </div>
    </div>
    <% } else if (formItem.getFormItemName() == FormItemName.POST_CODE) { %>
    <input type="hidden" class="optionType" value="POST_CODE"/>
    <input type="hidden" class="advSearchOptionDisplayType" value="<%= OptionDisplayType.POST_CODE %>"
           checked="checked">

    <div class="default_settings_subblock">
        <international:get name="zipHeader"/>
    </div>
    <div class="default_settings_subblock">
        <international:get name="zipExplan"/>
    </div>
    <% } else if (formItem.getFormItemName().isText()) { %>
    <input type="hidden" class="optionType" value="TEXT"/>

    <div class="default_settings_subblock">
        <input type="radio" name="textRadio<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"
               <% if (searchOption.getDisplayType() == OptionDisplayType.TEXT_AS_SEP_OPTION || searchOption.getDisplayType() == null) { %>checked="checked"<% } %>
               id="textAsSepOption<%= uniqueSearchOptionsId %>"
               value="<%= OptionDisplayType.TEXT_AS_SEP_OPTION %>"
               onclick="$(this).parents('.optionTr').find('.textAsFreeDiv').hide();getActiveWindow().resize();"
               <% if (searchOption.getDisplayType() == null) { %>checked="checked"<% } %>/>
        <label for="textAsSepOption<%= uniqueSearchOptionsId %>"><international:get
                name="textAsSepOption"/></label>
    </div>
    <div class="default_settings_subblock">
        <input type="radio" name="textRadio<%= uniqueSearchOptionsId %>" class="advSearchOptionDisplayType"
               <% if (searchOption.getDisplayType() == OptionDisplayType.TEXT_AS_FREE) { %>checked="checked"<% } %>
               id="textAsFree<%= uniqueSearchOptionsId %>" value="<%= OptionDisplayType.TEXT_AS_FREE %>"
               onclick="$(this).parents('.optionTr').find('.textAsFreeDiv').show();getActiveWindow().resize();"/>
        <label for="textAsFree<%= uniqueSearchOptionsId %>"><international:get
                name="textAsFree"/></label>
    </div>
    <div class="default_settings_subblock textAsFreeDiv"
         <% if (searchOption.getDisplayType() != OptionDisplayType.TEXT_AS_FREE) { %>style="display:none;"<% } %>>
        <div class="default_settings_subblock">
            <international:get name="otherTextSources"/>
            <div class="default_settings_subblock otherTextSourcesDiv">
                <% if (searchOption.getDisplayType() == OptionDisplayType.TEXT_AS_FREE) { %>
                <% for (Integer fieldId : searchOption.getAlsoSearchByFields()) { %>
                <% final DraftFormItem formItemFromCriteria = ServiceLocator.getPersistance().getFormItemById(fieldId); %>
                <div class='searchOptionParameter' value='<%= fieldId %>'
                     itemname='<%= formItemFromCriteria.getItemName() %>'>
                    <%= formItemFromCriteria.getItemName() %>
                    <img src='/images/cross-circle.png' alt='' class='searchOptionParameterDeleteImg'
                         onclick='configureAdvancedSearch.removeOptionParameter(this);'/>
                </div>
                <% } %>
                <% } %>
            </div>
        </div>
        <div class="default_settings_subblock">
            <international:get name="otherTextField"/>
            <br/>

            <select class="otherTextFieldsSelect">
                <option value="-1">Select a field</option>
                <%
                    final List<FormItem> additionalFormItems = FormItemsManager.getCorrectFormItemsForAdvancedSearch(
                            ServiceLocator.getPersistance().getFormById(formId).getDraftFormItems());
                    for (FormItem additionalFormItem : additionalFormItems) {
                        if (searchOption.getAlsoSearchByFields().contains(additionalFormItem.getFormItemId())) {
                            continue;
                        }
                %>
                <option value="<%= additionalFormItem.getFormItemId() %>"
                        itemName="<%= additionalFormItem.getItemName() %>"><%= additionalFormItem.getItemName() %>
                </option>
                <% } %>
            </select>
            <input type="button" value="Add Field" id="addFieldButton"
                   onmouseout="this.className='but_w73';" onclick="configureAdvancedSearch.addFieldClick(this);"
                   onmouseover="this.className='but_w73_Over';" class="but_w73"/>

            <div class="addFieldError" style="color:red;display:inline;opacity:0;filter:alpha(opacity=0);">
                <international:get name="addFieldError"/>
            </div>

        </div>
    </div>
    <% } else if (formItem.getFormItemName().getType() == FormItemType.CHECKBOX) { %>
    <input type="hidden" class="optionType" value="SINGLE_CHECKBOX"/>

    <div class="default_settings_subblock">
        <international:get name="pickListHeader"/>
    </div>
    <input type="checkbox" class="pickListOption" style="display:none;" checked="checked"
           value="<%= formItem.getItemName() %>"/>
    <input type="radio" class="advSearchOptionDisplayType" style="display:none;"
           value="<%= OptionDisplayType.SINGLE_CHECKBOX %>" checked="checked"/>
    <% } %>
</td>
<td>
    <img src="/images/cross-circle.png" style="cursor:pointer;" alt="Delete"
         onclick="configureAdvancedSearch.removeSearchOption(this, <%= searchOption.getAdvancedSearchOptionId() %>, <%= uniqueSearchOptionsId %>);"/>
</td>
</tr>
<% } %>
</tbody>
</table>
