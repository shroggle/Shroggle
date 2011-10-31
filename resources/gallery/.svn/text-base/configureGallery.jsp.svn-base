<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.entity.FormItemType" %>
<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.logic.form.FormItemsManager" %>
<jsp:useBean id="galleryService" scope="request" type="com.shroggle.presentation.gallery.ConfigureGalleryService"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/gallery/configureGalleryData.jsp" flush="true"/>
<international:part part="configureGallery"/>
<%
    boolean showDescription = galleryService.getGallery().isShowNotesComments();
    final String header = galleryService.getGallery().getNotesComments();
%>
<input type="hidden" id="selectedGalleryId" value="<%= galleryService.getGallery().getId() %>">
<input type="hidden" id="wrongRegistrationFormId" value="<international:get name="wrongRegistrationFormId"/>">
<input type="hidden" id="wrongYourVotesId" value="<international:get name="wrongYourVotesId"/>">
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
<input type="hidden" value="<%= FormItemType.TEXT_INPUT_FIELD.toString() %>" id="textInputFieldFormItemType"/>

<% for (FormItemName formItemName : FormItemsManager.getRestrictedFormItemsForGalleryDataAndNavigation()) { %>
<input type="hidden" class="galleryRestrictedFields" value="<%= formItemName %>"/>
<% } %>

<% for (FormItemName formItemName : FormItemsManager.getRestrictedFormItemsForGalleryNavigation()) { %>
<input type="hidden" class="galleryNavigationRestrictedFields" value="<%= formItemName %>"/>
<% } %>

<div id="GalleryHeader" style="display:none"><%= header %>
</div>
<input type="hidden" id="showGalleryHeader" value="<%= showDescription %>"/>

<textarea onfocus="trimTextArea(this);" rows="5" cols="5" style="display: none;" id="configureGalleryForm">
    <jsp:include page="/gallery/getForm.jsp" flush="true"/>
</textarea>

<div class="extendedItemSettingsWindowDiv">
    <h1><international:get name="galleryCatalogSettings"/></h1>
    <% if (galleryService.getWidgetTitle() != null) { %>
    <widget:title customServiceName="galleryService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="galleryErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="galleryReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <%---------------------------------------------Div with gallery name; edit link-----------------------------------%>
    <div id="galleryNameDiv" style="margin-bottom:10px;margin-top:10px;">
        <span style="margin-right:100px">
            <label for="configureGalleryName">
                <international:get name="galleryName"/>
            </label>
            &nbsp;<input type="text" class="title" maxlength="255" value="<%= galleryService.getGallery().getName() %>"
                         id="configureGalleryName">
        </span>
        <span>
            <label for="galleryShowEditorLink"
                   onmouseover="bindTooltip({element:this, contentId:'GalleryHeader'});">
                <international:get name="galleryNotesComments"/>
            </label>
            <a id="galleryShowEditorLink"
               href="javascript:showConfigureItemDescription({id:'Gallery'});"
               onmouseover="bindTooltip({element:this, contentId:'GalleryHeader'});">
                <international:get name="edit"/>
            </a>
        </span>
    </div>
    <%---------------------------------------------Div with gallery name; edit link-----------------------------------%>
    <%---------------------------------------------Div with tabs------------------------------------------------------%>
    <div id="tabs">
        <input id="tabWidth" value="180" type="hidden">
        <ul>
            <li>
                <a class="selected_tab tabTitle" onclick="showTab(this);">
                    <international:get name="basicSettings"/>
                </a>
                <a class="unselected_tab tabTitle" onclick="showTab(this);">
                    <international:get name="advancedSettings"/>
                </a>
                <a class="unselected_tab tabTitle"
                   onclick="showTab(this);">
                    <international:get name="voteRanking"/>
                </a>
                <a class="unselected_tab tabTitle" id="eCommerceTab" onclick="showTab(this);">
                    <international:get name="eCommerceTab"/>
                </a>
                <a id="interlayer" class="interlayer_tab" style="padding-right:133px">
                    &nbsp;
                </a>
            </li>
        </ul>
    </div>
    <%---------------------------------------------Div with tabs------------------------------------------------------%>
    <%---------------------------------------------Div with content of tabs-------------------------------------------%>
    <div class="tabbedArea">
        <div class="tab galleryTab">
            <jsp:include page="/gallery/configureGalleryBasicSettings.jsp" flush="true"/>
        </div>
        <div class="tab galleryTab" style="display: none;">
            <jsp:include page="/gallery/configureGalleryAdvancedSettings.jsp" flush="true"/>
        </div>
        <div class="tab galleryTab" style="display: none;">
            <jsp:include page="/gallery/configureGalleryVoteSettings.jsp" flush="true"/>
        </div>
        <div class="tab galleryTab" style="display: none;">
            <jsp:include page="/gallery/configureGalleryECommerce.jsp" flush="true"/>
        </div>
    </div>
    <%---------------------------------------------Div with content of tabs-------------------------------------------%>
    <br/>

</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureGalleryButtons">
        <input type="button" value="Apply" id="windowApply" onclick="configureGallery.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave"
               onclick="configureGallery.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

<div id="iamgeWhatsThis" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow: auto; height: 50px; width: 320px; padding: 10px; text-align: left;">
            <div id="helpText">&nbsp;</div>
            <br><br>
        </div>
        <br clear="all"><br>

        <p align="right">
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" value="Close"
                   onclick="return false; closeConfigureWidgetDiv();"/>
        </p>
    </div>
</div>

