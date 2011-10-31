<%@ page import="com.shroggle.presentation.customForm.ConfigureCustomFormService" %>
<%@ page import="com.shroggle.entity.DraftCustomForm" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="configureCustomForm"/>
<%
    final ConfigureCustomFormService service = (ConfigureCustomFormService) request.getAttribute("customFormService");

    boolean showDescription = service.getSelectedCustomForm().isShowDescription();
    final String header = service.getSelectedCustomForm().getDescription() != null ?
            service.getSelectedCustomForm().getDescription() : "";
%>
<div class="extendedItemSettingsWindowDiv">
    <input type="hidden" id="selectedCustomFormId" value="<%= service.getSelectedCustomForm().getId() %>"/>
    <input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
    <input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
    <input type="hidden" value="<international:get name="emptyFormName"/>" id="emptyFormName"/>

    <div id="CustomFormHeader" style="display:none"><%= header %>
    </div>
    <input type="hidden" id="showCustomFormHeader" value="<%= showDescription %>"/>

    <h1><international:get name="addEditCustomForm"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="customFormService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="customFormErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="customFormReadOnlyMessage">You have only read-only
        access to this module.</div>

    <div style="width:100%">
        <label for="formName" style="width:5%;"><international:get name="formName"/></label>
        <input class="title" style="width:40%;" type="text" id="formName" maxlength="255"
               value="<%=service.getSelectedCustomForm().getName()%>">
        <span style="padding-left:100px;">
            <label for="editCustomFormHeader"
                   onmouseover="bindTooltip({element:this, contentId:'CustomFormHeader'});"><international:get
                    name="customFormDesc"/></label>
            <a id="editCustomFormHeader"
               onmouseover="bindTooltip({element:this, contentId:'CustomFormHeader'}, event);"
               href="javascript:showConfigureItemDescription({id:'CustomForm'});">
                <international:get name="editCustomFormHeader"/>
            </a>
        </span>
    </div>
    <br/>

    <div class="inform_mark" style="margin-left:0"><international:get name="inform"/></div>
    <div class="mark" style="margin-left:0"><a
            href="javascript:showCreateRegistrationFormInfoWindow()"><international:get name="createReg"/></a></div>
    <div id="customFormInfoWindow" style="display:none">
        <div class="windowOneColumn">
            <div style="margin-bottom:10px;">
                <international:get name="infoWindowText"/>
            </div>

            <div align="right">
                <input type="button" onclick="closeConfigureWidgetDiv();" value="Close"
                       class="but_w73" onmouseover="this.className='but_w73_Over';"
                       onmouseout="this.className='but_w73';">
            </div>
        </div>
    </div>
    <br>

    <div id="formTableDiv" style="height:320px">
        <% request.setAttribute("formService", service); %>
        <%@ include file="formTable.jsp" %>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureCustomFormButtons">
        <input type="button" value="Apply" id="windowApply" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel">
    </div>
</div>
