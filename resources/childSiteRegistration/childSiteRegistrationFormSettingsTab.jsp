<%@ page import="com.shroggle.entity.DraftChildSiteRegistration" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.presentation.site.ConfigureChildSiteRegistrationService" %>
<%
    final ConfigureChildSiteRegistrationService serviceFormTab = (ConfigureChildSiteRegistrationService) request.getAttribute("childSiteRegistrationService");

    final boolean showDescription = serviceFormTab.getChildSiteRegistration().isShowDescription();
    final String header = serviceFormTab.getChildSiteRegistration().getDescription() != null ?
            serviceFormTab.getChildSiteRegistration().getDescription() : "";
%>

<input type="hidden" value="<%= serviceFormTab.getWidget() != null ? serviceFormTab.getWidget().getWidgetId() : "-1" %>"
       id="childSiteRegistrationWidgetId"
       style="display:none;">

<div id="ChildSiteRegistrationHeader" style="display:none"><%= header %>
</div>
<input type="hidden" id="showChildSiteRegistrationHeader" value="<%= showDescription %>"/>
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>

<div style="margin:15px 0;">
    <label for="childSiteRegistrationName">
        <international:get name="formName"/>
    </label>
    &nbsp;
    <input type="text" id="childSiteRegistrationName" class="title" maxlength="255"
           value="<%= serviceFormTab.getChildSiteRegistration().getName().replace("\"", "'") %>">
            <span style="background:url(../images/warning.png) no-repeat left 2px; padding:5px 0 0 20px;margin-left:30px; font-size:11px; font-weight:bold; ">
                <international:get name="displaysPublicly"/>
            </span>
</div>

<div style="">
    <div onmouseover="bindTooltip({element:this, contentId:'ChildSiteRegistrationHeader'});">
        <label for="editChildSiteRegistrationHeader">
            <international:get name="childSiteRegistrationDesc"/>
        </label>
        <a id="editChildSiteRegistrationHeader"
           href="javascript:showConfigureItemDescription({id:'ChildSiteRegistration'});">
            <international:get name="editChildSiteRegistrationHeader"/>
        </a>
    </div>
</div>
<div id="childSiteRegistrationTable" style="height:290px;margin-top:20px;">
    <% request.setAttribute("formService", serviceFormTab); %>
    <%@ include file="../site/formTable.jsp" %>
</div>

