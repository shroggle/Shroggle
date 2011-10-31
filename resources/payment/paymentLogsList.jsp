<%@ page import="com.shroggle.entity.PaymentLog" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final List<PaymentLog> paymentLogs = (List<PaymentLog>) request.getAttribute("paymentLogs"); %>
<% final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy @ HH:mm"); %>
<table class="tbl_blog">
    <thead>
    <tr>
        <td>
            User Id
        </td>
        <td>
            Creation date
        </td>
        <td>
            Message
        </td>
        <td>
            Payment method
        </td>
        <td>
            Transaction status
        </td>
        <td>
            siteId
        </td>
        <td>
            childSiteSettingsId
        </td>
        <td>
            CCId
        </td>
        <td>
            Monthly Sum
        </td>
        <td>
            Sum
        </td>
        <td>
            Payment Reason
        </td>
    </tr>
    </thead>
    <tbody>
    <% for (PaymentLog paymentLog : paymentLogs) { %>
    <tr <% if (!StringUtil.isNullOrEmpty(paymentLog.getErrorMessage())) { %>
            style="background-color:#FEE;" <% } else { %> style="background-color:#EFE;" <% } %>>
        <td title="User Id">
            <%= paymentLog.getUserId() == null ? "" : paymentLog.getUserId() %>
        </td>
        <td title="Creation Date">
            <%= paymentLog.getCreationDate() == null ? "" : dateFormat.format(paymentLog.getCreationDate()).replaceAll(" ", "&nbsp;") %>
        </td>
        <td title="Message">
            <%= !StringUtil.isNullOrEmpty(paymentLog.getErrorMessage()) ?
                    ("Payment operation: " + (paymentLog.getMessage() == null ? "" : paymentLog.getMessage()) +
                            "<br/>Error Message:&nbsp;<div class='paymentErrorMessage'>" + (paymentLog.getErrorMessage() == null ? "" : paymentLog.getErrorMessage() + "&nbsp;") + "</div><div class='paymentLogsLinkDiv'><span onclick='paymentLogs.viewError(this);'>View</span></div>") :
                    paymentLog.getMessage()
            %>
        </td>
        <td title="Payment Method">
            <%= paymentLog.getPaymentMethod() == null ? "" : paymentLog.getPaymentMethod() %>
        </td>
        <td title="Transaction Status">
            <%= paymentLog.getTransactionStatus() == null ? "" : paymentLog.getTransactionStatus() %>
        </td>
        <td title="Site Id">
            <%= paymentLog.getSiteId() == null ? "" : paymentLog.getSiteId() %>
        </td>
        <td title="Child Site Settings Id">
            <%= paymentLog.getChildSiteSettingsId() == null ? "" : paymentLog.getChildSiteSettingsId() %>
        </td>
        <td title="Credit Card Id">
            <div class="paymentLogsLinkDiv">
                <span onclick="paymentLogs.showCC(this);"><%= paymentLog.getCreditCardId() == null ? "" : paymentLog.getCreditCardId() %></span>
            </div>
            <% if (paymentLog.getCreditCardId() != null) { %>
            <div class="paymentLogsCreditCardDetails" style="display:none">
                <div class="windowOneColumn">
                    <h1>
                        Credit Card Details:
                    </h1>

                    <table class="paymentLogsCreditCardDetailsTable">
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Credit Card Type:
                            </td>
                            <td>
                                <%= paymentLog.getCreditCardType() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Credit Card Number:
                            </td>
                            <td>
                                <%= paymentLog.getCreditCardNumber() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Expiration Year:
                            </td>
                            <td>
                                <%= paymentLog.getExpirationYear() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Expiration Month:
                            </td>
                            <td>
                                <%= paymentLog.getExpirationMonth() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Security Code:
                            </td>
                            <td>
                                <%= paymentLog.getSecurityCode() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Use Contact Info:
                            </td>
                            <td>
                                <%= paymentLog.isUseContactInfo() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Billing Address 1:
                            </td>
                            <td>
                                <%= paymentLog.getBillingAddress1() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Billing Address 2:
                            </td>
                            <td>
                                <%= paymentLog.getBillingAddress2() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                City:
                            </td>
                            <td>
                                <%= paymentLog.getCity() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Country:
                            </td>
                            <td>
                                <%= paymentLog.getCountry() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Region:
                            </td>
                            <td>
                                <%= paymentLog.getRegion() %>
                            </td>
                        </tr>
                        <tr>
                            <td class="paymentLogsColumnHeader">
                                Postal Code:
                            </td>
                            <td>
                                <%= paymentLog.getPostalCode() %>
                            </td>
                        </tr>
                    </table>

                    <div align="right" style="margin-top:5px;">
                        <input type="button" value="Close" class="but_w73" onclick="closeConfigureWidgetDiv();"
                               onmouseover="this.className = 'but_w73_Over';"
                               onmouseout="this.className = 'but_w73';"/>
                    </div>
                </div>
            </div>
            <% } %>
        </td>
        <td title="Monthly Sum">
            <%= paymentLog.getMonthlySum() == null ? "" : paymentLog.getMonthlySum() %>
        </td>
        <td title="Sum">
            <%= paymentLog.getSum() == null ? "" : paymentLog.getSum() %>
        </td>
        <td title="Payment Reason">
            <%= paymentLog.getPaymentReason() == null ? "" : paymentLog.getPaymentReason() %>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

