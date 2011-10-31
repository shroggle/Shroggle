<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.presentation.image.ConfigureImageData" %>
<%@ page import="com.shroggle.presentation.image.ConfigureImageService" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureImageWidget"/>

<%
    final ConfigureImageService service = (ConfigureImageService) request.getAttribute("imageService");
    final ConfigureImageData data = service.getConfigureImageData();
%>
<input type="hidden" id="disableLinksArea" value="<%= data.isDisableLinksArea() %>">

<div style="width:800px;height:540px;">
<span>
    <international:get name="specifyImage"/>
    <a href="javascript:showImageWhatsThisWindow()" class="blue_11"
       style=" background:url(/images/QuestionMark.png) no-repeat right top; padding:2px 20px 2px 0;">
        <international:get name="whatsThis"/>
    </a>

    <br/>
    <br/>

    <div style="margin-bottom:10px">
        <label for="widgetImageTitle"><international:get name="imageTitle"/></label>
        <input type="text" id="widgetImageTitle" value="<%= data.getWidgetImageTitle() %>" maxlength="255"
               onblur="setSampleTitle();" onkeyup="checkLabelsCheckboxes();" class="txt_nowidth"
               style="width:140px;margin-left:10px">
    </div>


    <table style="width:300px;">
        <tr>
            <td style="padding-bottom:15px;width:80px;">
                <label for="displayLabel">
                    <international:get name="displayLabel"/>
                </label>

            </td>
            <td>
                <input id="displayLabel" <% if (data.isLabelCheckboxesDisabled()) { %>
                       disabled<% } %>
                       onchange="disableAboveBelowRadiobuttons(this);"
                       type="checkbox" <%= data.getTitlePosition() != TitlePosition.NONE ? "checked=\"checked\"" : "" %>>
            </td>
            <td>
                <input type="radio" style="margin-left:35px"
                       id="displayLabelAbove"
                       name="titlePosition"
                    <%= data.getTitlePosition() == TitlePosition.ABOVE ? "checked=\"checked\"" : "" %>
                    <%= (data.getTitlePosition() == TitlePosition.NONE || data.isLabelCheckboxesDisabled()) ? "disabled=\"true\"" : "" %>
                        >
                <label for="displayLabelAbove">
                    <international:get name="above"/>
                </label>
                &nbsp;
                <input type="radio" id="displayLabelBelow"
                       name="titlePosition" <%= data.getTitlePosition() == TitlePosition.BELOW ? "checked=\"checked\"" : "" %>
                    <% if (data.getTitlePosition() == TitlePosition.NONE || data.isLabelCheckboxesDisabled()) { %>
                       disabled="true" checked="checked"<% } %>>&nbsp;
                <label for="displayLabelBelow">
                    <international:get name="below"/>
                </label>
            </td>
        </tr>
        <tr>
            <td style="padding-bottom:15px;width:80px;">
                <label for="labelIsALink">
                    <international:get name="labelIsALink"/>
                </label>
            </td>
            <td>
                <input type="checkbox" id="labelIsALink"
                       onchange="disableLinksArea(!this.checked && !document.getElementById('imageIsALink').checked);checkDisplayLabelCheckbox(this.checked);"
                    <%= data.isLabelIsALinnk() ? "checked=\"checked\"" : "" %> <% if (data.isLabelCheckboxesDisabled()) { %>
                       disabled<% } %>>
            </td>
            <td></td>
        </tr>
        <tr>
            <td style="width:80px;">
                <label for="imageIsALink">
                    <international:get name="imageIsALlink"/>
                </label>
            </td>
            <td><input type="checkbox" id="imageIsALink"
                       onchange="disableLinksArea(!this.checked && !document.getElementById('labelIsALink').checked);"
                <%= data.isImageIsALinnk() ? "checked=\"checked\"" : "" %>></td>
            <td></td>
        </tr>
    </table>
    <br/>
</span>
<div style="margin-bottom:10px;">
    <international:get name="selectWhatTheThumbnailsWillLinkTo"/>
</div>
<%----------------------------------------Image Link Type Select----------------------------------------------%>
<select id="imageLinkType" onchange="thumbnailLinksChanged();" style="width:200px; margin-top:5px;">
    <option value="EXTERNAL_URL"
            <%= data.getImageLinkType().equals(ImageLinkType.EXTERNAL_URL) ? "selected" : "" %>
            >
        <international:get name="externalURL"/>
    </option>
    <option value="INTERNAL_URL"
            <%= data.getImageLinkType().equals(ImageLinkType.INTERNAL_URL) ? "selected" : "" %>
            >
        <international:get name="internalPage"/>
    </option>
    <option value="MEDIA_FILE"
            <%= data.getImageLinkType().equals(ImageLinkType.MEDIA_FILE) ? "selected" : "" %>
            >
        <international:get name="mediaFile"/>
    </option>
    <option value="TEXT_AREA"
            <%= data.getImageLinkType().equals(ImageLinkType.TEXT_AREA) ? "selected" : "" %>
            >
        <international:get name="textArea"/>
    </option>
