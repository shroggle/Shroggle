<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<%@ page import="com.shroggle.logic.statistics.SiteTrafficInfo" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsManager" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsSortType" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="trafficSites"/>
<% final Paginator<SiteTrafficInfo> paginator = (Paginator<SiteTrafficInfo>) request.getAttribute("paginator");
    final List<SiteTrafficInfo> siteTrafficInfoList = paginator.getItems();
    final StatisticsSortType sortType = (StatisticsSortType) request.getAttribute("trafficSitesSortType"); %>
<table class="tbl_blog" id="statisticsSiteTable" style="width:100%">
    <thead style="cursor:default" class="sortable">
    <tr>
        <td onclick="updateSiteStatistics();" id="name">
            <international:get name="siteName"/>&nbsp;
            <% request.setAttribute("show", (sortType == StatisticsSortType.NAME));
                request.setAttribute("sortFieldType", StatisticsSortType.NAME.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="updateSiteStatistics();" id="hits">
            <international:get name="hits"/>&nbsp;  
            <% request.setAttribute("show", (sortType == StatisticsSortType.HITS));
                request.setAttribute("sortFieldType", StatisticsSortType.HITS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="updateSiteStatistics();" id="visits">
            <international:get name="visits"/>&nbsp; 
            <% request.setAttribute("show", (sortType == StatisticsSortType.VISITS));
                request.setAttribute("sortFieldType", StatisticsSortType.VISITS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="updateSiteStatistics();" id="time">
            <international:get name="timeOnPage"/>&nbsp; 
            <% request.setAttribute("show", (sortType == StatisticsSortType.TIME));
                request.setAttribute("sortFieldType", StatisticsSortType.TIME.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td onclick="updateSiteStatistics();" id="ref_urls">
            <international:get name="refURLs"/>&nbsp; 
            <% request.setAttribute("show", (sortType == StatisticsSortType.REF_URLS));
                request.setAttribute("sortFieldType", StatisticsSortType.REF_URLS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
    </tr>
    </thead>
    <tbody id="sitesTableBody">
    <%if (siteTrafficInfoList == null || siteTrafficInfoList.isEmpty()) {%>
    <tr>
        <td colspan="5"><international:get
                name="emptyTable"/></td>
    </tr>
    <%} else {%>
    <% boolean odd = false; %>

    <% for (final SiteTrafficInfo siteTrafficInfo : siteTrafficInfoList) { %>
    <tr <%= odd ? "class=\"odd\"" : "" %>>
        <td style="vertical-align:top;">
            <% final boolean isNeedLimitation = HtmlUtil.isNeedLimitation(siteTrafficInfo.getSiteName(), 40); %>
            <div <% if (isNeedLimitation) { %>onmouseover="bindTooltip({element:this, contentElement:$(this).find('.content')});"<% } %>>
                <stripes:link beanclass="com.shroggle.presentation.site.traffic.TrafficPagesAction">
                    <stripes:param name="siteId" value="<%= siteTrafficInfo.getSiteId() %>"/>
                    <%= HtmlUtil.limitName(siteTrafficInfo.getSiteName(), 40) %>
                </stripes:link>

                <div style="display:none" class="content">
                    <%= siteTrafficInfo.getSiteName() %>
                </div>
            </div>
        </td>
        <td style="text-align:right" style="vertical-align:top;">
            <%= siteTrafficInfo.getHits() %>
        </td>
        <td style="text-align:right" style="vertical-align:top;">
            <%= siteTrafficInfo.getVisits() %>
        </td>
        <td style="text-align:right" style="vertical-align:top;">
            <%
                long seconds = siteTrafficInfo.getTime() / 1000;
                long days = seconds / (24 * 60 * 60);
                seconds -= days * 24 * 60 * 60;
                long hours = seconds / (60 * 60);
                seconds -= hours * 60 * 60;
                long minutes = seconds / 60;
                seconds -= minutes * 60;
            %>
            <%if (days != 0) {%><%=days%><international:get
                name="days"/><%}%><%=(hours < 10 ? "0" + hours : hours)%><%= ":" + (minutes < 10 ? "0" + minutes : minutes)%><%= ":" + (seconds < 10 ? "0" + seconds : seconds)%>
        </td>
        <td style="vertical-align:top;">
            <%if (!siteTrafficInfo.getRefURLs().isEmpty()) {%>
            <div style="display:inline;text-align:left;float:left">
                <% for (int i = 0; i < siteTrafficInfo.getRefURLsVisitCount().size(); i++) { %>
                <%= siteTrafficInfo.getRefURLsVisitCount().get(i) %>&nbsp;time(s),
                <% final boolean URL_NotSet = siteTrafficInfo.getRefURLs().get(i).equals("direct traffic"); %>
                <% if (!URL_NotSet) { %>
                <a href="<%= siteTrafficInfo.getRefURLs().get(i) %>">
                    <% } %>
                    <%= HtmlUtil.limitName(siteTrafficInfo.getRefURLs().get(i), 38) %>
                    <% if (!URL_NotSet) { %>
                </a>
                <% } %>
                <br/>
                <% } %>

                <% for (int i = 0; i < Math.abs(siteTrafficInfo.getRefURLsVisitCount().size() - StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT); i++) { %>
                <br/>
                <% } %>
            </div>
            <div style="display:inline;text-align:right;float:right"><a
                    href="javascript:showAllRefUrlsForSite(<%= siteTrafficInfo.getSiteId() %>);"
                    id="showAll<%= siteTrafficInfo.getSiteId() %>"><international:get name="showAll"/></a></div>
            <%} else {%>
            <international:get name="noRefUrls"/>
            <% for (int i = 0; i < StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT; i++) { %>
            <br/>
            <% } %>
            <%}%>
        </td>
    </tr>
    <% odd = !odd; %>
    <% } %>
    <%}%>
    </tbody>
</table>

<input type="hidden" id="paginatorDivToUpdate" value="sitesTableDiv"/>
<% request.setAttribute("updatePaginatorItemsFunction", "updateSiteStatistics"); %>
<jsp:include page="/paginator/paginator.jsp"/>