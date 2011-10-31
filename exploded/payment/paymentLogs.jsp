<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.payment.UpdatePaymentInfoAction" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.util.payment.javien.Javien" %>
<%@ page import="com.shroggle.util.payment.javien.javienResponse.JavienProduct" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager" %>
<%@ page import="com.shroggle.logic.site.PublishingInfoResponse" %>
<%@ page import="com.shroggle.presentation.site.payment.PaymentLogsAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="paymentLogs"/>
<%
    final PaymentLogsAction paymentLogsAction = (PaymentLogsAction) request.getAttribute("actionBean");
%>
<html>
<head>
    <title>Payment logs</title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="../includeHeadApplication.jsp" %>
        <div class="content">
            <div class="box_70">
                <%@ include file="../account/accountMenuInclude.jsp" %>
            </div>
            <!-- start label-box -->
            <div class="box_100">
                <div class="inside_70" style="padding:0 5px">
                    <div style="margin-bottom:5px;">
                        <span style="font-weight:bold;font-size:11pt;">Filter by:</span>
                        <label style="margin-left:5px" for="paymentMehtodFilter">Payment Method</label>
                        <select id="paymentMehtodFilter" onchange="filterPaymentLogs();">
                            <option value="null">All</option>
                            <option value="<%= PaymentMethod.PAYPAL %>">PayPal</option>
                            <option value="<%= PaymentMethod.AUTHORIZE_NET %>">Javien</option>
                        </select>
                        &nbsp;<input type="checkbox" id="showOnlyForLogined" onchange="filterPaymentLogs();">
                        <label for="showOnlyForLogined">Show logined user logs only</label>
                        <br>
                        <input type="button" value="Filter by siteId"
                               onClick="filterPaymentLogs();"> :
                        &nbsp;<input type="text" maxlength="5" id="filterBySiteId"
                                     onkeypress="return numbersOnly(this, event);">

                        <br>
                        <input type="button" value="Filter by childSiteSettingsId"
                               onClick="filterPaymentLogs();"> :
                        &nbsp;<input type="text" maxlength="5" id="filterByChildSiteSettingsId"
                                     onkeypress="return numbersOnly(this, event);">
                    </div>
                    <div id="paymentLogsDiv">
                        <jsp:include page="paymentLogsList.jsp"/>
                    </div>
                </div>
            </div>
            <br>
        </div>
        <%@ include file="../includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>