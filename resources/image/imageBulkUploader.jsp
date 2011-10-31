<%@ page import="com.shroggle.presentation.image.BulkUploadImageService" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="imageBulkUploader"/>
<input type="hidden" value="<international:get name="emptyFile"/>" id="emptyFile">
<input type="hidden" id="loginedUserId"
       value="<%= ((BulkUploadImageService) request.getAttribute("service")).getLoginedUserId() %>">

<div class="windowOneColumn">
    <h1><international:get name="header"/></h1>

    <div class="inform_mark" style="margin-left:0">
        <international:get name="instructions"/>
    </div>

    <div class="emptyError" id="errors"></div>

    <div style="font-size: 13px;font-weight:bold;margin-bottom:15px;">
        <international:get name="subHeader"/>
    </div>

    <table style="width:725px;border:solid black 1px;margin:5px;">
        <tbody>
        <tr>
            <td style="padding:5px;vertical-align:top;">
                <div id="bulkUploadProgress" style="width:340px;height:160px;overflow-y:auto;overflow-x:hidden;">

                </div>
            </td>
            <td style="padding:5px;vertical-align:top;">
                <div style="width:340px;height:160px;margin-left:30px;">

                    <div id="uploadedFilesNumber"
                         style="color:green;font-weight:bold;font-size:13px;margin-top:10px;margin-bottom:25px;">
                        <span style="visibility:hidden;"> Upload files... </span>
                    </div>
                    <div style="font-size:12px;margin-bottom:20px;">
                        <international:get name="pressTheBrowseButtonInstrunction"/>
                    </div>
                    <input type="button" value="<international:get name="browseAndUpload"/>"
                           id="browseAndUploadBulkButton"
                           class="but_w170_misc">
                    <span id="imageBulkUploadButtonContainer"
                         style="margin-right:10px;position:relative;top:-25px;left:0;cursor: pointer;"
                         onmouseout="$('#browseAndUploadBulkButton')[0].className='but_w170_misc';"
                         onmouseover="$('#browseAndUploadBulkButton')[0].className='but_w170_Over_misc';">
                        <span id="imageBulkUploadButtonPlaceHolder">

                        </span>
                    </span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>


    <br/>

    <p align="right" style="margin-bottom:10px;">
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="close"/>" onclick="showFormsAfterBulkUpload();"/>
        <%--<input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDiv();"/>--%>
    </p>
</div>