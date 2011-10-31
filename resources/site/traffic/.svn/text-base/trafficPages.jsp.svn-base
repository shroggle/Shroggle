<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.logic.statistics.SiteTrafficInfo" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficSitesAction" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficPagesAction" %>
<%@ page import="com.shroggle.logic.statistics.PageTrafficInfo" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsSortType" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsTimePeriodType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="trafficPages"/>
<% final TrafficPagesAction action = (TrafficPagesAction) request.getAttribute("actionBean"); %>
<html>
<head>
    <title><international:get name="pageHeader"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript" src="/tinymce/jquery.tinymce.js"></script>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <span class="grey_24"><international:get name="mainHeader"/></span> <br>
            <jsp:include page="/account/dashboardMenu.jsp"/>
            <br>

            <div class="box_100">
                <div class="inside" style="padding: 10px 15px 0 15px;">
                    <div style="width:100%;">
                        <input type="hidden" id="siteId" value="<%= action.getSite().getSiteId() %>"/>
                        <div class="pageTitle" style="float:left;">
                            <international:get name="pageTitle"/>
                            <br>
                            <%= action.getSiteTitle() %>&nbsp;
                        </div>
                        <div style="float:right;">
                            <stripes:link beanclass="com.shroggle.presentation.site.traffic.TrafficSitesAction">
                                <international:get name="backToSitesStat"/>
                            </stripes:link>
                        </div>
                        <br clear="all">

                        <div style="float:left;">
                            <international:get name="pageDescritpion"/>
                        </div>
                        <div style="float:right;">
                            <international:get name="timePeriod"/>
                            <select id="timePeriodSelect" onchange="updatePageStatistics();">
                                <% final StatisticsTimePeriodType timePeriod = action.getTimePeriod(); %>
                                <option <% if (StatisticsTimePeriodType.ALL_TIME.equals(timePeriod)) { %>selected="selected"<% } %>
                                        value="ALL_TIME">
                                    <international:get name="allTime"/>
                                </option>
                                <option <% if (StatisticsTimePeriodType.LAST_MONTH.equals(timePeriod)) { %>selected="selected"<% } %>
                                        value="LAST_MONTH">
                                    <international:get name="lastMonth"/>
                                </option>
                                <option <% if (StatisticsTimePeriodType.THIS_MONTH.equals(timePeriod)) { %>selected="selected"<% } %>
                                        value="THIS_MONTH">
                                    <international:get name="thisMonth"/>
                                </option>
                            </select>
                        </div>
                        <br><br>

                        <div id="pagesTableDiv">
                            <jsp:include page="trafficPagesList.jsp"/>
                        </div>

                    </div>
                </div>
            </div>
            <br>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>