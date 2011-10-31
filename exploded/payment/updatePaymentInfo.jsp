<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="updatePaymentInfo"/>
<%
    final UpdatePaymentInfoAction actionBean = (UpdatePaymentInfoAction) request.getAttribute("actionBean");
%>
<html>
<head>
    <title><international:get name="addUpdatePaymentInformation"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<input type="hidden" id="profileActive" value="<international:get name="profileActive"/>"/>
<input type="hidden" id="profileActiveWithOneTimeFee" value="<international:get name="profileActiveWithOneTimeFee"/>"/>
<input type="hidden" id="javienActive" value="<international:get name="javienActive"/>"/>
<input type="hidden" id="cardProcessing" value="<international:get name="cardProcessing"/>">
<input type="hidden" id="redirectingToPaypal" value="<international:get name="redirectingToPaypal"/>">
<input type="hidden" id="termsAndConditions" value="<international:get name="termsAndConditions"/>">
<input type="hidden" id="selectCreditCard" value="<international:get name="selectCreditCard"/>">
<input type="hidden" id="selectSite" value="<international:get name="selectSite"/>">
<input type="hidden" id="checkIAgree" value="<international:get name="checkIAgree"/>">
<input type="hidden" id="siteActivation" value="<international:get name="siteActivation"/>">
<input type="hidden" id="cantBePublishedUntil" value="<international:get name="cantBePublishedUntil"/>">

<div class="wrapper">
    <div class="container">
        <%@ include file="../includeHeadApplication.jsp" %>
        <div class="content">
            <div class="box_70">
                <%@ include file="../account/accountMenuInclude.jsp" %>
            </div>
            <!-- start label-box -->
            <div class="box_70">
                    <div id="noAccess" <% if (!actionBean.getData().isHasAdminAccessToThisSite()) { %>
                         style="display:block; text-align:center;"
                         <% } else { %>style="display:none; text-align:center;"<% } %>>
                        <international:get name="youHaveNoAccess"/>
                    </div>
                    <% if (request.getParameter("showTransaction") != null && request.getParameter("showTransaction").equals("true")) { %>
                    <div style="text-align:center;"><span style="font-weight:bold;"><international:get
                            name="thankYouForYourPayment"/></span></div>
                    <% } else if (request.getParameter("showPaypalError") != null && request.getParameter("showPaypalError").equals("true")) { %>
                    <div style="text-align:left" class="error" id="errors">
                        <div style="font-weight:bold;"><international:get name="showPaypalError"/></div>
                    </div>
                    <% } else { %>
                    <div id="pageContent" <% if (!actionBean.getData().isHasAdminAccessToThisSite()) { %>
                         style="display:none"
                         <% } else { %>style="display:block"<% } %>>
                        <br>
                        <international:get name="selectASite"/>
                        <select id="selectedSite" onchange="siteChanged(this.value);" style="width:150px;">
                            <% for (Site site : actionBean.getData().getAvailableSites()) { %>
                            <option id="<%= site.getSiteId() %>"
                                    <% if (actionBean.getData().getSelectedSite() != null &&
                                            actionBean.getData().getSelectedSite().getSiteId() == site.getSiteId()) { %>
                                    selected
                                    <% } %>
                                    value="<%= site.getSiteId() %>">
                                <%= site.getTitle() %>
                            </option>
                            <% } %>
                        </select>
                        <% request.setAttribute("updatePaymentInfoData", actionBean.getData()); %>
                        <div id="updatePaymentInfoInternal" style="margin-top:25px;">
                            <jsp:include page="updatePaymentInfoInternal.jsp" flush="true"/>
                        </div>
                    </div>
                    <%}%>
            </div>
            <br>
        </div>
        <%@ include file="../includeFooterApplication.jsp" %>
    </div>
</div>

</body>
</html>