<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.presentation.form.filledForms.ShowEditFilledFormService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<international:part part="manageFormRecords"/>
<%
    final ShowEditFilledFormService service = (ShowEditFilledFormService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <input id="showMoreText" type="hidden" value="<international:get name="showMore"/>">
    <h1><international:get name="editRecordHeader"/></h1>

    <div class="emptyError" id="errors"></div>

    <% if (!StringUtil.isNullOrEmpty(service.getFilledForm().getFormDescription())) { %>
    <div class="registrationHeader" id="formDescription">
        <h2 style="display:inline;margin-left:0;"><international:get name="subHeader"/></h2>
        &nbsp;<%=  HtmlUtil.removeParagraphs(service.getFilledForm().getFormDescription()) %>
    </div>
    <% } %>

    <div id="editVisitorDiv">
        <table class="registrationTable" id="editRegisteredVisitorTable" width="95%">
            <tr><td width="30%">&nbsp;</td><td width="70%">&nbsp;</td></tr>
            <jsp:include page="widgetForm.jsp" flush="true"/>
        </table>
    </div>

    <br>

    <div align="right">
        <input type="button" class="but_w73" value="Save"
               id="windowSave"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';"
               onclick="saveFilledFormEdit(<%= service.getFilledForm().getFilledFormId() %>, <%= service.getUniqueWidgetId() %>, false, <%= service.getLinkedFilledFormId() %>);">
        <input type="button" class="but_w73" value="Close"
               id="windowCancel"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();">
    </div>
</div>