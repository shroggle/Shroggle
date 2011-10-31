<%@ page import="com.shroggle.entity.DraftCustomForm" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.logic.form.FormData" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureCustomForm"/>
<style type="text/css">
    #test {
        background-color: black;
        width: 300px;
        height: 300px;
    }

    #divWrapWithVerticalCenteredContent {
        display: table-cell;
        vertical-align: middle;
    }

    #divWithVerticalCenteredContent {
        margin-top: expression(((outer.offsetHeight/2)-parseInt(offsetHeight)/2) < 0 ? "0" : (outer.offsetHeight/2)-(parseInt(offsetHeight)/2) +'px');
    }
</style>

<%
    final int widgetId = (Integer) request.getAttribute("widgetId");
    final FormData customForm = (FormData) request.getAttribute("form");

    final boolean showFromAddRecord = request.getParameter("showFromAddRecord") != null && Boolean.parseBoolean(request.getParameter("showFromAddRecord"));
    final int pageBreaksToPass = request.getParameter("pageBreaksToPass") != null ? Integer.parseInt(request.getParameter("pageBreaksToPass")) : 0;
    final int totalPageBreaks = FormManager.getTotalPageBreaks(customForm);
%>
<div class="customFormBlock" id="customFormBlock<%= widgetId %>">
    <input id="registrationErrorsEmptyCode<%=widgetId%>" type="hidden"
           value="<international:get name="emptyCode"/>">
    <input id="successfulSubmit<%=widgetId%>" type="hidden"
           value="<international:get name="successfulSubmit"/>">

    <% if (showFromAddRecord) { %>
    <div id="customFormErrorBlock<%= widgetId %>"></div>
    <% } %>

    <% if (!StringUtil.isNullOrEmpty(customForm.getDescription()) && customForm.isShowDescription()) { %>
    <div class="customFormHeader">
        <%=  customForm.getDescription() %>
    </div>
    <% } %>

    <table class="customFormTable form<%= widgetId %>">
        <tbody>
        <jsp:include page="/site/render/widgetForm.jsp" flush="true"/>
        <% if (!showFromAddRecord && pageBreaksToPass == 0) { %>
        <tr>
            <td colspan="2">
                <div id="securityCodeText" class="customFormSercurityCodeText"><international:get
                        name="securityCode"/></div>
            </td>
        </tr>
        <tr class="trWithVerificationWord">
            <td>
                <label><img src="/noBotImage.action?noBotPrefix=customForm<%= widgetId %>&noCache=<%= Math.random() %>"
                            alt="Verification code" class="bot_code" id="noBotImage<%= widgetId %>"></label>
            </td>
            <td>
                <input type="text" class="formTextInput" id="customFormVerificationCode<%= widgetId %>" maxlength="255"><br/><br/>
            </td>
        </tr>
        <% } %>
        <tr>
            <td class="customFormSubmitBlockTd" colspan="2">
                <div class="customFormSubmitBlock" <% if (showFromAddRecord) { %>style="display:none;"<% } %>>
                    <% if (!showFromAddRecord) { %>
                    <div id="customFormErrorBlock<%= widgetId %>"></div>
                    <% } %>

                    <% if (pageBreaksToPass != 0) { %>
                    <input value="<international:get name="back"/>" id="back<%= widgetId %>" type="button"
                           class="formBackButton"
                           onclick="goBackOnForms(<%= widgetId %>, <%= customForm.getFormId() %>, <%= showFromAddRecord %>, '<%= ItemType.CUSTOM_FORM %>');"/>
                    <% } %>
                    <input type="button" id="submit<%= widgetId %>"
                           <% if (pageBreaksToPass == totalPageBreaks) { %>value="<international:get name="submit"/>"
                           <% } else { %>value="<international:get name="next"/>"<% } %>
                           onclick="sumbitCustomForm(<%= widgetId %>, <%= showFromAddRecord %>);"/>
                    <input type="button" value="<international:get name="reset"/>" id="reset<%= widgetId %>"
                           onclick="resetForm(<%= widgetId %>, <%= customForm.getFormId() %>, <%= showFromAddRecord %>, '<%= ItemType.CUSTOM_FORM %>');">

                    <div id="formsLoadingMessageDiv<%= widgetId %>" style="display:none;">
                        <img alt="Loading text editor..." src="/images/ajax-loader.gif"
                             style="vertical-align:middle;padding: 0 3px 0 0"/>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>


</div>