</select>
<%----------------------------------------Image Link Type Select----------------------------------------------%>
<br/>
<br/>


<%----------------------------------EXTERNAL_URLDiv----------------------------------------%>

<div id="EXTERNAL_URLDiv"
        <%= data.getImageLinkType().equals(ImageLinkType.EXTERNAL_URL) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
    <international:get name="enterTheURL"/>
    <br/>
    <br/>
    http:// <input type="text" style="width:300px;margin-right:15px;" id="externalUrl" maxlength="255"
                   value="<%= data.getExternalUrl() %>"
        >
    <a href="#" target="_blank" style="font-weight:bold;" id="previewExternalUrl"
       onclick="this.href= createCorrectUrl(document.getElementById('externalUrl').value);">
        <international:get name="openThisUrl"/>
    </a>
    <br/>
    <br/>
    <input type="radio"
        <%= data.getExternalUrlDisplaySettings() ==
            ExternalUrlDisplaySettings.OPEN_IN_NEW_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_NEW_WINDOW_EXTERNAL_URL"
           name="EXTERNAL_URLDivImageBrowserWindow"
            >
    <label for="OPEN_IN_NEW_WINDOW_EXTERNAL_URL">
        <international:get name="newBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;"
        <%= data.getExternalUrlDisplaySettings() ==
            ExternalUrlDisplaySettings.OPEN_IN_SAME_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SAME_WINDOW_EXTERNAL_URL"
           name="EXTERNAL_URLDivImageBrowserWindow"
            >
    <label for="OPEN_IN_SAME_WINDOW_EXTERNAL_URL">
        <international:get name="sameBrowserWindow"/>
    </label>

    <%--<div style="margin-bottom:180px;">&nbsp;</div>--%>
</div>
<%----------------------------------EXTERNAL_URLDiv----------------------------------------%>


<%----------------------------------INTERNAL_URLDiv----------------------------------------%>
<div id="INTERNAL_URLDiv"
        <%= data.getImageLinkType().equals(ImageLinkType.INTERNAL_URL) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
    <div style="margin-bottom:10px;">
        <international:get name="selectThePage"/>
    </div>
    <select id="internalPageId">
        <% for (PageManager pageManager : data.getPageManagers()) {%>
        <option value="<%= pageManager.getPage().getPageId() %>"
                <%= data.getInternalPageId() == pageManager.getPage().getPageId() ? "selected" : "" %>>
            <%= pageManager.getName() %>
        </option>
        <% } %>
    </select>

    <br/>
    <br/>
    <input type="radio"
        <%= data.getInternalPageDisplaySettings() ==
            InternalPageDisplaySettings.OPEN_IN_NEW_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_NEW_WINDOW_INTERNAL_URL"
           name="INTERNAL_URLDivImageBrowserWindow"
            >
    <label for="OPEN_IN_NEW_WINDOW_INTERNAL_URL">
        <international:get name="newBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;"
        <%= data.getInternalPageDisplaySettings() ==
            InternalPageDisplaySettings.OPEN_IN_SAME_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SAME_WINDOW_INTERNAL_URL"
           name="INTERNAL_URLDivImageBrowserWindow"
            >
    <label for="OPEN_IN_SAME_WINDOW_INTERNAL_URL">
        <international:get name="sameBrowserWindow"/>
    </label>

    <%--    <div style="margin-bottom:180px;">&nbsp;</div>--%>
</div>
<%----------------------------------INTERNAL_URLDiv----------------------------------------%>


<%----------------------------------MEDIA_FILEDiv----------------------------------------%>
<div id="MEDIA_FILEDiv"
        <%= data.getImageLinkType().equals(ImageLinkType.MEDIA_FILE) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
<br/>
    <span>
        <international:get name="selectTheTypeOfFile"/>:
    </span>
