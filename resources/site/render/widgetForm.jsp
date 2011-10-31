<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.entity.FormItemType" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.form.*" %>
<%@ page import="com.shroggle.logic.text.TextAreaSettings" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.shroggle.logic.groups.SubscriptionTimeType" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="onload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<style type="text/css">
    .notNumberInputError {
        background-color: #E9C0B6
    }
</style>
<%
    FormData formData = (FormData) request.getAttribute("form");
    int formWidgetId = (Integer) request.getAttribute("widgetId");
    User loginedUser = (User) request.getAttribute("loginedUser");
    int formSiteId = (Integer) request.getAttribute("siteId");
    boolean forcePrefill = request.getAttribute("forcePrefill") != null ? (Boolean) request.getAttribute("forcePrefill") : false;
    final int formPageBreaksToPass = request.getParameter("pageBreaksToPass") != null ? Integer.parseInt(request.getParameter("pageBreaksToPass")) : 0;
    final boolean showFromEditRecord = request.getAttribute("showFromEditRecord") != null ? (Boolean) request.getAttribute("showFromEditRecord") : false;
    final boolean showFromAddRecord = request.getParameter("showFromAddRecord") != null && Boolean.parseBoolean(request.getParameter("showFromAddRecord"));
    final boolean cameFromReset = request.getParameter("cameFromReset") != null && Boolean.parseBoolean(request.getParameter("cameFromReset"));
    FilledForm prefilledForm = request.getAttribute("prefilledForm") != null ? (FilledForm) request.getAttribute("prefilledForm") : null;

    final Integer filledFormToUpdateId = request.getParameter("filledFormToUpdateId") != null ? Integer.valueOf(request.getParameter("filledFormToUpdateId")) : null;
    prefilledForm = prefilledForm == null && filledFormToUpdateId != null ?
            ServiceLocator.getPersistance().getFilledFormById(filledFormToUpdateId) : prefilledForm;

    if (formData != null) {
        prefilledForm = prefilledForm != null ? prefilledForm : (cameFromReset ? null : FilledFormManager.findPrefilledForm(loginedUser, formSiteId, forcePrefill));
        for (FormItem formItem : FormItemsManager.getSortedFormItemsByPageBreakIndex(formData.getFormItems(), formPageBreaksToPass, showFromEditRecord)) {
            final FormItemType formItemType = formItem.getFormItemName() == FormItemName.LINKED ? formItem.getFormItemDisplayType() : formItem.getFormItemName().getType();
            final List<String> prefilledItems = FilledFormManager.getFilledFormItemValueByItemNameList(prefilledForm, formItem.getFormItemName(), formItem.getItemName(), formData.getFormId());
%>
<%--FORM-SPECIFIC FIELDS. Here will be all form-specific fields it's done to unify form logic.--%>
<%
    if (formItem.getFormItemName() == FormItemName.CONTACT_US_MESSAGE) {
        String contactUsMessageText = formItem.getItemName();
%>
<tr>
    <td>
        <label for="contactUsTextarea<%= formWidgetId %>"><%= contactUsMessageText != null ? contactUsMessageText + " *" : FormManager.getFormInternational().get("message") %>
        </label>
    </td>
    <td>
        <textarea onfocus="trimTextArea(this);" class="formTextArea" cols="30" rows="5"
                  id="CONTACT_US_MESSAGE<%= formWidgetId %>"
                  required="true"
                  formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
                  formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
                  name="TEXT_AREA<%=formWidgetId%>"><%= !prefilledItems.isEmpty() ? prefilledItems.get(0) : "" %>
        </textarea>
    </td>
</tr>
<%
} else if (formItem.getFormItemName() == FormItemName.REGISTRATION_EMAIL) {
    final String registrationEmailText = formItem.getItemName();
    final String emailFromLoginedUser = loginedUser != null ? loginedUser.getEmail() : "";
    final String emailFromPrefilledForm = !prefilledItems.isEmpty() ? prefilledItems.get(0) : "";
%>
<tr>
    <td><%= registrationEmailText != null ? registrationEmailText + " *" : FormManager.getFormInternational().get("enterLogin") %>
    </td>
    <td>
        <input class="formTextInput" id="REGISTRATION_EMAIL<%= formWidgetId %>" type="text"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               name="TEXT_INPUT_FIELD<%=formWidgetId%>"
                <% final String prefillWithEmail = request.getParameter("prefillWithEmail");
                    if (prefillWithEmail != null) { %>
               value="<%= prefillWithEmail %>"
               disabled="disabled" style="background-color:lightgray;"
                <% } else { %>
                <%if (showFromEditRecord || (request.getAttribute("disableEmailField") != null && (Boolean) request.getAttribute("disableEmailField"))) { %>
               value="<%= showFromEditRecord || showFromAddRecord ? (emailFromPrefilledForm) : (emailFromLoginedUser.isEmpty() ? emailFromPrefilledForm : emailFromLoginedUser)%>"
               disabled="disabled" style="background-color:lightgray;"
                <% } %>
                <% } %>/>

    </td>
</tr>
<%
} else if (formItem.getFormItemName() == FormItemName.REGISTRATION_PASSWORD) {
    if (!showFromEditRecord) {
        String password = formItem.getItemName();
%>
<tr>
    <td><%= password != null ? password + " *" : FormManager.getFormInternational().get("enterPassword") %>
    </td>
    <td><input class="formTextInput" id="REGISTRATION_PASSWORD<%= formWidgetId %>" type="password"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               name="TEXT_INPUT_FIELD<%=formWidgetId%>"
               <% if (request.getAttribute("disablePasswordField") != null && (Boolean) request.getAttribute("disablePasswordField")) { %>disabled="disabled"
               style="background-color:lightgray" <%} %>/></td>
</tr>
<%}%>
<%
} else if (formItem.getFormItemName() == FormItemName.REGISTRATION_PASSWORD_RETYPE) {
    if (!showFromEditRecord) {
        String retypePassword = formItem.getItemName();
%>
<tr>
    <td><%= retypePassword != null ? retypePassword + " *" : FormManager.getFormInternational().get("retypePassword") %>
    </td>
    <td><input class="formTextInput" id="REGISTRATION_PASSWORD_RETYPE<%= formWidgetId %>" type="password"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               name="TEXT_INPUT_FIELD<%=formWidgetId%>"
               <% if (request.getAttribute("disablePasswordField") != null && (Boolean)request.getAttribute("disablePasswordField")) { %>disabled="disabled"
               style="background-color:lightgray;" <%} %>/></td>
</tr>
<%}%>
<%--FORM-SPECIFIC FIELDS END'S--%>

<%--COMMON FIELD'S--%>
<%} else if (formItemType == FormItemType.TEXT_INPUT_FIELD) {%>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <input class="formTextInput" id="<%= formItem.getItemName() %>"
               name="TEXT_INPUT_FIELD<%=formWidgetId%>"
               <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)){%>onkeyup="checkNumbersOnlyFilter(this);"<% } %>
               <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_INTEGER_NUMBERS)){%>onkeyup="checkIntegerNumbersOnlyFilter(this);"<% } %>
               type="text"
               formItemName="<%= formItem.getFormItemName().toString() %>"
               required="<%= formItem.isRequired() %>"
               position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>"
               formItemText="<%= formItem.getItemName() %>"
               value="<%= !prefilledItems.isEmpty()? StringUtil.getEmptyOrString(prefilledItems.get(0)) : "" %>"/>
    </td>
