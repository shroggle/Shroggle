<%@ page import="com.shroggle.entity.ChildSiteSettings" %>
<%@ page import="com.shroggle.entity.ChargeType" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.logic.site.billingInfo.ChargeTypeManager" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="networkSettingsInfo"/>
<%
    ChildSiteSettings childSiteSettings = (ChildSiteSettings) request.getAttribute("settings");
    double monthlyShroggleBillingPrice = new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice();
    double monthlyChildBillingPrice = childSiteSettings.isUseOneTimeFee() ? childSiteSettings.getOneTimeFee() : childSiteSettings.getPrice250mb();
%>
<div class="windowOneColumn">
    <div>
        <center>
            <span style="font-weight:bold;">
                <international:get name="networkRegistrationSettings"/>
            </span>
        </center>
        <br/>

        <div style="margin-bottom:10px;">
            <international:get name="registrationDate"/> &nbsp;
            <%= DateUtil.toCommonDateStr(childSiteSettings.getCreatedDate()) %>
        </div>

        <div style="margin-bottom:10px;">
            <div style="font-weight:bold;margin-bottom:3px;">
                <international:get name="mediaStorageOption"/>
            </div>
            <div style="margin-left:10px;">
                <international:get name="shroggleFee"/>
                <%= monthlyShroggleBillingPrice %>
                <international:get name="perMonth"/>
            </div>
            <div style="margin-left:10px;">
                <% if (childSiteSettings.isUseOneTimeFee()) { %>
                <international:get name="oneTimeSetupFee"/>
                <%= monthlyChildBillingPrice %>
                <% } else { %>
                <international:get name="networkFee"/>
                <%= monthlyChildBillingPrice %>
                <international:get name="perMonth"/>
                <% } %>
            </div>
        </div>
        <% if (childSiteSettings.getStartDate() != null || childSiteSettings.getEndDate() != null) { %>
        <div style="font-weight:bold;margin-bottom:3px;">
            <international:get name="chronologicalLimits"/>
        </div>
        <% } %>
        <% if (childSiteSettings.getStartDate() != null) { %>
        <div style="margin-left:10px;">
            <international:get name="startDate"/>
            <%= DateUtil.toMonthDayAndYear(childSiteSettings.getStartDate()) %>
        </div>
        <% } %>
        <% if (childSiteSettings.getEndDate() != null) { %>
        <div style="margin-left:10px;">
            <international:get name="endDate"/>
            <%= DateUtil.toMonthDayAndYear(childSiteSettings.getEndDate()) %>
        </div>
        <% } %>
        <p align="right">
            <input type="button" value="<international:get name="close"/>" class="but_w73"
                   onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   onclick="closeConfigureWidgetDiv();"/>
        </p>
    </div>
</div>