<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ page import="com.shroggle.presentation.site.ConfigureChildSiteRegistrationService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.entity.SiteAccessLevel" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.shroggle.entity.DraftChildSiteRegistration" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureChildSiteRegistration"/>
<% final ConfigureChildSiteRegistrationService service =
        (ConfigureChildSiteRegistrationService) request.getAttribute("childSiteRegistrationService"); %>
<input type="hidden" id="selectedChildSiteRegistrationId"
       value="<%= service.getChildSiteRegistration().getFormId() %>"/>
<input type="hidden" id="childSiteRegSiteId" value="<%= service.getSite().getSiteId() %>"/>
<input type="hidden" value="<international:get name="childSiteRegistrationNameCannotBeEmpty"/>"
       id="childSiteRegistrationNameCannotBeEmpty">
<input type="hidden" value="<international:get name="childSiteRegistrationFromEmailCannotBeEmpty"/>"
       id="childSiteRegistrationFromEmailCannotBeEmpty">
<input type="hidden" value="<international:get name="selectExistingChildSiteRegistration"/>"
       id="selectExistingChildSiteRegistration">
<input type="hidden" value="<international:get name="membershipOptionNameCannotBeEmpty"/>"
       id="membershipOptionNameCannotBeEmpty">
<input type="hidden" value="<international:get name="membershipOptionNameNotUnique"/>"
       id="membershipOptionNameNotUnique">
<input type="hidden" value="<international:get name="selectExistingMembershipOption"/>"
       id="selectExistingMembershipOption">
<input type="hidden" value="<international:get name="wrongStartDate"/>" id="wrongStartDate">
<input type="hidden" value="<international:get name="wrongEndDate"/>" id="wrongEndDate">
<input type="hidden" value="<international:get name="endBeforeStart"/>" id="endBeforeStart">
<input type="hidden" value="<international:get name="endDatePassed"/>" id="endDatePassed">
<input type="hidden" value="<international:get name="termsAndConditionsError"/>" id="termsAndConditionsError">
<input type="hidden" value="<international:get name="atLeastOneBlueprintHasToBeSelected"/>"
       id="atLeastOneBlueprintHasToBeSelected">
<input type="hidden" value="<international:get name="inputCorrectPrice"/>" id="inputCorrectPrice">
<input type="hidden" value="<%= service.getWidget() != null ? service.getWidget().getWidgetId() : -1 %>"
       id="childSiteRegistrationWidgetId">
<input type="hidden" value="<international:get name="enterTotalPrice"/>" id="enterTotalPriceText">

<input type="hidden" value="<international:get name="authorizeLoginCannotBeEmpty"/>" id="authorizeLoginCannotBeEmpty">
<input type="hidden" value="<international:get name="authorizeTransactionKeyCannotBeEmpty"/>" id="authorizeTransactionKeyCannotBeEmpty">
<input type="hidden" value="<international:get name="paypalApiUserNameCannotBeEmpty"/>" id="paypalApiUserNameCannotBeEmpty">
<input type="hidden" value="<international:get name="paypalApiPasswordCannotBeEmpty"/>" id="paypalApiPasswordCannotBeEmpty">
<input type="hidden" value="<international:get name="paypalSignatureCannotBeEmpty"/>" id="paypalSignatureCannotBeEmpty">


