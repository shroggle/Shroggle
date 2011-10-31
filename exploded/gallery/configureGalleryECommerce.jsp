<%@ page import="com.shroggle.entity.DraftItem" %>
<%@ page import="com.shroggle.entity.WidgetItem" %>
<%@ page import="com.shroggle.logic.site.page.PageSettingsManager" %>
<%@ page import="com.shroggle.presentation.gallery.ConfigureGalleryService" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="siteItems" tagdir="/WEB-INF/tags/siteItems" %>
<%
    final ConfigureGalleryService service = (ConfigureGalleryService) request.getAttribute("galleryService");
%>
<international:part part="configureGallery"/>

<input type="checkbox" style="vertical-align: middle; padding:0;margin:0;" id="configureGalleryECommerceEnable"
       onclick="changeConfigureGalleryECommerceEnable();">
<label for="configureGalleryECommerceEnable" style="font-weight:bold;position:relative;top:2px">Enable E-Commerce for
    this gallery</label>

<div class="eCommerceSettingsBlock">
    By enabling the e-commerce feature you will turn your gallery into an online store. Each product in your
    gallery will purchasable by your site visitors. You will be able track orders and customers.
    <br><br>

    <div class="eCommerceMerchantEmailDiv">
        <label for="configureGalleryECommerceMerchantEmail">Merchant email address:</label>
        <input type="text" id="configureGalleryECommerceMerchantEmail"
               onchange="setWindowSettingsChanged();" value="<%= service.getUser().getEmail() %>"
               class="configureGalleryECommerce">
    </div>

    <div style="clear:both;"></div>
    <div class="eCommerceFilledFormItemDiv">
        <label for="configureGalleryECommerceFullPrice">Full price:</label>
        <br>
        <select size="1" id="configureGalleryECommerceFullPrice" class="configureGalleryECommerce"
                onchange="setWindowSettingsChanged();">
            <option>n/a</option>
        </select>
    </div>

    <div class="eCommerceFilledFormItemDiv">
        <label for="configureGalleryECommerceProductName">Product name:</label>
        <br>
        <select size="1" id="configureGalleryECommerceProductName" class="configureGalleryECommerce"
                onchange="setWindowSettingsChanged();">
            <option>n/a</option>
        </select>
    </div>

    <div class="eCommerceFilledFormItemDiv">
        <label for="configureGalleryECommerceProductDescription">Product description:</label>
        <br>
        <select size="1" id="configureGalleryECommerceProductDescription" class="configureGalleryECommerce"
                onchange="setWindowSettingsChanged();">
            <option>n/a</option>
        </select>
    </div>

    <div class="eCommerceFilledFormItemDiv">
        <label for="configureGalleryECommerceProductImage">Product image:</label>
        <br>
        <select size="1" id="configureGalleryECommerceProductImage" class="configureGalleryECommerce"
                onchange="setWindowSettingsChanged();">
            <option>n/a</option>
        </select>
    </div>
    <div style="clear:both;"></div>

    <br>
</div>

<b>Our web site tracks your orders</b>

<div class="eCommerceSettingsBlock">
    <div class="inform_mark" style="line-height:16px;">
        If 'order tracking' is selected all purchases will be recorded in your 'Order management form'.
        You will be able to review and modify your customers and their purchases by editing the records of the
        order management form. If you select this option all purchasers will be required to register on your
        site before they can make a purchase.
        <span class="mark2">
            <a href="javascript:showConfigureGalleryECommerceTrackOrderInfo();">More Info</a>
        </span>
    </div>

    <div style="display: none;" id="configureGalleryECommerceInfo">
        <div class="windowOneColumn">
            Each purchase made in this store is represented by a record in 'Order management'. If you would like to
            edit these records or to edit the fields or field labels in the order management form itself, go to your
            dashboard and select the 'Manage Forms & Records' interface', and then select the Order Management for
            [product form name].<br><br>

            <p align="right">
                <input type="button" onclick="closeConfigureWidgetDiv();"
                       class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';" value="Close">
            </p>
        </div>
    </div>
</div>

<b>Customers have to be registered / logged on your site</b>

<div class="eCommerceSettingsBlock">
    <label for="configureGalleryECommerceRegistrationForm">Select account registration form for your customers:</label>
    <select size="1" id="configureGalleryECommerceRegistrationForm" class="configureGalleryECommerce"
            onchange="setWindowSettingsChanged();">
        <% for (final DraftItem registrationForm : service.getRegistrationFormsForVoters()) { %>
        <option value="<%= registrationForm.getFormId() %>">
            <%= HtmlUtil.limitName(registrationForm.getName()) %>
        </option>
        <% } %>
    </select>
    <span class="mark2">
        <a href="javascript:showConfigureGalleryECommerceRegistrationFormInfo();">What's this?</a>
    </span>
    <br>

    <div style="display: none;" id="configureGalleryECommerceRegistrationFormInfo">
        <div class="windowOneColumn">
            This form can be used to collect all information you need to provide product selling service.<br><br>

            <p align="right">
                <input type="button" onclick="closeConfigureWidgetDiv();"
                       class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';" value="Close">
            </p>
        </div>
    </div>
</div>

<div class="eCommerceSettingsBlock">
    <label for="configureECommercePurchaseHistory">Select a Purchase History item:</label>
    <select size="1" id="configureECommercePurchaseHistory" class="configureGalleryECommerce"
            onchange="setWindowSettingsChanged();">
        <option>No Purchase History</option>
        <% for (final WidgetItem purchaseHistoryWidget : service.getPurchaseHistoryWidgets()) { %>
        <option value="<%= purchaseHistoryWidget.getDraftItem().getId() %>"
                pageId="<%= purchaseHistoryWidget.getPage().getPageId() %>">
            <%= HtmlUtil.limitName(new PageSettingsManager(purchaseHistoryWidget.getPageSettings()).getName())%>
            / <%= HtmlUtil.limitName(purchaseHistoryWidget.getDraftItem().getName()) %>
        </option>
        <% } %>
    </select>
</div>

<b>Purchase interface</b>

<div class="eCommerceSettingsBlock">
    <input type="radio" id="configureGalleryECommerceDirectPaypal" class="configureGalleryECommerce"
           name="configureGalleryECommercePurchase" checked="checked"
           onclick="changeConfigureGalleryECommerceUseCart();">
    <label for="configureGalleryECommerceDirectPaypal">Go directly to PayPal payment site</label><br>

    <input type="radio" id="configureGalleryECommerceUseCart" class="configureGalleryECommerce"
           name="configureGalleryECommercePurchase" onclick="changeConfigureGalleryECommerceUseCart();">
    <label for="configureGalleryECommerceUseCart">Add product to shopping cart</label>

    <div style="margin-top: 10px;">
        <label for="configureGalleryECommerceCart">Select a Shopping Cart item on a page:</label>
        <select size="1" id="configureGalleryECommerceCart" class="configureGalleryECommerce"
                onchange="setWindowSettingsChanged();">
            <option>Select shopping cart</option>
            <% for (final WidgetItem shoppingCartWidget : service.getShoppingCartWidgets()) { %>
            <option value="<%= shoppingCartWidget.getDraftItem().getId() %>"
                    pageId="<%= shoppingCartWidget.getPage().getPageId() %>">
                <%= HtmlUtil.limitName(new PageSettingsManager(shoppingCartWidget.getPageSettings()).getName())%>
                / <%= HtmlUtil.limitName(shoppingCartWidget.getDraftItem().getName()) %>
            </option>
            <% } %>
        </select>
    </div>
</div>

