<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.entity.User" %>
<%@ page import="com.shroggle.logic.form.FormData" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="childSiteRegistration"/>
<%
    final int widgetId = (Integer) request.getAttribute("widgetId");
    final FormData childSiteRegistration = (FormData) request.getAttribute("form");
    final String startDate = (childSiteRegistration.isUseStartDate() && childSiteRegistration.getStartDate() != null && childSiteRegistration.getStartDate().after(new Date())) ? DateUtil.toMonthDayAndYear(childSiteRegistration.getStartDate()) : DateUtil.toMonthDayAndYear(new Date());
    final String endDate = childSiteRegistration.isUseEndDate() && childSiteRegistration.getEndDate() != null ? DateUtil.toMonthDayAndYear(childSiteRegistration.getEndDate()) : "";

    final boolean showFromAddRecord = request.getParameter("showFromAddRecord") != null && Boolean.parseBoolean(request.getParameter("showFromAddRecord"));

    final Boolean editDetails = (request.getAttribute("editDetails") != null && (Boolean) request.getAttribute("editDetails")) ||
            request.getParameter("filledFormToUpdateId") != null;

    request.setAttribute("disableEmailField", editDetails);
    request.setAttribute("disablePasswordField", editDetails);

    final boolean hideVerificationTextAndButtons = (editDetails || showFromAddRecord);

    final User childSiteLoginedUser = (User) request.getAttribute("loginedUser");

    final Integer childSiteSettingsId = request.getAttribute("settingsId") != null ? (Integer) request.getAttribute("settingsId") :
            (request.getParameter("settingsId") != null ? Integer.parseInt(request.getParameter("settingsId")) : null);
    Integer childSiteUserId = request.getAttribute("childSiteUserId") != null ? (Integer) request.getAttribute("childSiteUserId") :
            (request.getParameter("childSiteUserId") != null ? Integer.parseInt(request.getParameter("childSiteUserId")) : null);
    request.setAttribute("settingsId", childSiteSettingsId);
    request.setAttribute("childSiteUserId", childSiteUserId);
    if (childSiteUserId == null && editDetails) {
        childSiteUserId = childSiteLoginedUser != null ? childSiteLoginedUser.getUserId() : null;
    }

    final int pageBreakNumber = request.getParameter("pageBreaksToPass") != null ? Integer.parseInt(request.getParameter("pageBreaksToPass")) : 0;
    final int totalPageBreaks = FormManager.getTotalPageBreaks(childSiteRegistration);

    final String childSiteRegistrationFilledFormId = request.getParameter("filledFormToUpdateId");
    final String paymentErrorHtml = request.getParameter("paymentError" + childSiteRegistrationFilledFormId) != null ? "<div id=\"paymentRequiredError\" style=\"color:red;\"> Payment is required. </div>" : "";