</tr>
<%} else if (formItemType == FormItemType.RTF) {%>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <div id="formItemEditorDiv<%= formItem.getFormItemId() %>"
             name="RTF<%= formWidgetId %>"
             type="text"
             formItemName="<%= formItem.getFormItemName().toString() %>"
             required="<%= formItem.isRequired() %>"
             position="<%= formItem.getPosition() %>"
             formItemId="<%= formItem.getFormItemId() %>"
             formItemText="<%= formItem.getItemName() %>">
        </div>
        <div id="formItemEditorValue<%= formItem.getFormItemId() %>" style="display:none;">
            <%= !prefilledItems.isEmpty()? StringUtil.getEmptyOrString(prefilledItems.get(0)) : "" %>
        </div>
        <% String textEditorOnloadFunc = "initFormTextEditor(" + formItem.getFormItemId() + ");"; %>
        <onload:element onload="<%= textEditorOnloadFunc %>"/>
    </td>
</tr>
<% } else if (formItemType == FormItemType.RADIOBUTTON) { %>
<% List<String> options = FormItemManager.getItemOptionsList(formItem); %>
<% if (!options.isEmpty()) { %>
<tr>
    <td>
        <%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <input type="hidden" name="RADIOBUTTON<%=formWidgetId%>" id="<%= formItem.getItemName() %>"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               required="<%= formItem.isRequired() %>"/>
        <% for (int i = 0; i < options.size(); i++) { %>
        <% String option = options.get(i); %>
        <div>
            <input type="radio" name="<%= formItem.getItemName() %>" value="<%= option%>"
                   <% if (!prefilledItems.isEmpty() && prefilledItems.get(0).equals(option)){ %>checked="checked"<% } %>/><%= option%>
        </div>
        <% } %>
    </td>
</tr>
<% } %>
<% } else if (formItemType == FormItemType.RADIO_LIST) { %>
<%
    FormItem linkedItem = ServiceLocator.getPersistance().getFormItemById(formItem.getLinkedFormItemId());
    boolean isImageLinkedItem = linkedItem != null && linkedItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD;
    List<String> options = FormItemManager.getItemOptionsList(formItem);
    List<String> linkedFilledFormIds = null;
    List<String> linkedFilledFormItemIds = null;

    if (formItem.getFormItemName() == FormItemName.LINKED) {
        LinkedFormManager.SplitOptionResponse splitOptionResponse = LinkedFormManager.splitAndRestoreLinkedOptions(options, prefilledItems);
        options = splitOptionResponse.getOptions();
        linkedFilledFormIds = splitOptionResponse.getLinkedFilledFormsIds();
        linkedFilledFormItemIds = splitOptionResponse.getLinkedFilledFormItemsIds();
    }
%>
<tr>
    <td>
        <%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <% if (!options.isEmpty()) { %>
        <input type="hidden" name="RADIO_LIST<%=formWidgetId%>" id="<%= formItem.getItemName() %>"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               required="<%= formItem.isRequired() %>" linkedFormItemId="<%= formItem.getLinkedFormItemId() %>"
               formItemDisplayType="<%= formItem.getFormItemDisplayType() %>"/>
        <%
            for (int i = 0; i < options.size(); i++) {
                final int uniqueOptionId = new Random().nextInt();
                String option = options.get(i);
                String linkedFilledFormId = linkedFilledFormIds != null ? linkedFilledFormIds.get(i) : null;
                String linkedFilledFormItemId = linkedFilledFormItemIds != null ? linkedFilledFormItemIds.get(i) : null;

                LinkedFormManager.LinkedFormFileData linkedImageData = null;
                if (isImageLinkedItem) {
                    linkedImageData = LinkedFormManager.createLinkedFormFileData(Integer.parseInt(linkedFilledFormItemId));
                    if (linkedImageData == null) {
                        // if file not exists then let's skip this option.
                        continue;
                    }
                }
        %>
        <div>
            <input type="radio" name="<%= formItem.getItemName() %>" value="<%= option%>"
                   style="vertical-align:top;"
                   linkedFilledFormId="<%= linkedFilledFormId %>" linkedFilledFormItemId="<%= linkedFilledFormItemId %>"
                   id="uniqueOption<%= uniqueOptionId %>" class="radioListItem"
                   <% if (LinkedFormManager.isPrefilledItemsContainsFilledFormItemId(prefilledItems, linkedFilledFormId)) { %>checked="checked"<% } %>/>
            <label for="uniqueOption<%= uniqueOptionId %>">
                <% if (isImageLinkedItem) { %>
                <% request.setAttribute("linkedFormFileData", linkedImageData); %>
                <jsp:include page="linkedFormFile.jsp"/>
                <% } else { %>
                <%= option %>
                <% } %>
            </label>
            <% if (showFromEditRecord && linkedFilledFormId != null && !option.equals(LinkedFormManager.DELTED_RECORD_TEXT)) { %>
            <a class="showLinkedRecordLink"
               href="javascript:showEditRecordWindow(<%= linkedFilledFormId %>, <%= prefilledForm.getFilledFormId() %>)">
                <%= FormManager.getFormInternational().get("showLinkedRecordLink") %>
            </a>
            <% } %>
        </div>
        <% } %>
        <% } else if (formItem.getFormItemName() == FormItemName.LINKED) { %>
        <%= FormManager.getFormInternational().get("linkedItemWithoutValues") %>
        <% } %>
    </td>
</tr>
<% } else if (formItemType == FormItemType.SELECTION_LIST) { %>
<%
    FormItem linkedItem = ServiceLocator.getPersistance().getFormItemById(formItem.getLinkedFormItemId());
    boolean isImageLinkedItem = linkedItem != null && linkedItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD;
    List<String> options = FormItemManager.getItemOptionsList(formItem);
    List<String> linkedFilledFormsIds = null;
    List<String> linkedFilledFormItemIds = null;

    if (formItem.getFormItemName() == FormItemName.LINKED) {
        LinkedFormManager.SplitOptionResponse splitOptionResponse = LinkedFormManager.splitAndRestoreLinkedOptions(options, prefilledItems);
        options = splitOptionResponse.getOptions();
        linkedFilledFormsIds = splitOptionResponse.getLinkedFilledFormsIds();
        linkedFilledFormItemIds = splitOptionResponse.getLinkedFilledFormItemsIds();
    }
%>
<tr>
    <td>
        <%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <% if (!options.isEmpty()) { %>
        <input type="hidden" name="SELECTION_LIST<%=formWidgetId%>" id="<%= formItem.getItemName() %>"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               required="<%= formItem.isRequired() %>" linkedFormItemId="<%= formItem.getLinkedFormItemId() %>"
               formItemDisplayType="<%= formItem.getFormItemDisplayType() %>"/>
        <%
            for (int i = 0; i < options.size(); i++) {
                final int uniqueOptionId = new Random().nextInt();
                String option = options.get(i);
                String linkedFilledFormId = linkedFilledFormsIds != null ? linkedFilledFormsIds.get(i) : null;
                String linkedFilledFormItemId = linkedFilledFormItemIds != null ? linkedFilledFormItemIds.get(i) : null;

                LinkedFormManager.LinkedFormFileData linkedImageData = null;
                if (isImageLinkedItem) {
                    linkedImageData = LinkedFormManager.createLinkedFormFileData(Integer.parseInt(linkedFilledFormItemId));
                    if (linkedImageData == null) {
                        // if file not exists then let's skip this option.
                        continue;
                    }
                }
        %>
        <div>
            <input type="checkbox" name="<%= formItem.getItemName() %>" value="<%= option %>"
                   style="vertical-align:top;"
                   linkedFilledFormId="<%= linkedFilledFormId %>" linkedFilledFormItemId="<%= linkedFilledFormItemId %>"
                   id="uniqueOption<%= uniqueOptionId %>" class="selectionListItem"
                   <% if (LinkedFormManager.isPrefilledItemsContainsFilledFormItemId(prefilledItems, linkedFilledFormId)){ %>checked="checked"<% } %>/>
            <label for="uniqueOption<%= uniqueOptionId %>">
                <% if (isImageLinkedItem) { %>
                <% request.setAttribute("linkedFormFileData", linkedImageData); %>
                <jsp:include page="linkedFormFile.jsp"/>
                <% } else { %>
                <%= option %>
                <% } %>
            </label>
            <% if (showFromEditRecord && linkedFilledFormId != null && !option.equals(LinkedFormManager.DELTED_RECORD_TEXT)) { %>
            <a class="showLinkedRecordLink"
               href="javascript:showEditRecordWindow(<%= linkedFilledFormId %>, <%= prefilledForm.getFilledFormId() %>)">
                <%= FormManager.getFormInternational().get("showLinkedRecordLink") %>
            </a>
            <% } %>
        </div>
        <% } %>
        <% } else if (formItem.getFormItemName() == FormItemName.LINKED) { %>
        <%= FormManager.getFormInternational().get("linkedItemWithoutValues") %>
        <% } %>
    </td>
</tr>
<% } else if (formItemType == FormItemType.TEXT_AREA || formItemType == FormItemType.TEXT_AREA_DOUBLE_SIZE) { %>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <% final TextAreaSettings textAreaSettings = TextAreaSettings.getTextAreaSettings(formItemType); %>
    <td><textarea onfocus="trimTextArea(this);" class="formTextArea"
                  rows="<%= textAreaSettings.getRows() %>"
                  cols="<%= textAreaSettings.getCols() %>"
                  id="<%= formItem.getItemName() %>"
                  formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
                  formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
                  required="<%= formItem.isRequired() %>"
                  name="TEXT_AREA<%=formWidgetId%>"><%= !prefilledItems.isEmpty() ? prefilledItems.get(0) : "" %>
    </textarea></td>
</tr>
<% } else if (formItemType == FormItemType.TWO_TEXT_FIELDS) { %>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <input type="hidden" name="TWO_TEXT_FIELDS<%=formWidgetId%>"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               required="<%= formItem.isRequired() %>"
               id="<%= formItem.getItemName() %>">
        <input class="formTextInputHalfSize" type="text" name="<%= formItem.getItemName() %>" maxlength="255"
               <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)){ %>onkeyup="checkNumbersOnlyFilter(this);"<% } %>
               <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_INTEGER_NUMBERS)){ %>onkeyup="checkIntegerNumbersOnlyFilter(this);"<% } %>
               value="<%= !prefilledItems.isEmpty()? prefilledItems.get(0) : "" %>"/>
        <input class="formTextInputHalfSize" type="text" name="<%= formItem.getItemName() %>" maxlength="255"
               <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)){ %>onkeyup="checkNumbersOnlyFilter(this);"<% } %>
               <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_INTEGER_NUMBERS)){ %>onkeyup="checkIntegerNumbersOnlyFilter(this);"<% } %>
               value="<%= !prefilledItems.isEmpty() && prefilledItems.size() > 1 ? prefilledItems.get(1) : "" %>"/>
    </td>
