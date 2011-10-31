<%@ page import="com.shroggle.logic.form.FormFileData" %>
<%@ page import="com.shroggle.logic.form.FormFileTypeForDeletion" %>
<%@ page import="com.shroggle.logic.form.FormFileManager" %>
<%@ page import="com.shroggle.logic.stripes.MaxPostSizeCreator" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<international:part part="formFile"/>
<%--
 @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final FormFileData formFileData = (FormFileData) request.getAttribute("formFileData"); %>

<tr>
    <% if (formFileData.isShowItemName()) { %>
    <td id="formImageTd1<%= formFileData.getId() %>"
        style='width:30%;<%= formFileData.isShowImagePreview() ? "height:100px;" : "height:25px;" %>vertical-align:middle;'>
        <%=  formFileData.getItemName()  %><%=  formFileData.isRequired() ? "&nbsp;*" : ""  %>
    </td>
    <% } %>
    <td id="formImageTd2<%= formFileData.getId() %>"
        style='width:70%;<%= formFileData.isShowImagePreview() ? "height:100px;" : "height:25px;" %>vertical-align:middle;'>
        <table width="100%">

            <tr id="previewImageTR<%= formFileData.getId() %>"
                style='<%= formFileData.isFileExist() ? "" : "display:none" %>;'>
                <%--------------------------------------------Preview Image-------------------------------------------%>
                <td width="1%" style="vertical-align:middle;">
                    <% if (formFileData.isShowImagePreview()) { %>
                    <a href="<%= formFileData.getImageFullSizeUrl() %>" target="_blank">
                        <img width="100px" border="0"
                             src="<%= formFileData.getImagePreviewUrl() %>"
                             alt="<%= formFileData.getKeywords() %>"
                             title="<%= formFileData.getKeywords() %>"
                             onload="try{getActiveWindow().resize();}catch(ex){}">
                    </a>
                    <% } %>
                </td>
                <%--------------------------------------------Preview Image-------------------------------------------%>
                <%-------------------------file name, remove existing file button-------------------------------------%>
                <td width="90%" height="100%" style="vertical-align:middle;">
                    <%---------------------------------------preview video link---------------------------------------%>
                    <% final boolean showVideoLink = formFileData.getFormFileTypeForDeletion() == FormFileTypeForDeletion.VIDEO &&
                            formFileData.isFileExist(); %>
                    <% if (showVideoLink) { %>
                    <input type="hidden" id="widgetVideoUrl<%= formFileData.getId() %>"
                           value="<%= formFileData.getFlvVideoUrl() %>"/>
                    <input type="hidden" id="widgetVideoImageUrl<%= formFileData.getVideoImageUrlId() %>"
                           value="<%= formFileData.getVideoImageUrl() %>"/>
                    <% } %>
                    <span id="<%= formFileData.getId() %>videoLinkDiv" title="<international:get name="clickToPlay"/>"
                          style="<% if (!showVideoLink) { %>display:none;<% } %>margin-left:5px;">
                        <a href="javascript:createVideo({videoFlvId: <%= formFileData.getFlvVideoId() %>, videoUrlId : 'widgetVideoUrl<%= formFileData.getId() %>', imageUrlId : 'widgetVideoImageUrl<%= formFileData.getVideoImageUrlId() %>',
                                                width: '<%= formFileData.getSourceVideoWidth() %>', height: '<%= formFileData.getSourceVideoHeight() %>',videoPlayerDivId:'userVideo<%= formFileData.getId() %>',videoPlayerId: 'userVideo<%= formFileData.getId() %>Player',
                        videoStatus:'<%= formFileData.getVideoStatus() %>'});"><%= formFileData.getFileName() %>
                        </a>
                        &nbsp;&nbsp;
                    </span>
                    <%---------------------------------------preview video link---------------------------------------%>
                    <span id="<%= formFileData.getId() %>videoNameDiv"
                          style="<% if (showVideoLink) { %>display:none;<% } %>width:85%;margin-left:5px;">
                        <input type="text" disabled maxlength="255"
                               id="<%= formFileData.getId() %>PreviewImageTxtFileName"
                               value="<%= formFileData.getFileName() %>"
                               class="txt"
                               style="background-color: #EBEBE4;"/>
                    </span>
                    <img id="<%= formFileData.getId() %>RemoveFormFileButton"
                         src="/images/cross-circle.png"
                         alt="Delete"
                         style='cursor:pointer; display:<%= formFileData.isFileExist() ? "inline" : "none" %>;'
                         onclick="
                            removePreviewImage('<%= formFileData.getId() %>', '<%= formFileData.getFilledFormId() %>');
                            removeFormFile(
                             <%= formFileData.getFilledFormId() %>,
                             <%= formFileData.getFilledFormItemId() %>,
                            '<%= formFileData.getId() %>',
                            '<%= FormFileManager.getFileTypesDescriptionByFormItemName(formFileData.getFormItemName()) %>',
                            '<%= formFileData.getWidgetId() %>',
                            '<%= formFileData.getPosition() %>',
                            '<%= formFileData.getItemName() %>',
                            '<%= formFileData.getFormItemName().toString() %>',
                            '<%= formFileData.getFormFileTypeForDeletion().toString() %>',
                            '<%= MaxPostSizeCreator.createMaxPostSizeString() %>',
                            '<%= formFileData.getBulkUploaderId() %>');">
                    <span id="<%= formFileData.getId() %>downloadVideoLink"
                          style="<% if (!showVideoLink) { %>display:none;<% } %>">
                        <a href="<%= formFileData.getSourceVideoUrl() %>"><international:get name="download"/></a>
                    </span>
                </td>
                <%-------------------------file name, remove existing file button-------------------------------------%>
            </tr>

            <%--------------------------------------------file upload field-------------------------------------------%>
            <tr id="withoutImageTR<%= formFileData.getId() %>"
                style='<%= !formFileData.isFileExist() ? "" : "display:none" %>;'>
                <td width="100%" height="100%" style="vertical-align:middle;">
                    <jsp:include page="formFileUploadField.jsp" flush="true"/>
                </td>
            </tr>
            <%--------------------------------------------file upload field-------------------------------------------%>
        </table>
    </td>
</tr>
<%------------------------------------------------image keywords field------------------------------------------------%>
<% if (formFileData.showKeywordsField()) { %>
<tr>
    <td>
        <international:get name="keywordsAltText"/>
    </td>
    <td>
        <input type="text" class="formTextInput" id="<%= formFileData.getId() %>imageKeywords" value="<%= formFileData.getKeywords() %>"/>
    </td>
</tr>
<% } %>
<%------------------------------------------------image keywords field------------------------------------------------%>