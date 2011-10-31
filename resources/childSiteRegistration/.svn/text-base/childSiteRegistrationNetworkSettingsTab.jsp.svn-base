<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.shroggle.util.config.Config" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.presentation.site.ConfigureChildSiteRegistrationService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager" %>
<%@ page import="com.shroggle.logic.image.ImageManager" %>
<%@ page import="com.shroggle.util.config.ConfigPayment" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Random" %>
<%@ page import="com.shroggle.logic.site.billingInfo.ChargeTypeManager" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureChildSiteRegistration"/>
<%
    final ConfigureChildSiteRegistrationService servicePaymentTab = (ConfigureChildSiteRegistrationService) request.getAttribute("childSiteRegistrationService");

    final DraftChildSiteRegistration settings = servicePaymentTab.getChildSiteRegistration();
    final List<Site> blueprints = servicePaymentTab.getBlueprints() != null ? servicePaymentTab.getBlueprints() : new ArrayList<Site>();
    final List<Integer> selectedBlueprintsId = settings.getBlueprintsId() != null ? settings.getBlueprintsId() : new ArrayList<Integer>();

    final boolean useStartDate = settings.getStartDate() != null;
    final boolean useEndDate = settings.getEndDate() != null;

    final International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);
    ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(settings);
    final boolean logoExist = servicePaymentTab.getImageUrl() != null && !servicePaymentTab.getImageUrl().trim().startsWith("..");
    Image logo = logoExist ? ServiceLocator.getPersistance().getImageById(settings.getLogoId()) : new Image();
%>
<input type="hidden" value="<%= servicePaymentTab.getPaypalEmail() %>" id="paypalEmailHiddenText">
<input type="hidden" value="<international:get name="enterTotalPrice"/>" id="enterTotalPriceText">
<input type="hidden" value="<international:get name="dateFormat"/>" id="dateFormatHiddenText">
<input type="hidden" id="priceMessageText" value="<international:get name="priceMessage"/>">
<input type="hidden" id="enterAPriceText" value="<international:get name="enterAPrice"/>">
<input type="hidden" value="<international:get name="emptyFile"/>" id="emptyFile">
<input type="hidden" value="<%= logo.getImageId() %>" id="logoImageId">
<input type="hidden" value="<%= logoExist %>" id="logoExist">

<div style="margin-bottom:10px;">
    <div style="background:url(../images/warning.png) no-repeat left 2px; padding:5px 0 0 20px;
                       font-size:15px; font-weight:bold; margin:0 auto;width:550px;">
        <international:get name="changesWillAppliedToChildSites"/>
    </div>
