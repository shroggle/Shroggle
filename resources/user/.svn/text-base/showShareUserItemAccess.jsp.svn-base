<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.presentation.account.items.ShowShareUserItem" %>
<%@ page import="com.shroggle.presentation.account.items.ShowShareUserItemService" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="showShareUserItemAccess"/>
<%
    final ShowShareUserItemService service =
            (ShowShareUserItemService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <h1>Share Content Between My Sites</h1><br>

    <div class="inform_mark">
        The items you have chosen to share are listed below, please select the name of the site or
        sites that you wish to share it with. Note that the permission level chosen will be
        applied to all selected items and sites.
    </div>
    <br>

    <div style="overflow-y: auto; max-height: 100px;">
        <% for (final ShowShareUserItem item : service.getItems()) { %>
        <input type="hidden" class="itemIdAndType" value="<%= item.getItemId() %>,<%= item.getItemType() %>">

        <h2><%= item.getItemType() %>: <%= item.getItemName() %> owned by <%= item.getItemSiteName() %> site</h2>
        <% } %>
    </div>
    <br>

    Will be shared with:<br>

    <div style="overflow-y: auto; height: 100px;">
        <% for (final Site site : service.getSites()) { %>
        <input type="checkbox" id="targetSite<%= site.getSiteId() %>" class="targetSiteId"
               value="<%= site.getSiteId() %>">
        <label for="targetSite<%= site.getSiteId() %>">
            <% if (new SiteManager(site).isBlueprint()) { %>
            <international:get name="blueprint"/>:&nbsp;
            <% } %><%= HtmlUtil.limitName(site.getTitle(), 40) %>
        </label><br>
        <% } %>
    </div>
    <br>
    Permission Level:<br>
    <input type="radio" checked="checked" name="itemAccess" id="shareTypeRead"> <label for="shareTypeRead">Read
    Only</label><br>
    <input type="radio" name="itemAccess" id="shareTypeEdit"> <label for="shareTypeEdit">Editing Access</label>

    <div id="BackgroundSettingsButtons" align="right">
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="Share" onclick="itemsShare.shareItem();"/>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';" id="shareUserItemsCancel" value="Cancel"/>
    </div>
</div>