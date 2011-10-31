<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.entity.PurchaseMailLog" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.persistance.Persistance" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="paymentLogs"/>

<%--
  @author Balakirev Anatoliy
--%>

<% final Persistance persistance = ServiceLocator.getPersistance(); %>
<html>
<head>
    <title>
        Purchase emails
    </title>
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
                    <div id="paymentLogsDiv">
                        <table class="tbl_blog">
                            <thead>
                            <tr>
                                <td>
                                    Logs Creation Date
                                </td>
                                <td>
                                    User
                                </td>
                                <td>
                                    Site
                                </td>
                                <td>
                                    Old Expiration Date
                                </td>
                                <td>
                                    New Expiration Date
                                </td>
                                <td>
                                    Message
                                </td>
                                <td>
                                    Purchase Status
                                </td>
                                <td>
                                    Errors Occurred While Sending Email
                                </td>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                List<PurchaseMailLog> logs = ((ShowPurchaseMailLogAction) request.getAttribute("actionBean")).getPurchaseMailLogs();
                                //final Date firstLogDate = (logs == null || logs.isEmpty()) ? new Date() : logs.get(0).getCreationDate();
                                int year = -1;
                                int month = -1;
                                int day = -1;
                                for (PurchaseMailLog log : logs) {
                                    final Calendar creationDate = new GregorianCalendar();
                                    creationDate.setTime(log.getCreationDate());
                                    boolean addSeparator = (month != creationDate.get(Calendar.MONTH) || day != creationDate.get(Calendar.DAY_OF_MONTH) || year != creationDate.get(Calendar.YEAR));
                                    year = creationDate.get(Calendar.YEAR);
                                    month = creationDate.get(Calendar.MONTH);
                                    day = creationDate.get(Calendar.DAY_OF_MONTH);
                                    final User user = persistance.getUserById(log.getUserId());
                                    final String userInfo = user != null ? (StringUtil.getEmptyOrString(user.getFirstName()) + " " +
                                            StringUtil.getEmptyOrString(user.getLastName()) + " (userId = " + user.getUserId() + ")") : "User not found";
                                    final Site site = persistance.getSite(log.getSiteId());
                                    final String siteInfo = site != null ? (site.getTitle() + " (siteId = " + site.getSiteId() +
                                            ", current expiration date: " + DateUtil.toMonthDayAndYear(site.getSitePaymentSettings().getExpirationDate()) + ")") : "Site not found";
                                    final boolean error = (user == null || site == null || log.isErrorsWhileSendingMail() || !log.isPurchaseComplete());
                            %>
                            <% if (addSeparator) { %>
                            <tr>
                                <td colspan="8" style="text-align:center;font-weight:bold;">
                                    <%= DateUtil.toMonthDayAndYear(log.getCreationDate()) %>:
                                </td>
                            </tr>
                            <% } %>
                            <tr <% if (error) { %>
                                    style="background-color:#FEE;" <% } else { %>
                                    style="background-color:#EFE;" <% } %>>
                                <td title="Logs Creation Date">
                                    <%= log.getCreationDate() %>
                                </td>
                                <td title="User">
                                    <%= userInfo %>
                                </td>
                                <td title="Site">
                                    <%= siteInfo %>
                                </td>
                                <td title="Old Expiration Date">
                                    <%= log.getOldExpirationDate() != null ? DateUtil.toMonthDayAndYear(log.getOldExpirationDate()) : null %>
                                </td>
                                <td title="New Expiration Date">
                                    <%= DateUtil.toMonthDayAndYear(log.getNewExpirationDate()) %>
                                </td>
                                <td title="Message">
                                    <a href="javascript:purchaseMailLogs.showMessage(<%= log.getId() %>);">Message</a>

                                    <div id="message<%= log.getId() %>" style="display:none">
                                        <div class="windowOneColumn">
                                            <h1>
                                                Email text:
                                            </h1>
                                            <%= log.getEmailText().replace("\n", "<br>") %>
                                        </div>
                                    </div>
                                </td>
                                <td title="Purchase Complete">
                                    <%= log.isPurchaseComplete() ? "Complete" : "Failed" %>
                                </td>
                                <td title="Errors Occurred While Sending Email">
                                    <%= log.isErrorsWhileSendingMail() %>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
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