    <%@ page import="com.shroggle.entity.DraftSlideShow" %>
<%@ page import="com.shroggle.presentation.slideShow.ConfigureSlideShowService" %>
<%@ page import="com.shroggle.entity.SlideShowTransitionEffectType" %>
<%@ page import="com.shroggle.entity.SlideShowType" %>
<%@ page import="com.shroggle.entity.SlideShowDisplayType" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="siteItems" tagdir="/WEB-INF/tags/siteItems" %>
<international:part part="slideShow"/>
<%
    final ConfigureSlideShowService service = (ConfigureSlideShowService) request.getAttribute("slideShowService");
    final DraftSlideShow slideShow = service.getSlideShow();

    final boolean showDescription = slideShow.isShowDescription();
    final String header = slideShow.getDescription() != null ? slideShow.getDescription() : "";
%>
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>

<div id="SlideShowHeader" style="display:none"><%= header %>
</div>
<input type="hidden" id="showSlideShowHeader" value="<%= showDescription %>"/>

<label for="slideShowName"><international:get name="slideShowName"/></label>
<input class="title" type="text" id="slideShowName" maxlength="255"
       value="<%= service.getSlideShow().getName() %>">
    <span style="padding-left:100px;">
    <label for="editSlideShowHeader"
           onmouseover="bindTooltip({element:this, contentId:'SlideShowHeader'});"><international:get
            name="slideShowHeader"/></label>
        <a id="editSlideShowHeader"
           onmouseover="bindTooltip({element:this, contentId:'SlideShowHeader'});"
           href="javascript:showConfigureItemDescription({id:'SlideShow'});"><international:get
                name="editHeader"/></a>
    </span>

<div class="slideShowSettingsBlock">
    <h2><international:get name="sourceHeader"/></h2>

    <div class="slideShowSourceSelectDiv">
        <label for="slideShowImagesSourceSelect"><international:get name="selectSource"/></label>
        <select id="slideShowImagesSourceSelect" onchange="configureSlideShow.sourceSelectChange(this);">
            <option id="UPLOAD_SOURCE" selected="selected"><international:get name="uploadSource"/></option>
            <option id="FORM_SOURCE"><international:get name="formSource"/></option>
        </select>
    </div>
</div>

<div class="imagesSourceDiv">
    <div id="UPLOAD_SOURCEDiv">
        <h2><international:get name="uploadSourceHeader"/></h2>

        <div id="slideShowUpload" class="slideShowUpload"></div>

        <div>
            <a href="#" onclick="$('#slideShowManageItemsTab').click()"><international:get
                    name="manageAndSortImages"/></a>
        </div>
    </div>

    <div id="FORM_SOURCEDiv" style="display:none;">
        <div style="float:left;">
            <label for="selectExistingForm"><international:get name="existingForm"/></label>
            <select id="selectExistingForm"
                    onchange="configureSlideShow.selectFormChange(this, <%= service.getSite().getSiteId() %>);">
                <option value="-1"><international:get name="selectExistingForm"/></option>
                <siteItems:asOptions value="<%= service.getFormManagers() %>"/>
            </select>
        </div>

        <div style="display:none;">
            <label for="selectImageFormItem">
                <international:get name="selectImageFormItemLabel"/>
            </label>
            <select id="selectImageFormItem">
                <option value="-1"><international:get name="selectImageFormItem"/></option>
            </select>
        </div>

        <div style="clear:both;"></div>

        <div>
            <label for="selectExistingFilter"><international:get name="existingFilter"/></label>
            <select id="selectExistingFilter">
                <option value="-1"><international:get name="selectExistingFilter"/></option>
            </select>
        </div>

        <div>
            <label for="selectLinkBackToGallery"><international:get name="linkBackToGallery"/></label>
            <select id="selectLinkBackToGallery">
                <option value="-1"><international:get name="selectGallery"/></option>
            </select>
        </div>

        <input type="button" class="but_w100_misc" onmouseout="this.className='but_w100_misc';"
               onmouseover="this.className='but_w100_Over_misc';" value="<international:get name="getPictures"/>"
               onclick="configureSlideShow.uploadImagesFromForm();"/>

        <div id="uploadSlideShowImagesLoadingDiv" style="display:none;">
            <img alt="Loading..." src="/images/ajax-loader-minor.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
        <div id="numberOfUploadedImagesText" class="inlineSuccess"></div>

        <div>
            <a href="javascript:$('#slideShowManageItemsTab').click()"><international:get
                    name="manageAndSortImages"/></a>
        </div>
    </div>
</div>

<div class="slideShowSettingsBlock">
    <h2><international:get name="imageSizeHeader"/></h2>

    <table class="slideShowImageSizeTable">
        <tr>
            <td><label for="imageWidth"><international:get name="imageWidth"/></label></td>
            <td><input type="text" onkeyup="acceptOnlyDigits(this);"
                       value="<%= slideShow.getImageWidth() != null ? slideShow.getImageWidth() : "" %>"
                       id="imageWidth"/>px
            </td>
        </tr>
        <tr>
            <td><label for="imageHeight"><international:get name="imageHeight"/></label></td>
            <td><input type="text" onkeyup="acceptOnlyDigits(this);"
                       value="<%= slideShow.getImageHeight() != null ? slideShow.getImageHeight() : "" %>"
                       id="imageHeight"/>px
            </td>
        </tr>
    </table>
</div>

