<%@ page import="com.shroggle.presentation.childSites.ManageNetworkRegistrantsService" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.logic.site.billingInfo.ChargeTypeManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<international:part part="editChildSiteRegistrationWindow"/>
<%
    final ManageNetworkRegistrantsService service = (ManageNetworkRegistrantsService) request.getAttribute("service");
    final ChildSiteSettings childSiteSettings = service.getChildSiteSettings();
    double monthlyShroggleBillingPrice = new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice();
    final List<Site> blueprints = service.getBlueprints() != null ? service.getBlueprints() : new ArrayList<Site>();
%>
<input type="hidden" id="filledFormId" value="<%= service.getFilledForm().getFilledFormId() %>">
<input type="hidden" id="accessHelpText" value="<international:get name="accessHelpText"/>">
<input type="hidden" id="contentHelpText" value="<international:get name="contentHelpText"/>">


<div class="windowOneColumn">
    <h1>
        <international:get name='<%= service.isShowVisitor() ? "editRegisteredVisitor" : "editRegistrationData" %>'/>
    </h1>

    <div class="emptyError" id="errors"></div>

    <table>
        <tr>
            <td style="padding-right:20px;">
                <center>
                        <span style="font-weight:bold;">
                            <international:get name="childSiteRegistrationData"/>
                        </span>
                </center>
                <br>

                <div class="editChildSiteRegistrationDiv" style="overflow-x:hidden;">
                    <table class="registrationTable" id="editRegisteredVisitorTable" width="80%">
                        <jsp:include page="../site/render/widgetForm.jsp" flush="true"/>
                    </table>
                </div>
            </td>
            <td>
                <% if (!service.isShowVisitor()) { %>
                <% if (childSiteSettings != null) { %>
                <% double monthlyChildBillingPrice = childSiteSettings.isUseOneTimeFee() ? childSiteSettings.getOneTimeFee() : childSiteSettings.getPrice250mb(); %>
                <div>
                    <center>
                        <span style="font-weight:bold;">
                            <international:get name="networkSettings"/>
                        </span>
                    </center>
                    <br>

                    <div class="editChildSiteRegistrationDiv">
                        <international:get name="paid"/>
                        <% if (SiteStatus.ACTIVE.equals(childSiteSettings.getSiteStatus())) { %>
                        <international:get name="yes"/>
                        <% } else { %>
                        <international:get name="no"/>
                        <% } %>
                        <br>
                        <international:get name="paymentMethod"/>
                        <% if (childSiteSettings.getSitePaymentSettings().getCreditCard() != null) { %>
                        <international:get name="cc"/>
                        <% } else { %>
                        <international:get name="paypal"/>
                        <% } %>
                        <br>
                        <br>

                        <span style="font-weight:bold;"><international:get name="mediaStorageOption"/></span>

                        <div style="margin-left:5px;">
                            <international:get name="shroggleFee"/> <%= monthlyShroggleBillingPrice %>
                            <international:get
                                    name="perMonth"/>
                            <br>
                            <% if (childSiteSettings.isUseOneTimeFee()) { %>
                            <international:get name="oneTimeSetupFee"/>
                            <%= monthlyChildBillingPrice %>
                            <% } else { %>
                            <international:get name="networkFee"/>
                            <%= monthlyChildBillingPrice %>
                            <international:get name="perMonth"/>
                            <% } %>
                        </div>

                        <br>
                        <% if (childSiteSettings.getStartDate() != null || childSiteSettings.getEndDate() != null) { %>
                        <span style="font-weight:bold"><international:get name="chronologicalLimits"/></span>

                        <div style="margin-left:5px;">
                            <% } %>
                            <% if (childSiteSettings.getStartDate() != null) { %>
                            <international:get
                                    name="StartDate"/> <%= DateUtil.toMonthDayAndYear(childSiteSettings.getStartDate()) %>
                            <br>
                            <% } %>
                            <% if (childSiteSettings.getEndDate() != null) { %>
                            <international:get
                                    name="endDate"/> <%= DateUtil.toMonthDayAndYear(childSiteSettings.getEndDate()) %>
                            <br>
                            <% } %>
                            <% if (childSiteSettings.getStartDate() != null || childSiteSettings.getEndDate() != null) { %>
                        </div>
                        <% } %>
                        <span style="font-weight:bold"><international:get name="blueprintSitesAvailable"/></span>
                        <br>

                        <div style="width:300px;height:100px;overflow:auto; border: 1px solid darkgray; padding:5px;">
                            <% for (Site blueprint : blueprints) { %>
                            <%= blueprint.getTitle() %> <br>
                            <% } %>
                        </div>
                        <international:get name="requiredToUseASiteBlueprint"/>
                        <% if (childSiteSettings.isRequiredToUseSiteBlueprint()) { %>
                        <international:get name="yes"/>
                        <% } else { %>
                        <international:get name="no"/>
                        <% } %>
                        <br>
                        <br>
                        <span style="font-weight:bold"><international:get name="termsAndConditions"/></span>
                        <br>

                        <div style="width:300px;height:100px;overflow:auto; border: 1px solid darkgray; padding:10px;">
                            <%= childSiteSettings.getTermsAndConditions() != null ? childSiteSettings.getTermsAndConditions() : ""%>
                        </div>
                        <br>
                            <span style="font-weight:bold"><international:get
                                    name="yourLevelOofAccessOnRegistrantSites"/></span>
                        <% if (SiteAccessLevel.ADMINISTRATOR.equals(childSiteSettings.getAccessLevel())) { %>
                        <international:get name="administrator"/>
                        <% } else { %>
                        <international:get name="siteEditor"/>
                        <% } %>
                        <br>
                        <a href="javascript:showEditCSRHelpWindow('accessHelpText', 110);">
                            <international:get name="whatsThis"/>
                        </a>
                        <br><br>
                        <span style="font-weight:bold"><international:get name="sharedContent"/></span>
                        <br>
                        <a href="javascript:showEditCSRHelpWindow('contentHelpText', 30);">
                            <international:get name="whatsThis"/>
                        </a>
                        <br>
                        <br>
                    </div>
                </div>
                <% } %>
                <% } %>
            </td>
        </tr>
    </table>

    <div align="right" style="margin-top:10px;">
        <% if (!service.isShowVisitor()) { %>
        <center>
            <span id="passwordSentText" style="color:green;visibility:hidden;">
                <international:get name="passwordSent"/>
            </span>
        </center>
        <input type="button" value="<international:get name="sendPassword"/>"
               class="but_w230"
               onmouseover="this.className = 'but_w230_Over';"
               onmouseout="this.className = 'but_w230';"
               onclick="sendVisitorPassword(<%= service.getVisitor().getUserId() %>);">
        <% } %>
        <input type="button" id="submit0" class="but_w73" value="Save"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';"
            <% if (service.getParentSiteId() > 0) { %>
               onclick="saveNetworkVisitorEdit(<%= service.getVisitor().getUserId() %>, <%= service.getUniqueWidgetId() %>, <%= service.getParentSiteId() %>);"
            <% } else { %>
               onclick="saveFilledFormEdit(<%= service.getFilledForm().getFilledFormId() %>, <%= service.getUniqueWidgetId() %>, true, false);"
            <% } %> >

        <input type="button" id="close0" class="but_w73" value="Close"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();">
    </div>
</div>


<div id="editCSRWhatsThis" style="display:none;">
    <div class="windowOneColumn">
        <div id="innerTextDiv" style="overflow:auto; width:320px; padding:10px; text-align:left;">
            <div id="editCSRHelpText">&nbsp;</div>
            <br>
            <br>
        </div>
        <bR clear="all"><bR>

        <p align="right">
            <input type="button" value="Close" class="but_w73"
                   onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   onclick="closeConfigureWidgetDiv();"/>
        </p>
    </div>
</div>