</tr>
<%
} else if (formItemType == FormItemType.FILE_UPLOAD) {
    request.setAttribute("videoFileUpload", false);
    if (formItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
        request.setAttribute("filledForm", prefilledForm);
        request.setAttribute("formItem", formItem);
        request.setAttribute("widgetId", formWidgetId);
        request.setAttribute("videoFileUpload", true);
%>
<jsp:include page="formVideoFile.jsp"/>
<%
} else {
    request.setAttribute("hideBulkUploadButton", showFromEditRecord);
    request.setAttribute("formFileData", FormFileManager.createFormFileData(prefilledForm, formItem, formWidgetId));
%>
<jsp:include page="formFile.jsp"/>
<% }
} else if (formItemType == FormItemType.CHECKBOX) { %>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td><input id="<%= formItem.getItemName() %>" name="CHECKBOX<%=formWidgetId%>"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               required="<%= formItem.isRequired() %>"
               type="checkbox"
               <% if (!prefilledItems.isEmpty() && prefilledItems.get(0).equals("Checked")){ %>checked=""<% } %>/>
    </td>
</tr>
<% } else if (formItemType == FormItemType.SELECT || formItemType == FormItemType.TWO_PICK_LISTS || formItemType == FormItemType.THREE_PICK_LISTS || formItemType == FormItemType.FIVE_PICK_LISTS || formItemType == FormItemType.SINGLE_CHOICE_OPTION_LIST) { %>
<% if (formItem.getFormItemName() == FormItemName.PRODUCT_TAX_RATE) { %>
<tr>
    <td colspan="2">
        Select a tax rate item to apply taxes to your product. Note that taxes will be applied if a customer has the
        same state with you
    </td>
</tr>
<% } %>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <input type="hidden" name="<%=formItemType.toString() + formWidgetId%>"
           formItemName="<%= formItem.getFormItemName().toString() %>" formItemId="<%= formItem.getFormItemId() %>"
           position="<%= formItem.getPosition() %>" formItemText="<%= formItem.getItemName() %>"
           required="<%= formItem.isRequired() %>" linkedFormItemId="<%= formItem.getLinkedFormItemId() %>"
           formItemDisplayType="<%= formItem.getFormItemDisplayType() %>"/>
    <input type="hidden" value="<%= formItemType.getPickListCount() %>"/>
    <td>
        <% for (int i = 1; i <= formItemType.getPickListCount(); i++) { %>
        <% String selectedLinkedFilledFormId = null; %>
        <select class="formSelect" id="<%= formItem.getItemName() %><%= i %>"
                <% if (formItemType == FormItemType.SINGLE_CHOICE_OPTION_LIST) { %>size="5"<% } %>
                <% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK) && i == 1) { %>
                onchange="updateDays(this);changeLinkedRecordLinkOnReslect(<%= prefilledForm != null ? prefilledForm.getFilledFormId() : null %>, this);"
                <% } else if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK) && i == 3) { %>
                onchange="updateDaysByYear(this);changeLinkedRecordLinkOnReslect(<%= prefilledForm != null ? prefilledForm.getFilledFormId() : null %>, this);"
                <% } else { %>
                onchange="changeLinkedRecordLinkOnReslect(<%= prefilledForm != null ? prefilledForm.getFilledFormId() : null %>, this);"
                <% } %>>
            <% if (formItemType != FormItemType.SINGLE_CHOICE_OPTION_LIST && formItem.getFormItemName() != FormItemName.LINKED
                    && formItem.getFormItemName() != FormItemName.SUBSCRIPTION_BILLING_PERIOD) { %>
            <option value=""
                    <% if (prefilledItems.isEmpty()){ %>selected="true"<% } %>><%= FormItemManager.getPickListDefaultOption(formItem.getFormItemName(), i) %>
            </option>
            <% } %>

            <% if (formItem.getFormItemName() == FormItemName.SUBSCRIPTION_BILLING_PERIOD) { %>
            <% for (SubscriptionTimeType subscriptionTimeType : SubscriptionTimeType.values()) { %>
            <option value="<%= subscriptionTimeType %>"
                    <% if (!prefilledItems.isEmpty() && prefilledItems.size() > (i - 1) && prefilledItems.get(i-1).equals(subscriptionTimeType.toString()) || (prefilledItems.isEmpty() && subscriptionTimeType == SubscriptionTimeType.INDEFINITE)) { %>selected="true"<% } %>>
                <%= subscriptionTimeType.getText() %>
            </option>
            <% } %>
            <% } else if (formItem.getFormItemName() == FormItemName.PRODUCT_TAX_RATE) { %>
            <% List<String> taxRatesIds = FormItemManager.getItemOptionListForPickList(formItem).get(i); %>
            <% List<String> taxRatesNames = FormItemManager.getItemOptionListForPickList(formItem).get(i + 1); %>
            <% for (int taxRateIndex = 0; taxRateIndex < taxRatesIds.size(); taxRateIndex++) { %>
            <option value="<%= taxRatesIds.get(taxRateIndex) %>"
                    <% if (!prefilledItems.isEmpty() && prefilledItems.size() > (i - 1) && prefilledItems.get(i-1).equals(taxRatesIds.get(taxRateIndex))) { %>selected="true"<% } %>>
                <%= taxRatesNames.get(taxRateIndex) %>
            </option>
            <% } %>
            <% } else if (formItem.getFormItemName() == FormItemName.STATE) { %>
            <% List<String> stateCodes = FormItemManager.getItemOptionListForPickList(formItem).get(i); %>
            <% List<String> stateNames = FormItemManager.getItemOptionListForPickList(formItem).get(i + 1); %>
            <% for (int stateIndex = 0; stateIndex < stateCodes.size(); stateIndex++) { %>
            <option value="<%= stateCodes.get(stateIndex) %>"
                    <% if (!prefilledItems.isEmpty() && prefilledItems.size() > (i - 1) && prefilledItems.get(i-1).equals(stateCodes.get(stateIndex))) { %>selected="true"<% } %>>
                <%= stateNames.get(stateIndex) %>
            </option>
            <% } %>
            <% } else { %>
            <%
                List<String> options = FormItemManager.getItemOptionListForPickList(formItem).get(i);
                FormItem linkedItem = ServiceLocator.getPersistance().getFormItemById(formItem.getLinkedFormItemId());
                boolean isImageLinkedItem = linkedItem != null ? linkedItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD : false;
                List<String> linkedFilledFormsIds = null;
                List<String> linkedFilledFormsItemIds = null;

                if (formItem.getFormItemName() == FormItemName.LINKED) {
                    LinkedFormManager.SplitOptionResponse splitOptionResponse = LinkedFormManager.splitAndRestoreLinkedOptions(options, prefilledItems);
                    options = splitOptionResponse.getOptions();
                    linkedFilledFormsIds = splitOptionResponse.getLinkedFilledFormsIds();
                    linkedFilledFormsItemIds = splitOptionResponse.getLinkedFilledFormItemsIds();
                }
            %>
            <% if (!options.isEmpty()) { %>
            <%
                for (int j = 0; j < options.size(); j++) {
                    String option = options.get(j);

                    String linkedFilledFormId = linkedFilledFormsIds != null ? linkedFilledFormsIds.get(j) : null;
                    String linkedFilledFormItemId = linkedFilledFormsItemIds != null ? linkedFilledFormsItemIds.get(j) : null;


                    boolean gotPrefilledRecord = FilledFormManager.isGotPrefilledRecordForSelect(prefilledItems, formItem, i,
                            formItem.getFormItemName() == FormItemName.LINKED ? linkedFilledFormId : option);


                    if (linkedFilledFormsIds != null && !linkedFilledFormsIds.isEmpty() && selectedLinkedFilledFormId == null) {
                        selectedLinkedFilledFormId = gotPrefilledRecord ? linkedFilledFormsIds.get(j) : null;
                    }

                    LinkedFormManager.LinkedFormFileData linkedImageData = null;
                    if (isImageLinkedItem) {
                        linkedImageData = LinkedFormManager.createLinkedFormFileData(Integer.parseInt(linkedFilledFormItemId));
                        if (linkedImageData == null) {
                            // if file not exists then let's skip this option.
                            continue;
                        }
                    }
            %>
            <option value="<%= option %>" linkedFilledFormId="<%= linkedFilledFormId %>"
                    linkedFilledFormItemId="<%= linkedFilledFormItemId %>"
                    <% if (gotPrefilledRecord) { %>selected="true"<% } %>>
                <%= isImageLinkedItem ? linkedImageData.getFileName() : option %>
            </option>
            <% } %>
            <% } %>

            <% } %>
        </select>

        <% if (showFromEditRecord && selectedLinkedFilledFormId != null) { %>
        <a class="showLinkedRecordLink"
           href="javascript:showEditRecordWindow(<%= selectedLinkedFilledFormId %>, <%= prefilledForm.getFilledFormId() %>)">
            <%= FormManager.getFormInternational().get("showLinkedRecordLink") %>
        </a>
        <% } %>
        <%}%>
    </td>