<div class="slideShowSettingsBlock">
    <h2><international:get name="slideShowType"/></h2>
</div>

<div id="multipleImagesDiv" class="multipleImagesDiv">
    <label for="numberOfImagesShown"><international:get name="showText"/></label>
    <% final int numberOfImagesShown = slideShow.getNumberOfImagesShown(); %>
    <select id="numberOfImagesShown" onchange="configureSlideShow.onChangingNumOfSlides(this);">
        <option <% if (numberOfImagesShown == 1 ) { %>selected="selected"<% } %> value="1">1</option>
        <option <% if (numberOfImagesShown == 2 ) { %>selected="selected"<% } %> value="2">2</option>
        <option <% if (numberOfImagesShown == 3 ) { %>selected="selected"<% } %> value="3">3</option>
        <option <% if (numberOfImagesShown == 4 ) { %>selected="selected"<% } %> value="4">4</option>
        <option <% if (numberOfImagesShown == 5 ) { %>selected="selected"<% } %> value="5">5</option>
        <option <% if (numberOfImagesShown == 6 ) { %>selected="selected"<% } %> value="6">6</option>
        <option <% if (numberOfImagesShown == 7 ) { %>selected="selected"<% } %> value="7">7</option>
        <option <% if (numberOfImagesShown == 8 ) { %>selected="selected"<% } %> value="8">8</option>
        <option <% if (numberOfImagesShown == 9 ) { %>selected="selected"<% } %> value="9">9</option>
        <option <% if (numberOfImagesShown == 10 ) { %>selected="selected"<% } %> value="10">10</option>
        <option <% if (numberOfImagesShown == 11 ) { %>selected="selected"<% } %> value="11">11</option>
        <option <% if (numberOfImagesShown == 12 ) { %>selected="selected"<% } %> value="12">12</option>
    </select>
    <label for="numberOfImagesShown"><international:get name="multipleImages"/></label>

    <br/>
</div>
<div id="imagesDisplayStyle" class="multipleImagesDiv">
    <select id="displayStyle" onchange="configureSlideShow.displayStyleChange(this);"
        <% if (slideShow.getNumberOfImagesShown() == 1 ) {%>
        disabled="disabled"
        <% } %>>
        <% for (SlideShowDisplayType displayType : SlideShowDisplayType.values()) { %>
        <option value="<%= displayType %>" <% if (slideShow.getDisplayType() != null &&
            slideShow.getDisplayType() == displayType) { %>selected="selected"<% } %>
                ><international:get name="<%= displayType.toString() %>"/></option>
        <% } %>
    </select>
    <label for="displayStyle"><international:get name="displayStyle"/></label>
</div>

<div class="slideShowTransitionBlock">
    <label for="transitionEffect"><international:get name="transitionEffect"/></label>
    <select id="transitionEffect"
            <% if (slideShow.getSlideShowType() == SlideShowType.MULTIPLE_IMAGES &&  
                    (slideShow.getDisplayType() == SlideShowDisplayType.MOVING_STRIP_HORIZONTAL ||
                    slideShow.getDisplayType() == SlideShowDisplayType.MOVING_STRIP_VERTICAL)) { %>
            disabled="disabled"
            <% } %>>
        <% for (SlideShowTransitionEffectType transitionEffectType : SlideShowTransitionEffectType.values()) { %>
        <option value="<%= transitionEffectType %>" <% if (slideShow.getTransitionEffectType() != null &&
            slideShow.getTransitionEffectType() == transitionEffectType) { %>selected="selected"<% } %>
                ><international:get name="<%= transitionEffectType.toString() %>"/></option>
        <% } %>
    </select>
</div>

<div class="slideShowTransitionBlock">
        <label for="autoPlayInterval"><international:get name="autoPlayInterval"/></label>
        <% final int autoPlayInterval = slideShow.getAutoPlayInterval(); %>
        <select id="autoPlayInterval" name="autoPlayIntervalSelect">
            <option <% if (autoPlayInterval == 0 ) { %>selected="selected"<% } %> value="0">0s</option>
            <option <% if (autoPlayInterval == 1000 ) { %>selected="selected"<% } %> value="1000">1s</option>
            <option <% if (autoPlayInterval == 2000 ) { %>selected="selected"<% } %> value="2000">2s</option>
            <option <% if (autoPlayInterval == 3000 ) { %>selected="selected"<% } %> value="3000">3s</option>
            <option <% if (autoPlayInterval == 4000 ) { %>selected="selected"<% } %> value="4000">4s</option>
            <option <% if (autoPlayInterval == 5000 ) { %>selected="selected"<% } %> value="5000">5s</option>
            <option <% if (autoPlayInterval == 7000 ) { %>selected="selected"<% } %> value="7000">7s</option>
            <option <% if (autoPlayInterval == 10000 ) { %>selected="selected"<% } %> value="10000">10s</option>
            <option <% if (autoPlayInterval == 15000 ) { %>selected="selected"<% } %> value="15000">15s</option>
        </select>
        <label for="autoPlayInterval"><international:get name="intervalUnits"/></label>
</div>

<div class="slideShowTransitionBlock">
        <input type="checkbox" id="displayControls" onclick="configureSlideShow.displayControlsClick();"
               <% if (slideShow.isDisplayControls()) { %>checked="checked"<% } %>/>
        <label for="displayControls"><international:get name="displayControls"/></label>
</div>