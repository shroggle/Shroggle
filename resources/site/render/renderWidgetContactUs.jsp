<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.logic.form.FormData" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="noBot" tagdir="/WEB-INF/tags/nobot" %>
<international:part part="widgetContactUs"/>
<script type="text/javascript" src="/dwr/interface/SendContactUsInfoService.js"></script>

<%
    final int widgetId = (Integer) request.getAttribute("widgetId");
    final FormData contactUs = (FormData) request.getAttribute("form");
    final String siteName = StringUtil.getEmptyOrString(request.getAttribute("siteName"));

    final boolean showFromAddRecord = request.getParameter("showFromAddRecord") != null && Boolean.parseBoolean(request.getParameter("showFromAddRecord"));
    final int pageBreaksToPass = request.getParameter("pageBreaksToPass") != null ? Integer.parseInt(request.getParameter("pageBreaksToPass")) : 0;
    final int totalPageBreaks = FormManager.getTotalPageBreaks(contactUs);
%>
<div id="contactUsBlock<%= widgetId %>">
    <div id="messageSent<%= widgetId %>" style="display:none;">
        <b style="color:green"><international:get name="messageSent"/>&nbsp;<%= siteName %></b>
    </div>
    <div id="form<%= widgetId%>" class="contactUsBlock">
        <input type="hidden" value="<international:get name="emptyCode"/>" id="emptyCode<%= widgetId %>">
        <input type="hidden" value="<international:get name="wrongCode"/>" id="wrongCode<%= widgetId %>">
        <input type="hidden" value="<%= siteName %>" id="siteName<%=  widgetId %>">
        <input type="hidden" value="<%= contactUs.getFormId() %>" id="contactUsFormId<%= widgetId %>">
        <input type="hidden" value="<%= request.getAttribute("siteShowOption").toString() %>" id="siteShowOption<%= widgetId %>">

        <% if (showFromAddRecord) { %>
        <div id="contactUsErrorBlock<%= widgetId %>"></div>
        <% } %>

        <% if (contactUs.isShowDescription()) { %>
        <H3 class="contactUsHeader">
            <%= contactUs.getDescription() %>
        </H3>
        <br/>
        <% } %>

        <table class="contactUsTable form<%= widgetId %>">
            <tbody>
            <jsp:include page="widgetForm.jsp" flush="true"/>
            <% if (!showFromAddRecord && pageBreaksToPass == 0) { %>
            <tr>
                <td colspan="2">
                    <div id="securityCodeText" class="contactUsSecurityCodeText"><international:get
                            name="securityCode"/></div>
                </td>
            </tr>
            <tr class="trWithVerificationWord">
                <td>
                    <label><img
                            src="/noBotImage.action?noBotPrefix=contactUs<%= widgetId %>&noCache=<%= Math.random() %>"
                            alt="Verification code" class="bot_code" id="noBotImage<%= widgetId %>"></label>
                </td>
                <td>
                    <input type="text" class="formTextInput" id="verificationCode<%= widgetId %>" maxlength="255">
                </td>
            </tr>
            <% } %>
            <tr>
                <td class="contactUsSubmitBlock" colspan="2">
                    <div class="contactUsSubmitBlock" <% if (showFromAddRecord) { %>style="display:none;"<% } %>>
                        <% if (!showFromAddRecord) { %>
                        <div id="contactUsErrorBlock<%= widgetId %>"></div>
                        <% } %>

                        <% if (pageBreaksToPass != 0) { %>
                        <input value="<international:get name="back"/>"
                               id="back<%= widgetId %>" type="button" class="formBackButton"
                               onclick="goBackOnForms(<%= widgetId %>, <%= contactUs.getFormId() %>, <%= showFromAddRecord %>, '<%= ItemType.CONTACT_US %>');"/>
                        <% } %>
                        <input type="button" id="submit<%= widgetId %>"
                               <% if (pageBreaksToPass == totalPageBreaks) { %>value="<international:get name="send"/>"
                               <% } else { %>value="<international:get name="next"/>"<% } %>
                               onclick="sendMessage(<%= widgetId %>, <%= showFromAddRecord%>);">
                        <input type="button" value="<international:get name="reset"/>" id="reset<%= widgetId %>"
                               onclick="resetForm(<%= widgetId %>, <%= contactUs.getFormId() %>, <%= showFromAddRecord %>, '<%= ItemType.CONTACT_US %>');">

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
</div>
