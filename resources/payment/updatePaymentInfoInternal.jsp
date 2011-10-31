<%@ page import="com.shroggle.logic.form.PaymentSettingsManager" %>
<%@ page import="com.shroggle.logic.form.PaymentSettings" %>
<%@ page import="com.shroggle.presentation.site.payment.CreditCardData" %>
<%@ page import="com.shroggle.presentation.site.payment.UpdatePaymentInfoData" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.logic.site.PublishingInfoResponse" %>
<%@ page import="com.shroggle.logic.site.billingInfo.ChargeTypeManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="updatePaymentInfo"/>
<%--
 @author Balakirev Anatoliy
--%>
<%
    UpdatePaymentInfoData data = (UpdatePaymentInfoData) request.getAttribute("updatePaymentInfoData");
    final Site selectedSite = data.getSelectedSite();
    final boolean siteExist = selectedSite != null;
    final SitePaymentSettings selectedSitePaymentSettings = siteExist ? selectedSite.getSitePaymentSettings() : new SitePaymentSettings();
    final PublishingInfoResponse selectedSiteCanBePublished = siteExist ? new SiteManager(selectedSite).checkChildSiteStartDate() : null;
    Integer selectedCardId = siteExist ? selectedSite.getSitePaymentSettings().getCreditCard() != null ? selectedSite.getSitePaymentSettings().getCreditCard().getCreditCardId() : null : null;
    final boolean isChildSite = siteExist && selectedSite.getChildSiteSettings() != null;
    ChildSiteSettings childSiteSettings = siteExist ? selectedSite.getChildSiteSettings() : null;
    double annualBillingPrice = new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice();
    double monthlyBillingPrice = new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice();
%>
<international:get name="siteStatus"/>
<% if (data.getSelectedSiteStatus() != null) { %>
<span id="siteStatus" <% if (data.getSelectedSiteStatus() == SiteStatus.ACTIVE) { %>class="active_txt"
      <% } else { %>class="pending_txt"<% } %>>
    <international:get name="<%= data.getSelectedSiteStatus().toString() %>"/>
</span>
<% } %>

<br><br>
<%----------------------------------------------------Payment Info----------------------------------------------------%>
<div id="paymentInfo">
    <div id="newAgreement" style="display:none;">
        <%= new PaymentSettingsManager(childSiteSettings != null ? new PaymentSettings(childSiteSettings) : null).getAgreement() %>
    </div>
    <% if (isChildSite) { %>
    <input type="hidden" id="childSiteSettingsId" value="<%= childSiteSettings.getChildSiteSettingsId() %>"/>
    <%-------------------------------------------Child Site Payment Types---------------------------------------------%>
    <% if (childSiteSettings.isUseOneTimeFee()) { %>
    <%--------------------------------------------------One Time Fee--------------------------------------------------%>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_ONE_TIME_FEE %>" checked style="display:none;"
           id="<%= ChargeType.SITE_ONE_TIME_FEE.toString() %>">

    <span class="b_16">
        <international:get name="oneTimeFee"/>
    </span>
    <span class="blue_16">
        $<%= childSiteSettings.getOneTimeFee() %>
    </span>
    <br>
    <%--------------------------------------------------One Time Fee--------------------------------------------------%>
    <% } else { %>
    <%---------------------------------------------------Monthly Fee--------------------------------------------------%>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_250MB_MONTHLY_FEE %>" checked
           id="<%= ChargeType.SITE_250MB_MONTHLY_FEE.toString() %>">
    <label for="<%= ChargeType.SITE_250MB_MONTHLY_FEE.toString() %>">
        <span class="b_16">
            <international:get name="monthlyCharge250"/>
        </span>
        <span class="blue_16">
            $<%= childSiteSettings.getPrice250mb() %>
        </span>
    </label>
    <br>
    <international:get name="or"/>
    <br>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_500MB_MONTHLY_FEE %>" checked
           id="<%= ChargeType.SITE_500MB_MONTHLY_FEE.toString() %>">
    <label for="<%= ChargeType.SITE_500MB_MONTHLY_FEE.toString() %>">
        <span class="b_16">
            <international:get name="monthlyCharge500"/>
        </span>
        <span class="blue_16">
            $<%= childSiteSettings.getPrice500mb() %>
        </span>
    </label>
    <br>
    <international:get name="or"/>
    <br>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_1GB_MONTHLY_FEE %>" checked
           id="<%= ChargeType.SITE_1GB_MONTHLY_FEE.toString() %>">
    <label for="<%= ChargeType.SITE_1GB_MONTHLY_FEE.toString() %>">
        <span class="b_16">
            <international:get name="monthlyCharge1"/>
        </span>
        <span class="blue_16">
            $<%= childSiteSettings.getPrice1gb() %>
        </span>
    </label>
    <br>
    <international:get name="or"/>
    <br>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_3GB_MONTHLY_FEE %>" checked
           id="<%= ChargeType.SITE_3GB_MONTHLY_FEE.toString() %>">
    <label for="<%= ChargeType.SITE_3GB_MONTHLY_FEE.toString() %>">
        <span class="b_16">
            <international:get name="monthlyCharge3"/>
        </span>
        <span class="blue_16">
            $<%= childSiteSettings.getPrice3gb() %>
        </span>
    </label>
    <br>
    <%---------------------------------------------------monthly fee--------------------------------------------------%>
    <% } %>
    <%-------------------------------------------Child site payment types---------------------------------------------%>
    <% } else { %>
    <%----------------------------------------------Site payment types------------------------------------------------%>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_MONTHLY_FEE %>"
           <% if ((siteExist && selectedSitePaymentSettings.getChargeType().getPaymentPeriod().equals(PaymentPeriod.MONTH))) { %>checked="checked"<% } %>
           id="<%= ChargeType.SITE_MONTHLY_FEE.toString() %>">
    <label for="<%= ChargeType.SITE_MONTHLY_FEE.toString() %>">
        <span class="b_16">
            <international:get name="monthlyCharge"/>
        </span>
        <span class="blue_16">
            $<%= monthlyBillingPrice %>
        </span>
    </label>
    <br>
    <international:get name="or"/>
    <br>
    <input type="radio" name="chargeType" value="<%= ChargeType.SITE_ANNUAL_FEE %>"
           <% if ((siteExist && selectedSitePaymentSettings.getChargeType().getPaymentPeriod().equals(PaymentPeriod.YEAR))) { %>checked="checked"<% } %>
           id="<%= ChargeType.SITE_ANNUAL_FEE.toString() %>">
    <label for="<%= ChargeType.SITE_ANNUAL_FEE.toString() %>">
        <span class="b_16">
            <international:get name="annualCharge"/>
        </span>
        <span class="blue_16">
            $<%= annualBillingPrice %>
        </span>
    </label>
    <br>
    <br>
    <international:get name="monthlyBilling"/>
    <%------------------------------------------------Site payment types--------------------------------------------------%>
    <% } %>