</div>
<div style="padding:5px 5px 0 5px;width:50%;float:left;">
    <div>
        <%-------------------------------------pay after registration or before post life-----------------------------%>
        <div style="background:url(../images/warning.png) no-repeat left 2px; padding:5px 0 0 20px;
                       font-size:11px; font-weight:bold;margin-bottom:15px;">
            <international:get name="paymentsInstructions"/>
            <a href="javascript:showNetworkSettingsHelpWindow('moreInfoText');">
                <international:get name="moreInfo"/></a>
        </div>
        <%-------------------------------------pay after registration or before post life-----------------------------%>


        <%-----------------------------Enter the prices you will charge your subscribing customers--------------------%>
        <div style="font-weight:bold;">
            <input type="radio" name="feeType" id="useOneTimeFee" onclick="disablePricesTable(true);"
                <% if (settings.isUseOneTimeFee()) { %> checked <% } %>>
            <label for="useOneTimeFee">
                &nbsp;<international:get name="oneTimeFee"/>&nbsp;&nbsp;$
            </label>
            <input id="oneTimeFee" type="text" maxlength="17" onkeypress="return numbersAndOneDotOnly(this, event);"
                <% if (!settings.isUseOneTimeFee()) { %> disabled <% } %>
                   defaultPrice="0"
                   value="<%= (!settings.isUseOneTimeFee()) ? international.get("enterTotalPrice") : settings.getOneTimeFee() %>"
                   onblur="checkNetworkPrices(this);" onfocus="clearField(this);"
                   style="width:90px; padding-bottom:5px;">
        </div>

        <div style="margin-bottom:15px; font-weight:bold;">
            <input type="radio" name="feeType" id="prices" onclick="disablePricesTable(false);"
                <% if (!settings.isUseOneTimeFee()) { %> checked <% } %>>
            <label for="prices">
                &nbsp;<international:get name="enterThePrices"/>
            </label>
        </div>
    </div>
    <table style="width:97%; border:1px solid #dedede; margin-bottom:15px; line-height:22px;">
        <tr>
            <td style="width:33%;border:1px solid #999;height:21px; padding-left:5px;">
                <international:get name="mediaStorage"/>
            </td>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;">
                <international:get name="monthlyShroggleFee"/>
            </td>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;">
                <international:get name="setYourPricing"/>
            </td>

        </tr>

        <tr>
            <td style="width:33% ;border:1px solid #999;  padding-left:5px;" height="21px">
                <span style="font-weight:bold">250mb</span>
            </td>
            <td style="width:33%  ;border:1px solid #999;  padding-left:5px;">
                <span style="font-weight:bold">$<%= new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() %></span>
            </td>
            <td style="width:33% ;border:1px solid #999;  padding-left:5px;">
                <input type="text" id="price250mb" onKeyPress="return numbersAndOneDotOnly(this, event);"
                       maxlength="255"
                       style="width:99%;height:17px;border:none;"
                       defaultPrice="<%= new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() %>"
                       value="<%= (settings.isUseOneTimeFee()) ? international.get("enterTotalPrice") : settings.getPrice250mb() %>"
                       onblur="checkNetworkPrices(this);"
                       onfocus="clearField(this);"
                    <% if (settings.isUseOneTimeFee()) { %> disabled <% } %>>
            </td>
        </tr>
        <tr>
            <td style="width:33%;border:1px solid #999 ;  padding-left:5px;" height="21px">
                <span style="font-weight:bold">500mb</span>
            </td>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;">
                <span style="font-weight:bold">$<%= new ChargeTypeManager(ChargeType.SITE_500MB_MONTHLY_FEE).getPrice() %></span>
            </td>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;">
                <input type="text" id="price500mb" onKeyPress="return numbersAndOneDotOnly(this, event);"
                       maxlength="255"
                       style="width:99%;height:17px;border:none;"
                       defaultPrice="<%= new ChargeTypeManager(ChargeType.SITE_500MB_MONTHLY_FEE).getPrice() %>"
                       value="<%= (settings.isUseOneTimeFee()) ? international.get("enterTotalPrice") : settings.getPrice500mb() %>"
                       onblur="checkNetworkPrices(this);"
                       onfocus="clearField(this);"
                    <% if (settings.isUseOneTimeFee()) { %> disabled <% } %>>
            </td>
        </tr>
        <tr>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;" height="21px">
                <span style="font-weight:bold">1gb</span>
            </td>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;">
                <span style="font-weight:bold">$<%= new ChargeTypeManager(ChargeType.SITE_1GB_MONTHLY_FEE).getPrice() %></span>
            </td>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;">
                <input type="text" id="price1gb" onKeyPress="return numbersAndOneDotOnly(this, event);" maxlength="255"
                       style="width:99%;height:17px;border:none;"
                       defaultPrice="<%= new ChargeTypeManager(ChargeType.SITE_1GB_MONTHLY_FEE).getPrice() %>"
                       value="<%= (settings.isUseOneTimeFee()) ? international.get("enterTotalPrice") : settings.getPrice1gb() %>"
                       onblur="checkNetworkPrices(this);"
                       onfocus="clearField(this);"
                    <% if (settings.isUseOneTimeFee()) { %> disabled <% } %>>
            </td>
        </tr>
        <tr>
            <td style="width:33%;border:1px solid #999;  padding-left:5px;" height="21px">
                <span style="font-weight:bold">3gb</span>
            </td>
            <td style="width:33% ;border:1px solid #999;  padding-left:5px;">
                <span style="font-weight:bold">$<%= new ChargeTypeManager(ChargeType.SITE_3GB_MONTHLY_FEE).getPrice() %></span>
            </td>
            <td style="width:33% ;border:1px solid #999;  padding-left:5px;">
                <input type="text" id="price3gb" onKeyPress="return numbersAndOneDotOnly(this, event);" maxlength="255"
                       style="width:99%;height:17px;border:none;"
                       defaultPrice="<%= new ChargeTypeManager(ChargeType.SITE_3GB_MONTHLY_FEE).getPrice() %>"
                       value="<%= (settings.isUseOneTimeFee()) ? international.get("enterTotalPrice") : settings.getPrice3gb() %>"
                       onblur="checkNetworkPrices(this);"
                       onfocus="clearField(this);"
                    <% if (settings.isUseOneTimeFee()) { %> disabled <% } %>>
            </td>
        </tr>
    </table>
    <%------------------------------Enter the prices you will charge your subscribing customers-----------------------%>
    <%--------------------------------------------------Blueprints----------------------------------------------------%>
    <div style="margin-top:10px;margin-bottom:10px;">
        <span id="selectTemplatesSpan" style="font-weight:bold;">
            <international:get name="selectTemplates"/>
        </span>
        <a href="javascript:showNetworkSettingsHelpWindow('whatsThisTemplateInstruction');">
            <international:get name="whatsThis"/></a>
    </div>
    <div style="width:90%; height:100px; overflow:auto; border: 3px double darkgray; padding:10px;margin-bottom:10px;"
         id="childSiteRegistrationBlueprints">
        <% for (Site blueprint : blueprints) { %>
        <% int uniqueId = new Random().nextInt(); %>
        <input type="checkbox" <%= selectedBlueprintsId.contains(blueprint.getSiteId()) ? "checked" : "" %>
               value="<%= blueprint.getSiteId() %>" id="blueprint<%= uniqueId %>">
        <label for="blueprint<%= uniqueId %>">
            <%= blueprint.getTitle() %>
        </label>
        <br/>
        <% } %>
    </div>
    <div>
        <input type="checkbox" id="userIsRequiredToUseASiteBlueprint"
            <%= settings.isRequiredToUseSiteBlueprint() ? "checked" : "" %>>
        <label for="userIsRequiredToUseASiteBlueprint">
            &nbsp;<international:get name="userIsRequiredToUseASiteBlueprint"/>
        </label>
    </div>
    <%--------------------------------------------------Blueprints----------------------------------------------------%>