<br/>
<select id="imageFileType"
        onchange="showImageFileUploader('<%= data.getSiteId() %>', this.value); fileTypeChanged(this.value);"
        style="width:200px; margin-top:5px">
    <option value="PDF" id="PDF"
            <%= data.getImageFileType().equals(ImageFileType.PDF) ? "selected" : "" %>
            >
        <international:get name="pdf"/>
    </option>
    <option value="IMAGE" id="IMAGE"
            <%= data.getImageFileType().equals(ImageFileType.IMAGE) ? "selected" : "" %>
            >
        <international:get name="image"/>
    </option>
    <option value="FLASH" id="FLASH"
            <%= data.getImageFileType().equals(ImageFileType.FLASH) ? "selected" : "" %>
            >
        <international:get name="flashFile"/>
    </option>
    <option value="AUDIO" id="AUDIO"
            <%= data.getImageFileType().equals(ImageFileType.AUDIO) ? "selected" : "" %>
            >
        <international:get name="audioFile"/>
    </option>
    <option value="CAD" id="CAD"
            <%= data.getImageFileType().equals(ImageFileType.CAD) ? "selected" : "" %>
            >
        <international:get name="cadFile"/>
    </option>
</select>
<br/>
<br/>

<span style="margin-right:200px;">
    <international:get name="selectFromTheFollowingListOfFiles"/>
</span>
<international:get name="orUploadANewFile"/>
<br/>

<span id="uploadedImageFilesSelect">
    <jsp:include page="uploadedImageFilesSelect.jsp" flush="true"/>
</span>

<div class="uploadImageFile">
    <input type="button" value="<international:get name="browseAndUpload"/>" id="browseAndUploadImageFileButton" class="but_w170_misc">
    <span id="imageFileButtonContainer" style="margin-right:7px;position:relative;top:-25px;left:0;cursor: pointer;"
          onmouseout="$('#browseAndUploadImageFileButton')[0].className='but_w170_misc';"
          onmouseover="$('#browseAndUploadImageFileButton')[0].className='but_w170_Over_misc';">
        <span id="imageFileButtonPlaceHolder">

        </span>
    </span>
</div>
<br clear="all"/>

<%----------------------------------MEDIA_FILEDiv; pdfRadioDiv----------------------------------------%>
<div id="pdfRadioDiv"
        <%= data.getImageFileType().equals(ImageFileType.PDF) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
    <br/>
    <input type="radio"
        <%= data.getImagePdfDisplaySettings() ==
            ImagePdfDisplaySettings.OPEN_IN_NEW_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_NEW_WINDOW_PDF"
           name="pdfMediaFileDivImageBrowserWindow"
            >
    <label for="OPEN_IN_NEW_WINDOW_PDF">
        <international:get name="displayingThePDFInNewBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;"
        <%= data.getImagePdfDisplaySettings() ==
            ImagePdfDisplaySettings.OPEN_IN_SAME_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SAME_WINDOW_PDF"
           name="pdfMediaFileDivImageBrowserWindow"
            >
    <label for="OPEN_IN_SAME_WINDOW_PDF">
        <international:get name="displayingThePDFInSameBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;"
        <%= data.getImagePdfDisplaySettings() ==
            ImagePdfDisplaySettings.PROMPT_DOWNLOAD ? "checked=\"checked\"" : "" %>
           id="PROMPT_DOWNLOAD_PDF"
           name="pdfMediaFileDivImageBrowserWindow"
            >
    <label for="PROMPT_DOWNLOAD_PDF">
        <international:get name="downloadThePDF"/>
    </label>

    <%--    <div style="margin-bottom:65px;">&nbsp;</div>--%>
</div>
<%----------------------------------MEDIA_FILEDiv; pdfRadioDiv----------------------------------------%>

<%----------------------------------MEDIA_FILEDiv; audioRadioDiv----------------------------------------%>
<div id="audioRadioDiv"
        <%= data.getImageFileType().equals(ImageFileType.AUDIO) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
    <br/>
    <input type="radio"
        <%= data.getImageAudioDisplaySettings() ==
            ImageAudioDisplaySettings.PLAY_IN_CURRENT_WINDOW ? "checked=\"checked\"" : "" %>
           id="PLAY_IN_CURRENT_WINDOW"
           name="audioMediaFileDivImageBrowserWindow"
            >
    <label for="PLAY_IN_CURRENT_WINDOW">
        <international:get name="playsAudioFileWithoutOpeningAnotherPage"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;"
        <%= data.getImageAudioDisplaySettings() ==
            ImageAudioDisplaySettings.PLAY_IN_SMALL_WINDOW ? "checked=\"checked\"" : "" %>
           id="PLAY_IN_SMALL_WINDOW"
           name="audioMediaFileDivImageBrowserWindow"
            >
    <label for="PLAY_IN_SMALL_WINDOW">
        <international:get name="playsMediaFileInSmallWindow"/>
    </label>

    <%--    <div style="margin-bottom:90px;">&nbsp;</div>--%>
