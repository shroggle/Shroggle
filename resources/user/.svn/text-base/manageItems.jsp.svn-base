<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.presentation.account.items.ManageItemsAction" %>
<%@ page import="com.shroggle.presentation.account.items.ShowUserItemSite" %>
<%@ page import="com.shroggle.presentation.account.items.*" %>
<%@ page import="com.shroggle.logic.SiteOnItemManager" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.logic.user.items.UserItemsSortType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<international:part part="manageItems"/>
<% final ManageItemsAction action = (ManageItemsAction) request.getAttribute("actionBean");
    Locale.setDefault(new Locale("en")); %>
<html>
<head>
    <title><international:get name="manageYourItems"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript" src="/tinymce/jquery.tinymce.js"></script>
</head>
<body>
<input type="hidden" id="youHaveNoSitesToCreateItem" value="<international:get name="youHaveNoSitesToCreateItem"/>">
<input type="hidden" id="selectContentModule" value="<international:get name="selectContentModule"/>">

<input type="hidden" id="selectOneOrMoreObjectsToDelete"
       value="<international:get name="selectOneOrMoreObjectsToDelete"/>">
<input type="hidden" id="deleteItemsConfirm" value="<international:get name="deleteItemsConfirm"/>">
<input type="hidden" id="removeAccessPermissionsConfirm"
       value="<international:get name="removeAccessPermissionsConfirm"/>">
<input type="hidden" id="selectOneOrMoreObjectsToShare"
       value="<international:get name="selectOneOrMoreObjectsToShare"/>">
<input type="hidden" id="dashboardItemTypes"
       value="<%= ItemType.getItemsForDashboardMenu().toString().replace("[", "").replace("]", "") %>">
<input type="hidden" id="allItemsType"
       value="<%= ItemType.ALL_ITEMS %>">
<input type="hidden" id="formItemsType"
       value="<%= ItemType.getFormItems().toString().replace("[", "").replace("]", "") + "," + ItemType.ORDER_FORM.toString() %>">
<input type="hidden" id="allFormsType"
       value="<%= ItemType.ALL_FORMS %>">

<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <span class="grey_24"><international:get name="manageYourItems"/></span> <br>

            <jsp:include page="/account/dashboardMenu.jsp"/>
            <br>

            <div class="inform_mark_shifted" style="margin:0 0 5px 0;"><international:get
                    name="infoText"/></div>
            <div class="warning" style="margin-bottom:15px;"><international:get
                    name="warningText"/></div>
            <% if (action.getInfoText() != null) { %>
            <div style="color: green; margin-left: 30px;"><%= action.getInfoText() %>
            </div>
            <br>
            <% } %>

            <c:if test="${actionBean.fromRemoveItems}">
                <div style="color: green; margin-left: 30px;">
                    <international:get name="itemHasBeenDeleted">
                        <international:param value="${actionBean.successRemovedItems}"/>
                        <international:param value="${actionBean.itemType}"/>
                    </international:get>
                </div>
                <br>

                <% if (action.getFailedToRemoveItemsMessages() != null) { %>
                <% for (String message : action.getFailedToRemoveItemsMessages()) { %>
                <div style="color: red; margin-left: 30px;">
                    <%= message %>
                </div>
                <br>
                <% } %>
                <% } %>
            </c:if>

            <div id="filterDiv" class="manageFormsFilterDiv">
                <label for="filterItemType"><international:get name="selectFilterByItemType"/></label>
                <select id="filterItemType" onchange="manageItems.updateUserItems();">
                    <% for (String itemName : ManageItemsAction.getFilterByItemTypesNames()) { %>
                    <option value="<%= ManageItemsAction.getItemTypeByItemName(itemName) %>"
                            <% if (action.getItemType() == ManageItemsAction.getItemTypeByItemName(itemName)) { %>selected="selected"<% } %>>
                        <%= itemName %></option>
                    <% } %>
                </select>

                <label for="filterSiteOwner"><international:get name="filterSiteOwnerLabel"/></label>
                <select id="filterSiteOwner" onchange="manageItems.updateUserItems();">
                    <option value="-1"><international:get name="filterSiteOwnerDefaultOption"/></option>
                    <% for (Site site : action.getSites()) { %>
                    <option value="<%= site.getSiteId() %>"
                            <% if (action.getPresetFilterByOwnerSiteId() == site.getSiteId()) { %>selected="selected"<% } %>>
                        <%= new SiteManager(site).getName() %>
                    </option>
                    <% } %>
                </select>
                <%-----------------------------------------------Search-----------------------------------------------%>
                <label for="searchByItemName"><international:get name="searchByItemName"/></label>
                <input type="text" id="searchByItemName" maxlength="250"
                       onkeyup="itemsSearch.searchByName(this.value);">

                <div style="display:inline;visibility:hidden;" id="clearSearch">
                    <img src="/images/cross-circle.png" alt="<international:get name="showAll"/>" style="cursor:pointer;"
                         onclick="itemsSearch.clearSearch();"></div>
                <%-----------------------------------------------Search-----------------------------------------------%>
            </div>

            <div id="userItemsDiv">
                <jsp:include page="manageItemsList.jsp"/>
            </div>

            <br clear="all">

            <div class="buttons_box" align="right">
                <% if (action.isShowRequestContentButton()) {%>
                <input type="button" value="<international:get name="requestNewContentModule"/>"
                       onclick="requestContent.showRequestContent();"
                       onmouseout="this.className='but_w230';"
                       onmouseover="this.className='but_w230_Over';" class="but_w230">
                <% } %>
                <input type="button" value="<international:get name="share"/>"
                       onclick="itemsShare.showShareItem();" onmouseout="this.className='but_w100';"
                       onmouseover="this.className='but_w100_Over';" class="but_w100">
                <input type="button" onclick="itemsDeletion.deleteItems();"
                       value="<international:get name="delete"/>"
                       onmouseout="this.className='but_w100';" onmouseover="this.className='but_w100_Over';"
                       class="but_w100">
                <input type="button"
                       onclick="<% if (!action.isShowAddButton()) { %>alert('<international:get name="youHaveNoSitesToCreateItem"/>');<% } else { %>manageItems.showAddItem();<% } %>"
                       value="<international:get name="addNewItem"/>"
                       onmouseout="this.className='but_w130';" onmouseover="this.className='but_w130_Over';"
                       class="but_w130">
            </div>
            <br>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>