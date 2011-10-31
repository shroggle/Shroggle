<%@ page import="java.util.Date" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.logic.form.PaymentSettings" %>
<%@ page import="com.shroggle.logic.form.PaymentSettingsManager" %>
<%@ page import="com.shroggle.presentation.site.payment.AddEditCreditCardData" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="addEditCreditCardInfo"/>
<%
    Integer paymentWidgetId = (Integer) request.getAttribute("widgetId");
    Integer settingsId = (Integer) request.getAttribute("settingsId");
    final ChildSiteSettings childSiteSettings = ServiceLocator.getPersistance().getChildSiteSettingsById(settingsId);
    final CreditCard creditCard = childSiteSettings != null ? childSiteSettings.getSitePaymentSettings().getCreditCard() : null;
    final PaymentSettings settings = (PaymentSettings) request.getAttribute("paymentSettings");
    final String paymentStartDate = (settings.getStartDate() != null && settings.getStartDate().after(new Date())) ? DateUtil.toMonthDayAndYear(settings.getStartDate()) : DateUtil.toMonthDayAndYear(new Date());
    final String paymentEndDate = settings.getEndDate() != null ? DateUtil.toMonthDayAndYear(settings.getEndDate()) : "";

    final boolean isPaymentComplete = childSiteSettings != null && childSiteSettings.getSitePaymentSettings().getSiteStatus() == SiteStatus.ACTIVE;
    final boolean payPalPaymentComplete = isPaymentComplete && childSiteSettings.getSitePaymentSettings().getPaymentMethod() == PaymentMethod.PAYPAL
            && childSiteSettings.getSitePaymentSettings().getRecurringPaymentId() != null;
    final boolean javienPaymentComplete = isPaymentComplete && childSiteSettings.getSitePaymentSettings().getPaymentMethod() == PaymentMethod.AUTHORIZE_NET
            && creditCard != null;
    final boolean paymentComplete = payPalPaymentComplete || javienPaymentComplete;
    final boolean showFromAddRecord = request.getParameter("showFromAddRecord") != null && Boolean.parseBoolean(request.getParameter("showFromAddRecord"));
    final boolean showFromEditRecord = request.getAttribute("showFromEditRecord") != null ? (Boolean) request.getAttribute("showFromEditRecord") : false;
    final boolean addOrEditRecord = showFromAddRecord || showFromEditRecord;
    final boolean notInNetwork = childSiteSettings == null;
%>
<input type="hidden" id="childSiteRegistrationPaymentComplete<%= paymentWidgetId %>"
       value="<%= paymentComplete || addOrEditRecord || notInNetwork %>">

