<%@ page import="com.shroggle.logic.image.ImageManager" %>
<%@ page import="com.shroggle.presentation.image.ConfigureImageData" %>
<%@ page import="com.shroggle.presentation.image.ConfigureImageService" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureImageWidget"/>
<%
    ConfigureImageService service = (ConfigureImageService) request.getAttribute("imageService");
    final ConfigureImageData data = service.getConfigureImageData();
%>
<div class="itemSettingsWindowDiv">
    <h1 id="primaryImageHeader"><international:get name="editModulePrimaryImage"/></h1>
    <h1 id="rollOverImageHeader" style="display:none;"><international:get name="editModuleRollOverImage"/></h1>
    <h1 id="labelsLinksImageHeader" style="display:none;"><international:get name="editModuleLabelsAndLinks"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="imageService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="main_content">
        <input type="hidden" value="<%= service.getImageItem().getId() %>" id="selectedImageItemId">
        <input type="hidden" value="<%= service.getImageItem().getTextArea() %>" id="selectedImageItemTextArea">
        <input type="hidden" id="selectedImageId"
               value="<%= ImageManager.imageFileExist(data.getImageId()) ? data.getImageId() : "undefined" %>">
        <input type="hidden" id="selectedRollOverImageId"
               value="<%= ImageManager.imageFileExist(data.getRollOverImageId()) ? data.getRollOverImageId() : "undefined" %>">
        <input type="hidden" id="selectedImageUrl" value="<%= data.getImageUrl() %>">
        <input type="hidden" id="selectedRollOverImageUrl" value="<%= data.getRollOverImageUrl() %>">
        <input type="hidden" id="selectedImageWidth"
               value="<%= data.getWidth() != null ? data.getWidth() : "" %>">
        <input type="hidden" id="selectedImageHeight"
               value="<%= data.getHeight() != null ? data.getHeight() : "" %>">
        <input type="hidden" value="<%= data.getSiteId() %>" id="siteId">
        <input type="hidden" value="<%= data.getImageFileType() %>" id="imageFileType">
        <input type="hidden" value="<%= data.getWidgetId() %>" id="widgetId">
        <input type="hidden" value="<international:get name="emptyFile"/>" id="emptyFile">
        <input type="hidden" value="<international:get name="deleteImageConfirmation"/>"
               id="deleteImageConfirmation">
        <input type="hidden" value="<international:get name="pleaseEnterValidWidthHeightNumbers"/>" id="pleaseEnterValidWidthHeightNumbers">
        <input type="hidden" value="<international:get name="pleaseSelectPrimaryImage"/>" id="pleaseSelectPrimaryImage">
        <input type="hidden" value="<international:get name="pleaseSelectImage"/>" id="pleaseSelectImage">
        <input type="hidden" value="<international:get name="defineCustomWindowSize"/>" id="defineCustomWindowSize">
        <input type="hidden" value="<international:get name="selectLinkTarget"/>" id="selectLinkTarget">

        <div class="emptyError" id="imageErrors"></div>

        <div class="readOnlyWarning" style="display:none;" id="imageReadOnlyMessage">You have only read-only
            access to this module.</div>

        <div id="primaryImageTabContent" class="tabContent">
            <jsp:include page="configureImagePrimaryRollOver.jsp" flush="true"/>
        </div>
        <div id="labelsLinksImageTabContent" class="tabContent" style="display:none;">
            <jsp:include page="configureImageLabelsLinks.jsp" flush="true"/>
        </div>
        <br>
        <br clear="all">
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <%-- todo this button not works from manage items. Revise. --%>
   <%-- <% if (data.getWidgetId() != null) { %>
    <div class="itemSettingsButtonsDivInnerInlineL" align="right">
        <input type="button" value="Remove" onclick="resetWidgetImage(<%= data.getWidgetId() %>);"
               onmouseout="this.className='but_w100';" onmouseover="this.className='but_w100_Over';"
               class="but_w100">
    </div>
    <% } %>--%>
    <div class="itemSettingsButtonsDivInnerInlineR" align="right" id="configureImageButtons">
        <input type="button" value="<international:get name="apply"/>" onclick="configureImage.save(false);"
               id="windowApply" onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="save"/>" onclick="configureImage.save(true);"
               id="windowSave" onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDivWithConfirm();"
               id="windowCancel" onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
    </div>
</div>

<div id="iamgeWhatsThis" style="display:none;">
    <div class="windowOneColumn">
        <div>
            <international:get name="whatsThisImageInstruction"/>
        </div>
        <br clear="all"/>

        <div align="right">
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   value="Close" onclick="closeConfigureWidgetDiv();"/>
        </div>
    </div>
</div>

<div style="position:absolute;top:150px;right:30px;">
    <span style="float:right;">
        <span style="font-weight:bold;"><international:get name="currentSelected"/></span>
        <br/><br/>

        <div style="text-align:center;width:120px;overflow:auto;">
            <img height="80px"
                <% String thumbnailImageUrl = data.getImageUrl(); %>
                 <% if (thumbnailImageUrl.equals("undefined")) { %>style="visibility:hidden;"<% } %>
                 src="<%= thumbnailImageUrl %>"
                 alt=""
                 id="curentlySelectedImage"
                 title="<%= StringUtil.getEmptyOrString(data.getTitle()) %>"
                 onmouseout="setThumbnailImageSrc('image');"
                 onmouseover="setThumbnailImageSrc('rollOverImage');">
        </div>
    </span>

    <span style="float:right;padding-right:5px;">
        <div style="text-align:center;margin-top:20px;">
            <a href="javascript:deselectImage()" class="detectControlClick" id="deselectImageControl">
                <international:get name="deselect"/>
            </a>
            <br/>
            <br/>
        </div>
        <div style="text-align:center;font-style:italic;">
            <international:get name="mouseOverEffectText"/>
        </div>
    </span>
</div>