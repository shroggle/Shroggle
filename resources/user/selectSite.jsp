<%@ page import="com.shroggle.presentation.account.items.SelectSiteService" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<% final SelectSiteService service = (SelectSiteService) request.getAttribute("service"); %>
<div class="windowOneColumn">
    <h1>Host site selection</h1>

    <div class="emptyError" id="errors"></div>

    <div>
        <label for="selectSiteIds">Select a host site for your new item:</label><br>
        <select size="5" id="selectSiteIds" style="width: 300px;">
            <% for (final Site site : service.getSites()) { %>
                <option value="<%= site.getSiteId() %>"><%= site.getTitle() %></option>
            <% } %>
        </select>

    </div><br><br>

    <div style="text-align: right;">
        <input type="button" value="Save"
               id="windowSave"
               onclick="window.selectSite.save();"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDiv();"
               id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