</div>
<%----------------------------------MEDIA_FILEDiv; audioRadioDiv----------------------------------------%>
<%----------------------------------MEDIA_FILEDiv; cadRadioDiv----------------------------------------%>
<div id="cadRadioDiv"
        <%= data.getImageFileType().equals(ImageFileType.CAD) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>>
    <br/>

    <div style="margin-left:15px;">
        <international:get name="clickingOnTheImagePromptsDownload"/>
    </div>
</div>
<%----------------------------------MEDIA_FILEDiv; cadRadioDiv----------------------------------------%>
<%----------------------------------MEDIA_FILEDiv; imageFlashRadioDiv----------------------------------------%>
<div id="imageFlashRadioDiv"
        <%= data.getImageFileType().equals(ImageFileType.FLASH) ||
                data.getImageFileType().equals(ImageFileType.IMAGE) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
    <br/>
    <input type="radio" onclick="disableCustomizeWindowSizeDiv(true);"
        <%= data.getImageFlashDisplaySettings() ==
            ImageFlashDisplaySettings.OPEN_IN_NEW_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_NEW_WINDOW_IMAGE_FLASH"
           name="imageFlashMediaFileDivImageBrowserWindow"
            >
    <label for="OPEN_IN_NEW_WINDOW_IMAGE_FLASH">
        <international:get name="clickingOnTheImageOpensNewBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;" onclick="disableCustomizeWindowSizeDiv(true);"
        <%= data.getImageFlashDisplaySettings() ==
            ImageFlashDisplaySettings.OPEN_IN_SAME_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SAME_WINDOW_IMAGE_FLASH"
           name="imageFlashMediaFileDivImageBrowserWindow"
            >
    <label for="OPEN_IN_SAME_WINDOW_IMAGE_FLASH">
        <international:get name="clickingOnTheImageOpensSameBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;" onclick="disableCustomizeWindowSizeDiv(false);"
        <%= data.getImageFlashDisplaySettings() ==
            ImageFlashDisplaySettings.OPEN_IN_SMALL_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SMALL_WINDOW_IMAGE_FLASH"
           name="imageFlashMediaFileDivImageBrowserWindow"
            >
    <label for="OPEN_IN_SMALL_WINDOW_IMAGE_FLASH">
        <international:get name="clickingOnTheImageOpensSmallerWindow"/>
    </label>
    <br/>
    <%-----------------MEDIA_FILEDiv; imageFlashRadioDiv; customizeWindowSizeDiv -----------------%>
    <div style="margin-top:10px;">
        <input type="checkbox" id="customizeWindowSize" style="margin:0 10px 0 20px"
               onchange="disableCustomizeWindowWidthHeight(!this.checked);"
            <%= data.getImageFlashDisplaySettings() !=
            ImageFlashDisplaySettings.OPEN_IN_SMALL_WINDOW ? "disabled" : "" %>
            <%= data.isCustomizeWindowSize() ? "checked=\"checked\"" : "" %>
                >
        <label for="customizeWindowSize">
            <international:get name="customizeWindowSize"/>
        </label>
    </div>
    <div style="margin:10px 0 0 40px;">
        <label for="customizeWindowWidth">
            <international:get name="windowWidth"/>
        </label>
        <input type="text" id="customizeWindowWidth" style="margin-right:10px;width:50px;"
               value="<%= data.getNewWindowWidth()%>"
               onKeyPress="return numbersOnly(this, event);"
               maxlength="5"
            <%= data.getImageFlashDisplaySettings() !=
            ImageFlashDisplaySettings.OPEN_IN_SMALL_WINDOW ||
            !data.isCustomizeWindowSize() ? "disabled" : "" %>
                >
        <label for="customizeWindowHeight">
            <international:get name="windowHeight"/>
        </label>
        <input type="text" id="customizeWindowHeight" style="width:50px;"
               value="<%= data.getNewWindowHeight()%>"
               onKeyPress="return numbersOnly(this, event);"
               maxlength="5"
            <%= data.getImageFlashDisplaySettings() !=
            ImageFlashDisplaySettings.OPEN_IN_SMALL_WINDOW ||
            !data.isCustomizeWindowSize() ? "disabled" : "" %>
                >
    </div>
    <%-----------------MEDIA_FILEDiv; imageFlashRadioDiv; customizeWindowSizeDiv -----------------%>
    <input type="radio" style="margin-top:10px;" onclick="disableCustomizeWindowSizeDiv(true);"
        <%= data.getImageFlashDisplaySettings() ==
            ImageFlashDisplaySettings.PROMPT_DOWNLOAD ? "checked=\"checked\"" : "" %>
           id="PROMPT_DOWNLOAD_IMAGE_FLASH"
           name="imageFlashMediaFileDivImageBrowserWindow"
            >
    <label for="PROMPT_DOWNLOAD_IMAGE_FLASH">
        <international:get name="clickingOnTheImagePromptsTheDownload"/>
    </label>

    <%--    <div style="margin-bottom:55px;">&nbsp;</div>--%>
