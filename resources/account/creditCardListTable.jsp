<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.CreditCard" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.presentation.site.payment.UserCreditCardsInfo" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.creditCard.CreditCardManager" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="creditCardList"/>

<%
    UserCreditCardsInfo service = (UserCreditCardsInfo) request.getAttribute("service");
    if (service == null) {
        service = (UserCreditCardsInfo) request.getAttribute("actionBean");
    }
    final SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
%>

<table class="tbl_blog" style="width:96%;">
    <thead>
    <tr>
        <td><international:get name="CC_NUMBER"/>
        </td>
        <td><international:get name="CC_EXPIRATION_DATA"/>
        </td>
        <td><international:get name="ITEM_DELETE"/>
        </td>
        <td><international:get name="ITEM_SITES"/>
        </td>
    </tr>
    </thead>
    <tbody>
    <%
        if (service.getCreditCards() != null && service.getCreditCards().size() != 0) {
            Calendar calendar = new GregorianCalendar();
            for (CreditCard card : service.getCreditCards()) {
                calendar.set(Calendar.MONTH, card.getExpirationMonth());
                calendar.set(Calendar.YEAR, card.getExpirationYear());
                final List<Site> connectedSites = new CreditCardManager(card).getSitesConnectedToCreditCard();
    %>
    <tr>
        <td style="text-align:center">
            <a href='javascript:showAddEditCreditCardWindow(false, <%= card.getCreditCardId() %>);'>
                <international:get name="<%= card.getCreditCardType().toString() %>"/><%= ": XXXX XXXX XXXX " + card.getCreditCardNumber().substring(card.getCreditCardNumber().length() - 4) %>
            </a>
        </td>
        <td style="text-align:center"><%= format.format(calendar.getTime()) %>
        </td>
        <td style="text-align:center">
            <input type="image" src="/images/cross-circle.png" value="Delete"
                   onclick="deleteCreditCard('<% if (connectedSites.size() != 0) { %><international:get name="ccInUse"/><% } else { %><international:get name="confirmCCRemove"/><% } %>', <%= card.getCreditCardId() %>);">
        </td>
        <td style="text-align:center">
            <% for (Site site : connectedSites) { %>
            <a href="updatePaymentInfo.action?selectedSiteId=<%= site.getSiteId() %>"><%= site.getTitle() + " " %>
            </a>&nbsp;
            <% } %>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">
            <div id="noCreditCardFound">
                <international:get name="noCreditCardFound"/>
            </div>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
