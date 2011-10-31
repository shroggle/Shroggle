<%@ page import="com.shroggle.entity.FilledFormItem" %>
<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.presentation.form.filledForms.ShowFilledFormService" %>
<%@ page import="com.shroggle.logic.form.FilledFormItemManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<international:part part="manageRegistrants"/>
<% final ShowFilledFormService service = (ShowFilledFormService) request.getAttribute("service"); %>
<div class="windowOneColumn">
    <div id="editVisitorDiv" style="overflow-x:hidden;">
        <%
            for (FilledFormItem filledFormItem :  FilledFormManager.sortByPositionFilledFormItems(service.getFilledForm().getFilledFormItems())) {
                if (filledFormItem.getFormItemName().equals(FormItemName.REGISTRATION_PASSWORD) ||
                        filledFormItem.getFormItemName().equals(FormItemName.REGISTRATION_PASSWORD_RETYPE)) {
                    continue;
                }
        %>
        <span style="font-weight:bold;"><%= filledFormItem.getItemName() %>:</span>
        <%= new FilledFormItemManager(filledFormItem).getFormattedValue(null) %>
        <br/>
        <% } %>
    </div>
    <br>

    <div align="right">
        <input type="button" class="but_w73" value="Close"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();">
    </div>
</div>