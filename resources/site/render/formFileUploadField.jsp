<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.logic.form.FormFileData" %>
<%@ page import="com.shroggle.logic.form.FormFileManager" %>
<%@ page import="com.shroggle.logic.stripes.MaxPostSizeCreator" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="formFileUploadField"/>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%--
 @author Balakirev Anatoliy
--%>

<% final FormFileData formFileData = (FormFileData) request.getAttribute("formFileData");
    final String uploaderId = formFileData.getId();
    final String bulkUploaderId = formFileData.getBulkUploaderId();
    final boolean showBulkUploader = ((request.getAttribute("hideBulkUploadButton") == null ||
            !(Boolean) request.getAttribute("hideBulkUploadButton")) &&
            (request.getAttribute("videoFileUpload") == null || !(Boolean) request.getAttribute("videoFileUpload"))) && formFileData.isImage(); %>

<input type="text" id="<%= formFileData.getId() %>TxtFileName" disabled class="formTextInput fileUploadInput"
       maxlength="255"
       value="<%= formFileData.getFileName() %>"
       style="background-color: #EBEBE4;float:left;/*width:145px;*/"/>

<%-------------------------------------------------Show uploader script-----------------------------------------------%>
<% final String showFormUploader = "showFileUploadFields(" +
        "'" + FormFileManager.getFileTypesDescriptionByFormItemName(formFileData.getFormItemName()) + "'," +
        "'" + uploaderId + "SpanButtonPlaceHolder'," +
        formFileData.getWidgetId() + "," +
        "'" + uploaderId + "'," +
        "'" + formFileData.getFormItemName().toString() + "'," +
        "'" + formFileData.getPosition() + "'," +
        "'" + formFileData.getItemName() + "'," +
        "'" + MaxPostSizeCreator.createMaxPostSizeString() + "'," +
        "'" + bulkUploaderId + "');"; %>
<%-------------------------------------------------Show uploader script-----------------------------------------------%>


<%----------------------------------------------Show bulk uploader script---------------------------------------------%>
<input type="hidden" id="bulkUploadCanBeAppliedOnlyOnce"
       value="<international:get name="bulkUploadCanBeAppliedOnlyOnce"/>"/>
<% final String showFormBulkUploaders = "showFormBulkUploaders(" +
        "'" + FormFileManager.getFileTypesDescriptionByFormItemName(formFileData.getFormItemName()) + "'," +
        formFileData.getWidgetId() + "," +
        "'" + bulkUploaderId + "'," +
        "'" + formFileData.getFormItemName().toString() + "'," +
        "'" + formFileData.getPosition() + "'," +
        "'" + formFileData.getItemName() + "'," +
        "'" + MaxPostSizeCreator.createMaxPostSizeString() + "'," +
        "'" + uploaderId + "');"; %>
<%----------------------------------------------Show bulk uploader script---------------------------------------------%>


<%--------------------------------------------Delete selected file button---------------------------------------------%>
<img id="<%= uploaderId %>RemoveSelectedFileButton" src="/images/cross-circle.png"
     alt="Delete Selected File"
     style='cursor:pointer; display:none;'
     onclick="document.getElementById('<%= uploaderId %>TxtFileName').value = '';<%= showFormUploader %>">
<%--------------------------------------------Delete selected file button---------------------------------------------%>

<%-----------------------------------------Delete selected bulk files button------------------------------------------%>
<img id="<%= bulkUploaderId %>RemoveSelectedFileButton" src="/images/cross-circle.png"
     alt="<international:get name="deleteSelectedFiles"/>" style='cursor:pointer; display:none;'
     onclick="document.getElementById('<%= uploaderId %>TxtFileName').value = '';usedBulkUploaders.setBulkUploaderUnused(<%= formFileData.getWidgetId() %>);<%= showFormBulkUploaders %>">
<%-----------------------------------------Delete selected bulk files button------------------------------------------%>

<%---------------------------------------------------Upload button----------------------------------------------------%>
<div style="float:left;width:73px;height:25px;margin-left:5px;">
    <input type="button" value="<international:get name="browse"/>"
           id="<%= uploaderId %>browseFormFileButton"
           class="but_w73_misc">
    <span id="<%= uploaderId %>SpanButtonContainer"
          style="cursor: pointer;<% if (formFileData.isFileExist()) { %>display:none;<% } %>position:relative;top:-25px;left:0;"
          onmouseout="$('#<%= uploaderId %>browseFormFileButton')[0].className='but_w73_misc';"
          onmouseover="$('#<%= uploaderId %>browseFormFileButton')[0].className='but_w73_Over_misc';">
    <span id="<%= uploaderId %>SpanButtonPlaceHolder">

    </span>
</span>
    <% if (!formFileData.isFileExist()) { %>
    <elementWithOnload:element onload="<%= showFormUploader %>"/>
    <% } %>
</div>
<%---------------------------------------------------Upload button----------------------------------------------------%>

<%------------------------------------------------Bulk Upload button--------------------------------------------------%>
<div style="float:left;width:100px;height:25px;margin-left:5px;">
    <% if (showBulkUploader) { %>
    <input type="button" value="<international:get name="bulkUpload"/>"
           onclick="alert('<international:get name="bulkUploadCanBeAppliedToOneForm"/>');"
           id="<%= bulkUploaderId %>browseFormFileButton"
           class="but_w100_misc">

<span id="<%= bulkUploaderId %>SpanButtonContainer"
      style="cursor: pointer;<% if (formFileData.isFileExist()) { %>display:none;<% } %>position:relative;top:-25px;left:0;"
      onmouseout="$('#<%= bulkUploaderId %>browseFormFileButton')[0].className='but_w100_misc';"
      onmouseover="$('#<%= bulkUploaderId %>browseFormFileButton')[0].className='but_w100_Over_misc';">
    <span id="<%= bulkUploaderId %>SpanButtonPlaceHolder">

    </span>
</span>
    <elementWithOnload:element onload="<%= showFormBulkUploaders %>"/>
    <% } %>
    <%------------------------------------------------Bulk Upload button--------------------------------------------------%>
</div>

<%---------------------------------------------------hidden fields----------------------------------------------------%>
<input type="hidden" name="fileUploaderName<%= formFileData.getWidgetId() %>"
       id="<%= uploaderId %>" value="<%= formFileData.getFormItemName().toString() %>"/>

<input type="hidden" name="fileUploaderPosition" value="<%= formFileData.getPosition() %>"
       id="<%= uploaderId %>fileUploaderPosition"/>

<input type="hidden" name="fileUploaderFormItemText" value="<%= formFileData.getItemName() %>"
       id="<%= uploaderId %>fileUploaderFormItemText"/>

<input type="hidden" name="fileUploaderItemId" value="<%= formFileData.getFormItemId() %>"
       id="<%= uploaderId %>fileUploaderItemId"/>

<input type="hidden" name="fileUploaderRequired" value="<%= formFileData.isRequired() %>"
       id="<%= uploaderId %>fileUploaderRequired"/>
<%---------------------------------------------------hidden fields----------------------------------------------------%>