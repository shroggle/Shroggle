<%@ page import="com.shroggle.presentation.video.ConfigureVideoService" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.logic.stripes.MaxPostSizeCreator" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureVideo"/>
<%
    final ConfigureVideoService service = (ConfigureVideoService) request.getAttribute("videoService");
    final DraftVideo draftVideo = service.getDraftVideo();
    final FlvVideo flvVideo = ServiceLocator.getPersistance().getFlvVideo(draftVideo.getFlvVideoId());
    boolean videoExist = draftVideo.getFlvVideoId() != null;
    String videoSmallSize = (videoExist && draftVideo.getVideoSmallSize() != null) ? draftVideo.getVideoSmallSize() : "";
    String videoLargeSize = (videoExist && draftVideo.getVideoLargeSize() != null) ? draftVideo.getVideoLargeSize() : "";
    final String[] smallOptions = {"128x72", "128x96", "160x90", "160x120", "160x128", "160x160", "200x160", "208x176",
            "220x176", "300x240", "320x180", "320x200", "320x234", "320x240", "320x320", "382x234", "384x234"};
    final String[] largeOptions = {"382x234", "384x234", "450x360", "480x234", "480x270", "480x320", "620x480", "624x352",
            "624x468", "640x200", "640x272", "640x360", "640x480", "720x480", "720x576", "800x600"};
    final VideoData selectedVideo = service.getVideos().size() > 0 && service.getVideos().get(0) != null ? service.getVideos().get(0) : new VideoData();

    final String sourceVideoName = StringUtil.getEmptyOrString(selectedVideo.getName());
    final String sourceVideoWisth = StringUtil.getEmptyOrString(selectedVideo.getWidth());
    final String sourceVideoHeight = StringUtil.getEmptyOrString(selectedVideo.getHeight());

    final String videoName = draftVideo.getName() != null ? draftVideo.getName() : sourceVideoName;
    final String videoWidth = videoExist && flvVideo.getWidth() != null && flvVideo.getWidth() > 0 ? String.valueOf(flvVideo.getWidth()) : sourceVideoWisth;
    final String videoHeight = videoExist && flvVideo.getHeight() != null && flvVideo.getHeight() > 0 ? String.valueOf(flvVideo.getHeight()) : sourceVideoHeight;
%>

<input type="hidden" id="selectedImageForVideoId"
       value="<%=  draftVideo.getImageId() != null ? draftVideo.getImageId() : -1 %>">
<input type="hidden" id="selectedImageWidth"
       value="<%= StringUtil.getEmptyOrString(service.getWidth()) %>">
<input type="hidden" id="selectedImageHeight"
       value="<%= StringUtil.getEmptyOrString(service.getHeight()) %>">
<input type="hidden" id="selectedVideoId"
       value="<%= (videoExist && service.getSelectedVideo() != null) ? service.getSelectedVideo().getVideoId() : "undefined" %>">
<input type="hidden" id="canFindSourceFileError" value="<international:get name="canFindSourceFileError"/>">
<input type="hidden" id="selectedVideoItemId" value="<%= service.getDraftVideo().getId() %>">
<input type="hidden" value="<international:get name="emptyFile"/>" id="emptyFile">
<input type="hidden" value="<international:get name="unableToSaveFile"/>" id="unableToSaveFile">
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
<input type="hidden" value="<%= service.getSiteId() %>" id="siteId"/>
<input type="hidden" value="<%= MaxPostSizeCreator.createMaxPostSizeString() %>" id="fileSizeLimit"/>

<%-- Errors texts--%>
<input type="hidden" value="<international:get name="convertingVideo"/>" id="convertingVideo"/>
<input type="hidden" value="<international:get name="selectExistingVideo"/>" id="selectExistingVideo"/>
<input type="hidden" value="<international:get name="notValidWidthNumbers"/>" id="notValidWidthNumbers"/>
<input type="hidden" value="<international:get name="notValidHeightNumbers"/>" id="notValidHeightNumbers"/>
<input type="hidden" value="<international:get name="widthMultipleBeAMultipleOf2"/>" id="widthMultipleBeAMultipleOf2"/>
<input type="hidden" value="<international:get name="heightMultipleBeAMultipleOf2"/>"
       id="heightMultipleBeAMultipleOf2"/>
