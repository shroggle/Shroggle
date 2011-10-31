<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.logic.groups.SubscriptionTimeType" %>
<%@ page import="com.shroggle.logic.shoppingCart.ShoppingCartHelper" %>
<%@ page import="com.shroggle.logic.form.FilledFormItemManager" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.util.DoubleUtil" %>
<%@ page import="com.shroggle.logic.site.WidgetManager" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.purchaseHistory.PurchaseHistoryHelper" %>
<%@ page import="com.shroggle.logic.purchaseHistory.Purchase" %>
<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderPurchaseHistory"/>
<%
    final PurchaseHistory purchaseHistory = (PurchaseHistory) request.getAttribute("purchaseHistory");
    final Widget widget = (Widget) request.getAttribute("widget");
    final int widgetId = widget.getWidgetId();
    final Integer loginedUserId = new UsersManager().getLoginedUser() != null ? new UsersManager().getLoginedUser().getUserId() : null;
    final List<Purchase> userPurchases = new PurchaseHistoryHelper().getPurchases(loginedUserId, purchaseHistory.getSiteId());
%>

<% if (loginedUserId != null) { %>
<div class="blockToReload<%= widgetId %>" id="purchaseHistory<%= widgetId %>">
    <% if (purchaseHistory.isShowDescription()) { %>
    <div class="purchaseHistoryHeader">
        <%= purchaseHistory.getDescription() %>
    </div>
    <% } %>
    <% if (userPurchases != null && !userPurchases.isEmpty()) { %>
    <table class="purchaseHistoryTable">
        <tr class="purchaseHistoryTableHeader">
            <td class="purchaseHistoryTdFirst">
                <international:get name="purchaseDate"/>
            </td>
            <td class="purchaseHistoryTd">
                <international:get name="itemPurchased"/>
            </td>
            <td class="purchaseHistoryTd">
                <international:get name="charged"/>
            </td>
            <td class="purchaseHistoryTd">
                <international:get name="orderStatus"/>
            </td>
        </tr>
        <% for (Purchase purchase : userPurchases) { %>
        <tr>
            <td class="purchaseHistoryTdFirst">
                <%= purchase.getPurchaseDate() %>
            </td>
            <td class="purchaseHistoryTd" valign="top">
                <% if (purchaseHistory.isShowProductImage()) { %>
                <img src="<%= FilledFormManager.createImageItemUrl(FilledFormItemManager.getValue(purchase.getProductImageItem(), 0)) %>"
                <%--onclick="<%= purchase.getProductPageUrl().getUserScript() %>"--%>
                <%--ajaxHistory="<%= purchase.getProductPageUrl().getAjaxDispatch() %>"--%>
                     style="width:<%= purchaseHistory.getImageWidth() %>px;height:<%= purchaseHistory.getImageHeight() %>px;cursor:pointer;"
                     alt="<%= FilledFormItemManager.getValue(purchase.getProductImageItem(), 1) %>">
                <% } %>

                <div class="purchaseHistoryProductName">
                    <%= purchase.getProductName() %>
                </div>
            </td>
            <td class="purchaseHistoryTd">
                <%= purchase.getProductPrice() %>
            </td>
            <td class="purchaseHistoryTd">
                <%= purchase.getProductOrderStatus() %>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <international:get name="emptyPurchaseHistory"/>
    <% } %>
</div>
<% } else { %>
<script type="text/javascript">
    showVisitorLogin(<%= widget.getWidgetId() %>, false);
</script>
<% } %>