</div>
<%----------------------------------------------------Payment Info----------------------------------------------------%>
<div class="emptyError" id="errors"></div>
<hr>
<table id="updatePaymentInfoTable">
    <tr valign="top" style="vertical-align:top">
        <td>
            <dl class="w_30">
                <dt>
                    <label>
                        <international:get name="paymentMethod"/>
                    </label>
                </dt>
                <%-----------------------------------------------Paypal-----------------------------------------------%>
<%--
                <dd class="radiobtn_l">
                    <input type="radio" id="PayPal"
                           onclick="payPalSelected();"
                           <% if (siteExist && selectedSitePaymentSettings.getPaymentMethod().equals(PaymentMethod.PAYPAL)) { %>checked="checked"<% } %>
                           name="method"/>
                    <label for="PayPal" style="float:none;">
                        <international:get name="payPal"/>
                    </label>
                    <br>
                </dd>
--%>                
                <%-----------------------------------------------Paypal-----------------------------------------------%>
                <dt><label>&nbsp;</label></dt>
                <%-----------------------------------------------Javien-----------------------------------------------%>
                <dd class="radiobtn_l">
                    <img id="creditCardImageId" src="../images/CCSymbols.gif"
                         alt="Credit Card"
                         width="120"
                         height="26"
                         style="float:right; margin-right:40px;">
                    <input type="radio"
                           onclick="creditCardSelected();"
                           name="method"
                           id="CC"
                           <% if ((siteExist && selectedSitePaymentSettings.getPaymentMethod().equals(PaymentMethod.AUTHORIZE_NET))) { %>checked="checked"<% } %>/>
                    <label for="CC" style="float:none;">
                        <international:get name="creditCard"/>
                    </label>&nbsp;
                    <div id="creditCardSelectBlock"
                         <% if (siteExist && selectedSitePaymentSettings.getPaymentMethod().equals(PaymentMethod.PAYPAL)) { %>style="display:none;"<% } %>>
                        <br clear="all"><br><br>
                        <international:get name="selectCard"/>
                        <%----------------------------------------Credit Cards----------------------------------------%>
                        <select id="creditCardSelect"
                                <% if (data.getCreditCards().size() == 0) { %>disabled<% } %>>
                            <% if (data.getCreditCards().size() == 0) { %>
                            <option value="-1">
                                <international:get name="noCreditCard"/>
                            </option>
                            <% } else { %>
                            <option value="-1" <% if (selectedCardId == null) { %>selected<% } %>>
                                <international:get name="selectCardOption"/>
                            </option>
                            <% for (CreditCardData card : data.getCreditCards()) {%>
                            <option value="<%= card.getCreditCardId() %>"
                                    <% if (selectedCardId != null && card.getCreditCardId() == selectedCardId) { %>selected<% } %>>
                                <% if (card.isOwner()) { %>
                                <%= card.getCreditCardNumber() %>
                                <% } else { %>
                                <%= card.getCreditCardOwnerName() %>
                                <% } %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                        <%----------------------------------------Credit Cards----------------------------------------%>
                        <br>
                        <br>
                        <a href="javascript:showAddEditCreditCardWindow('false');"
                           style="font-weight:bold;margin-right:20px;">
                            <international:get name="addCreditCard"/>
                        </a>
                        <a href="/account/showCreditCardList.action" style="font-weight:bold">
                            <international:get name="editCardDetails"/>
                        </a>
                    </div>
                </dd>
                <%-----------------------------------------------Javien-----------------------------------------------%>
            </dl>
        </td>
        <td style="vertical-align:top;width:240px;">
            <div id="oldAgreement">
                <%= new PaymentSettingsManager(siteExist && selectedSite.getChildSiteSettings() != null ? new PaymentSettings(selectedSite.getChildSiteSettings()) : null).getAgreement() %>
            </div>
            <input type="checkbox" id="iAgreeWithTermsAndConditionsCheckboxCC"/>
            <label for="iAgreeWithTermsAndConditionsCheckboxCC">
                <international:get name="termsAndCond"/>
            </label>
        </td>
    </tr>
</table>
<br>
<br>
<br clear=all>

<div class="buttons_box" id="updatePaymentInfoButtons"
     <% if (siteExist && ((selectedSitePaymentSettings.getPaymentMethod().equals(PaymentMethod.AUTHORIZE_NET)))) { %>style="float:right;"
     <% } else { %>style="display:none;float:right"<% } %>>
    <input onmouseout="this.className='but_w73';" value="Update"
           onmouseover="this.className='but_w73_Over';" class="but_w73" type="button"
           id="updateButton"
           onclick="if(checkPaymentForm())updateSitePaymentInfo();"
           <% if (data.getSelectedSiteStatus() == SiteStatus.ACTIVE) { %>style="display:block;"
           <% } else { %>style="display:none; "<% } %>>
    <input onmouseout="this.className='but_w100';" value="Reactivate"
           onmouseover="this.className='but_w100_Over';" class="but_w100" type="button"
           id="reactivateButton"
           onclick="if(checkPaymentForm())reactivateSitePaymentInfo();"
           <% if (data.getSelectedSiteStatus() == SiteStatus.SUSPENDED) { %>style="display:block;"
           <% } else { %>style="display:none;"<% } %>>
    <input onmouseout="this.className='but_w130';" value="Secure Payment"
           onmouseover="this.className='but_w130_Over';" class="but_w130" type="button"
           id="securePaymentButton"
        <% if (siteExist && !selectedSiteCanBePublished.isCanBePublished()) { %>
           onclick="alert($('#cantBePublishedUntil').val() + ' ' + '<%= selectedSiteCanBePublished.getStartDateString() %>');"
        <% } else { %>
           onclick="if(checkPaymentForm())updateSitePaymentInfo();"
        <% } %>
           <% if (data.getSelectedSiteStatus() == SiteStatus.PENDING) { %>style="display:block;"
           <% } else { %>style="display:none;"<% } %>>
</div>
<div class="buttons_box" id="updatePaymentInfoPaypalButton"
        <% if (siteExist && selectedSitePaymentSettings.getPaymentMethod().equals(PaymentMethod.PAYPAL)) { %>
     style="float:right;" <% } else { %> style="float:right;display:none;"<% } %>>
    <input type="button" name="payPal" onmouseout="this.className='but_w130';"
            <% if (siteExist && !selectedSiteCanBePublished.isCanBePublished()) { %>
           onclick="alert($('#cantBePublishedUntil').val() + ' ' + '<%= selectedSiteCanBePublished.getStartDateString() %>');"
            <% } else { %>
           onclick="goToPaypal();"
            <% } %>
           <% if (data.getSelectedSiteStatus() == SiteStatus.ACTIVE || data.getSelectedSiteStatus() == SiteStatus.PENDING) { %>style="display:block;"
           <% } else { %>style="display:none;"<% } %>
           value="Go to Pay Pal" id="goToPaypalButton"
           onmouseover="this.className='but_w130_Over';" class="but_w130"/>
    <input type="button" name="payPal" onmouseout="this.className='but_w130';"
           onclick="reactivateSitePaymentInfo();"
           <% if (data.getSelectedSiteStatus() == SiteStatus.SUSPENDED) { %>style="display:block;"
           <% } else { %>style="display:none;"<% } %>
           value="Reactivate" id="reactivateSitePaypal"
           onmouseover="this.className='but_w130_Over';" class="but_w130"/>
</div>
<br clear=all>&nbsp;