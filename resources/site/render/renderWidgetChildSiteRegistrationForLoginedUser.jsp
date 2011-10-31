<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.entity.FilledForm" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.shroggle.entity.FilledFormItem" %>
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.form.FormData" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="childSiteRegistration"/>
<%--
 @author Balakirev Anatoliy
--%>

<%
    List<FilledForm> filledForms = (List<FilledForm>) request.getAttribute("filledForms");
    filledForms = filledForms != null ? filledForms : new ArrayList<FilledForm>();
    final FormData form = (FormData) request.getAttribute("form");
    final Integer widgetId = (Integer) request.getAttribute("widgetId");
    final String networkName = form != null ? form.getFormName() : "";
%>
<div class="childSiteRegistrationBlock" id="childSiteRegistratonFormList<%= widgetId %>">
    <input type="hidden" id="deleteConfirmation<%= widgetId %>"
           value="<international:get name="deleteConfirmation"> <international:param value="<%= networkName %>"/></international:get>">
    <input type="hidden" id="optOutConfirmation<%= widgetId %>"
           value="<international:get name="optOutConfirmation"> <international:param value="<%= networkName %>"/></international:get>">

    <div style="font-weight:bold;margin-bottom:25px;text-align:center;">
        <international:get name="thankYouForRegistering"/>
    </div>
    <table style="width:100%;text-align:center;">
        <td style="text-align:center;">
            <international:get name="siteName"/>
        </td>
        <td style="text-align:center;">
            <international:get name="editForm"/>
        </td>
        <td style="text-align:center;">
            <span title="<international:get name="optOutTitle"> <international:param value="<%= networkName %>"/></international:get>">
                <international:get name="optOut"/>&nbsp;</span>
            <span class="inform_mark" style="cursor: pointer;" onclick="aboutOptOut();"
                  onmouseover="this.className='inform_mark_Over';" onmouseout="this.className='inform_mark';">
                &nbsp;
            </span>
        </td>
        <td style="text-align:center;">
            <span title="<international:get name="deleteTitle"/>"><international:get name="delete"/></span>
        </td>
        <td style="text-align:center;">
        </td>
        <%
            for (FilledForm filledForm : filledForms) {
                FilledFormItem siteNameItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_PAGE_SITE_NAME);
                final String siteName = siteNameItem != null ? StringUtil.getEmptyOrString(siteNameItem.getValues().get(0)) : "";
        %>
        <tr>
            <td style="text-align:center;">
                <%= siteName %>
            </td>
            <td style="text-align:center;">
                <a href="javascript:childSiteRegistrationEdit(<%= widgetId %>, <%= filledForm.getFilledFormId() %>);"><international:get
                        name="edit"/></a>
            </td>
            <td style="text-align:center;">
                <a <% if (filledForm.getChildSiteSettingsId() == null) { %>
                        href="javascript:void(0);" style="color:gray;"
                        <% } else {%>
                        href="javascript:childSiteRegistrationOptOut(<%= widgetId %>, <%= filledForm.getFilledFormId() %>);"<% } %>><international:get
                        name="optOut"/></a>
            </td>
            <td style="text-align:center;">
                <a href="javascript:childSiteRegistrationDelete(<%= widgetId %>, <%= filledForm.getFilledFormId() %>);"><international:get
                        name="delete"/></a>
            </td>
            <td style="text-align:center;">
                <% if (filledForm.getChildSiteSettingsId() == null) { %>
                <international:get name="notInNetwork"/>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
    <div style="margin-top:15px;text-align:center;">
        <a href="javascript:logout();"><international:get name="fillTheFormAgain"/></a>
        &nbsp;&nbsp;&nbsp;<a href="javascript:logout();"><international:get name="signOut"/></a>
    </div>
</div>

<div id="aboutOptOutText" style="display:none">
    <div class="windowOneColumn">
        <div style="overflow:auto;width:520px;height:70px;padding:10px; text-align:left;">
            <international:get name="aboutOptOut">
                <international:param value="<%= networkName %>"/>
            </international:get>
        </div>
        <div align="center" style="margin-bottom:10px;">
            <input type="button" value="Close" onclick="closeConfigureWidgetDiv();"/>
        </div>
    </div>
</div>