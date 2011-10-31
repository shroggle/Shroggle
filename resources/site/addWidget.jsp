<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.presentation.site.ShowAddWidgetAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="addWidget"/>
<%
    final ShowAddWidgetAction action = (ShowAddWidgetAction) request.getAttribute("actionBean");
    final boolean showFromManageItemsPage = action.getAvailableSites() != null;
%>
<div class="windowOneColumn">
<input type="hidden" id="selectContentModule" value="<international:get name="selectContentModule"/>">
<input type="hidden" id="selectExistingItem" value="<international:get name="selectExistingItem"/>">
<input type="hidden" id="selectedSiteId" value="<%= action.getSelectedSiteId() %>">
<input type="hidden" id="insertingElement" value="false">

<h1><international:get name="header"/></h1>
<% if (!showFromManageItemsPage) { %>
<widget:title/>
<% } %>
<div class="emptyError" id="errors"></div>
<div class="inform_mark" style="margin-left:7px;"><international:get name="info"/></div>

<div id="addWidgetElements" class="span-7 addWidgetLeftDiv">
<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get name="basicHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.TEXT %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.TEXT %>">
        <label><international:get name="<%= ItemType.TEXT.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.IMAGE %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.IMAGE %>">
        <label><international:get name="<%= ItemType.IMAGE.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.VIDEO %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.VIDEO %>">
        <label><international:get name="<%= ItemType.VIDEO.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.SCRIPT %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.SCRIPT %>">
        <label><international:get name="<%= ItemType.SCRIPT.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.CONTACT_US %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.CONTACT_US %>">
        <label><international:get name="<%= ItemType.CONTACT_US.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get
        name="navigationHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.MENU %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.MENU %>">
        <label><international:get name="<%= ItemType.MENU.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.GALLERY %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.GALLERY %>">
        <label><international:get name="<%= ItemType.GALLERY.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.ADVANCED_SEARCH %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.ADVANCED_SEARCH %>">
        <label><international:get name="<%= ItemType.ADVANCED_SEARCH.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get name="formsHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.CONTACT_US %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.CONTACT_US %>">
        <label><international:get name="<%= ItemType.CONTACT_US.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.CUSTOM_FORM %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.CUSTOM_FORM %>">
        <label><international:get name="<%= ItemType.CUSTOM_FORM.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.REGISTRATION %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.REGISTRATION %>">
        <label><international:get name="<%= ItemType.REGISTRATION.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.TELL_FRIEND %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.TELL_FRIEND %>">
        <label><international:get name="<%= ItemType.TELL_FRIEND.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.CHILD_SITE_REGISTRATION %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.CHILD_SITE_REGISTRATION %>">
        <label><international:get name="<%= ItemType.CHILD_SITE_REGISTRATION.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get name="mediaHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.TEXT %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.TEXT %>">
        <label><international:get name="<%= ItemType.TEXT.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.IMAGE %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.IMAGE %>">
        <label><international:get name="<%= ItemType.IMAGE.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.GALLERY %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.GALLERY %>">
        <label><international:get name="<%= ItemType.GALLERY.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.VIDEO %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.VIDEO %>">
        <label><international:get name="<%= ItemType.VIDEO.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.SLIDE_SHOW %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.SLIDE_SHOW %>">
        <label><international:get name="<%= ItemType.SLIDE_SHOW.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get
        name="socialMediaCommunicationHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.BLOG %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.BLOG %>">
        <label><international:get name="<%= ItemType.BLOG.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.FORUM %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.FORUM %>">
        <label><international:get name="<%= ItemType.FORUM.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.BLOG_SUMMARY %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.BLOG_SUMMARY %>">
        <label><international:get name="<%= ItemType.BLOG_SUMMARY.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.TELL_FRIEND %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.TELL_FRIEND %>">
        <label><international:get name="<%= ItemType.TELL_FRIEND.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get name="galleryHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="IMAGE_GALLERYWidget"
         ondblclick="createWidgetDelegate();" widgetType="IMAGE_GALLERY" realWidgetType="<%= ItemType.GALLERY %>">
        <label><international:get name="IMAGE_GALLERY"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.ADVANCED_SEARCH %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.ADVANCED_SEARCH %>">
        <label><international:get name="<%= ItemType.ADVANCED_SEARCH.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="DIRECTORYWidget"
         ondblclick="createWidgetDelegate();" widgetType="DIRECTORY" realWidgetType="<%= ItemType.GALLERY %>">
        <label><international:get name="DIRECTORY"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.E_COMMERCE_STORE %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.E_COMMERCE_STORE %>">
        <label><international:get name="<%= ItemType.E_COMMERCE_STORE.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get name="loginsHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.LOGIN %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.LOGIN %>">
        <label><international:get name="<%= ItemType.LOGIN.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.ADMIN_LOGIN %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.ADMIN_LOGIN %>">
        <label><international:get name="<%= ItemType.ADMIN_LOGIN.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get name="storeHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.E_COMMERCE_STORE %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.E_COMMERCE_STORE %>">
        <label><international:get name="<%= ItemType.E_COMMERCE_STORE.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.SHOPPING_CART %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.SHOPPING_CART %>">
        <label><international:get name="<%= ItemType.SHOPPING_CART.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.PURCHASE_HISTORY %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.PURCHASE_HISTORY %>">
        <label><international:get name="<%= ItemType.PURCHASE_HISTORY.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.ADVANCED_SEARCH %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.ADVANCED_SEARCH %>">
        <label><international:get name="<%= ItemType.ADVANCED_SEARCH.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.TAX_RATES %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.TAX_RATES %>">
        <label><international:get name="<%= ItemType.TAX_RATES.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader"><span class="addWidgetHeaderText"><international:get
        name="networkSitesHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.CHILD_SITE_REGISTRATION %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.CHILD_SITE_REGISTRATION %>">
        <label><international:get name="<%= ItemType.CHILD_SITE_REGISTRATION.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.ADMIN_LOGIN %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.ADMIN_LOGIN %>">
        <label><international:get name="<%= ItemType.ADMIN_LOGIN.toString() %>"/></label>
    </div>