<input type="hidden" value="<international:get name="errorWhileTranscodingVideo"/>" id="errorWhileTranscodingVideo"/>
<input type="hidden" value="<international:get name="widgetVideoNotFound"/>" id="widgetVideoNotFound"/>
<input type="hidden" value="<international:get name="videoNotFound"/>" id="videoNotFound"/>
<input type="hidden" value="<international:get name="pleaseEnterValidWidthHeightNumbers"/>"
       id="pleaseEnterValidWidthHeightNumbers"/>


<div class="itemSettingsWindowDiv">
<h1><international:get name="addEditVideo"/></h1>
<% if (service.getWidgetTitle() != null) { %>
<widget:title customServiceName="videoService"/>
<% } %>
<div class="windowTopLine">&nbsp;</div>

<div class="emptyError" id="videoErrors"></div>

<div class="readOnlyWarning" style="display:none;" id="videoReadOnlyMessage">You have only read-only
    access to this module.
</div>

<div>
    <%-----------------------------------------------left part of widow-----------------------------------------------%>
    <div style="width:50%;float:left;">
        <div style="margin-bottom:10px;">
            <international:get name="selectExistingVideoFile"/>:
        </div>
        <div style="margin-bottom:20px;">
            <span id="uploadedVideosSelect">
                <%@include file="uploadedVideoSelect.jsp" %>
            </span>
            <span style="margin-left:30px;">
                <international:get name="or"/>
            </span>
        </div>
        <div style="width:300px;margin-bottom:20px;">
            <label for="videoName">
                <international:get name="videoName"/>&nbsp;
            </label>
            <input type="text" id="videoName" style="width:220px;" maxlength="255"
                   value="<%= videoName %>"/>
        </div>
        <span class="inform_mark" style="margin-left:0;">
            <a href="javascript:showVideoHelpWindow('videoWhatsThisPlayerSize');">
                <international:get name="playerSize"/>
            </a>
        </span>

        <div style="margin-bottom:15px;margin-left:20px;margin-top:10px;">
            <international:get name="size"/>&nbsp;&nbsp;
            <label for="width">
                <international:get name="width"/>
            </label>
            <input type="text" id="width" value="<%= videoWidth %>"
                   style="width:30px;margin-right:10px;"
                   maxlength="4"
                   <% if (selectedVideo.isAudioFile()) { %>disabled="disabled"<% } %>
                   onkeypress="return numbersOnly(this, event);">
            <label for="height">
                <international:get name="height"/>
            </label>
            <input type="text" id="height"
                   value="<%= videoHeight %>"
                   style="width:30px"
                   maxlength="4"
                   <% if (selectedVideo.isAudioFile()) { %>disabled="disabled"<% } %>
                   onkeypress="return numbersOnly(this, event);">
        </div>

        <div style="margin-top:15px;font-weight:bold;">
            <international:get name="twoOptions"/>
        </div>

        <div style="margin-top:10px;margin-left:20px;">
            <table>
                <tr>
                    <td style="width:100px;padding-bottom:15px;">
                        <input type="checkbox" id="displaySmallOptionsCheckbox"
                               <% if (selectedVideo.isAudioFile()) { %>disabled="disabled"<% } %>
                               <% if (draftVideo.isDisplaySmallOptions() && !selectedVideo.isAudioFile()) { %>checked<% } %>
                               onclick="document.getElementById('smallOptions').disabled = !this.checked;">&nbsp;
                        <label for="displaySmallOptionsCheckbox">
                            <international:get name="small"/>
                        </label>
                    </td>
                    <td style="width:100px;padding-bottom:15px;">
                        <select id="smallOptions"
                                <% if (!draftVideo.isDisplaySmallOptions() || selectedVideo.isAudioFile()) { %>disabled<% } %>>
                            <option value="">
                                <international:get name="notApplicable"/>
                            </option>
                            <% for (String option : smallOptions) { %>
                            <option value="<%= option %>"
                                    <% if (!selectedVideo.isAudioFile() && draftVideo.isDisplaySmallOptions() && videoSmallSize.equals(option)) { %>selected<% } %>>
                                <%= option %>
                            </option>
                            <% } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" id="displayLargeOptionsCheckbox"
                               <% if (selectedVideo.isAudioFile()) { %>disabled="disabled"<% } %>
                               <% if (draftVideo.isDisplayLargeOptions() && !selectedVideo.isAudioFile()) { %>checked<% } %>
                               onclick="document.getElementById('largeOptions').disabled = !this.checked;">&nbsp;
                        <label for="displayLargeOptionsCheckbox">
                            <international:get name="large"/>
                        </label>
                    </td>
                    <td>
                        <select id="largeOptions"
                                <% if (!draftVideo.isDisplayLargeOptions() || selectedVideo.isAudioFile()) { %>disabled="disabled"<% } %>>
                            <option value="">
                                <international:get name="notApplicable"/>
                            </option>
                            <% for (String option : largeOptions) { %>
                            <option value="<%= option %>"
                                    <% if (!selectedVideo.isAudioFile() && draftVideo.isDisplayLargeOptions() && videoLargeSize.equals(option)) { %>selected<% } %>>
                                <%= option %>
                            </option>
                            <% } %>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <%-----------------------------------------------left part of widow-----------------------------------------------%>
    <%-----------------------------------------------right part of widow----------------------------------------------%>
    <div style="width:50%;float:left;">
        <div style="margin-bottom:7px;">
            <international:get name="uploadVideoFile"/>:
        </div>
        <div style="margin-bottom:15px;height:25px;">
            <input type="button" value="<international:get name="browseAndUpload"/>" id="browseAndUploadVideoButton"
                   class="but_w170_misc">
                <span id="videoButtonContainer"
                      style="margin-right:5px;position:relative;top:0;left:-170px;cursor: pointer;"
                      onmouseout="$('#browseAndUploadVideoButton')[0].className='but_w170_misc';"
                      onmouseover="$('#browseAndUploadVideoButton')[0].className='but_w170_Over_misc';">
                    <span id="videoButtonPlaceHolder">

                    </span>
                </span>
        </div>
        <span class="inform_mark" style="margin-left:0">
            <a href="javascript:showVideoHelpWindow('videoWhatsThis');">
                <international:get name="noteThatFileUploadMayBeQuiteLong"/>
            </a>
        </span>
        <%------------------------------------------------text editor---------------------------------------------------------%>
        <div>
            <div id="VideoHeader" style="display: none;">
                <%= draftVideo.getDescription() != null ? draftVideo.getDescription() : "" %>
            </div>
            <input type="hidden" id="showVideoHeader" value="<%= draftVideo.isIncludeDescription() %>">

            <div style="width:90px;margin-top:20px;"
                 onmouseover="bindTooltip({element:this, contentId:'VideoHeader'});">
                <label for="videoShowEditorLink">
                    <international:get name="description"/>
                </label>
                <a id="videoShowEditorLink"
                   href="javascript:showConfigureItemDescription({id:'Video'});">
                    <international:get name="edit"/>
                </a>
            </div>
        </div>
        <%------------------------------------------------text editor---------------------------------------------------------%>
        <div style="margin:20px 0;">
            <label for="keywords">
                <international:get name="keywords"/>
            </label>
            <input type="text" id="keywords" style="width:250px" maxlength="255"
                   value="<%= draftVideo.getKeywords() != null ? draftVideo.getKeywords() : ""%>">
        </div>
        <div style="font-weight:bold;margin-bottom:10px;">
            <international:get name="selectWhereVideoShouldPlay"/>
        </div>
        <div>
            <input type="radio" id="currentPage" name="location"
                <%= draftVideo.isPlayInCurrentPage() ? "checked='true'" : ""%>>
            <label for="currentPage">
                <international:get name="currentPage"/>
            </label>
            <br/>
            <br/>
            <input type="radio" id="newPage" name="location"
                <%= !draftVideo.isPlayInCurrentPage() ? "checked='true'" : ""%>>
            <label for="newPage">
                <international:get name="newPage"/>
            </label>
        </div>
    </div>
    <%-----------------------------------------------right part of widow----------------------------------------------%>
