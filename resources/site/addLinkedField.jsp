<%@ page import="com.shroggle.entity.FormItem" %>
<%@ page import="com.shroggle.entity.FormItemType" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.logic.accessibility.UserRightManager" %>
<%@ page import="com.shroggle.logic.site.item.ItemManager" %>
<%@ page import="com.shroggle.presentation.form.ShowAddLinkedFieldService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="siteItems" tagdir="/WEB-INF/tags/siteItems" %>
<international:part part="addLinkedField"/>
<%
    final ShowAddLinkedFieldService service = (ShowAddLinkedFieldService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <input type="hidden" id="selectFormItemDefaultOption"
           value="<international:get name="selectFormItemDefaultOption"/>"/>
    <input type="hidden" id="AddLinkedFieldFormNotSelectedException"
           value="<international:get name="AddLinkedFieldFormNotSelectedException"/>"/>
    <input type="hidden" id="AddLinkedFieldFormItemNotSelectedException"
           value="<international:get name="AddLinkedFieldFormItemNotSelectedException"/>"/>
    <input type="hidden" id="AddLinkedFieldDuplicateNameException"
           value="<international:get name="AddLinkedFieldDuplicateNameException"/>"/>
    <input type="hidden" id="selectFormDefaultOption" value="<international:get name="selectFormDefaultOption"/>"/>
    <% if (service.getSelectedFormItem() != null) { %>
    <input type="hidden" id="isEditLinkedField" value="yeah"/>
    <% } %>

    <h1><% if (service.getTargetForm() == null) { %><international:get name="newFormHeader"/>
        <% } else { %><%= service.getTargetForm().getName() %><% } %></h1>

    <h2><international:get name="subHeader"/></h2>

    <div class="emptyError" id="errors"></div>

    <div class="default_settings_header">
        <international:get name="linkedFormHeader"/>
    </div>

    <div class="default_settings_block">
        <div>
            <%
                final UserRightManager userRightManager = service.getLoginedUser().getRight();
                final List<ItemManager> formManagers = ItemManager.siteItemsToManagers(
                        userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));
            %>
            <select id="linkedFieldFormSelect" onchange="addLinkedField.formsSelectOnChange();">
                <option value="-1"><international:get name="selectFormDefaultOption"/></option>
                <siteItems:asOptions value="<%= formManagers %>"
                                     selectedItemId="<%= service.getSourceForm() != null ? service.getSourceForm().getId() : null %>"/>
            </select>

            <div id="linkedFieldFormSelectLoadingDiv" style="display:inline; visibility:hidden;">
                <img alt="Loading text editor..." src="/images/ajax-loader.gif"
                     style="vertical-align:middle;padding: 0 3px 0 0"/>
            </div>

            <div>
                <a id="addLinkedFieldEditSelectedFormLink"
                   href="javascript:addLinkedField.editSelectedForm()"><international:get name="editSelected"/></a>
                <a style="margin-left:3px;" href="javascript:addLinkedField.createNewForm(<%= service.getTargetForm().getSiteId() %>)"
                   id="addLinkedFieldCraeteNewFormLink"><international:get name="createNewForm"/></a>
            </div>
        </div>
    </div>

    <div class="default_settings_header">
        <international:get name="linkedFormExplanHeader"/>
    </div>

    <div class="default_settings_block">
        <div>
            <label for="linkedFieldFormItemSelect"><international:get name="selectFormItemLabel"/></label>
            <select id="linkedFieldFormItemSelect" onchange="addLinkedField.formItemsSelectOnChange();">
                <option value="-1"><international:get name="selectFormItemDefaultOption"/></option>
                <% if (service.getSourceForm() != null && service.getSelectedFormItem() != null) { %>
                <% for (FormItem formItem : service.getLinkedAllowedItems()) {%>
                <option <% if (service.getSelectedFormItem().getLinkedFormItemId() == formItem.getFormItemId()) { %>
                        selected="selected"
                        <% } %> value="<%= formItem.getFormItemId() %>" itemName="<%= formItem.getItemName() %>">
                    <%= formItem.getItemName() %>
                </option>
                <% } %>
                <% } %>
            </select>
        </div>
    </div>

    <div class="default_settings_block">
        <div>
            <div>
                <label for="linkedFieldItemTypeSelect"><international:get name="linkedFieldItemTypeSelect"/></label>
                <select id="linkedFieldItemTypeSelect" style="width:120px;">
                    <option <% if (service.getSelectedFormItem() != null &&
                            service.getSelectedFormItem().getFormItemDisplayType() == FormItemType.SELECT) { %>
                            selected="selected"
                            <% } %> value="<%= FormItemType.SELECT %>">
                        <international:get name="dropDownBoxItemTypeOption"/>
                    </option>
                    <option <% if (service.getSelectedFormItem() != null &&
                            service.getSelectedFormItem().getFormItemDisplayType() == FormItemType.RADIO_LIST) { %>
                            selected="selected"
                            <% } %> value="<%= FormItemType.RADIO_LIST %>">
                        <international:get name="radioBoxesItemTypeOption"/>
                    </option>
                    <option <% if (service.getSelectedFormItem() != null &&
                            service.getSelectedFormItem().getFormItemDisplayType() == FormItemType.SELECTION_LIST) { %>
                            selected="selected"
                            <% } %> value="<%= FormItemType.SELECTION_LIST %>">
                        <international:get name="checkboxesItemTypeOption"/>
                    </option>
                </select>
            </div>

            <div class="default_settings_subblock">
                <label for="linkedFieldTextEdit"><international:get name="linkedFieldTextEdit"/></label>
                <input type="text" id="linkedFieldTextEdit" style="width:247px"
                        <% if (service.getSelectedFormItem() != null) { %>
                       value="<%=service.getSelectedFormItem().getItemName()%>"
                        <% } %>/>
            </div>
        </div>
    </div>

    <div class="buttons_box" style="width:100%;margin:20px 0 0">
        <input type="button" id="windowSave" value="Save" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="addLinkedField.save();"/>
        <input type="button" id="windowCancel" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="closeConfigureWidgetDiv();"/>
    </div>
</div>
