<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<%@ page import="com.shroggle.logic.statistics.PageTrafficInfo" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsManager" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsSortType" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="trafficPages"/>
<% final Paginator<PageTrafficInfo> paginator = (Paginator<PageTrafficInfo>) request.getAttribute("paginator");
    final List<PageTrafficInfo> pageTrafficInfoList = paginator.getItems(); %>
<% final StatisticsSortType sortType = (StatisticsSortType) request.getAttribute("trafficPagesSortType"); %>
<table class="tbl_blog" style="width:100%" id="statisticsPageTable">
    <thead style="cursor:default" class="sortable">
    <tr>
        <td id="NAME"
            onclick="updatePageStatistics();">
            <international:get name="pageName"/>&nbsp;
            <% request.setAttribute("show", (sortType == StatisticsSortType.NAME));
                request.setAttribute("sortFieldType", StatisticsSortType.NAME.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="HITS"
            onclick="updatePageStatistics();">
            <international:get name="hits"/>&nbsp;
            <% request.setAttribute("show", (sortType == StatisticsSortType.HITS));
                request.setAttribute("sortFieldType", StatisticsSortType.HITS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="VISITS"
            onclick="updatePageStatistics();">
            <international:get name="visits"/>&nbsp;
            <% request.setAttribute("show", (sortType == StatisticsSortType.VISITS));
                request.setAttribute("sortFieldType", StatisticsSortType.VISITS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="TIME"
            onclick="updatePageStatistics();">
            <international:get name="timeOnPage"/>&nbsp;
            <% request.setAttribute("show", (sortType == StatisticsSortType.TIME));
                request.setAttribute("sortFieldType", StatisticsSortType.TIME.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="SEARCH_TERMS"
            onclick="updatePageStatistics();">
            <international:get name="searchTerms"/>&nbsp;
            <% request.setAttribute("show", (sortType == StatisticsSortType.SEARCH_TERMS));
                request.setAttribute("sortFieldType", StatisticsSortType.SEARCH_TERMS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="REF_URLS"
            onclick="updatePageStatistics();">
            <international:get name="refURLs"/>
            <% request.setAttribute("show", (sortType == StatisticsSortType.REF_URLS));
                request.setAttribute("sortFieldType", StatisticsSortType.REF_URLS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
    </tr>
    </thead>
    <tbody id="pagesTableBody">
    <%if (pageTrafficInfoList == null || pageTrafficInfoList.isEmpty()) {%>
    <tr>
        <td colspan="6"><international:get
                name="emptyTable"/></td>
    </tr>
    <%} else {%>
    <% boolean odd = false; %>

    <% for (final PageTrafficInfo pageTrafficInfo : pageTrafficInfoList) { %>
    <tr <%= odd ? "class=\"odd\"" : "" %>>
        <td style="vertical-align:top;">
            <% final boolean isNeedLimitation = HtmlUtil.isNeedLimitation(pageTrafficInfo.getPageName(), 40); %>
            <div <% if (isNeedLimitation) { %>onmouseover="bindTooltip({element:this, contentElement:$(this).find('.content')});"<% } %>>
                <stripes:link beanclass="com.shroggle.presentation.site.traffic.TrafficVisitorsAction">
                    <stripes:param name="pageId" value="<%=pageTrafficInfo.getPageId()%>"/>
                    <%= HtmlUtil.limitName(pageTrafficInfo.getPageName(), 40) %>
                </stripes:link>

                <div style="display:none" class="content">
                    <%= pageTrafficInfo.getPageName() %>
                </div>
            </div>
        </td>
        <td style="text-align:right;vertical-align:top;">
            <%=pageTrafficInfo.getHits()%>
        </td>
        <td style="text-align:right;vertical-align:top;">
            <%=pageTrafficInfo.getVisits()%>
        </td>
        <td style="text-align:right;vertical-align:top;">
            <%
                long seconds = pageTrafficInfo.getTime() / 1000;
                long days = seconds / (24 * 60 * 60);
                seconds -= days * 24 * 60 * 60;
                long hours = seconds / (60 * 60);
                seconds -= hours * 60 * 60;
                long minutes = seconds / 60;
                seconds -= minutes * 60;
            %>
            <% if (days != 0) { %><%= days %><international:get
                name="days"/><% } %><%= (hours < 10 ? "0" + hours : hours) %><%= (minutes < 10 ? ":0" + minutes : ":" + minutes)%><%= (seconds < 10 ? ":0" + seconds : ":" + seconds)%>
        </td>
        <td style="vertical-align:top;">
            <%if (!pageTrafficInfo.getRefTerms().isEmpty()) {%>
            <div style="display:inline;text-align:left;float:left">
                <% for (int i = 0; i < pageTrafficInfo.getRefTermsVisitCount().size(); i++) { %>
                <div style="display:inline;text-align:left;float:left"><%= pageTrafficInfo.getRefTermsVisitCount().get(i) %>
                    &nbsp;time(s),&nbsp;<span><%= HtmlUtil.limitName(pageTrafficInfo.getRefTerms().get(i), 30)%></span>
                </div>
                <br/>
                <% } %>


                <% for (int i = 0; i < Math.abs(pageTrafficInfo.getRefTermsVisitCount().size() - StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT); i++) { %>
                <br/>
                <% } %>
            </div>
            <div style="display:inline;text-align:right;float:right"><a
                    href="javascript:showAllSearchTermsForPage(<%= pageTrafficInfo.getPageId() %>);"
                    id="showAllTerms<%=pageTrafficInfo.getPageId()%>"><international:get name="showAll"/></a></div>
            <%} else {%>
            <international:get name="noSearchTerms"/>
            <% for (int i = 0; i < StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT; i++) { %>
            <br/>
            <% } %>
            <%}%>
        </td>
        <td style="vertical-align:top;">
            <%if (!pageTrafficInfo.getRefURLs().isEmpty()) {%>
            <div style="display:inline;text-align:left;float:left">
                <% for (int i = 0; i < pageTrafficInfo.getRefURLsVisitCount().size(); i++) { %>
                <%= pageTrafficInfo.getRefURLsVisitCount().get(i) %>&nbsp;time(s),
                <% final boolean URL_NotSet = pageTrafficInfo.getRefURLs().get(i).equals("direct traffic"); %>
                <% if (!URL_NotSet) { %>
                <a href="<%= pageTrafficInfo.getRefURLs().get(i) %>">
                    <% } %>
                    <%= HtmlUtil.limitName(pageTrafficInfo.getRefURLs().get(i), 35) %>
                    <% if (!URL_NotSet) { %>
                </a>
                <% } %>
                <br/>
                <% } %>

                <% for (int i = 0; i < Math.abs(pageTrafficInfo.getRefURLsVisitCount().size() - StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT); i++) { %>
                <br/>
                <% } %>
            </div>
            <div style="display:inline;text-align:right;float:right"><a
                    href="javascript:showAllRefUrlsForPage(<%= pageTrafficInfo.getPageId() %>);"
                    id="showAll<%= pageTrafficInfo.getPageId() %>"><international:get name="showAll"/></a></div>
            <% } else { %>
            <international:get name="noRefUrls"/>
            <% for (int i = 0; i < StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT; i++) { %>
            <br/>
            <% } %>
            <% } %>
        </td>
    </tr>
    <% odd = !odd; %>
    <% } %>
    <% } %>
    </tbody>
</table>

<input type="hidden" id="paginatorDivToUpdate" value="pagesTableDiv"/>
<% request.setAttribute("updatePaginatorItemsFunction", "updatePageStatistics"); %>
<jsp:include page="/paginator/paginator.jsp"/>