</div>
<br clear="all"/>

<div style="margin-top:15px; margin-bottom: 10px; font-weight:bold;">
    <international:get name="staticImage"/>
</div>
<div>
    <div id="uploadedImagesForVideo" class="videoImageSelectDiv">
        <div id="uploadedVideoImages" class="videoImageUploadedImagesDiv">
            <%@include file="uploadedVideoImages.jsp" %>
        </div>
    </div>
    <div class="videoImageUploadDiv">
        <div style="margin-bottom:20px;">
            <international:get name="selectAndUploadImageText"/>
        </div>
        <div style="margin-bottom:40px;height:25px;">
            <input type="button" value="<international:get name="browseAndUpload"/>"
                   id="browseAndUploadVideoImageButton"
                   class="but_w170_misc">
                <span id="videoImageButtonContainer"
                      style="position:relative;top:-20px;left:0;cursor: pointer;"
                      onmouseout="$('#browseAndUploadVideoImageButton')[0].className='but_w170_misc';"
                      onmouseover="$('#browseAndUploadVideoImageButton')[0].className='but_w170_Over_misc';">
                    <span id="videoImageButtonPlaceHolder">

                    </span>
                </span>
        </div>
        <div>
            <international:get name="size"/>
            <label for="widgetImageWidth">
                &nbsp;&nbsp;<international:get name="width"/>
            </label>
            <input type="text" id="widgetImageWidth"
                   class="txt_nowidth" style="width:30px" maxlength="4"
                   value="<%= service.getWidth() == null ? "" : service.getWidth() %>"
                   onblur="widthChanged(this.value);"
                   onkeypress="return numbersOnly(this, event);">
            <label for="widgetImageHeight">
                &nbsp;<international:get name="height"/>
            </label>
            <input type="text"
                   id="widgetImageHeight"
                   class="txt_nowidth" style="width:30px" maxlength="4"
                   value="<%= service.getHeight() == null ? "" : service.getHeight() %>"
                   onblur="heightChanged(this.value);"
                   onkeypress="return numbersOnly(this, event);">

            <div>
                <label for="saveProportionCheckbox">
                    <international:get name="saveProportion"/>
                </label>
                <input type="checkbox" class="videoSaveProportionsCheckbox"
                       id="saveProportionCheckbox" <% if(service.isSaveRatio()) { %> checked <% } %>
                       onclick="saveProportionCheckboxChanged(this);">
            </div>
        </div>
    </div>
