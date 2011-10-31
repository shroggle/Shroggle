<%@ page import="com.shroggle.presentation.site.payment.AddEditCreditCardData" %>
<%@ page import="com.shroggle.entity.CreditCardType" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.shroggle.logic.countries.CountryManager" %>
<%@ page import="com.shroggle.entity.Country" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="addEditCreditCardInfo"/>
<%--
 @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    final AddEditCreditCardData data = (AddEditCreditCardData) request.getAttribute("addEditCreditCardData");
    final int widgetId = data.getWidgetId();
%>
<div id="creditCardOptions<%= widgetId %>">
<table class="childSiteRegistrationTable addEditCreditCardBlock" cellpadding="0" cellspacing="0">
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="creditCardType<%= widgetId %>">
                <international:get name="creditCardType"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%-----------------------------------------Credit Card Type-------------------------------------------%>
            <select name="creditCardType" id="creditCardType<%= widgetId %>" class="txt"
                    <% if (data.isDisableData()) { %>disabled<% } %>>
                <% for (CreditCardType type : CreditCardType.values()) { %>
                <option value="<%= type.toString() %>"
                        <% if (type == data.getCreditCardType()) { %> selected <% } %>>
                    <international:get name="<%= type.toString() %>"/>
                </option>
                <% } %>
            </select>
            <%-----------------------------------------Credit Card Type-------------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="creditCardNumber<%= widgetId %>"><international:get name="cardNumber"/></label>
        </td>
        <td style="padding-bottom:5px;">
            <%----------------------------------------Credit Card Number------------------------------------------%>
            <input type="text" class="txt" id="creditCardNumber<%= widgetId %>" maxlength="255"
                   name="creditCardNumber" <% if (data.isDisableData()) { %>disabled<% } %>
                   onKeyPress="return numbersOnly(this, event);" value="<%= data.getCreditCardNumber() %>"/>
            <%----------------------------------------Credit Card Number------------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="creditCardExpirationMonth<%= widgetId %>"><international:get name="expirationDate"/></label>
        </td>
        <td style="padding-bottom:5px;">
            <%-----------------------------------Credit Card Expiration Month-------------------------------------%>
            <select name="creditCardExpirationMonth" id="creditCardExpirationMonth<%= widgetId %>"
                    class="creditCardExpirationMonth" <% if (data.isDisableData()) { %>disabled<% } %>>
                <% for (int i = 0; i < 12; i++) { %>
                <option <% if(data.getExpirationMonth() == i) {  %>selected<% } %> value="<%= i %>">
                    <international:get name='<%= "month" + i %>'/>
                </option>
                <% } %>
            </select>
            <%-----------------------------------Credit Card Expiration Month-------------------------------------%>
            <%-----------------------------------Credit Card Expiration Year--------------------------------------%>
            <select name="creditCardExpirationYear" id="creditCardExpirationYear<%= widgetId %>"
                    class="creditCardExpirationYear" <% if (data.isDisableData()) { %>disabled<% } %>>
                <% final Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    short currentYear = (short) calendar.get(Calendar.YEAR);
                    for (int i = 0; i < 11; i++) { %>
                <option <% if (data.getExpirationYear() == currentYear) { %>selected<% } %> value="<%= currentYear %>">
                    <%= currentYear %>
                </option>
                <% currentYear++;
                } %>
            </select>
            <%-----------------------------------Credit Card Expiration Year--------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="securityCode<%= widgetId %>">
                <international:get name="securityCode"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%------------------------------------Credit Card Security Code---------------------------------------%>
            <input type="text" class="txt" id="securityCode<%= widgetId %>" name="securityCode" maxlength="255"
                   style="width:55px" <% if (data.isDisableData()) { %>disabled<% } %>
                   onKeyPress="return numbersOnly(this, event);" value="<%= data.getSecurityCode() %>"/>
            <%------------------------------------Credit Card Security Code---------------------------------------%>
            &nbsp;<a <% if (data.isDisableData()) { %>href="javascript:void(0);" style="color:gray;" <% } else { %>
                     href="javascript:showWhatsThisForSecurityCode('<%= widgetId %>', 'whatsThisPaymentText')"<% } %>
                     id="whatsThisCSRWindow<%= widgetId %>"
                     style=" background:url(../images/QuestionMark.png) no-repeat right top; padding:2px 20px 2px 0;color:#196DB3;
                font-weight:bold; font-size:11px; text-decoration:none;"><international:get
                name="whatsThis"/></a>
        </td>
    </tr>
