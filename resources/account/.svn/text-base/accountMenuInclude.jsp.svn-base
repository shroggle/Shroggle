
<%@ page import="com.shroggle.presentation.Action" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.presentation.account.accessPermissions.AccessPermissionsAction" %>
<%@ page import="com.shroggle.presentation.site.payment.PaymentLogsAction" %>
<%@ page import="com.shroggle.presentation.site.payment.ShowPurchaseMailLogAction" %>
<%@ page import="com.shroggle.presentation.site.payment.UpdatePaymentInfoAction" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<% final Action accountMenuAction = (Action) request.getAttribute("actionBean"); %>
<p class="menu_2" style="text-align:center; margin-top: 5px;">
    <% if (accountMenuAction instanceof UserInfoAction && (!Boolean.parseBoolean(accountMenuAction.getContext().getRequest().getParameter("userInfoUpdated")) || Boolean.parseBoolean(accountMenuAction.getContext().getRequest().getParameter("emailUpdated")))) { %>
    <span style="font-weight:bold;">
    <a href="javascript:void(0);"><international:get name="yourContactDetails"/></a>
    </span>
    <% } else {%>
    <stripes:link beanclass="com.shroggle.presentation.site.UserInfoAction"><international:get
            name="yourContactDetails"/></stripes:link>
    <% } %>
    <% if ((accountMenuAction instanceof UpdatePaymentInfoAction) && !((request.getParameter("showTransaction") != null && request.getParameter("showTransaction").equals("true")) || (request.getParameter("showPaypalError") != null && request.getParameter("showPaypalError").equals("true")) || accountMenuAction instanceof ShowCreditCardListAction)) { %>
    |
    <span id="updatePaymentInfoDiv" style="font-weight:bold;">
        <% if (accountMenuAction instanceof ShowCreditCardListAction) { %>
    <stripes:link
            beanclass="com.shroggle.presentation.site.payment.UpdatePaymentInfoAction"><international:get
            name="siteActivation"/></stripes:link>
    <% } else { %>
        <a href="javascript:void(0);"><international:get name="siteActivation"/></a>
        <% }
        } else { %>
    | <stripes:link
            beanclass="com.shroggle.presentation.site.payment.UpdatePaymentInfoAction"><international:get
            name="siteActivation"/></stripes:link>
    <% } %>
</span>|
    <% if (accountMenuAction instanceof AccessPermissionsAction) { %>
    <span style="font-weight:bold;">
        <a href="javascript:void(0);"><international:get name="accessPermissions"/></a>
    </span>
    <% } else {%>
    <stripes:link beanclass="com.shroggle.presentation.account.accessPermissions.AccessPermissionsAction"><international:get
            name="accessPermissions"/></stripes:link>
    <% } %>
    |
    <% if (accountMenuAction instanceof LoginHelpAction) { %>
    <span style="font-weight:bold;">
        <a href="javascript:void(0);"><international:get name="loginHelp"/></a>
    </span>
    <% } else {%>
    <stripes:link beanclass="com.shroggle.presentation.site.LoginHelpAction"><international:get
            name="loginHelp"/></stripes:link>
    <% } %>
    <% if (accountMenuAction instanceof DeleteUserAction) { %>
    | <span style="font-weight:bold;">
        <a href="javascript:void(0);"><international:get name="deleteAccount"/></a>
    </span>
    <% } else {%>
    | <stripes:link
        beanclass="com.shroggle.presentation.site.DeleteUserAction"><international:get
        name="deleteAccount"/></stripes:link>
    <% } %>

    <% if (ServiceLocator.getConfigStorage().get().isShowLogs()) { %>
    <% if (accountMenuAction instanceof PaymentLogsAction) { %>
    | <span style="font-weight:bold;">
        <a href="javascript:void(0);"><international:get name="paymentLogs"/></a>
    </span>
    <% } else {%>
    | <stripes:link
        beanclass="com.shroggle.presentation.site.payment.PaymentLogsAction"><international:get
        name="paymentLogs"/></stripes:link>
    <% } %>

    <% if (accountMenuAction instanceof ShowPurchaseMailLogAction) { %>
    | <span style="font-weight:bold;">
        <a href="javascript:void(0);"><international:get name="purchaseMailLog"/></a>
    </span>
    <% } else {%>
    | <stripes:link
        beanclass="com.shroggle.presentation.site.payment.ShowPurchaseMailLogAction"><international:get
        name="purchaseMailLog"/></stripes:link>
    <% } %>

    <% } %>
</p>
<br>