</tr>
<% } else if (formItemType == FormItemType.MULITSELECT) { %>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <input type="hidden" name="MULITSELECT<%=formWidgetId%>" formItemName="<%= formItem.getFormItemName().toString() %>"
           formItemId="<%= formItem.getFormItemId() %>" position="<%= formItem.getPosition() %>"
           formItemText="<%= formItem.getItemName() %>" required="<%= formItem.isRequired() %>"/>
    <td>
        <select class="formMultipleSelect" size="6" multiple="multiple" id="<%= formItem.getItemName() %>">
            <% List<String> options = FormItemManager.getItemOptionsList(formItem); %>
            <% if (!options.isEmpty()) { %>
            <% for (String option : options) { %>
            <option value="<%= option%>"
                    <% if (!prefilledItems.isEmpty()&& prefilledItems.contains(option)){ %>selected="true"<% } %>><%= option%>
            </option>
            <% } %>
            <% } %>
        </select>
    </td>
</tr>
<% } else if (formItemType == FormItemType.PICK_LIST_AND_TEXT_FIELD) { %>
<tr>
    <td><%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <select class="formSelect" id="<%= formItem.getItemName() %>_SELECT">
            <option value=""
                    <%  if (prefilledItems.isEmpty()){ %>selected="selected"<% } %>><%= FormManager.getFormInternational().get(formItem.getFormItemName().toString() + "_DO") %>
            </option>
            <% List<String> options = FormItemManager.getItemOptionsList(formItem); %>
            <% if (!options.isEmpty()) { %>
            <% for (String option : options) { %>
            <option value="<%= option%>" <%=  !prefilledItems.isEmpty() && prefilledItems.get(0).equals(option) ? "selected='selected'" : "" %>><%= option%>
            </option>
            <% } %>
            <% } %>
        </select>
        <input class="formTextInput" type="text" id="<%= formItem.getItemName() %>" maxlength="255"
               value="<%=  !prefilledItems.isEmpty() && prefilledItems.size() >= 2 ? prefilledItems.get(1) : "" %>"
               name="PICK_LIST_AND_TEXT_FIELD<%=formWidgetId%>"
               formItemName="<%= formItem.getFormItemName().toString() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               position="<%= formItem.getPosition() %>" required="<%= formItem.isRequired() %>"/>
    </td>
</tr>
<% } else if (formItemType == FormItemType.ACCESS_GROUPS) { %>
<% final Site site = ServiceLocator.getPersistance().getSite(formSiteId); %>
<% final List<Group> siteGroups = site != null ? site.getOwnGroups() : new ArrayList<Group>(); %>
<tr>
    <td>
        <%= formItem.getItemName() %><%= formItem.isRequired() ? "&nbsp;*" : "" %>
    </td>
    <td>
        <% if (!siteGroups.isEmpty()) { %>
        <input type="hidden" name="ACCESS_GROUPS<%= formWidgetId %>" id="<%= formItem.getItemName() %>"
               formItemName="<%= formItem.getFormItemName().toString() %>" position="<%= formItem.getPosition() %>"
               formItemId="<%= formItem.getFormItemId() %>" formItemText="<%= formItem.getItemName() %>"
               required="<%= formItem.isRequired() %>"/>
        <% for (Group group : siteGroups) { %>
        <% final int uniqueOptionId = new Random().nextInt(); %>
        <% FilledFormManager.GroupsPrefilledRecord groupsPrefilledRecord = FilledFormManager.getGroupsPrefilledRecord(
                prefilledItems, group.getGroupId());%>
        <div class="formGroupDiv">
            <input type="checkbox" name="<%= formItem.getItemName() %>" value="<%= group.getGroupId() %>"
                   id="uniqueOption<%= uniqueOptionId %>" class="accessGroupItem"
                   onclick="formAccessGroupsCheckboxClick(this);"
                   <% if (groupsPrefilledRecord != null){ %>checked="checked"<% } %>/>
            <label for="uniqueOption<%= uniqueOptionId %>">
                <%= group.getName() %>&nbsp;<%= FormManager.getFormInternational().get("group") %>
            </label>

            <% final int uniqueCheckboxId = new Random().nextInt(); %>
            <div class="formsLimitedTimeDiv">
                <input type="checkbox" id="limitedTimeCheckbox<%= uniqueCheckboxId  %>" class="limitedTimeCheckbox"
                       <% if (groupsPrefilledRecord == null) { %>disabled="disabled"<% } %>
                       <% if (groupsPrefilledRecord != null && !groupsPrefilledRecord.getGroupTimePeriod().equals(SubscriptionTimeType.INDEFINITE.toString())) { %>checked="checked"<% } %>
                       onclick="formAccessGroupsCheckboxLimitedTimeClick(this);"/>
                <label for="limitedTimeCheckbox<%= uniqueCheckboxId  %>"><%= FormManager.getFormInternational().get("limitedTimeText") %>
                </label>
                <select class="formsLimitedTimeSelect"
                        <% if (groupsPrefilledRecord != null && groupsPrefilledRecord.getGroupTimePeriod().equals(SubscriptionTimeType.INDEFINITE.toString())
                 || groupsPrefilledRecord == null){ %>disabled="disabled"<% } %>>
                    <% for (SubscriptionTimeType subscriptionTimeType : SubscriptionTimeType.values()) { %>
                    <% if (subscriptionTimeType != SubscriptionTimeType.INDEFINITE) { %>
                    <option value="<%= subscriptionTimeType %>" <% if (groupsPrefilledRecord != null &&
                     groupsPrefilledRecord.getGroupTimePeriod().equals(subscriptionTimeType.toString())) { %>selected="selected"<% } %>>
                        <%= subscriptionTimeType.getText() %>
                    </option>
                    <% } %>
                    <% } %>
                </select>
            </div>
        </div>
        <% } %>
        <% } %>
    </td>
</tr>
<% } %>