</table>
<div style="font-weight:bold;padding-top:10px;">
    <international:get name="billingAddress"/>
</div>
<div style="padding:10px 0;<% if (!data.isShowCheckbox()) { %>display:none;<% } %>">
    <%-------------------------------------Use My Contact Information Checkbox----------------------------------------%>
    <input type="checkbox" id="useInfo<%= widgetId %>" name="useContactInfo"
           <% if (data.isDisableData()) { %>disabled<% } %>
           <% if (data.isUseContactInfo()) { %>checked<% } %>
           onclick="useUserBillingInfoForCreditCard('<%= widgetId %>');"/>
    <%-------------------------------------Use My Contact Information Checkbox----------------------------------------%>
    <label for="useInfo<%= widgetId %>">
        &nbsp;<international:get name="useMyContactInformation"/>
    </label>
</div>
<table id="billingInfo<%= widgetId %>" class="childSiteRegistrationTable addEditCreditCardBlock" cellpadding="0"
       cellspacing="0">
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="billingAddress1<%= widgetId %>">
                <international:get name="billingAddress1"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%-----------------------------------Credit Card Billing Adress1--------------------------------------%>
            <input type="text" class="txt" id="billingAddress1<%= widgetId %>" maxlength="255"
                   name="billingAddress1" <% if (data.isDisableData()) { %>disabled<% } %>
                   value="<%= data.getBillingAddress1() %>"
                   onKeyPress="return numbersRomanCharactersOrSpaceOnly(this, event);"/>
            <%-----------------------------------Credit Card Billing Adress1--------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="billingAddress2<%= widgetId %>">
                <international:get name="billingAddress2"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%-----------------------------------Credit Card Billing Adress2--------------------------------------%>
            <input type="text" class="txt" id="billingAddress2<%= widgetId %>" maxlength="255"
                   name="billingAddress2" <% if (data.isDisableData()) { %>disabled<% } %>
                   value="<%= data.getBillingAddress2() %>"
                   onKeyPress="return numbersRomanCharactersOrSpaceOnly(this, event);"/>
            <%-----------------------------------Credit Card Billing Adress2--------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="city<%= widgetId %>">
                <international:get name="city"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%-----------------------------------------------City-------------------------------------------------%>
            <input type="text" class="txt" id="city<%= widgetId %>" name="city" maxlength="255"
                   value="<%= data.getCity() %>" <% if (data.isDisableData()) { %>disabled<% } %>
                   onKeyPress="return romanCharactersOrSpaceOnly(this, event);"/>
            <%-----------------------------------------------City-------------------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="country<%= widgetId %>">
                <international:get name="country"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%----------------------------------------------Country-----------------------------------------------%>
            <select id="country<%= widgetId %>" name="country" class="txt"
                    <% if (data.isDisableData()) { %>disabled<% } %>
                    onchange="selectCountryForCreditCard('', '<%= widgetId %>');">
                <%
                    for (Country country : Country.values()) {
                %>
                <option value="<%= country.toString() %>"
                        <% if (country == data.getCountry()) { %> selected <% } %>>
                    <%= CountryManager.getCountryValue(country) %>
                </option>
                <% } %>
            </select>
            <%----------------------------------------------Country-----------------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="region<%= widgetId %>">
                    <span id="regionText<%= widgetId %>">
                        <international:get name="stateProvince"/>
                    </span>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%-------------------------------------------------States-------------------------------------------------%>
            <div id="statesHolder<%= widgetId %>">
                <%
                    request.setAttribute("widgetId", widgetId);
                    request.setAttribute("states", data.getStates());
                    request.setAttribute("state", data.getRegion());
                    request.setAttribute("disable", data.isDisableData());
                %>
                <jsp:include page="../account/states.jsp" flush="true"/>
            </div>
            <%-------------------------------------------------States-------------------------------------------------%>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:5px;padding-right:5px;">
            <label for="postalCode<%= widgetId %>">
                <international:get name="ZIPPostalCode"/>
            </label>
        </td>
        <td style="padding-bottom:5px;">
            <%------------------------------------------Zip/Postal Code-------------------------------------------%>
            <input type="text" class="txt" id="postalCode<%= widgetId %>" name="postalCode" maxlength="255"
                   value="<%= data.getPostalCode() %>" <% if (data.isDisableData()) { %>disabled<% } %>
                   onKeyPress='return checkPostCharactersByCountry(this, $("#country" + $("#creditCardInfoWidgetId").val()).val(), event);'/>
            <%------------------------------------------Zip/Postal Code-------------------------------------------%>
        </td>
    </tr>