</div>

<div style="padding:5px 0 0 5px; width:48%;float:left;">
    <div style="margin-left:5px;">
        <%---------------------------Is there a chronological limit on this membership option?------------------------%>
        <div style="margin-bottom:15px;padding-top:7px;margin-left:5px;">
            <div style="margin-bottom:5px;">
                <international:get name="isChronological"/>
            </div>
            <div style="margin-left:5px;">
                <input type="checkbox" id="startDate" <% if(useStartDate) { %>checked<% } %>
                       onclick="document.getElementById('startDateText').disabled = !this.checked;">
                <label for="startDate">
                    &nbsp;<international:get name="startDate"/>
                </label>
                <input type="text" id="startDateText" style="width:70px;" maxlength="10"
                       <% if (!useStartDate) { %>disabled="true"<% } %>
                       value='<% if (settings.getStartDate() == null) { %><international:get name="dateFormat"/><% } else if (settings.getStartDate() != null) { %><%= DateUtil.toMonthDayAndYear(settings.getStartDate()) %><% } %>'>
                &nbsp;
                <input type="checkbox" id="endDate" <% if(useEndDate) { %>checked<% } %>
                       onclick="document.getElementById('endDateText').disabled = !this.checked;">
                <label for="endDate">
                    &nbsp;<international:get name="endDate"/>
                </label>
                <input type="text" id="endDateText" style="width:70px;" maxlength="10"
                       <% if (!useEndDate) { %>disabled="true"<% } %>
                       value="<% if (settings.getEndDate() == null) { %><international:get name="dateFormat"/><% } else if (settings.getEndDate() != null) { %><%= DateUtil.toMonthDayAndYear(settings.getEndDate()) %><% } %>">
            </div>
        </div>
        <%---------------------------Is there a chronological limit on this membership option?------------------------%>
        <%--------------------------------------Select a way to charge your child sites---------------------------------------%>
        <div style="margin-bottom:10px;">
            <span style="font-weight:bold;">
                <international:get name="selectAWayToChargeYourChildSites"/>
            </span>
            &nbsp;<a href="javascript:showNetworkSettingsHelpWindow('whatsThisChargeInstruction');"><international:get
                name="moreInfo"/></a>
        </div>
        <%-----------------------------------------------------Authorize------------------------------------------------------%>

        <div style="margin-bottom:5px;">
            <input type="radio" id="shrogglesAuthorize" name="authorizeCredentials"
                   <% if (!manager.isUseOwnAuthorize()) { %>checked<% } %>
                   onclick="configureChildSiteRegistration.disableAuthorizeCredentials(true);"/>
            <label for="shrogglesAuthorize">
                <international:get name="shrogglesAuthorize"/>
            </label>
        </div>
        <div style="margin-bottom:5px;">
            <input type="radio" id="ownAuthorize" name="authorizeCredentials"
                   <% if (manager.isUseOwnAuthorize()) { %>checked<% } %>
                   onclick="configureChildSiteRegistration.disableAuthorizeCredentials(false);"/>
            <label for="ownAuthorize">
                <international:get name="ownAuthorize"/>
            </label>
        </div>
        <div style="margin-left:25px;">
            <div style="font-weight:bold;margin-bottom:5px;">
                <international:get name="uathorizeCredentials"/>:
            </div>
            <table class="marginLeft10px">
                <tr>
                    <td class="width100px paddingBottom5px">
                        <label for="authorizeLogin">
                            <international:get name="login"/>
                        </label>
                    </td>
                    <td class="paddingBottom5px">
                        <input type="text" id="authorizeLogin" value="<%= manager.getAuthorizeLogin() %>"
                               <% if (!manager.isUseOwnAuthorize()) { %>disabled<% } %> />
                    </td>
                </tr>
                <tr>
                    <td class="width100px paddingBottom5px">
                        <label for="authorizeTransactionKey">
                            <international:get name="transactionKey"/>
                        </label>
                    </td>
                    <td class="paddingBottom5px">
                        <input type="text" id="authorizeTransactionKey"
                               value="<%= manager.getAuthorizeTransactionKey() %>"
                               <% if (!manager.isUseOwnAuthorize()) { %>disabled<% } %>/>
                    </td>
                </tr>
            </table>
        </div>
        <%-----------------------------------------------------Authorize------------------------------------------------------%>

        <%------------------------------------------------------Paypal--------------------------------------------------------%>

        <div style="margin-top:10px;margin-bottom:5px;">
            <input type="radio" id="shrogglesPaypal" name="paypalCredentials"
                   <% if (!manager.isUseOwnPaypal()) { %>checked<% } %>
                   onclick="configureChildSiteRegistration.disablePaypalCredentials(true);"/>
            <label for="shrogglesPaypal">
                <international:get name="shrogglesPaypal"/>
            </label>
        </div>
        <div style="margin-bottom:5px;">
            <input type="radio" id="ownPaypal" name="paypalCredentials"
                   <% if (manager.isUseOwnPaypal()) { %>checked<% } %>
                   onclick="configureChildSiteRegistration.disablePaypalCredentials(false);"/>
            <label for="ownPaypal"><international:get name="ownPaypal"/>
            </label>
        </div>
        <div style="margin-left:25px;">
            <div style="font-weight:bold;margin-bottom:5px;">
                <international:get name="paypalCredentials"/>:
            </div>
            <table class="marginLeft10px">
                <tr>
                    <td class="width100px paddingBottom5px">
                        <label for="paypalApiUserName">
                            <international:get name="apiUserName"/>
                        </label>
                    </td>
                    <td class="paddingBottom5px">
                        <input type="text" id="paypalApiUserName" value="<%= manager.getPaypalApiUserName() %>"
                               <% if (!manager.isUseOwnPaypal()) { %>disabled<% } %>/>
                    </td>
                </tr>
                <tr>
                    <td class="width100px paddingBottom5px">
                        <label for="paypalApiPassword">
                            <international:get name="apiPassword"/>
                        </label>
                    </td>
                    <td class="paddingBottom5px">
                        <input type="text" id="paypalApiPassword" value="<%= manager.getPaypalApiPassword() %>"
                               <% if (!manager.isUseOwnPaypal()) { %>disabled<% } %>/>
                    </td>
                </tr>
                <tr>
                    <td class="width100px paddingBottom5px">
                        <label for="paypalSignature">
                            <international:get name="signature"/>
                        </label>
                    </td>
                    <td class="paddingBottom5px">
                        <input type="text" id="paypalSignature" value="<%= manager.getPaypalSignature() %>"
                               <% if (!manager.isUseOwnPaypal()) { %>disabled<% } %>/>
                    </td>
                </tr>
            </table>
        </div>
        <%------------------------------------------------------Paypal--------------------------------------------------------%>
        <%--------------------------------------Select a way to charge your child sites---------------------------------------%>
        <%---------------------------------Your level of access on registrant sites-----------------------------------%>
        <div style="margin:15px 0;">
            <span style="font-weight:bold;"><international:get name="accessLevel"/></span>
            <a href="javascript:showNetworkSettingsHelpWindow('whatsThisAccessInstruction');">
                <international:get name="whatsThis"/></a>
        </div>
        <div style="margin-left:5px;">
            <input type="radio" name="accessLevel" id="adminAccessLevel"
                   <% if (settings.getAccessLevel() == SiteAccessLevel.ADMINISTRATOR){ %>checked<% } %>>
            <label for="adminAccessLevel">
                &nbsp;<international:get name="administrator"/>
            </label>
            &nbsp;
            <input type="radio" name="accessLevel" id="siteEditorAccessLevel"
                   <% if (settings.getAccessLevel() == SiteAccessLevel.EDITOR){ %>checked<% } %>>
            <label for="siteEditorAccessLevel">
                &nbsp;<international:get name="siteEditor"/>
            </label>
        </div>
        <%-----------------------------------Your level of access on registrant sites-------------------------------------%>

    </div>
</div>