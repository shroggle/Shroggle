<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.FilledForm" %>
<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.persistance.Persistance" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.FormType" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageNetworkRegistrants"/>

<%
    final List<FilledForm> filledForms = (List<FilledForm>) request.getAttribute("filledForms");
    final Integer parentSiteId = (Integer) request.getAttribute("parentSiteId");
    final Persistance persistance = ServiceLocator.getPersistance();
%>
<input type="hidden" id="parentSiteId" value="<%= parentSiteId %>">
<table class="tbl_blog" style="width:100%">
    <thead style="cursor:default">
    <tr>
        <td>
            <span style="text-align: center;"><international:get name="firstName"/></span>
        </td>
        <td>
            <span style="text-align: center;"><international:get name="lastName"/></span>
        </td>
        <td>
            <span style="text-align: center;"><international:get name="userId"/></span>
        </td>
        <td>
            <span style="text-align: center;"><international:get name="created"/></span>
        </td>
        <td>
            <span style="text-align: center;"><international:get name="updated"/></span>
        </td>
        <td>
            <span style="text-align: center;"><international:get name="formName"/></span>
        </td>
    </tr>
    </thead>
    <tbody>
    <% if (filledForms.isEmpty()) { %>
    <tr>
        <td colspan="6">
            <international:get name="noRegistrants"/>
        </td>
    </tr>
    <% } %>
    <%
        int i = 0;
        for (FilledForm form : filledForms) {
            String firstName = FilledFormManager.getFilledFormItemValueByItemName(form, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN"));
            String lastName = FilledFormManager.getFilledFormItemValueByItemName(form, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN"));
            /*!!!important*/
            if (form.getUser() == null) {
                continue;
            }
            String email = form.getUser().getEmail();
    %>
    <tr <% if(i % 2 == 0) { %>class="odd"<% } %>>
        <td>
            <div style="text-align: center;">
                <a href="javascript:editChildSiteRegistrationWindow(<%= form.getUser().getUserId() %>, <%= form.getFilledFormId() %>, <%= parentSiteId %>, false);">
                    <%= StringUtil.isNullOrEmpty(firstName) ? "&lt;not specified&gt;" : firstName %>
                </a>
            </div>
        </td>
        <td>
            <div style="text-align: center;">
                <a href="javascript:editChildSiteRegistrationWindow(<%= form.getUser().getUserId() %>, <%= form.getFilledFormId() %>, <%= parentSiteId %>, false);">
                    <%= StringUtil.isNullOrEmpty(lastName) ? "&lt;not specified&gt;" : lastName %>
                </a>
            </div>
        </td>
        <td>
            <div style="text-align: center;">
                <a href="javascript:editChildSiteRegistrationWindow(<%= form.getUser().getUserId() %>, <%= form.getFilledFormId() %>, <%= parentSiteId %>, true);">
                    <%= email %>
                </a>
            </div>
        </td>
        <td>
            <div style="text-align: center;">
                <%= DateUtil.toDateStrWithSeparators(form.getFillDate()) %>
            </div>
        </td>
        <td>
            <div style="text-align: center;">
                <%= DateUtil.toDateStrWithSeparators(form.getUpdatedDate() == null ? form.getFillDate() : form.getUpdatedDate()) %>
            </div>
        </td>
        <td>
            <div style="text-align: center;">
                <a href="javascript:manageItems.showItemSettings({itemId:<%= form.getFormId() %>, itemType: '<%= FormType.CHILD_SITE_REGISTRATION %>'});">
                    <span id="dashboardNetworkName<%= form.getFormId() %>">
                        <%= persistance.getChildSiteRegistrationById(form.getFormId()).getName() %>
                    </span>
                </a>
            </div>
        </td>
    </tr>
    <%
            i++;
        }
    %>
    </tbody>
</table>
<bR clear="all"> &nbsp;