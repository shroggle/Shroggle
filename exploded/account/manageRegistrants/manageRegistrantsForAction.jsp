<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.logic.statistics.SiteTrafficInfo" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.presentation.site.traffic.TrafficSitesAction" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.presentation.manageRegistrants.ManageRegistratnsAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="manageRegistrants"/>
<% final int siteId = (Integer) request.getAttribute("manageRegistrantsSiteId"); %>
<html>
<head>
    <title><international:get name="pageHeader"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript" src="/tinymce/jquery.tinymce.js"></script>
    <script type="text/javascript">
        var siteId = <%= siteId %>;
    </script>
</head>
<body>
<input type="hidden" id="deleteVisitors" value="<international:get name="deleteVisitors"/>">

<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <div class="box_100">
                <div class="inside_95">
                    <jsp:include page="manageRegistrantsContent.jsp" flush="true"/>
                </div>
            </div>
            <br>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>