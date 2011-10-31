<%@ page import="com.shroggle.presentation.site.render.shoppingCart.FilledFormGalleryIdsPair" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.logic.groups.SubscriptionTimeType" %>
<%@ page import="com.shroggle.logic.shoppingCart.ShoppingCartHelper" %>
<%@ page import="com.shroggle.logic.shoppingCart.ShoppingCartGroupData" %>
<%@ page import="com.shroggle.logic.shoppingCart.ShoppingCartGroupType" %>
<%@ page import="com.shroggle.entity.ShoppingCart" %>
<%@ page import="com.shroggle.logic.form.FilledFormItemManager" %>
<%@ page import="com.shroggle.logic.shoppingCart.ShoppingCartItemData" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.entity.Page" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page import="com.shroggle.util.DoubleUtil" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="com.shroggle.logic.site.WidgetManager" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.logic.site.taxRates.TaxManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderShoppingCart"/>
<%
    final List<FilledFormGalleryIdsPair> filledFormGalleryPairs =
            (List<FilledFormGalleryIdsPair>) request.getAttribute("filledFormGalleryPairs");
    final ShoppingCart shoppingCart = (ShoppingCart) request.getAttribute("shoppingCart");
    final Widget widget = (Widget) request.getAttribute("widget");
    final int widgetId = widget.getWidgetId();
    final Integer galleryReferralPageId = request.getParameter("galleryReferralPageId") != null ?
            Integer.parseInt(request.getParameter("galleryReferralPageId")) : null;
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");


    String continueShoppingLink = "#";
    if (galleryReferralPageId != null) {
        final Page galleryPage = ServiceLocator.getPersistance().getPage(galleryReferralPageId);
        if (galleryPage != null) {
            continueShoppingLink = new PageManager(galleryPage, siteShowOption).getUrl();
        }
    }
%>

