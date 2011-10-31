<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.logic.form.FilledFormManager" %>
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsManager" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsSortType" %>
<%@ page import="com.shroggle.logic.statistics.VisitorTrafficInfo" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="trafficVisitors"/>
<%  final Paginator<VisitorTrafficInfo> paginator = (Paginator<VisitorTrafficInfo>) request.getAttribute("paginator");
    final List<VisitorTrafficInfo> visitorTrafficInfoList = paginator.getItems();
    final int pageId = (Integer) request.getAttribute("trafficVisitorsPageId"); 
    final int siteId = (Integer) request.getAttribute("trafficVisitorsSiteId");
    final StatisticsSortType sortType = (StatisticsSortType) request.getAttribute("trafficVisitorsSortType"); %>
<table class="tbl_blog" style="width:100%" id="statisticsVisitorsTable">
    <thead style="cursor:default" class="sortable">
    <tr>
        <td id="VISITOR_ID" 
            onclick="updateVisitorsStatistics();">
            <international:get name="visitorId"/> 
            <% request.setAttribute("show", (sortType == StatisticsSortType.VISITOR_ID));
                request.setAttribute("sortFieldType", StatisticsSortType.VISITOR_ID.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="VISIT_DATE" 
            onclick="updateVisitorsStatistics();">
            <international:get name="lastVisit"/> 
            <% request.setAttribute("show", (sortType == StatisticsSortType.VISIT_DATE));
                request.setAttribute("sortFieldType", StatisticsSortType.VISIT_DATE.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="HITS" 
            onclick="updateVisitorsStatistics();">
            <international:get name="hits"/>  
            <% request.setAttribute("show", (sortType == StatisticsSortType.HITS));
                request.setAttribute("sortFieldType", StatisticsSortType.HITS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="TIME" 
            onclick="updateVisitorsStatistics();">
            <international:get name="timeOnPage"/>  
            <% request.setAttribute("show", (sortType == StatisticsSortType.TIME));
                request.setAttribute("sortFieldType", StatisticsSortType.TIME.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="SEARCH_TERMS" 
            onclick="updateVisitorsStatistics();">
            <international:get name="searchTerms"/> 
            <% request.setAttribute("show", (sortType == StatisticsSortType.SEARCH_TERMS));
                request.setAttribute("sortFieldType", StatisticsSortType.SEARCH_TERMS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
        <td id="REF_URLS" 
            onclick="updateVisitorsStatistics();">
            <international:get name="refURLs"/> 
            <% request.setAttribute("show", (sortType == StatisticsSortType.REF_URLS));
                request.setAttribute("sortFieldType", StatisticsSortType.REF_URLS.toString()); %>
            <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
        </td>
    </tr>
    </thead>
    <tbody id="visitorsTableBody">
    <%if (visitorTrafficInfoList == null || visitorTrafficInfoList.isEmpty()) {%>
    <tr>
        <td colspan="6"><international:get name="emptyTable"/></td>
    </tr>
    <%} else {%>
    <% boolean odd = false; %>

    <% for (final VisitorTrafficInfo visitorTrafficInfo : visitorTrafficInfoList) { %>
    <tr <%= odd ? "class=\"odd\"" : "" %>>
        <td style="text-align:right; vertical-align:top;">
            <% if (visitorTrafficInfo.getVisitorId() != null &&
                     FilledFormManager.getFirstRegistrationFilledFormForSite(visitorTrafficInfo.getVisitorId(), siteId) != null) { %>
            <a href="javascript:manageRegistrants.showEditVisitor(<%= visitorTrafficInfo.getVisitorId() %>, <%= siteId %>)">
                <%= visitorTrafficInfo.getPageVisitorId() %>
            </a>
            <% } else { %>
            <%= visitorTrafficInfo.getPageVisitorId() %>
            <% } %>
        </td>
        <td style="text-align:right; vertical-align:top;">
            <%= visitorTrafficInfo.getLastVisit() %>
        </td>
        <td style="text-align:right; vertical-align:top;">
            <%= visitorTrafficInfo.getHits() %>
        </td>
        <td style="text-align:right; vertical-align:top;">
            <%
                long seconds = visitorTrafficInfo.getTime() / 1000;
                long days = seconds / (24 * 60 * 60);
                seconds -= days * 24 * 60 * 60;
                long hours = seconds / (60 * 60);
                seconds -= hours * 60 * 60;
                long minutes = seconds / 60;
                seconds -= minutes * 60;
            %>
            <%if (days != 0) {%><%=days%><international:get
                name="days"/><%}%><%= (hours < 10 ? "0" + hours : hours) %><%= ":" + (minutes < 10 ? "0" + minutes : minutes)%><%= ":" + (seconds < 10 ? "0" + seconds : seconds) %>
        </td>
        <td style="vertical-align:top;">
            <%if (!visitorTrafficInfo.getRefTerms().isEmpty()) {%>
            <div style="display:inline;text-align:left;float:left">
                <% for (int i = 0; i < visitorTrafficInfo.getRefTermsVisitCount().size(); i++) { %>
                <%=visitorTrafficInfo.getRefTermsVisitCount().get(i)%>&nbsp;time(s),&nbsp;
                <span><%= HtmlUtil.limitName(visitorTrafficInfo.getRefTerms().get(i), 35) %></span>
                <br/>
                <% } %>

                <% for (int i = 0; i < Math.abs(visitorTrafficInfo.getRefTermsVisitCount().size() - StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT); i++) { %>
                <br/>
                <% } %>
            </div>
            <div style="display:inline;text-align:right;float:right"><a
                    href="javascript:showAllSearchTermsForVisitor(<%= visitorTrafficInfo.getPageVisitorId() %>, <%= pageId %>);"
                    id="showAllTerms<%= visitorTrafficInfo.getPageVisitorId() %>"><international:get
                    name="showAll"/></a>
            </div>
            <%} else {%>
            <international:get name="noSearchTerms"/>
            <%}%>
        </td>
        <td style="vertical-align:top;">
            <%if (!visitorTrafficInfo.getRefURLs().isEmpty()) {%>
            <div style="display:inline;text-align:left;float:left">
                <% for (int i = 0; i < visitorTrafficInfo.getRefURLsVisitCount().size(); i++) { %>
                <%= visitorTrafficInfo.getRefURLsVisitCount().get(i) %>&nbsp;time(s),
                <% final boolean URL_NotSet = visitorTrafficInfo.getRefURLs().get(i).equals("direct traffic"); %>
                <% if (!URL_NotSet) { %>
                <a href="<%= visitorTrafficInfo.getRefURLs().get(i) %>">
                    <% } %>
                    <%= HtmlUtil.limitName(visitorTrafficInfo.getRefURLs().get(i), 35) %>
                    <% if (!URL_NotSet) { %>
                </a>
                <% } %>
                <br/>
                <% } %>

                <% for (int i = 0; i < Math.abs(visitorTrafficInfo.getRefURLsVisitCount().size() - StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT); i++) { %>
                <br/>
                <% } %>
            </div>
            <div style="display:inline;text-align:right;float:right"><a
                    href="javascript:showAllRefUrlsForVisitor(<%= visitorTrafficInfo.getPageVisitorId() %>, <%= pageId %>);"
                    id="showAll<%= visitorTrafficInfo.getPageVisitorId() %>"><international:get name="showAll"/></a>
            </div>
            <%} else {%>
            <international:get name="noRefUrls"/>
            <%}%>
        </td>
    </tr>
    <% odd = !odd; %>
    <% } %>
    <%}%>
    </tbody>
</table>

<input type="hidden" id="paginatorDivToUpdate" value="visitorsTableDiv"/>
<% request.setAttribute("updatePaginatorItemsFunction", "updateVisitorsStatistics"); %>
<jsp:include page="/paginator/paginator.jsp"/>