</div>
<br clear="all"/>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureVideoButtons">
        <input type="button" value="Apply" onClick="configureVideo.save(false);" id="windowApply"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73"/>
        <input type="button" value="Save" onClick="configureVideo.save(true);" id="windowSave"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73"/>
        <input type="button" value="Cancel" onClick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73"/>
    </div>
</div>

<div id="videoWhatsThis" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto;padding:10px; text-align:left;">

            <international:get name="fileUploadTimeMessage"/>
            <center>
                <div style="width:270px;margin: 20px 0;font-style:italic;border: 1px solid black; padding:5px;">
                    <international:get name="fileUploadTimeMessageFormula"/>
                </div>
            </center>
            <international:get name="note"/>
            <div style="margin-top:20px;">
                <international:get name="forExample"/>
            </div>
            <center>
                <div style="width:300px;margin-top:5px;text-align:center; border:1px solid black;padding:5px;">
                    <international:get name="example1"/><br/><br/>
                    <international:get name="example2"/><br/><br/>
                    <international:get name="example3"/><br/><br/>
                    <international:get name="example4"/><br/><br/>
                    <international:get name="example5"/><br/><br/>
                    <international:get name="example6"/><br/><br/>
                    <international:get name="example7"/><br/><br/>
                    <international:get name="example8"/><br/><br/>
                    <international:get name="example9"/>
                </div>
            </center>
            <br/>
        </div>
        <p align="right" style="margin-bottom:10px;">
            <input type="button" value="Close" class="but_w73"
                   onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   onclick="closeConfigureWidgetDiv();"/>
        </p>
    </div>
</div>

<div id="videoWhatsThisPlayerSize" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto;padding:10px; text-align:left;">
            <international:get name="videoWhatsThisPlayerSizeInstruction"/>
            <br/>
        </div>
        <p align="right" style="margin-bottom:10px;">
            <input type="button" value="Close" class="but_w73"
                   onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   onclick="closeConfigureWidgetDiv();"/>
        </p>
    </div>
</div>