%>
<% if (!childSiteRegistration.isRegistrationCanceled()) { %>
<div class="childSiteRegistrationBlock" id="childSiteRegistrationBlock<%= widgetId %>">
    <input type="hidden" id="childSiteTotalPageBreaks<%= widgetId %>" value="<%= totalPageBreaks %>">
    <input type="hidden" id="childSiteUserId<%= widgetId %>" value="<%= childSiteUserId %>">
    <input type="hidden" id="settingsId<%= widgetId %>" value="<%= childSiteSettingsId %>">

    <%----------------------------------------paypal succcessfull payment text----------------------------------------%>
    <div id="paypalPaymentComplete<%= widgetId %>" class="childSiteRegistrationPaypalPaymentComplete">
        <international:get name="paypalPaymentComplete"/>
    </div>
    <%----------------------------------------paypal succcessfull payment text----------------------------------------%>

    <%-------------------------------------------------after edit text------------------------------------------------%>
    <div id="afterEditText<%= widgetId %>" style="display:none;">
        <international:get name="thankYouForUpdatingYourInformation"/><br/>
        <a href="javascript:window.location.reload(true);"><international:get
                name="editYourRegistrationDetails"/></a><br/>
        <a href="/account/dashboard.action"><international:get name="editYourPages"/></a>
    </div>
    <%-------------------------------------------------after edit text------------------------------------------------%>

    <%-------------------------------------child site registration complete text--------------------------------------%>
    <div id="childSiteRegistrationComplete<%= widgetId %>" style="display:none">
        <%= childSiteRegistration.getThanksForRegisteringText() %>
    </div>
    <%-------------------------------------child site registration complete text--------------------------------------%>

    <div id="childSiteRegistration<%= widgetId %>">
        <% if (showFromAddRecord) { %>
        <div id="childSiteRegistrationErrorBlock<%= widgetId %>"><%= paymentErrorHtml %>
        </div>
        <% } %>

        <%------------------------------------------------Description-------------------------------------------------%>
        <% if (childSiteRegistration.isShowDescription()) { %>
        <div style="font-weight:bold;" class="childSiteRegistrationHeader">
            <%= childSiteRegistration.getDescription() != null ? childSiteRegistration.getDescription() : "" %>
        </div>
        <% } %>
        <%------------------------------------------------Description-------------------------------------------------%>

        <% if (pageBreakNumber == 0) { %>
        <div style='margin:20px 0;display:<%= hideVerificationTextAndButtons || childSiteLoginedUser != null ? "none" : "block"%>'>
            <a href="javascript:showLoginBlockForChildSiteRegistration(<%= widgetId %>);"><international:get
                    name="alreadyHaveAnAccount"/></a>
        </div>
        <div class="registerToJoin">
            <international:get name="registerToJoin"/>
            <% if (childSiteRegistration.isUseOneTimeFee()) { %>
            $<%= childSiteRegistration.getOneTimeFee() %>&nbsp;<international:get name="oneTimeFee"/>.
            <% } else { %>
            $<%= childSiteRegistration.getPrice250mb() %>&nbsp;<international:get name="perMonth"/>.
            <% } %>
        </div>
        <% if (childSiteRegistration.isUseEndDate()) { %>
        <div class="membershipLimited">
            <international:get name="membershipIsLimited"/>
        </div>
        <div class="startEndDate">
            <% if (childSiteRegistration.isUseStartDate()) { %>
            <international:get name="startDate"/>&nbsp;&mdash;&nbsp;<%= startDate %>.
            <% } %>
            <% if (childSiteRegistration.isUseEndDate()) { %>
            <international:get name="endDate"/>&nbsp;&mdash;&nbsp;<%= endDate %>
            <% } %>
        </div>
        <% } %>
        <% } %>

        <br>
        <br>

        <table class="childSiteRegistrationTable form<%= widgetId %>" id="childSiteRegistrationTable<%= widgetId %>">
            <tr>
                <td>
                    <international:get name="provideYourRegistrationInformationBelow"/>
                </td>
            </tr>

            <jsp:include page="widgetForm.jsp" flush="true"/>
            <% if (pageBreakNumber == 0) { %>
            <tr height="10">
                <td></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div id="childSiteRegistrationTextarea<%= widgetId %>"
                         style="width:98%;height:100px;overflow:auto;border:1px solid #333333;padding:5px;">
                        <%= StringUtil.getEmptyOrString((String) request.getAttribute("termsAndConditions")) %>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox"
                           id="childSiteRegistrationIAgree<%=widgetId%>" <% if (hideVerificationTextAndButtons) { %>
                           checked <% } %>>
                    <label for="childSiteRegistrationIAgree<%= widgetId %>">
                        <international:get name="iAgree"/>
                    </label>
                </td>
            </tr>
            <% } %>
            <% if (!hideVerificationTextAndButtons && pageBreakNumber == 0) { %>
            <tr>
                <td colspan="2">
                    <div id="securityCodeText" class="CSRSecurityCodeText"><international:get
                            name="securityCode"/></div>
                </td>
            </tr>
            <tr class="trWithVerificationWord">
                <td>
                    <label><img
                            src="/noBotImage.action?noBotPrefix=childSiteRegistration<%= widgetId %>&noCache=<%= Math.random() %>"
                            alt="Verification code" class="bot_code" id="noBotImage<%= widgetId %>"></label>
                </td>
                <td>
                    <input type="text" class="formTextInput" maxlength="255"
                           id="childSiteRegistrationVerificationCode<%= widgetId %>"><br>
                </td>
            </tr>
            <% } %>
            <tr>
                <td class="childSiteRegistrationSubmitBlockTd" colspan="2">
                    <%---------------------------------------------------Buttons--------------------------------------------------%>
                    <div class="childSiteRegistrationSubmitBlock"
                         <% if (showFromAddRecord) { %>style="display:none"<% } %>>
                        <% if (!showFromAddRecord) { %>
                        <div id="childSiteRegistrationErrorBlock<%= widgetId %>"><%= paymentErrorHtml %>
                        </div>
                        <% } %>

                        <div class="childSiteRegistrationButtons">
                            <% if (pageBreakNumber != 0) { %>
                            <input value="<international:get name="back"/>"
                                   id="back<%= widgetId %>" type="button" class="formBackButton"
                                   onclick="goBackOnForms(<%= widgetId %>, <%= childSiteRegistration.getFormId() %>, <%= showFromAddRecord %>, '<%= ItemType.CHILD_SITE_REGISTRATION %>');"/>
                            <% } %>
                            <input type="button" id="submit<%= widgetId%>"
                                    <% if (pageBreakNumber == totalPageBreaks && editDetails) { %>
                                   value="<international:get name="save"/>"
                                   onclick="createChildSite(<%= widgetId %>, <%= showFromAddRecord %>);"
                                    <% } else if (pageBreakNumber == totalPageBreaks) { %>
                                   value="<international:get name="submit"/>"
                                   onclick="createChildSite(<%= widgetId %>, <%= showFromAddRecord %>);"
                                    <% } else { %>
                                   value="<international:get name="next"/>"
                                   onclick="createChildSite(<%= widgetId %>, <%= showFromAddRecord %>);"
                                    <% } %>                        />
                            <input value="<international:get name="reset"/>" id="reset<%= widgetId %>" type="button"
                                   onclick="resetForm(<%= widgetId %>, <%= childSiteRegistration.getFormId() %>, <%= showFromAddRecord %>, '<%= ItemType.CHILD_SITE_REGISTRATION %>');"/>

                            <div id="formsLoadingMessageDiv<%= widgetId %>" style="display:none;">
                                <img alt="Loading text editor..." src="/images/ajax-loader.gif"
                                     style="vertical-align:middle;padding: 0 3px 0 0"/>
                            </div>
                        </div>
                    </div>
                    <%---------------------------------------------------Buttons--------------------------------------------------%>
                </td>
            </tr>
        </table>

    </div>
</div>

<%---------------------------------------------------hidden fields----------------------------------------------------%>
<input id="childSiteRegistrationId<%= widgetId %>" type="hidden" value="<%= childSiteRegistration.getFormId() %>">
<input id="childSiteUserId<%= widgetId%>" type="hidden" value="<%= childSiteUserId %>">
<input id="childSiteSettingsId<%= widgetId%>" type="hidden" value="<%= childSiteSettingsId %>">
<input id="editDetails<%= widgetId %>" type="hidden" value="<%= editDetails %>">
<input id="childSiteRegistrationErrorsEmptyCode<%= widgetId %>" type="hidden"
       value="<international:get name="emptyCode"/>">
<input id="childSiteRegistrationErrorsWrongCode<%= widgetId %>" type="hidden"
       value="<international:get name="wrongCode"/>">
<input id="childSiteRegistrationErrorsAgreeToTheConditions<%= widgetId %>" type="hidden"
       value="<international:get name="agreeToTheConditions"/>">
<input id="childSiteRegistrationErrorsEmailRegistered<%= widgetId %>" type="hidden"
       value="<international:get name="emailRegistered"/>">
<input id="childSiteRegistrationErrorsEnterCorrectDomainName<%= widgetId %>" type="hidden"
       value="<international:get name="enterCorrectDomainName"/>">
<%---------------------------------------------------hidden fields----------------------------------------------------%>
<% } else { %>
<international:get name="registrationClosed"/>
<% } %>
