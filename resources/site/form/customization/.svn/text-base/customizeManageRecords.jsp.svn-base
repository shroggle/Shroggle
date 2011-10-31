<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.logic.form.FormItemNameManager" %>
<%@ page import="com.shroggle.logic.form.customization.CustomizeManageRecordsFieldManager" %>
<%@ page import="com.shroggle.logic.form.customization.CustomizeManageRecordsManager" %>
<%@ page import="com.shroggle.presentation.form.customization.ShowCustomizeManageRecordsWindowService" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="customizeManageRecords"/>
<%--
    @author Balakirev Anatoliy
--%>
<% ShowCustomizeManageRecordsWindowService service = (ShowCustomizeManageRecordsWindowService) request.getAttribute("service");
    final CustomizeManageRecordsManager customizeManageRecordsManager = service.getCustomizeManageRecordsManager(); %>
<div class="windowOneColumn">
    <input type="hidden" id="formId" value="<%= customizeManageRecordsManager.getFormId() %>">
    <input type="hidden" id="tooManyFieldsErrorMessage" value="<international:get name="tooManyFieldsErrorMessage"/>">
    <input type="hidden" id="unableToIncludeErrorMessage"
           value="<international:get name="unableToIncludeErrorMessage"/>">
    <input type="hidden" id="MAX_FIELDS_QUANTITY"
           value="<%= CustomizeManageRecordsFieldManager.getMaxFieldsQuantity() %>">

    <h1><%= customizeManageRecordsManager.getFormName() %>.&nbsp;<international:get
            name="manageRecordsViewCustomization"/>.</h1>

    <div style="height:1.3em;font-weight:normal;overflow:hidden">
        <h2 style="display:inline;margin-left:0;"><international:get name="formDescription"/></h2>
        &nbsp;<%=  HtmlUtil.limitName(HtmlUtil.removeAllTags(customizeManageRecordsManager.getFormDescription()), 65) %>
    </div>

    <div id="errors" class="emptyError"></div>

    <div style="margin-bottom:15px;">
        <international:get name="instruction"/>
    </div>
    <table class="customizeDataExportTable tbl_blog" style="border-bottom:none;">
        <thead>
        <tr>
            <td>
                <international:get name="show"/>
            </td>
            <td>
                <international:get name="order"/>
            </td>
            <td>
                <international:get name="formFieldName"/>
            </td>
        </tr>
        </thead>
    </table>
    <table id="customizeManageRecordsTable" class="customizeDataExportTable tbl_blog">
        <% for (CustomizeManageRecordsFieldManager field : customizeManageRecordsManager.getFields()) { %>
        <tr id="<%= field.getFormItemId() %>">
            <td>
                <input id="include<%= field.getFormItemId() %>" type="checkbox"
                    <% FormItemName fieldFormItemName = field.getFormItemName();
                    if (FormItemNameManager.showFieldOnManageRecords(fieldFormItemName)) { %>
                       onchange="customizeManageRecords.checkIncludedFieldsQuantity(this);"
                    <% if(field.isInclude()) {%> <%  %>checked<% } } else { %>
                       disabled<%--onchange="customizeManageRecords.showUnableToIncludeMessage(this);"--%><% } %>>
            </td>
            <td>
                <% request.setAttribute("first", field.isFirst());
                    request.setAttribute("last", field.isLast()); %>
                <jsp:include page="../../upDownArrows.jsp" flush="true"/>
            </td>
            <td>
                <%= field.getItemName() %>
            </td>
        </tr>
        <% } %>
    </table>
    <div align="right" style="margin-top:25px;">
        <input type="button" class="but_w130" value="<international:get name="save"/>"
               id="windowSave"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="customizeManageRecords.save();"/>
        <input type="button" class="but_w130" value="<international:get name="cancel"/>"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();"/>
    </div>
</div>