</table>
</div>

<div id="whatsThisPaymentText<%= widgetId %>" style="display:none;">
    <div style="border:5px solid #ECE9E6; margin:0 10px 0 10px; padding-top:20px;padding-left:20px;padding-right:20px;">
        <div style="overflow:auto;width:320px;height:250px;padding:10px; text-align:left;">
            <div><international:get name="whatsThisText1"/></div>
            <br>

            <div class="b_16"><international:get name="whatsThisText2"/></div>
            <br>

            <div><international:get name="whatsThisText3"/></div>
            <br>

            <div><international:get name="whatsThisText4"/></div>
        </div>
        <p align="center">
            <input type="button" onclick="closeConfigureWidgetDiv();" value="Close"
                   onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
        </p>
    </div>
</div>
<input type="hidden" id="creditCardInfoWidgetId" value="<%= widgetId %>">

<input type="hidden" id="userBillingAddress1<%= widgetId %>" value="<%= data.getUserBillingAddress1() %>">
<input type="hidden" id="userBillingAddress2<%= widgetId %>" value="<%= data.getUserBillingAddress2()%>">
<input type="hidden" id="userCity<%= widgetId %>" value="<%= data.getUserCity() %>">
<input type="hidden" id="userCountry<%= widgetId %>" value="<%= data.getUserCountry() %>">
<input type="hidden" id="userPostalCode<%= widgetId %>" value="<%= data.getUserPostalCode() %>">
<input type="hidden" id="userRegion<%= widgetId %>" value="<%= data.getUserRegion() %>">

<%----------------------------------------------------Error Texts-----------------------------------------------------%>
<input type="hidden" id="checkIAgree<%= widgetId %>" value="<international:get name="checkIAgree"/>">
<input type="hidden" id="enterCCNumber<%= widgetId %>" value="<international:get name="enterCCNumber"/>">
<input type="hidden" id="enterValidVisaNumber<%= widgetId %>" value="<international:get name="enterValidVisaNumber"/>">
<input type="hidden" id="enterValidMCNumber<%= widgetId %>" value="<international:get name="enterValidMCNumber"/>">
<input type="hidden" id="wrongDate<%= widgetId %>" value="<international:get name="wrongDate"/>">
<input type="hidden" id="wrongSecurityCode<%= widgetId %>" value="<international:get name="wrongSecurityCode"/>">
<input type="hidden" id="billingAddress<%= widgetId %>" value="<international:get name="billingAddressError"/>">
<input type="hidden" id="enterCity<%= widgetId %>" value="<international:get name="enterCity"/>">
<input type="hidden" id="enterRegion<%= widgetId %>" value="<international:get name="enterRegion"/>">
<input type="hidden" id="enterZipPostal<%= widgetId %>" value="<international:get name="enterZipPostal"/>">
<%----------------------------------------------------Error Texts-----------------------------------------------------%>