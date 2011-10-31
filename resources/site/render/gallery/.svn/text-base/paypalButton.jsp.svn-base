<%--
  @author Balakirev Anatoliy, dmitry.solomadin
--%>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.presentation.site.payment.IPNListener" %>
<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.gallery.paypal.PaypalButtonHelper" %>
<%@ page import="com.shroggle.logic.gallery.paypal.PaypalButtonData" %>
<%@ page import="com.shroggle.logic.shoppingCart.ShoppingCartGroupData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderWidgetGalleryData"/>
<%
    final Gallery gallery = (Gallery) request.getAttribute("paypalButtonGallery");
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
    final PaypalSettingsForGallery paypalSettings = gallery.getPaypalSettings();
    final Integer registrationFormForByers = paypalSettings.getRegistrationFormForBuyers();
    final Widget widget = (Widget) request.getAttribute("widget");
    final int widgetId = widget.getWidgetId();
    final boolean fromShoppingCart = request.getAttribute("fromShoppingCart") != null ?
            (Boolean) request.getAttribute("fromShoppingCart") : false;
    final International galleryDataInternational = ServiceLocator.getInternationStorage().get("renderWidgetGalleryData", Locale.US);

    final String buttonName = fromShoppingCart ? galleryDataInternational.get("defaultShoppingCartCheckoutButtonName") :
            (StringUtil.isNullOrEmpty(paypalSettings.getPaypalSettingsName()) ? galleryDataInternational.get("defaultPaypalButtonName") :
                    paypalSettings.getPaypalSettingsName());

    final Integer currentDisplayedFilledFormId = request.getAttribute("currentDisplayedFilledFormId") != null ?
            (Integer) request.getAttribute("currentDisplayedFilledFormId") : null;
    final ShoppingCartGroupData shoppingCartGroupData = request.getAttribute("shoppingCartGroupData") != null ?
            (ShoppingCartGroupData) request.getAttribute("shoppingCartGroupData") : null;

    PaypalButtonData paypalButtonData = currentDisplayedFilledFormId == null ?
            null : PaypalButtonHelper.fillData(gallery, currentDisplayedFilledFormId, widget.getSiteId());

    final User loginedUser = new UsersManager().getLoginedUser();

    boolean isUserFromRightForm = false;
    if (loginedUser != null && registrationFormForByers != null &&
            new UsersManager().isUserLoginedAndRegisteredFromRightForm(registrationFormForByers)) {
        isUserFromRightForm = true;
    }

    boolean isPriceCorrect = paypalButtonData == null || paypalButtonData.isPriceCorrect();
%>