<div id="childSiteRegistrationPaymentDiv<%= paymentWidgetId %>">
    <div class="registerToJoin">
        <international:get name="registerToJoin"/>
        <% if (settings.isUseOneTimeFee()) { %>
        $<%= settings.getOneTimeFee() %>&nbsp;<international:get name="oneTimeFee"/>.
        <% } else { %>
        $<%= settings.getPrice250mb() %>&nbsp;<international:get name="perMonth"/>.
        <% } %>
    </div>
    <% if (settings.getEndDate() != null) { %>
    <div class="membershipLimited">
        <international:get name="membershipIsLimited"/>
    </div>
    <div class="startEndDate">
        <% if (settings.getStartDate() != null) { %>
        <international:get name="startDate"/>&nbsp;&mdash;&nbsp;<%= paymentStartDate %>.
        <% } %>
        <% if (settings.getEndDate() != null) { %>
        <international:get name="endDate"/>&nbsp;&mdash;&nbsp;<%= paymentEndDate %>
        <% } %>
    </div>
    <% } %>
    <div id="childSiteRegistrationPaymentCompleteMessageDiv<%= paymentWidgetId %>"
         style="color:green;font-weight:bold; visibility:<%= paymentComplete ? "visible" : "hidden" %>;">
        <international:get name="paymentHasBeenCompleted"/>
    </div>
    <% if (!paymentComplete && addOrEditRecord) { %>
    <div id="childSiteRegistrationPaymentCompleteMessageDiv<%= paymentWidgetId %>"
         style="color:red;font-weight:bold;">
        <international:get name="cardDetailsHaveNotProvided"/>
    </div>
    <% } %>
    <table class="childSiteRegistrationTable">
        <tr>
            <td>
                <span style="font-weight:bold"><international:get name="paymentMethod"/></span>
            </td>
        </tr>
        <tr>
            <td>
                <input type="radio" id="payPal<%= paymentWidgetId %>" name="method"
                       <% if (paymentComplete || addOrEditRecord || notInNetwork) { %>disabled<% } %>
                       <% if (payPalPaymentComplete) { %>checked="checked"<% } %>
                       onclick="payPalSelectedCSR('<%= paymentWidgetId %>');"/>
                <label for="payPal<%= paymentWidgetId %>"><international:get name="payPal"/></label>
            </td>
        </tr>
        <tr>
            <td>
                <input type="radio" id="creditCard<%= paymentWidgetId %>" name="method"
                       <% if (paymentComplete || addOrEditRecord || notInNetwork) { %>disabled<% } %>
                       <% if (javienPaymentComplete || !paymentComplete) { %>checked="checked"<% } %>
                       onclick="creditCardSelectedCSR('<%= paymentWidgetId %>');"/>
                <label for="creditCard<%= paymentWidgetId %>"><international:get name="creditCard"/></label>
            </td>
            <td>
                <img src="../images/CCSymbols.gif" alt="Credit Card" width="120" height="26">
            </td>
        </tr>
    </table>
    <br>

    <% if (!payPalPaymentComplete) { %>
    <% request.setAttribute("addEditCreditCardData", new AddEditCreditCardData(creditCard, paymentWidgetId,
            ServiceLocator.getPersistance().getUserById((Integer) request.getAttribute("childSiteUserId")), paymentComplete || addOrEditRecord || notInNetwork));%>
    <jsp:include page="addEditCreditCardInfo.jsp" flush="true"/>
    <% } %>

    <input type="checkbox" id="iAgreeWithTermsAndConditionsCheckboxCSR<%= paymentWidgetId %>"
           <% if (paymentComplete || addOrEditRecord || notInNetwork) { %>checked disabled<% } %>/>
    <a <% if (paymentComplete || addOrEditRecord || notInNetwork) { %>href="javascript:void(0);" style="color:gray;" <% } else { %>
       href="javascript:showPaymentTermsConditions('<%= paymentWidgetId %>')"<% } %>><international:get
            name="termsAndCond"/></a>
    <br>

    <%--<input type="button" id="secureCreditCardPaymentButton<%= paymentWidgetId %>" value="Secure Payment"
           onclick="updateSitePaymentInfoCSR('<%= paymentWidgetId %>');">

    <div id="securePaypalPaymentButton<%= paymentWidgetId %>" style="display:none;">
        <input type="button" value="Go to Pay Pal" onclick="goToPaypalCSR(<%= paymentWidgetId %>);">
    </div>--%>
    <%--<div id="childSiteRegistrationErrorBlock<%= paymentWidgetId %>" style="color:red;">&nbsp;</div>--%>


    <div id="agreementCSRPaymentText<%= paymentWidgetId %>" style="display:none;">
        <div style="border:5px solid #ECE9E6; margin:0 10px 0 10px; padding-top:20px;padding-left:20px;padding-right:20px;">
            <div style="overflow:auto;height:300px; padding:10px; text-align:left;">
                <%= new PaymentSettingsManager(settings).getAgreement() %>
                <international:get name="agreement"/>
            </div>
            <p align="center">
                <input type="button" onclick="closeConfigureWidgetDiv();" value="Close">
            </p>
        </div>
    </div>

    <%---------------------------------------------------hidden fields----------------------------------------------------%>
    <input type="hidden" id="cardProcessing" value="<international:get name="cardProcessing"/>">
    <input type="hidden" id="redirectingToPaypal" value="<international:get name="redirectingToPaypal"/>">
    <input type="hidden" id="settingsId<%= paymentWidgetId %>" value="<%= settingsId %>">
    <input type="hidden" id="chargeType<%= paymentWidgetId %>"
           value="<%= settings.isUseOneTimeFee() ? ChargeType.SITE_ONE_TIME_FEE.toString() : ChargeType.SITE_250MB_MONTHLY_FEE.toString() %>">
    <input type="hidden" id="paymentBlock<%= paymentWidgetId %>" value="true">
    <input type="hidden" id="paymentRequired<%= paymentWidgetId %>"
           value="<%= request.getAttribute("paymentRequired")%>">
    <%---------------------------------------------------hidden fields----------------------------------------------------%>

</div>