<div class="extendedItemSettingsWindowDiv">
    <h1 id="csrFormHeader"><international:get name="childSiteRegistration"/></h1>
    <h1 id="csrNetworkHeader" style="display: none;"><international:get name="editNetworkSettings"/></h1>
    <h1 id="csrWhiteLabelHeader" style="display: none;"><international:get name="editWhiteLabelSettings"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title>
        <jsp:attribute name="customServiceName">childSiteRegistrationService</jsp:attribute>
    </widget:title>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="childSiteRegistrationErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="csrReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <%---------------------------------------------Div with content of tabs-------------------------------------------%>
    <div id="formSettingsTab" class="tab" style='display:block;height:500px;'>
        <%@ include file="childSiteRegistrationFormSettingsTab.jsp" %>
        <br clear="all">
    </div>
    <div id="paymentSettingsTab" class="tab" style='display:none;height:500px;'>
        <%@ include file="childSiteRegistrationNetworkSettingsTab.jsp" %>
        <br clear="all">
    </div>
    <div id="whiteLabelSettingsTab" class="tab" style='display:none;height:500px;'>
        <%@ include file="childSiteRegistrationWhiteLabelSettingsTab.jsp" %>
        <br clear="all">
    </div>
    <%---------------------------------------------Div with content of tabs-------------------------------------------%>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureCSRButtons">
        <input type="checkbox"
               id="iAgreeTermsAndConditions" <% if (service.getChildSiteRegistration().isModified()) { %>
               checked="checked"<% } %>/>
        <span id="iAgreeTermsAndConditionsCheckboxWithlabel">
            <label id="iAgreeTermsAndConditionsLabel" for="iAgreeTermsAndConditions">
                &nbsp;<international:get name="iAgree"/>
            </label>
            <a href="javascript:showNetworkSettingsHelpWindow('termsOfUse');"><international:get
                    name="termsConditions"/></a>
        </span>
        &nbsp;&nbsp;
        <input type="button" value="Apply" id="windowApply" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureChildSiteRegistration.save(false);">
        <input type="button" value="Save" id="windowSave" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureChildSiteRegistration.save(true);">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>

<div style="display:none;">
    <div id="moreInfoText">
        <div class="windowOneColumn">
            <div style="overflow:auto;width:520px;padding:10px; text-align:left;">
                <div><international:get name="moreInfoText"/></div>
                <br>
                <br>
            </div>
            <div align="right" style="margin:10px 0;">
                <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';"
                       value="Close" onclick="closeConfigureWidgetDiv();"/>
            </div>
        </div>
    </div>
    <div id="whatsThisTemplateInstruction">
        <div class="windowOneColumn">
            <div style="overflow:auto;width:520px;height:140px;padding:10px; text-align:left;">
                <div><international:get name="whatsThisTemplateInstruction"/></div>
                <br>
                <br>
            </div>
            <div align="right" style="margin:10px 0;">
                <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';"
                       value="Close" onclick="closeConfigureWidgetDiv();"/>
            </div>
        </div>
    </div>
    <div id="whatsThisChargeInstruction">
        <div class="windowOneColumn">
            <div style="overflow:auto;width:520px;height:140px;padding:10px; text-align:left;">
                <div style="font-weight:bold;"><international:get name="thereAreTwoWays"/></div>
                <br>
                <div><international:get name="firstWay"/></div>
                <div><international:get name="secondWay"/></div>
                <br>
            </div>
            <div align="right" style="margin:10px 0;">
                <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';"
                       value="Close" onclick="closeConfigureWidgetDiv();"/>
            </div>
        </div>
    </div>
    <div id="whatsThisAccessInstruction">
        <div class="windowOneColumn">
            <div style="overflow:auto;width:520px;height:140px;padding:10px; text-align:left;">
                <div><international:get name="whatsThisAccessInstruction"/></div>
                <br>
                <br>
            </div>
            <div align="right" style="margin:10px 0;">
                <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';"
                       value="Close" onclick="closeConfigureWidgetDiv();"/>
            </div>
        </div>
    </div>
    <div id="whatsThisSharedContentInstruction">
        <div class="windowOneColumn">
            <div style="overflow:auto;width:520px;height:340px; padding:10px; text-align:left;">
                <div><international:get name="whatsThisSharedContentInstruction"/></div>
                <br>
                <br>
            </div>
            <div align="right" style="margin:10px 0;">
                <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';"
                       value="Close" onclick="closeConfigureWidgetDiv();"/>
            </div>
        </div>
    </div>
    <div id="termsOfUse">
        <div class="windowOneColumn">
            <div style="overflow:auto;width:520px;height:340px;padding:10px; text-align:left;">
                <div><international:get name="termsOfUse"/></div>
                <br>
                <br>
            </div>

            <div align="right" style="margin:10px 0;">
                <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';"
                       value="Close" onclick="closeConfigureWidgetDiv();"/>
            </div>
        </div>
    </div>
</div>