<% String instruction = StringUtil.getEmptyOrString(formItem.getInstruction()).trim();
    if (!instruction.isEmpty() && !instruction.replace("\n", "").isEmpty()) {
        if (formItem.getFormItemName() == FormItemName.HEADER) { %>
<tr>
    <td colspan="2">
        <div class="formHeader"><%= HtmlUtil.replaceNewLineByBr(formItem.getInstruction()) %>
        </div>
    </td>
</tr>
<% } else { %>
<tr>
    <td colspan="2">
        <div class="formInstruction"><%= HtmlUtil.replaceNewLineByBr(formItem.getInstruction()) %>
        </div>
    </td>
</tr>
<% }
} %>

<% if (formItem.getFormItemName() == FormItemName.LINE_HR) { %>
<tr>
    <td colspan="2">
        <hr>
    </td>
</tr>
<% } %>
<%
    if (formItem.getFormItemName() == FormItemName.PAYMENT_AREA) {
        request.setAttribute("paymentSettings", new PaymentSettings(formData));
        request.setAttribute("paymentRequired", formItem.isRequired());
%>
<tr>
    <td colspan="2">
        <jsp:include page="/payment/childSiteRegistrationPaymentInfo.jsp" flush="true"/>
    </td>
</tr>
<% } %>

<% } %>
<%----------------------------------------------------hidden fields---------------------------------------------------%>
<input type="hidden" id="formId<%= formWidgetId %>" value="<%= formData.getFormId() %>">
<input type="hidden" id="totalPageBreaks<%= formWidgetId %>" value="<%= FormManager.getTotalPageBreaks(formData) %>">
<input type="hidden" id="pageBreaksToPass<%= formWidgetId %>" value="<%= formPageBreaksToPass %>">
<input type="hidden" id="filledFormToUpdateId<%= formWidgetId %>"
       value="<%= filledFormToUpdateId != null ? filledFormToUpdateId : ""%>">
<% } %>
<input type="hidden" id="formWidgetId" value="<%= formWidgetId %>">
<input type="hidden" id="prefilledFormId" value="<%= prefilledForm != null ? prefilledForm.getFilledFormId() : "0"%>">
<%----------------------------------------------------hidden fields---------------------------------------------------%>