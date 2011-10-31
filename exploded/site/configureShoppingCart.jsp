<%@ page import="com.shroggle.presentation.shoppingCart.ConfigureShoppingCartService" %>
<%@ page import="com.shroggle.entity.DraftShoppingCart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="shoppingCart"/>
<% final ConfigureShoppingCartService service = (ConfigureShoppingCartService) request.getAttribute("shoppingCartService"); %>
<% final DraftShoppingCart draftShoppingCart = service.getShoppingCart(); %>
<input type="hidden" value="Header / Description" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="Display Header / Description" id="itemDescriptionDisplayHeader"/>
<input type="hidden" id="showShoppingCartHeader" value="<%= service.getShoppingCart().isShowDescription() %>"/>
<input type="hidden" id="ShoppingCartNameIsNullOrEmpty" value="<international:get name="ShoppingCartNameIsNullOrEmpty"/>"/>
<div id="ShoppingCartHeader" style="display:none"><%= service.getShoppingCart().getDescription() %></div>
<input type="hidden" id="selectedShoppingCartId" value="<%= service.getShoppingCart().getId() %>">

<div class="itemSettingsWindowDiv">
    <h1>Edit Shopping Cart Module: Module Settings</h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="shoppingCartService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="shoppingCartErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="shoppingCartReadOnlyMessage">You have only read-only
        access to this module.</div>

    <div class="inform_mark" style="margin-bottom: 10px;">
        If you have a store on your site here, and you want your purchasers to be able to buy multiple items at a
        time, then you will need to create a Shopping Cart page. They will see this page each time that they select
        the 'add to cart' button on a product page.
    </div>

    <div>
        <label for="configureShoppingCartName">Shopping Cart Name</label><br>
        <input type="text" id="configureShoppingCartName" maxlength="250" size="40" style="width: 220px;"
           value="<%= draftShoppingCart.getName() %>" onchange="setWindowSettingsChanged();">

        <span style="padding-left:20px;">
            Header / Description <a id="editShoppingCartDescLink" href="javascript:showConfigureItemDescription({id:'ShoppingCart'});">Edit</a>
        </span>
    </div><br><br>

    <b>Shopping Cart interface settings</b><br><br>
    <div style="margin-left: 10px;">
        <input type="checkbox" id="configureShoppingCartImageInFirstColumn"
               <%= draftShoppingCart.isImageInFirstColumn() ? "checked=\"checked\"" : "" %> onclick="setWindowSettingsChanged();">
        <label for="configureShoppingCartImageInFirstColumn">
            Show product image in the first column
        </label><br><br>

        <label for="configureShoppingCartImageWight">Product image dimensions:</label>
        <input type="text" id="configureShoppingCartImageWight" maxlength="4" size="4" onKeyPress="return numbersOnly(this, event);"
               value="<%= draftShoppingCart.getImageWidth() %>" onchange="setWindowSettingsChanged();">&nbsp;x&nbsp;
        <input type="text" id="configureShoppingCartImageHeight" maxlength="4" size="4" onKeyPress="return numbersOnly(this, event);"
               value="<%= draftShoppingCart.getImageHeight() %>" onchange="setWindowSettingsChanged();">px<br><br>

        <input type="checkbox" id="configureShoppingCartDescriptionAfterName" onclick="setWindowSettingsChanged();"
               <%= draftShoppingCart.isDescriptionAfterName() ? "checked=\"checked\"" : "" %>>
        <label for="configureShoppingCartDescriptionAfterName">
            Show product description next to the product name
        </label>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureShoppingCartButtons">
        <input type="button" value="Apply" id="windowApply"
               onclick="window.configureShoppingCart.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave"
               onclick="window.configureShoppingCart.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

<div id="iamgeWhatsThis" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow: auto; height: 50px; width: 320px; padding: 10px; text-align: left;">
            <div id="helpText">&nbsp;</div>
            <br><br>
        </div>
        <br clear="all"><br>

        <p align="right">
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" value="Close"
                   onclick="return false; closeConfigureWidgetDiv();"/>
        </p>
    </div>
</div>

