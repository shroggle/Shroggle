<%@ page import="com.shroggle.presentation.Action" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficVisitorsAction" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficPagesAction" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficSitesAction" %>
<%@ page import="com.shroggle.presentation.account.items.ManageItemsAction" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="dashboardMenu"/>
<% Action action = (Action) request.getAttribute("actionBean"); %>
<div class="topmenu2">
    <div class="menu_dashboard">
        <div class="topmenulink" onmouseover="dashboardMenu.setSelected(this);"
             onmouseout="dashboardMenu.setUnselected(this);">
            <div>
                <a href="/site/createSite.action" class="createSiteLink"><international:get name="createSite"/></a>
            </div>
        </div>
        <div class="menuline">|</div>
        <% for (ItemType itemType : ItemType.getItemsForDashboardMenu()) {
            final boolean selected = (action instanceof ManageItemsAction && ((ManageItemsAction) action).getItemType() == itemType); %>

        <div class="topmenulink<%= selected ? " selected" : "" %>"
             <% if (!selected) { %>onmouseover="dashboardMenu.setSelected(this);"
             onmouseout="dashboardMenu.setUnselected(this);" <% } %>>
            <div>
                <a id="dashboardMenuLink<%= itemType.toString() %>"
                   previousHref="<%= ("/user/manageItems.action?itemType=" + itemType.toString()) %>"
                   href="<%= selected ? "javascript:void(0);" : ("/user/manageItems.action?itemType=" + itemType.toString()) %>"
                   style="<%= selected ? "cursor:default;" : "" %>"><international:get
                        name="<%= itemType.toString() %>"/></a>
            </div>
        </div>

        <div class="menuline">|</div>
        <% } %>
        <% final boolean selected = (action instanceof TrafficVisitorsAction || action instanceof TrafficPagesAction || action instanceof TrafficSitesAction); %>
        <div class="topmenulink<%= selected ? " selected" : "" %>"
             <% if (!selected) { %>onmouseover="dashboardMenu.setSelected(this);"
             onmouseout="dashboardMenu.setUnselected(this);"<% } %>>
            <div>
                <a href="/site/trafficSites.action"><international:get name="dashboardTraffic"/></a>
            </div>
        </div>
        <br clear="all">
    </div>
</div>