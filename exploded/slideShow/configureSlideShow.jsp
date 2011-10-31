<%@ page import="com.shroggle.presentation.slideShow.ConfigureSlideShowService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="slideShow"/>
<%
    final ConfigureSlideShowService service = (ConfigureSlideShowService) request.getAttribute("slideShowService");
%>
<div class="itemSettingsWindowDiv">
    <input type="hidden" id="selectedSlideShowId" value="<%= service.getSlideShow().getId() %>"/>
    <input type="hidden" id="slideShowSiteId" value="<%= service.getSlideShow().getSiteId() %>"/>
    <input type="hidden" id="SlideShowNameEmptyException"
           value="<international:get name="SlideShowNameEmptyException"/>"/>
    <input type="hidden" id="SlideShowNameNotUniqueException"
           value="<international:get name="SlideShowNameNotUniqueException"/>"/>
    <input type="hidden" id="SlideShowImageHeightIsEmpty"
           value="<international:get name="SlideShowImageHeightIsEmpty"/>"/>
    <input type="hidden" id="SlideShowImageWidthIsEmpty"
           value="<international:get name="SlideShowImageWidthIsEmpty"/>"/>

    <input type="hidden" id="selectExistingFilterText"
           value="<international:get name="selectExistingFilter"/>"/>
    <input type="hidden" id="selectGalleryText"
           value="<international:get name="selectGallery"/>"/>
    <input type="hidden" id="noFilters"
           value="<international:get name="noFilters"/>"/>
    <input type="hidden" id="noGalleries"
           value="<international:get name="noGalleries"/>"/>
    <input type="hidden" id="selectImageFormItemText"
           value="<international:get name="selectImageFormItem"/>"/>

    <h1><international:get name="subHeader"/><span id="selectedSettingsTabName">Module Settings</span></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="slideShowService"/>
    <% } %>
    <div class="windowTopLine" style="margin-bottom: 5px;">&nbsp;</div>

    <div class="emptyError" id="slideShowErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="slideShowReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <div id="slideShowSettingsTab" class="tab" style='display:block;'>
        <jsp:include page="configureSlideShowSettings.jsp"/>
        <br clear="all">
    </div>
    <div id="manageImagesTab" class="tab" style='display:none;'>
        <jsp:include page="configureSlideShowManageImages.jsp"/>
        <br clear="all">
    </div>

</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" id="windowApply" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureSlideShow.save(false);">
        <input type="button" value="Save" id="windowSave" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureSlideShow.save(true);">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>