<div class="blockToReload<%= widgetId %>" id="shoppingCart<%= widgetId %>"
     shoppingCart="shoppingCart<%= shoppingCart.getId() %>" widgetId="<%= widgetId %>">
    <% if (shoppingCart.isShowDescription()) { %>
    <div class="shoppingCartHeader">
        <%= shoppingCart.getDescription() %>
    </div>
    <% } %>
    <% if (filledFormGalleryPairs != null && !filledFormGalleryPairs.isEmpty()) { %>

    <table>
        <tr>
            <td>
                <% for (ShoppingCartGroupData groupData : ShoppingCartHelper.splitItems(filledFormGalleryPairs, siteShowOption, widget)) { %>
                <div class="shoppingCartGroup">
                    <table class="shoppingCartTable">
                        <% List<Double> itemPricesWithTaxPoweredByQty = new ArrayList<Double>(); %>
                        <% for (ShoppingCartItemData item : groupData.getShoppingCartItemDataList()) { %>
                        <tr>
                            <% if (item.getPaypalButtonData().getImageFilledItem() != null && shoppingCart.isImageInFirstColumn()) { %>
                            <td class="shoppingCartTdFirst">
                                <img src="<%= FilledFormManager.createImageItemUrl(FilledFormItemManager.getValue(item.getPaypalButtonData().getImageFilledItem(), 0)) %>"
                                     onclick="<%= item.getProductPageUrl().getUserScript() %>"
                                     ajaxHistory="<%= item.getProductPageUrl().getAjaxDispatch() %>"
                                     style="width:<%= shoppingCart.getImageWidth() %>px;height:<%= shoppingCart.getImageHeight() %>px;cursor:pointer;"
                                     alt="<%= FilledFormItemManager.getValue(item.getPaypalButtonData().getImageFilledItem(), 1) %>">
                            </td>
                            <% } %>
                            <td class="shoppingCartTd">
                                <div class="shoppingCartProductName">
                                    <%= item.getPaypalButtonData().getProductFilledItemValue() %>
                                </div>
                                <% if (item.getPaypalButtonData().getDescriptionFilledItem() != null && shoppingCart.isDescriptionAfterName()) { %>
                                <div class="shoppingCartProductDescription">
                                    <%= item.getPaypalButtonData().getDescriptionFilledItem().getValue() %>
                                </div>
                                <% } %>
                            </td>
                            <% if (groupData.getShoppingCartGroupType() == ShoppingCartGroupType.NORMAL) { %>
                            <td class="shoppingCartTd">
                                <international:get name="quantity"/>
                                <input type="text" class="shoppingCartQtyInput" value="<%= item.getQuantity() %>"
                                       onkeyup="acceptOnlyDigits(this);" onblur="shoppingCart.quantityBlur(this, <%= shoppingCart.getId() %>,
                           <%= item.getPaypalButtonData().getFilledFormId() %>, <%= groupData.getGallery().getId() %>);"/>
                            </td>
                            <% } %>
                            <td class="shoppingCartTd">
                                <%
                                    double initialPrice = item.getPaypalButtonData().getPrice();
                                    double tax = item.getPaypalButtonData().getTaxSum(item.getQuantity());
                                    final String taxRatesString = item.getPaypalButtonData().getTaxString(item.getQuantity());

                                    final String pricePoweredByQty = DoubleUtil.roundWithPrecision((item.getPaypalButtonData().isRecurrent() ? initialPrice : initialPrice * item.getQuantity()), 2);
                                    final Double priceWithTaxPoweredByQty =
                                            item.getPaypalButtonData().isRecurrent() ? null : DoubleUtil.round((initialPrice + tax) * item.getQuantity(), 2);
                                    itemPricesWithTaxPoweredByQty.add(priceWithTaxPoweredByQty);
                                %>
                                $<span
                                    id="shoppingCartItemPrice<%= item.getPaypalButtonData().getFilledFormId() %><%= groupData.getGallery().getId() %>"
                                    class="shoppingCartItemPrice" quantity="<%= item.getQuantity() %>" tax="<%= tax %>"
                                    initialPrice="<%= initialPrice %>"><%= pricePoweredByQty %></span><%= item.getPaypalButtonData().isRecurrent() ? "/" + SubscriptionTimeType.valueOf(item.getPaypalButtonData().getSubscriptionFilledItemValue()).getText() : ""%>&nbsp;<%= taxRatesString %>
                            </td>
                            <td class="shoppingCartTd">
                                <div style="width:25px; height:25px;">
                                    <img src="../../images/cross-circle.png" alt="" style="cursor:pointer;"
                                         onclick="shoppingCart.removeItem(this, <%= widgetId %>, '<%= siteShowOption %>', <%= shoppingCart.getId() %>, <%= item.getPaypalButtonData().getFilledFormId() %>,
                      <%= groupData.getGallery().getId()%>);">
                                    <img src="../../images/ajax-loader.gif" alt="" class="shoppingCartLoading"
                                         style="display:none">
                                </div>
                            </td>
                        </tr>
                        <% } %>
                        <% if (groupData.getShoppingCartGroupType() == ShoppingCartGroupType.NORMAL) { %>
                        <tr class="totalTr">
                            <td <% if (shoppingCart.isImageInFirstColumn()) { %>colspan="2"
                                <% } %>class="shoppingCartTotalTd">
                                <b><international:get name="totalPrice"/></b>
                            </td>
                            <td></td>
                            <td class="shoppingCartTotalTd">
                                $<span
                                    class="shoppingCartTotalPrice"><%= ShoppingCartHelper.getTotalPrice(itemPricesWithTaxPoweredByQty) %></span>
                            </td>
                            <td></td>
                        </tr>
                        <% } %>
                    </table>

                    <input type="hidden" id="buyersFormId<%= widgetId %>"
                           value="<%= groupData.getGallery().getPaypalSettings().getRegistrationFormForBuyers() %>"/>

                    <div class="shoppingCartCheckoutButtons">
                        <%
                            if (galleryReferralPageId == null) {
                                final String tempLink = new ShoppingCartHelper().getGalleryPageUrlByGalleryId(groupData.getGallery().getId(),
                                        siteShowOption);
                                continueShoppingLink = tempLink != null ? tempLink : "#";
                            }
                        %>
                        <a href="<%= continueShoppingLink %>"><international:get name="continueShoppingLink"/></a>

                        <% request.setAttribute("paypalSettings", groupData.getGallery().getPaypalSettings()); %>
                        <% request.setAttribute("paypalButtonGallery", groupData.getGallery()); %>
                        <% request.setAttribute("fromShoppingCart", true); %>
                        <% if (groupData.getShoppingCartGroupType() == ShoppingCartGroupType.RECURRENT) { %>
                        <% request.setAttribute("currentDisplayedFilledFormId", groupData.getShoppingCartItemDataList().get(0).getPaypalButtonData().getFilledFormId()); %>
                        <% request.setAttribute("shoppingCartGroupData", null); %>
                        <% } else { %>
                        <% request.setAttribute("shoppingCartGroupData", groupData); %>
                        <% request.setAttribute("currentDisplayedFilledFormId", null); %>
                        <% } %>
                        <jsp:include page="gallery/paypalButton.jsp" flush="true"/>
                    </div>
                </div>
                <% } %>
            </td>
        </tr>
    </table>

    <% } else { %>
    <international:get name="emptyCart"/>
    <%
        if (galleryReferralPageId == null) {
            Page firstSitePage = new SiteManager(new WidgetManager(widget).getSite()).getFirstSitePage();

            if (firstSitePage != null) {
                continueShoppingLink = new PageManager(firstSitePage, siteShowOption).getUrl();
            }
        }
    %>
    <a href="<%= continueShoppingLink %>"><international:get name="continueShoppingLink"/></a>
    <% } %>
</div>
