<%@ page import="com.shroggle.presentation.registration.ConfigureRegistrationService" %>
<%@ page import="com.shroggle.entity.DraftRegistrationForm" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="registration"/>
<%
    final ConfigureRegistrationService service = (ConfigureRegistrationService) request.getAttribute("registrationService");
    final Integer defaultRegistrationFormId = service.getSite() == null ? null : service.getSite().getDefaultFormId();

    final boolean showDescription = service.getRegistrationForm().isShowDescription();

    final String header = service.getRegistrationForm().getDescription() != null ?
            service.getRegistrationForm().getDescription() : "";

    final String termsAndConditions = service.getRegistrationForm().getTermsAndConditions() != null ?
            service.getRegistrationForm().getTermsAndConditions() : "";
%>
<div class="extendedItemSettingsWindowDiv">
    <input type="hidden" id="selectedFormId"
           value="<%= service.getRegistrationForm().getFormId() %>"/>
    <input type="hidden" value="<international:get name="RegistrationFormNotUniqueNameException"/>"
           id="RegistrationFormNotUniqueNameException"/>
    <input type="hidden" value="<international:get name="RegistrationFormNullOrEmptyNameException"/>"
           id="RegistrationFormNullOrEmptyNameException"/>
    <input type="hidden" value="<international:get name="RegistrationFormNotSelectedException"/>"
           id="RegistrationFormNotSelectedException"/>

    <input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
    <input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
    <input type="hidden" value="<international:get name="registrationTermsAndConditionsWindowHeader"/>"
           id="registrationTermsAndConditionsWindowHeader"/>

    <div id="RegistrationHeader" style="display:none"><%= header %>
    </div>
    <input type="hidden" id="showRegistrationHeader" value="<%= showDescription %>"/>

    <div id="registrationTermsAndConditionsHeader" style="display:none">
        <%= termsAndConditions %>
    </div>

    <h1><international:get name="subHeader"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="registrationService"/>
    <% } %>
    <div class="windowTopLine" style="margin-bottom: 5px;">&nbsp;</div>

    <div class="emptyError" id="registrationErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="registrationReadOnlyMessage">You have only read-only
        access to this module.</div>

    <label for="registrationFormName"><international:get name="registrationFormName"/></label>
    <input class="title" type="text" id="registrationFormName" maxlength="255"
           value="<%= service.getRegistrationForm().getName() %>">
    <span style="padding-left:100px;">
    <label for="editRegistrationHeader"
           onmouseover="bindTooltip({element:this, contentId:'RegistrationHeader'});"><international:get
            name="registrationFormHeader"/></label>
        <a id="editRegistrationHeader"
           onmouseover="bindTooltip({element:this, contentId:'RegistrationHeader'});"
           href="javascript:showConfigureItemDescription({id:'Registration'});"><international:get
                name="editRegistrationFormHeader"/></a>
    </span>
    <br/><br/><input type="checkbox" id="registrationDefaultForm"
                     <% if (defaultRegistrationFormId != null && service.getRegistrationForm().getFormId() == defaultRegistrationFormId) {%>checked="checked"
                     disabled="disabled"<% } %>/>
    <label for="registrationDefaultForm"><international:get name="setAsDef"/></label>

    <div style="margin-top:5px;">
        <input type="checkbox" id="networkRegistration"
               <%if (service.getRegistrationForm().isNetworkRegistration()){ %>checked="" <% } %>/>
        <label for="networkRegistration"><international:get name="networkRegistration"/></label>
    </div>
    <div style="margin-top:5px;margin-left:5px;">
        <table>
            <tr>
                <td style="vertical-align:top;">
                    <international:get name="placeAllFormRegistrantsIntoTheFollowingGroups"/>:
                </td>
                <td style="vertical-align:middle;">
                    <div id="placeAllFormRegistrantsIntoTheFollowingGroups" class="configureRegistrationGroups">
                        <%
                            request.setAttribute("groups", service.getGroups());
                            request.setAttribute("savedGroupsWithTime", service.getGroupsWithTime());
                        %>
                        <jsp:include page="../account/groups/availableGroups.jsp" flush="true"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <!--REGISTRATION FIELDS TABLE-->
    <div id="formTableDiv" class="accountRegistrationFormTableDiv">
        <% request.setAttribute("formService", service); %>
        <%@ include file="formTable.jsp" %>
    </div>

    <div id="termsAndConditions" style="margin-top:15px">
        <input type="checkbox"
               <% if (service.getRegistrationForm().isRequireTermsAndConditions()) { %>checked="checked"<% } %>
               onclick="configureRegistration.disableTermsAndConditions();"
               id="requireTermsAndConditions"/><label for="requireTermsAndConditions"
                                                      onmouseover="bindTooltip({element:this, contentId:'registrationTermsAndConditionsHeader'});"><international:get
            name="requireTermsAndConditions"/></label>

        <a id="editTermsAndConditions" style="margin-left:20px"
           onmouseover="bindTooltip({element:this, contentId:'registrationTermsAndConditionsHeader'});"
           href="javascript:showConfigureItemDescription({id:'registrationTermsAndConditions', showCheckbox:false, headerId:'registrationTermsAndConditionsWindowHeader'});"><international:get
                name="editTermsAndConditions"/></a>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" id="windowApply" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureRegistration.save(false);">
        <input type="button" value="Save" id="windowSave" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureRegistration.save(true);">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>
