<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.logic.statistics.SiteTrafficInfo" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.logic.statistics.PageTrafficInfo" %>
<%@ page import="com.shroggle.logic.statistics.VisitorTrafficInfo" %>
<%@ page import="com.shroggle.logic.statistics.StatisticsTimePeriodType" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficVisitorsAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="trafficVisitors"/>
<% final TrafficVisitorsAction action = (TrafficVisitorsAction) request.getAttribute("actionBean"); %>
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
                        <input type="hidden" id="pageId" value="<%= action.getPageId() %>"/>
                        <div class="trafficPageTitle">
                            <international:get name="pageTitle"/>
                            <br>
                            <%= action.getSiteTitle() %>&nbsp;/&nbsp;<%= action.getPageTitle() %>&nbsp;
                        </div>
                        <div style="float:right;">
                            <stripes:link beanclass="com.shroggle.presentation.site.traffic.TrafficSitesAction">
                                <international:get name="backToSitesStat"/>
                            </stripes:link>
                            <br>
                            <stripes:link beanclass="com.shroggle.presentation.site.traffic.TrafficPagesAction">
                                <international:get name="backToPagesStat"/>
                                <stripes:param name="siteId" value="<%= action.getSiteId() %>"/>
                            </stripes:link>
                        </div>
                        <br clear="all"><br>

                        <div style="float:left;"><international:get name="pageDescritpion"/></div>
                        <div style="float:right;"><international:get name="timePeriod"/>
                            <select id="timePeriodSelect"
                                    onchange="updateVisitorsStatistics($('#sort_arrow').parent()[0].id, parseBoolean($('#sort_arrow').parent().attr('desc')), <%= action.pageId %>);">
                                <option <%if (action.getTimePeriod().equals(StatisticsTimePeriodType.ALL_TIME)){%>selected="selected"<%}%>
                                        value="ALL_TIME">All time
                                </option>
                                <option <%if (action.getTimePeriod().equals(StatisticsTimePeriodType.LAST_MONTH)){%>selected="selected"<%}%>
                                        value="LAST_MONTH">Last month
                                </option>
                                <option <%if (action.getTimePeriod().equals(StatisticsTimePeriodType.THIS_MONTH)){%>selected="selected"<%}%>
                                        value="THIS_MONTH">This month
                                </option>
                            </select>
                        </div>
                        <br><br>
                        <input type="hidden" id="manageRegistrantsSiteId" value="<%= action.getSiteId() %>"/>

                        <div id="visitorsTableDiv">
                            <jsp:include page="trafficVisitorsList.jsp"/>
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