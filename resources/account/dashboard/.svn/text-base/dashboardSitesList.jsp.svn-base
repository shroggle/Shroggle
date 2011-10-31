<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<%@ page import="com.shroggle.presentation.account.dashboard.DashboardSiteType" %>
<%@ page import="com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="dashboardsSitesList"/>
<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final Integer sitesCount = (Integer) request.getAttribute("sitesCount");
    final List<DashboardSiteInfo> allDashboardSitesForCurrentBlock = (List<DashboardSiteInfo>) request.getAttribute("dashboardSiteInfo");
    final Paginator<DashboardSiteInfo> paginator = new Paginator<DashboardSiteInfo>(allDashboardSitesForCurrentBlock, sitesCount);
    final long additionalId = System.nanoTime();
    int selectedScrollableBlock = 0;
    final boolean showPaginator = paginator.getPagesCount() > 1;
    final DashboardSiteInfo selectedSiteInfo = (DashboardSiteInfo) request.getAttribute("selectedSiteInfo"); %>
<!-- root element for scrollable -->
<% if (showPaginator) { %>
<div <% if (!allDashboardSitesForCurrentBlock.contains(selectedSiteInfo)) { %>style="display:none;" <% } %>>
    <div class="dashboardScrollablePrevHolder" style="">
        <div class="dashboardScrollableNextPrevContainer">
            <img src="/images/dashboard/arrow-up-transp.png" alt="Previous" class="prev"
                 onmouseover="this.src='/images/dashboard/arrow-up.png'"
                 onmouseout="this.src='/images/dashboard/arrow-up-transp.png'"/>
        </div>
    </div>
    <div class="scrollable vertical" id="scrollableId<%= additionalId %>">
        <!-- root element for the scrollable elements -->
        <div class="items">
            <% } %>
            <% for (int pageNumber = Paginator.getFirstPageNumber(); pageNumber <= paginator.getPagesCount(); pageNumber++) {
                paginator.setPageNumber(pageNumber);
                final List<DashboardSiteInfo> dashboardSiteInfo = paginator.getItems(); %>
            <div <% if (!showPaginator && !dashboardSiteInfo.contains(selectedSiteInfo)) { %>style="display:none;" <% } %>>
                <% int elementsCount = 0;
                    for (DashboardSiteInfo siteInfo : dashboardSiteInfo) {
                        final StringBuilder className = new StringBuilder("sitesListBackground");
                        className.append((elementsCount % 2 == 0) ? "White" : "Gray");
                        className.append((elementsCount == (sitesCount - 1)) ? "_last" : "");
                        className.append(siteInfo.equals(selectedSiteInfo) ? "_selected" : "");
                        if (siteInfo.isChildSite()) {
                            className.append(" childSite");
                        }
                        final boolean selected = className.toString().contains("_selected");
                        elementsCount++;
                        if (selected) {
                            selectedScrollableBlock = (pageNumber - 1);
                        }
                %>
                <% if (siteInfo.getSiteType() == DashboardSiteType.CREATE_BLUEPRINT_CELL) { %>
                <%---------------------------------------Create Blueprint Link----------------------------------------%>
                <div class="<%= className.toString() %> CREATE_BLUEPRINT_CELL">
                    <stripes:link beanclass="com.shroggle.presentation.site.CreateSiteAction">
                        <stripes:param name="siteType" value="<%= SiteType.BLUEPRINT %>"/>
                        <international:get name="createNewBlueprint"/>
                    </stripes:link>
                </div>
                <%---------------------------------------Create Blueprint Link----------------------------------------%>
                <% } else { %>
                <%------------------------------------------Site`s Info Link------------------------------------------%>
                <div class="<%= className.toString() %>"
                     onclick="showSiteInfo(<%= siteInfo.getSiteId() %>, <%= siteInfo.getChildSiteSettingsId() %>, this, '<%= siteInfo.getSiteType() %>');"
                     ondblclick="window.location = '/site/siteEditPage.action?siteId=<%= siteInfo.getSiteId() %>';"
                     <% if (selected) { %>selected="true"<% } %>>
                    <span>
                        <%= siteInfo.getName() %>
                    </span>
                </div>
                <%------------------------------------------Site`s Info Link------------------------------------------%>
                <% } %>
                <% } %>
                <%----------------------------------------Empty divs (SW-6270)----------------------------------------%>
                <% if (elementsCount > 0 && elementsCount < sitesCount) {
                    for (int i = 0; i < (sitesCount - elementsCount - 1); i++) { %>
                <div class="sitesListBackgroundWhite" style="cursor:default"></div>
                <% } %>
                <div class="sitesListBackgroundWhite_last" style="cursor:default"></div>
                <% } %>
                <%----------------------------------------Empty divs (SW-6270)----------------------------------------%>
            </div>
            <% } %>
            <% if (showPaginator) { %>
        </div>
    </div>
    <div class="dashboardScrollableNextHolder">
        <div class="dashboardScrollableNextPrevContainer">
            <img src="/images/dashboard/arrow-down-transp.png" alt="Next" class="next"
                 onmouseover="this.src='/images/dashboard/arrow-down.png'"
                 onmouseout="this.src='/images/dashboard/arrow-down-transp.png'"/>
        </div>
    </div>
    <script type="text/javascript">
        $(function() {
            // initialize scrollable with mousewheel support
            $("#scrollableId<%= additionalId %>").scrollable({ vertical: true, mousewheel: true, speed: 300, initialIndex:<%= selectedScrollableBlock %>});
        });
    </script>
</div>
<% } %>