</div>

<span class="addWidgetHeader addWidgetHeaderLast"><span class="addWidgetHeaderText"><international:get
        name="votingHeader"/></span></span>

<div class="addWidgetContent">
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.VOTING %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.VOTING %>">
        <label><international:get name="<%= ItemType.VOTING.toString() %>"/></label>
    </div>
    <div class="addWidgetItem" onclick="setSelectedClick(this);"
         onmouseover="setSelected(this);" id="<%= ItemType.MANAGE_VOTES %>Widget"
         ondblclick="createWidgetDelegate();" widgetType="<%= ItemType.MANAGE_VOTES %>">
        <label><international:get name="<%= ItemType.MANAGE_VOTES.toString() %>"/></label>
    </div>
</div>

</div>

<div id="infoHolder" class="span-8 addWidgetRightInfoDiv <% if (showFromManageItemsPage) { %>addWidgetRightInfoDivShrinked<% } %>">
    <% for (ItemType widgetType : ItemType.getItemTypeForAddPage()) { %>
    <span id="<%= widgetType %>Info" style="display:none;"><international:get
            name="<%= widgetType + \"Info\" %>"/></span>
    <% } %>
    <span id="IMAGE_GALLERYInfo" style="display:none;"><international:get
            name="IMAGE_GALLERYInfo"/></span>
    <span id="DIRECTORYInfo" style="display:none;"><international:get
            name="DIRECTORYInfo"/></span>
</div>

<div class="span-8 addWidgetRightInsertDiv <% if (showFromManageItemsPage) { %>addWidgetRightInsertDivExtended<% } %>" id="addWidgetRightInsertDiv">
    <div class="addWidgetRightInsertOptionsText"><international:get
            name="insertOptions"/></div>
    <div class="addWidgetRightInsertInnerDiv">
        <% if (showFromManageItemsPage) { %>
        <div>
            <div>
                <international:get name="selectSiteOwnerForYourNewItem"/>
            </div>
            <select id="availableSites" style="width:280px;"
                    onchange="$('#selectedSiteId').val($('#availableSites').val());reloadElementsArea();">
                <% for (Site site : action.getAvailableSites()) { %>
                <option value="<%= site.getSiteId() %>">
                    <%= site.getTitle() %>
                </option>
                <% } %>
            </select>
        </div>
        <% } %>
        <div>
      <span>
        <input type="radio" checked="checked" name="insertOption" id="insertNew" onclick="clickInsertNew();">
           </span>
            <label for="insertNew"><international:get name="insertNew"/> <span class="addItemType"></span></label>
        </div>

        <div>
            <input type="radio" name="insertOption" id="insertExist" onclick="clickInsertExist();">
            <label for="insertExist"><international:get name="insertExisting"/> <span class="addItemType"></span>
                <international:get name="from"/>:</label>
        </div>
        <div class="addWidgetRightInsertOptionsDiv">
            <select size="1" id="insertItems" style="width:293px;margin:5px 0;" class="insertCopy"></select>
            <input type="checkbox" class="insertCopy" id="onlyCurrentSite" checked="checked" onclick="reloadElementsArea();">
            <label for="onlyCurrentSite"><international:get name="onlyCurrentSite"/></label><br><br>

            <input type="radio" id="insertSame" class="insertCopy" name="insertType" checked="checked">
            <label for="insertSame"><international:get name="useTheSameItem"/></label><br>

            <input type="radio" id="insertCopy" class="insertCopy" name="insertType">
            <label for="insertCopy"><international:get name="createCopy"/></label>
        </div>
        <div class="addWidgetRightInsertOptionsModeDiv">
            <input type="checkbox" id="insertClearFontAntColor" class="insertCopy">
            <label for="insertClearFontAntColor"><international:get name="clearFontColorsSettings"/></label><br>

            <input type="checkbox" id="insertClearBorderAndBackground" class="insertCopy">
            <label for="insertClearBorderAndBackground"><international:get
                    name="clearBorderBackgroundSettings"/></label>
        </div>
    </div>
</div>

<br clear="all">

<div class="buttons_box">
    <input type="button" onmouseout="this.className='but_w73';" id="windowSave" value="Insert"
           onmouseover="this.className='but_w73_Over';" class="but_w73"
           onclick="createWidgetDelegate(<%= showFromManageItemsPage %>);"
           alt="Insert">
    <input type="button" onmouseout="this.className='but_w73';" id="windowCancel" value="Cancel"
           onmouseover="this.className='but_w73_Over';" class="but_w73"
           onclick="closeConfigureWidgetDiv();">
</div>
</div>