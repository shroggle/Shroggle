<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ page import="com.shroggle.presentation.site.ConfigureContactUsService" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureContactUs"/>
<%
    final ConfigureContactUsService service = (ConfigureContactUsService) request.getAttribute("contactUsService");

    final boolean showDescription = service.getContactUs().isShowDescription();
    final String header = service.getContactUs().getDescription() != null ?
            service.getContactUs().getDescription() : "";
%>
<input type="hidden" id="selectedContactUsId" value="<%= service.getContactUs().getId() %>">
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
<input type="hidden" value="<international:get name="InvalidContactUsEmail"/>" id="InvalidContactUsEmail"/>
<input type="hidden" value="<international:get name="EmptyContactUsNameException"/>" id="EmptyContactUsNameException"/>
<input type="hidden" value="<international:get name="ContactUsNotUniqueNameException"/>"
       id="ContactUsNotUniqueNameException"/>
<input type="hidden" value="<international:get name="SelectExistingContactUsException"/>"
       id="SelectExistingContactUsException"/>

<div class="extendedItemSettingsWindowDiv">
    <div id="ContactUsHeader" style="display:none"><%= header %>
    </div>
    <input type="hidden" id="showContactUsHeader" value="<%= showDescription %>"/>

    <h1><international:get name="addEditContactUs"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="contactUsService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="contactUsErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="contactUsReadOnlyMessage">You have only read-only
        access to this module.</div>

    <div style="margin-top:10px">
        <label for="contactUsName"> <international:get name="contactUsFormName"/></label>
        <input type="text" class="title" maxlength="255" id="contactUsName"
               value="<%= service.getContactUs().getName() %>"/>
        <span style="padding-left:100px;">
            <label for="editContactUsHeader"
                   onmouseover="bindTooltip({element:this, contentId:'ContactUsHeader'});">
                <international:get name="contactUsFormHeader"/>
            </label>
            <a id="editContactUsHeader"
               onmouseover="bindTooltip({element:this, contentId:'ContactUsHeader'});"
               href="javascript:showConfigureItemDescription({id:'ContactUs'});">
                <international:get name="editContactUsHeader"/>
            </a>
        </span>
    </div>

    <div style="margin-top:20px">
        <label for="emailTextField"><international:get name="emailAddress"/></label>
        <input type="text" class="title" id="emailTextField" size="40" maxlength="255"
               value="<%= service.getContactUs().getEmail() %>">
    </div>

    <div style="margin:10px 0 10px 0">
        <b class="inform_mark"><international:get name="info"/></b>
    </div>

    <div id="contactUsOptionsTableDiv" style="height:290px">
        <% request.setAttribute("formService", service); %>
        <%@ include file="formTable.jsp" %>
    </div>

    <br clear="all">
    <br>
    <br>

    <div class="inform_mark"><international:get name="info2"/></div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureContactUsButtons">
        <input type="button" value="Apply" id="windowApply" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" id="windowCancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

