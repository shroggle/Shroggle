<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.logic.statistics.SiteTrafficInfo" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficSitesAction" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="trafficSites"/>
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
                <div class="inside" style="padding: 10px 10px 0 30px;">
                    <div style="width:100%;">
                        <div class="pageTitle"><international:get name="pageTitle"/></div>
                        <international:get name="pageDescritpion"/>
                        <br><br>

                        <div id="sitesTableDiv">
                            <jsp:include page="trafficSitesList.jsp"/>
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