</div>
<%----------------------------------MEDIA_FILEDiv; imageFlashRadioDiv----------------------------------------%>
</div>
<%----------------------------------MEDIA_FILEDiv----------------------------------------%>


<%----------------------------------TEXT_AREADiv----------------------------------------%>
<div id="TEXT_AREADiv"
        <%= data.getImageLinkType().equals(ImageLinkType.TEXT_AREA) ? "style=\"display:block;\"" : "style=\"display:none;\"" %>
        >
    <div id="textAreaDiv" style="width:50px;">

    </div>
    <br/>
    <input type="radio" onclick="disableCustomizeTextWindowSize(true);"
        <%= data.getTextAreaDisplaySettings() ==
            TextAreaDisplaySettings.OPEN_IN_NEW_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_NEW_WINDOW_TEXT_AREA"
           name="TEXT_AREADivImageBrowserWindow"
            >
    <label for="OPEN_IN_NEW_WINDOW_TEXT_AREA">
        <international:get name="textInANewBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;" onclick="disableCustomizeTextWindowSize(true);"
        <%= data.getTextAreaDisplaySettings() ==
            TextAreaDisplaySettings.OPEN_IN_SAME_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SAME_WINDOW_TEXT_AREA"
           name="TEXT_AREADivImageBrowserWindow"
            >
    <label for="OPEN_IN_SAME_WINDOW_TEXT_AREA">
        <international:get name="textInTheSameBrowserWindow"/>
    </label>
    <br/>
    <input type="radio" style="margin-top:10px;" onclick="disableCustomizeTextWindowSize(false);"
        <%= data.getTextAreaDisplaySettings() ==
            TextAreaDisplaySettings.OPEN_IN_SMALL_WINDOW ? "checked=\"checked\"" : "" %>
           id="OPEN_IN_SMALL_WINDOW_TEXT_AREA"
           name="TEXT_AREADivImageBrowserWindow"
            >
    <label for="OPEN_IN_SMALL_WINDOW_TEXT_AREA">
        <international:get name="textInASmallerWindow"/>
    </label>
    <%-----------------MEDIA_FILEDiv; TEXT_AREADiv; customizeTextWindowSizeDiv -----------------%>
    <div style="margin-top:10px;">
        <input type="checkbox" id="customizeTextWindowSize" style="margin:0 10px 0 20px"
               onchange="disableCustomizeTextWindowWidthHeight(!this.checked);"
            <%= data.getTextAreaDisplaySettings() != TextAreaDisplaySettings .OPEN_IN_SMALL_WINDOW ? "disabled" : "" %>
            <%= data.isCustomizeWindowSize() ? "checked=\"checked\"" : "" %>
                >
        <label for="customizeTextWindowSize">
            <international:get name="customizeWindowSize"/>
        </label>
    </div>
    <div style="margin:10px 0 0 40px;">
        <label for="customizeTextWindowWidth">
            <international:get name="windowWidth"/>
        </label>
        <input type="text" id="customizeTextWindowWidth" style="margin-right:10px;width:50px;"
               value="<%= data.getNewWindowWidth()%>"
               onKeyPress="return numbersOnly(this, event);"
               maxlength="5"
            <%= data.getTextAreaDisplaySettings() !=
            TextAreaDisplaySettings.OPEN_IN_SMALL_WINDOW ||
            !data.isCustomizeWindowSize() ? "disabled" : "" %>
                >
        <label for="customizeTextWindowHeight">
            <international:get name="windowHeight"/>
        </label>
        <input type="text" id="customizeTextWindowHeight" style="width:50px;"
               value="<%= data.getNewWindowHeight()%>"
               onKeyPress="return numbersOnly(this, event);"
               maxlength="5"
            <%= data.getTextAreaDisplaySettings() !=
            TextAreaDisplaySettings.OPEN_IN_SMALL_WINDOW ||
            !data.isCustomizeWindowSize() ? "disabled" : "" %>
                >
    </div>
    <%-----------------MEDIA_FILEDiv; TEXT_AREADiv; customizeWindowSizeDiv -----------------%>

    <%--<div style="margin-bottom:10px;">&nbsp;</div>--%>
</div>
<%----------------------------------TEXT_AREADiv----------------------------------------%>
</div>