<%@ page import="com.shroggle.presentation.purchaseHistory.ConfigurePurchaseHistoryService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="purchaseHistory"/>
<% final ConfigurePurchaseHistoryService service = (ConfigurePurchaseHistoryService) request.getAttribute("purchaseHistoryService"); %>
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
<input type="hidden" id="PurchaseHisotryNameIsNullOrEmpty"
       value="<international:get name="PurchaseHisotryNameIsNullOrEmpty"/>"/>
<input type="hidden" id="showPurchaseHistoryHeader" value="<%= service.getPurchaseHistory().isShowDescription() %>"/>
<input type="hidden" id="selectedPurchaseHistoryId" value="<%= service.getPurchaseHistory().getId() %>">

<div id="PurchaseHistoryHeader" style="display:none"><%= service.getPurchaseHistory().getDescription() %>
</div>
<div class="itemSettingsWindowDiv">
    <h1><international:get name="subHeader"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="purchaseHistoryService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="purchaseHistoryErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="purchaseHistoryReadOnlyMessage">You have only read-only
        access to this module.</div>

    <div class="inform_mark" style="margin-bottom: 10px;">
        <international:get name="explan"/>
    </div>

    <div>
        <label for="configurePurchaseHistoryName"><international:get name="name"/></label><br>
        <input type="text" id="configurePurchaseHistoryName" maxlength="250" size="40" style="width: 220px;"
               value="<%= service.getPurchaseHistory().getName() %>" onchange="setWindowSettingsChanged();">

        <span style="padding-left:20px;">
            <international:get name="desc"/>&nbsp;<a id="editPurchaseHistoryDescLink"
                href="javascript:showConfigureItemDescription({id:'PurchaseHistory'});"><international:get
                name="editDesc"/></a>
        </span>
    </div>
    <br><br>

    <b><international:get name="settings"/></b><br><br>

    <div style="margin-left: 10px;">
        <input type="checkbox" id="configurePurchaseHistoryShowImage"
            <%= service.getPurchaseHistory().isShowProductImage() ? "checked=\"checked\"" : "" %>
               onclick="setWindowSettingsChanged();">
        <label for="configurePurchaseHistoryShowImage">
            <international:get name="showImage"/>
        </label><br><br>

        <label for="configurePurchaseHistoryImageWight"><international:get name="imageDimensions"/></label>
        <input type="text" id="configurePurchaseHistoryImageWight" maxlength="4" size="4"
               onKeyPress="return numbersOnly(this, event);"
               value="<%= service.getPurchaseHistory().getImageWidth() %>" onchange="setWindowSettingsChanged();">&nbsp;x&nbsp;
        <input type="text" id="configurePurchaseHistoryImageHeight" maxlength="4" size="4"
               onKeyPress="return numbersOnly(this, event);"
               value="<%= service.getPurchaseHistory().getImageHeight() %>"
               onchange="setWindowSettingsChanged();">px<br><br>

        <input type="checkbox" id="configurePurchaseHistoryShowProductDescription"
            <%= service.getPurchaseHistory().isShowProductDescription() ? "checked=\"checked\"" : "" %>
               onclick="setWindowSettingsChanged();">
        <label for="configurePurchaseHistoryShowProductDescription">
            <international:get name="showProductDescription"/>
        </label>
    </div>

    <br/>

    <div style="text-align: right;">

    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configurePurchaseHistoryButtons">
        <input type="button" value="Apply" id="windowApply" onclick="configurePurchaseHistory.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onclick="configurePurchaseHistory.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>