<% if (isPriceCorrect) { %>
<input type="hidden" id="youHaveToBeLoggedInToBuy<%= widgetId %>"
       value="<international:get name="youHaveToBeLoggedInToBuy"/>"/>
<input type="hidden" id="redirectingToPaypal<%= widgetId %>"
       value="<international:get name="redirectingToPaypal"/>"/>

<% if (shoppingCartGroupData != null || (paypalButtonData != null &&
        paypalButtonData.getProductFilledItemId() != null && paypalButtonData.getPriceFilledItemId() != null)) { %>

<% if (paypalSettings.getShoppingCartId() != null && !fromShoppingCart) { %>
<%----------------------------------------------------SHOPPING CART---------------------------------------------------%>
<input type="button" id="addItemToCartButton<%= widgetId %>"
       onclick="shoppingCart.addItemToCart(<%= widgetId %>, '<%= siteShowOption %>', <%= paypalSettings.getShoppingCartId() %>, <%= currentDisplayedFilledFormId %>, <%= gallery.getId() %>);"
       value="<%= StringUtil.isNullOrEmpty(paypalSettings.getPaypalSettingsName()) ?
     galleryDataInternational.get("defaultShoppingCartButtonName") : paypalSettings.getPaypalSettingsName() %>"/>
<img src="../../../images/ajax-loader-minor.gif" alt="" style="display:none; position:relative; top:3px;"
     id="addToCartLoadingImage<%= widgetId %>">
<%--------------------------------------------------SHOPPING CART END-------------------------------------------------%>
<% } else { %>
<%----------------------------------------------------DIRECT PAYPAL---------------------------------------------------%>
<% if (loginedUser == null || !isUserFromRightForm) { %>
<%-- If track orders is enabled then logined user should be present so we could track orders.--%>
<input type="hidden" id="buyersFormId<%= widgetId %>"
       value="<%= registrationFormForByers %>"/>

<input type="button" onclick="galleryPaypalButton.showLoginIfConfirm(<%= widgetId %>, <%= fromShoppingCart %>);"
       value="<%= buttonName %>"/>
<% } else {
    if (paypalButtonData != null && paypalButtonData.isRecurrent()) { %>
<%-------------------------------------------RECURRENT PROFILE PAYPAL BUTTON------------------------------------------%>
<input type="button" onclick="galleryPaypalButton.performRecurringPayment(<%= widgetId %>, <%= gallery.getId() %>,
<%= loginedUser.getUserId() %>, <%= paypalButtonData.getProductFilledItemId() %>, <%= paypalButtonData.getPriceFilledItemId() %>,
<%= paypalButtonData.getSubscriptionFilledItemId() %>, <%= paypalButtonData.getGroupsFilledItemId() %>,
<%= currentDisplayedFilledFormId %>, <%= registrationFormForByers %>,
<%= paypalSettings.getShoppingCartId()%>, <%= fromShoppingCart %>);" value="<%= buttonName %>"/>
<%-----------------------------------------RECURRENT PROFILE PAYPAL BUTTON END----------------------------------------%>
<% } else { %>
<%------------------------------------------------REGULAR PAYPAL BUTTON-----------------------------------------------%>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" style="display:inline;" target="_blank"
      <% if (fromShoppingCart) { %>onclick="galleryPaypalButton.removeItemFromCookie(this, <%= paypalSettings.getShoppingCartId() %>, <%= gallery.getId() %>);"<% } %>>
    <input type="hidden" name="cmd" value="_cart">
    <input type="hidden" name="upload" value="1">
    <input type="hidden" name="business" value="<%= paypalSettings.getPaypalEmail() %>">

    <% if (shoppingCartGroupData != null) {
        /*In shopping cart price should be always correct 'cos we are checking it in e-commerce store*/
        for (int i = 0; i < shoppingCartGroupData.getShoppingCartItemDataList().size(); i++) {
            paypalButtonData = shoppingCartGroupData.getShoppingCartItemDataList().get(i).getPaypalButtonData();
            final double tax = paypalButtonData.getTaxSum();
            int quantity = shoppingCartGroupData.getShoppingCartItemDataList().get(i).getQuantity(); %>
    <input type="hidden" name="item_name_<%= i + 1 %>"
           value="<%= paypalButtonData.getProductFilledItemValue() %>">
    <input type="hidden" name="amount_<%= i + 1 %>"
           value="<%= paypalButtonData.getPrice() + tax %>">
    <input type="hidden" name="quantity_<%= i + 1 %>" value="<%= quantity %>"
           id="paypalButtonQuantity<%= paypalButtonData.getFilledFormId() %><%= gallery.getId() %>">
    <input type="hidden" class="filledFormIds" filledFormId="<%= paypalButtonData.getFilledFormId() %>"/>
    <% }
    } else {
        final double tax = paypalButtonData.getTaxSum(); %>
    <input type="hidden" name="item_name_1" value="<%= paypalButtonData.getProductFilledItemValue() %>">
    <input type="hidden" name="amount_1"
           value="<%= paypalButtonData.getPrice() + tax %>">
    <input type="hidden" class="filledFormIds" filledFormId="<%= paypalButtonData.getFilledFormId() %>"/>
    <% }
        if (paypalSettings.getOrdersFormId() != null) { %>
    <input type="hidden" name="notify_url" value="<%= IPNListener.getFullUrl() %>">
    <% if (shoppingCartGroupData != null) {
        StringBuilder requestIds = new StringBuilder();
        for (int i = 0; i < shoppingCartGroupData.getShoppingCartItemDataList().size(); i++) {
            paypalButtonData = shoppingCartGroupData.getShoppingCartItemDataList().get(i).getPaypalButtonData();
            requestIds.append((i == 0 ? "" : ";"));
            requestIds.append(PaypalButtonHelper.putIPNRequest(gallery.getId(), loginedUser.getUserId(), registrationFormForByers,
                    widget.getSiteId(), paypalButtonData));
        } %>
    <input type="hidden" name="custom" value="<%= requestIds %>">
    <% } else { %>
    <input type="hidden" name="custom"
           value="<%= PaypalButtonHelper.putIPNRequest(gallery.getId(), loginedUser.getUserId(), registrationFormForByers,
            widget.getSiteId(), paypalButtonData) %>">
    <% }
    } %>

    <%--<input type="hidden" name="return" value="here should be some page.. maybe.. ask Igor.">--%>
    <input type="submit" value="<%= buttonName %>">

</form>
<%----------------------------------------------REGULAR PAYPAL BUTTON END---------------------------------------------%>
<% }
} %>
<%--------------------------------------------------DIRECT PAYPAL END-------------------------------------------------%>
<% }
} %>

<%-- SW-6499 | remove 'not for sale' text --%> 
<%--<% } else { %>--%>
<%--<international:get name="priceIsWrong"/>--%>
<% } %>

<div id="showPaypalButtonPaymentSuccess<%= widgetId %>" style="color:green;display:none;">
    <international:get name="showPaypalButtonPaymentSuccess"/>
</div>

<div id="successfullyAddedToCartMessage<%= widgetId %>" style="color:green;display:none">
    <international:get name="successfullyAddedToCartMessage"/>
</div>

